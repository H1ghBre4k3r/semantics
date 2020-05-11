/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kicool.ide.language.server

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Singleton
import de.cau.cs.kieler.kicool.System
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.CompilationContext
import de.cau.cs.kieler.kicool.compilation.Compile
import de.cau.cs.kieler.kicool.environments.Environment
import de.cau.cs.kieler.kicool.ide.view.IdeCompilerView
import de.cau.cs.kieler.klighd.lsp.KGraphDiagramState
import de.cau.cs.kieler.klighd.lsp.KGraphLanguageServerExtension
import de.cau.cs.kieler.language.server.ILanguageClientProvider
import de.cau.cs.kieler.language.server.KeithLanguageClient
import de.cau.cs.kieler.language.server.registration.RegistrationLanguageServerExtension
import java.io.File
import java.net.URLDecoder
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Observer
import java.util.concurrent.CompletableFuture
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.ide.server.ILanguageServerAccess
import org.eclipse.xtext.ide.server.ILanguageServerExtension
import org.eclipse.xtext.ide.server.concurrent.RequestManager
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator

/**
 * Implements methods to extend the LSP to allow compilation. Moreoever, getting compilation systems and showing
 * compiled snapshot models is supported.
 * 
 * @author sdo
 * 
 */
@Singleton
class KiCoolLanguageServerExtension implements ILanguageServerExtension, CommandExtension, ILanguageClientProvider {

    protected static val LOG = Logger.getLogger(KiCoolLanguageServerExtension)

    @Inject @Accessors(PUBLIC_GETTER) RequestManager requestManager

    @Inject
    Injector injector

    @Inject
    extension KGraphLanguageServerExtension
    
    @Inject KGraphDiagramState diagramState

    extension IdeCompilerView compilerView = new IdeCompilerView
    protected extension ILanguageServerAccess languageServerAccess

    /**
     * Holds compilation snapshots for every uri, which was compiled. Send to Theia client after compilation
     */
    protected Map<String, List<List<SnapshotDescription>>> snapshotMap = new HashMap<String, List<List<SnapshotDescription>>>

    /**
     * Holds models for every snapshot of every uri, which was compiled. Used to generate diagrams if requested 
     */
    @Accessors(PUBLIC_GETTER)
    protected Map<String, List<Object>> objectMap = new HashMap<String, List<Object>>

    /**
     * Used to filter the compilation system according to the compiler preferences
     */
    var Class<?> modelClassFilter

    /**
     * The command that was compiled the last time.
     */
    @Accessors(PUBLIC_GETTER)
    protected String lastCommand
    
    /**
     * The clientId for the last compilation.
     */
    @Accessors(PUBLIC_GETTER)
    protected String lastClientId

    /**
     * The index of the snapshot currently shown in the diagram view.
     */
    protected int currentIndex

    /**
     * The uri for that was compiled the last time.
     */
    @Accessors(PUBLIC_GETTER)
    protected String lastUri

    /**
     * Indicates if the last compilation was done inplace.
     */
    protected boolean lastInplace
    
    /**
     * Compilation thread, used to invoke a compilation in an own Thread. The {@code KeithCompilationUpdater} is
     * registered as an observer and gets all event.
     */
    protected CompilationThread compilationThread
    
    /**
     * Used to retrieve the compilation systems for a model.
     * This is done in an own thread to be able to cancel this.
     * TODO get cancelling to work.
     */
    protected GetSystemsThread getSystemsThread
    
    /**
     * Observers that are added to a started compilation.
     * Currently this should only be the SimulationLanguageServerExtension which might do this.
     */
    private List<Observer> compilationObservers = newArrayList
    
    /**
     * The language client allows to send notifications or requests from the server to the client.
     * Notifications are preferred, since they allow more asynchronity.
     */
    private KeithLanguageClient client
    
    /**
     * Holds the last compilation context. This is needed to set it as the preceding compilation context.
     * For example for snapshot simulation. 
     */
    private CompilationContext currentContext
    
    /**
     * Called by the client to compile a model
     */
    override compile(String uri, String clientId, String command, boolean inplace, boolean showResultingModel, boolean snapshot) {
        try {
            var Object model
            if (snapshot) {
                if (diagramState === null || diagramState.getKGraphContext(uri) === null) {
                    client.compile(null, uri, true, 0, 1000)
                    return
                }
                model = diagramState.getKGraphContext(uri).inputModel
            } else {
                model = getModelFromUri(uri)
            }
            if (model === null) {
                client.compile(null, uri, true, 0, 1000)
                return
            }
            this.currentContext = createContextAndStartCompilationThread(model, command, inplace, this.currentContext)
            // Add listener that sends snapshot descriptions to client and updates the diagram on finish if requested.
            currentContext.addObserver(new KeithCompilationUpdater(this, currentContext, uri, clientId, command, inplace, showResultingModel))
        } catch( Exception e) {
            e.printStackTrace()
            sendError(e.toString())
        }
        return
    }

    /**
     * Called after the compilation function is done. Handles what needs to be updated when the compilation is done,
     * such as requesting a new diagram for the previously shown snapshot.
     */
    protected def didCompile(String uri, boolean sameCompilation, String clientId, CancelIndicator cancelIndicator) {
        if (sameCompilation) { // if is the same compilation the model can be retrieved via the old index
            var Object model
            if (currentIndex == -1) {
                model = getModelFromUri(uri)
            } else {
                model = this.objectMap.get(uri).get(currentIndex)
            }
            showSnapshot(uri, clientId, model, cancelIndicator, true)
        } else {
            val newIndex = this.objectMap.get(uri).size - 1
            showSnapshot(uri, clientId, this.objectMap.get(uri).get(newIndex), cancelIndicator, false)
            currentIndex = newIndex
        }
        getSystems(uri)
        return
    }

    /**
     * Starts the compilation thread with the context created by the given model, the compilation system id.
     * 
     * @param model The model to compile
     * @param systemId id of compilation system
     * @param inplace whether inplace compilation should be enabled or disabled
     * @return compilation context that was used to create the newly started compilation thread.
     */
    private def CompilationContext createContextAndStartCompilationThread(Object model, String systemId, boolean inplace,
        CompilationContext oldContext
    ) {
        val context = Compile.createCompilationContext(systemId, model)
        context.startEnvironment.setProperty(Environment.INPLACE, inplace)
        context.startEnvironment.setProperty(Environment.PRECEEDING_COMPILATION_CONTEXT, oldContext)
        compilationObservers.forEach[observer | context.addObserver(observer)]
        this.compilationThread = new CompilationThread(context)
        this.compilationThread.start()
        return context
    }

    /**
     * Display the current snapshot given by uri and index on the diagram widget given by the clientId.
     * 
     * @param uri uri of model
     * @param clientId id of diagramServer
     * @param index index of snapshot. -1 equals the original model.
     * @return completable future with index and id of showed model
     */
    override show(String uri, String clientId, int index) {
        var Object model
        if (index != -1) {
            // get snapshto model from compiled models
            model = this.objectMap.get(uri).get(index)
        } else {
            // get model specified by uri
            model = getModelFromUri(uri)
        }
        val modelToSend = model
        return requestManager.runRead [ cancelIndicator |
            currentIndex = index
            showSnapshot(uri, clientId, modelToSend, cancelIndicator, false)
        ]
    }

    /**
     * Returns compilation systems on request
     * 
     * @param uri uri of model
     * @param filter Currently unused, CS are filtered on the client.
     * @return completable future of all compilation system descriptions {@code List<SystemDescription}
     */
    override getSystems(String uri) {
        try {
            val systemDescriptions = getCompilationSystems(uri, -1, false, false)
            val snapshotSystemDescriptions = getCompilationSystems(uri, -1, false, true)
            client.sendCompilationSystems(systemDescriptions, snapshotSystemDescriptions)
        } catch (Exception e) {
            e.printStackTrace()
            sendError("Could not retrieve compilation systems" + e)
        }
    }
    
    /**
     * Returns the compilation systems for model given by uri.
     * 
     * @param uri Uri of model
     * @param index index of snapshot, -1 for original model
     * @param filterForSimulation true if only simulation cs should be returned
     * @return CS specified by the above parameters.
     */
    def getCompilationSystems(String uri, int index, boolean filterForSimulation, boolean snapshotModel) {
        var Object model
        if (snapshotModel && diagramState !== null && diagramState.getKGraphContext(uri) !== null) {
           model = diagramState.getKGraphContext(uri).inputModel
        } else if (index != -1) {
            model = this.objectMap.get(uri).get(index)
        } else {
            // get model of model specified by uri   
            model = getModelFromUri(uri)
        }
        return getCompilationSystems(model, filterForSimulation, snapshotModel)
    }
    
    /**
     * Returns the compilation systems for model given by uri.
     * 
     * @param model Model for which the CS are requested.
     * @param filterForSimulation true if only simulation cs should be returned
     * @return CS specified by the above parameters.
     */
    def getCompilationSystems(Object model, boolean filterForSimulation, boolean snapshotModel) {
        this.getSystemsThread = new GetSystemsThread([
            if (model !== null && model.class !== modelClassFilter) {
                modelClassFilter = model.class
            }
            var systems = getSystemModels(true, modelClassFilter)
            var systemDescriptions = getSystemDescription(systems, snapshotModel)
            if (filterForSimulation) {
                getSystemsThread.systemDescriptions = systemDescriptions.filter[system | system.simulation].toList
            } else {
                getSystemsThread.systemDescriptions = systemDescriptions
            }
        ])
        this.getSystemsThread.start
        this.getSystemsThread.join()
        return this.getSystemsThread.systemDescriptions
    }

    /**
     * Gets the model for resource specified by given uri as String
     * 
     * @param uri uri as String for resource to get the model from
     * @return model of specified resource
     */
    def Object getModelFromUri(String uri) {
        var fileUri = URLDecoder.decode(uri, "UTF-8");
        if (fileUri.startsWith("file://")) {
            fileUri = fileUri.substring(7)
        }
        val uriObject = URI.createFileURI(fileUri)
        val ext = uriObject.fileExtension()
        if (!RegistrationLanguageServerExtension.registeredLanguageExtensions.contains(ext)) {
            val file = new File(fileUri)
            return new CodeContainer() => [
                addProxy(file, Files.asCharSource(file, Charsets.UTF_8).read)
            ]
        }
        val resource = uriObject.xtextResourceSet.getResource(uriObject, true)

        return resource.getContents().head
    }

    /**
     * @param uri URI of file without file://
     * @return the correct XtextResourceSet for the given uri based in its file extension.
     */
    def XtextResourceSet getXtextResourceSet(@NonNull URI uri) {
        return injector.getInstance(XtextResourceSet);
    }

    /**
     * Converts systems returned from language server to system description containing the information the Theia client
     * needs to display the available compilation systems
     * 
     * @param systems list of compilation systems
     * @return list of system description usable by Theia client
     */
    def List<SystemDescription> getSystemDescription(List<System> systems, boolean snapshotModel) {
        var systemDescription = newLinkedList
        for (system : systems) {
            systemDescription.add(new SystemDescription(system.label, system.id, system.public, system.simulation, snapshotModel))
        }
        return systemDescription
    }

    override initialize(ILanguageServerAccess access) {
        this.languageServerAccess = access
    }
    
    /**
     * Called on notification to cancel the compilation.
     * Sets {@code CANCEL_COMPILATION} property on all processors.
     */
    override cancelCompilation() {
        try {
            if (compilationThread.alive) {
                this.compilationThread.terminated = true
                var context = this.compilationThread.context
                context.startEnvironment.setProperty(Environment.CANCEL_COMPILATION, true)
                for (iResult : context.processorInstancesSequence) {
                    iResult.cancelCompilation()
                }
            }
        } catch (Exception e) {
            e.printStackTrace
            sendError("An error occurred during compilation cancel. " + e)
        }
        return
    }
    
    /**
     * Called on request to cancel the get systems process.
     * TODO currently does not work correctly.
     */
    override cancelGetSystems() {
        println("Interrupt thread")
        if (getSystemsThread.alive) {
            this.getSystemsThread.interrupt()
            println(compilationThread.alive)
        }
        return requestManager.runRead[ cancelIndicator |
            true
        ]
    }
    
    override setLanguageClient(LanguageClient client) {
        this.client = client as KeithLanguageClient
    }
    
    override getLanguageClient() {
        return this.client
    }
    
    /**
     * Send list of current snapshots in snapshot map to the client.
     * @param uri uri of model file
     * @param context CompilationContext of current compilation
     * @param clientId identifier used by the client to identify the diagram widget that should be updated. Only relevant if showSnapshot is true.
     * @param command Compilation system. Only relevant of showSnapshot is true.
     * @param inplace Whether the command was invoked with inplace compilation. Only relevant if showSnapshot is true.
     * @param finished whether the compilation finished after this snapshot (can also happen because it was stopped).
     * @param showResultingModel whether the last model should be shown.
     * @param currentIndex processor index of current snapshot
     * @param maxIndex maximum number of processors
     * 
     */
    def update(String uri, CompilationContext context, String clientId, String command, boolean inplace,
        boolean finished, boolean showResultingModel, int currentIndex, int maxIndex
    ) {
        try {
            val sameCompilation = command.equals(lastCommand) && uri.equals(lastUri) && inplace === lastInplace
            var future = new CompletableFuture()
            future.complete(void)
            future.thenAccept [
                client.compile(new CompilationResults(this.snapshotMap.get(uri)), uri, finished, currentIndex, maxIndex)
            ].exceptionally [ throwable |
                LOG.error('Error while sending compilation results.', throwable)
                sendError('Error while sending compilation results.' + throwable)
                return null
            ]
            if (finished && compilationThread.terminated) {
                future.thenAccept [
                    client.cancelCompilation(true)
                ].exceptionally [ throwable |
                    LOG.error('Error while sending compilation cancel.', throwable)
                    sendError('Error while sending compilation cancel.' + throwable)
                    return null
                ]
            } else {
                if (finished) {
                    lastUri = uri
                    lastCommand = command
                    lastInplace = inplace
                    future.thenRun [
                        if (showResultingModel) {
                            didCompile(uri, sameCompilation, clientId, CancelIndicator.NullImpl)
                        }
                    ].exceptionally [ throwable |
                        LOG.error('Error while running additional compilation effects.', throwable)
                        sendError('Error while running additional compilation effects.' + throwable)
                        return null
                    ]
                }
            }
        } catch (Exception e) {
            sendError('An error occurred on new compilation snapshot.' + e)
        }
        
    }
    
    /**
     * Register observer to be included on start of new compilation.
     * 
     * @param o observer to be registered
     */
    def registerObserverOnCompilation(Observer o) {
        this.compilationObservers.add(o)
    }
    
    /**
     * Removes observer form list of processors that should be registered as compilation observers.
     * Also removes observer from compilationThread if it exists.
     * 
     * @param o observer to be removed
     */
    def removeObserverOnCompilation(Observer o) {
        this.compilationObservers.remove(o)
        if (compilationThread !== null) {
            this.compilationThread.context.deleteObserver(o)
        }
    }
    
}
