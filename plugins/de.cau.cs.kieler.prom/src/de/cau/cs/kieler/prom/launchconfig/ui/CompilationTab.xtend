/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.prom.launchconfig.ui

import de.cau.cs.kieler.kico.internal.Transformation
import de.cau.cs.kieler.prom.common.FileCompilationData
import de.cau.cs.kieler.prom.common.KiCoLaunchData
import de.cau.cs.kieler.prom.common.ui.IProjectHolder
import de.cau.cs.kieler.prom.common.ui.UIUtil
import de.cau.cs.kieler.prom.launchconfig.LaunchConfiguration
import java.io.File
import java.util.ArrayList
import java.util.EnumSet
import java.util.List
import java.util.Set
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.LabelProvider
import org.eclipse.jface.viewers.ListViewer
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.swt.SWT
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.ModifyListener
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Text
import org.eclipse.ui.dialogs.ResourceSelectionDialog

/**
 * The tab with the controls to set files to be compiled via KiCo.
 * 
 * @author aas
 */
class CompilationTab extends AbstractKiCoLaunchConfigurationTab implements IProjectHolder {
    
    /**
     * The currently selected data of the list control.
     */
    private var FileCompilationData currentData

    /**
     * The list control for the file data objects.
     */
    private var ListViewer list

    /**
     * The button which
     * opens a resource selection dialog and adds all selected files to the list.
     */
    private var Button addButton

    /**
     * The button which
     * removes the current selection from the list.
     */
    private var Button removeButton

    /**
     * The control to select the target transformation (e.g. Java Code or C Code).
     */
    private var ComboViewer targetLanguage

    /**
     * The control to select the target language file extension (e.g. '.java' for Java).
     */
    private var Text targetLanguageFileExtension

    /**
     * The input field for the file used as template for the compilation output.
     */
    private var Text targetTemplate

    /**
     * The radio button for the default target directory.
     */
    private var Button targetDirectoryKielerGen

    /**
     * The radio button to specify
     * that output files should be saved to the same directory as input files.
     */
    private var Button targetDirectorySameAsInput
    
    /**
     * The input field for the file used as wrapper code template.
     * The wrapper code will be inserted to this file template.
     */
    private var Text wrapperCodeTemplate

    /**
     * The input field for the directory with the wrapper code snippets.
     */
    private var Text wrapperCodeSnippets

    /**
     * The project of this launch configuration.
     */
    private var IProject project

    /**
     * Constructor
     */
    new(KiCoLaunchConfigurationTabGroup tabGroup) {
        super(tabGroup)
    }


    /**
     * {@inheritDoc}
     */
    override createControl(Composite parent) {
        var Composite comp = new Composite(parent, SWT.NONE)
        setControl(comp)

        comp.setLayout(new GridLayout(1, true))
        comp.setFont(parent.getFont())

        createFilesComponent(comp)
        createTargetComponent(comp)
        createWrapperCodeComponent(comp)
    }

    /**
     * Creates a new group with a list control and add and remove buttons.
     * The add button opens a resource selection dialog such that the user can add
     * files to the list.
     * The remove button removes the currently selected item from the list.
     * 
     * @param parent The parent composite 
     */
    private def void createFilesComponent(Composite parent) {
        val group = UIUtil.createGroup(parent, "Files", 2)

        // Create list for files
        list = new ListViewer(group, SWT.DEFAULT)
        list.getControl().setLayoutData(new GridData(GridData.FILL_BOTH))

        // Create content provider
        list.setContentProvider(ArrayContentProvider.instance);
        list.input = new ArrayList<FileCompilationData>()

        // Create label provider
        list.setLabelProvider(new LabelProvider() {
            override String getText(Object element) {
                val data = (element as FileCompilationData)
                if (data != null)
                    return data.projectRelativePath
                else
                    return ""
            }
        });

        // Create selection event
        list.addSelectionChangedListener(new ISelectionChangedListener() {
            override void selectionChanged(SelectionChangedEvent event) {
                val selection = event.selection as IStructuredSelection
                currentData = selection.firstElement as FileCompilationData
                updateEnabled()
            }
        });

        // Create buttons
        val bcomp = UIUtil.createComposite(group, 1, GridData.HORIZONTAL_ALIGN_END)
        
        val simpleSelectionListener = new SelectionAdapter() {
            override widgetSelected(SelectionEvent e) {
                updateLaunchConfigurationDialog()
            }
        }

        // Create add Button
        addButton = createPushButton(bcomp, "Add...", null)
        addButton.addSelectionListener(
            new SelectionAdapter() {
                override void widgetSelected(SelectionEvent e) {
                    // Create dialog.
                    val dialog = new ResourceSelectionDialog(shell, project,
                        "Select files that should be compiled via KiCo.")
                    dialog.open()

                    // Get results.
                    val results = dialog.result
                    if (results != null) {
                        val inputArray = list.input as ArrayList<FileCompilationData>

                        // Add resources to the gui list
                        for (var i = 0; i < results.length; i++) {
                            val resource = results.get(i) as IResource
                            val projectRelativePath = resource.projectRelativePath.toOSString

                            // Filter files which are already in the list.
                            var isOK = true
                            for (FileCompilationData d : inputArray) {
                                if (d.projectRelativePath == projectRelativePath)
                                    isOK = false
                            }

                            // Add if the new element is ok.
                            if (isOK)
                                inputArray.add(new FileCompilationData(projectRelativePath))
                            else
                                println("Resource '" + resource.name + "' is already in list!")
                        }
                        list.refresh()

                        updateLaunchConfigurationDialog()
                    }
                }
            }
        )

        // Create remove Button
        removeButton = UIUtil.createRemoveButton(bcomp, list)
        removeButton.addSelectionListener(simpleSelectionListener)

        // Create up Button
        val upButton = UIUtil.createUpButton(bcomp, list)
        upButton.addSelectionListener(simpleSelectionListener)

        // Create down Button
        val downButton =UIUtil.createDownButton(bcomp, list)
        downButton.addSelectionListener(simpleSelectionListener)
    }

    /**
     * Creates a group and composite with controls to specify the target language and output template.
     * 
     * @param parent The parent composite
     */
    private def void createTargetComponent(Composite parent) {
        val group = UIUtil.createGroup(parent, "Target", 1)

        // Create language control
        targetLanguage = UIUtil.createKiCoTargetsCombo(group)
        targetLanguage.addSelectionChangedListener(new ISelectionChangedListener {

            override selectionChanged(SelectionChangedEvent event) {
                updateLaunchConfigurationDialog()
            }
        })
        targetLanguage.combo.toolTipText = "Target transformation of the KIELER Compiler"

        // Create file extension control
        val comp2 = UIUtil.createComposite(group, 2)

        targetLanguageFileExtension = UIUtil.createTextField(comp2, "File extension", EnumSet.of(UIUtil.Buttons.NONE))
        targetLanguageFileExtension.addModifyListener(new ModifyListener() {
            override modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog()
            }
        })
        targetLanguageFileExtension.toolTipText = "File extension for the target language (e.g. '.java' for Java)"
        
        // Create template control
        val comp3 = UIUtil.createComposite(group, 3)
        
        targetTemplate = UIUtil.createTextField(comp3, "Output template", EnumSet.of(UIUtil.Buttons.RESOURCE_BUTTON), this)
        targetTemplate.addModifyListener(new ModifyListener() {
            override modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog()
            }
        })
        targetTemplate.toolTipText = "Template for the compiled output.\n"
            + "Use ${" + LaunchConfiguration.COMPILED_CODE_PLACEHOLDER + "} in the template file as placeholder."
            
        // Create target directory control
        val comp4 = UIUtil.createComposite(group, 1)
        
        val buttons = UIUtil.createTargetDirectoryButtons(comp4)
        for(button : buttons) {
            button.addSelectionListener(new SelectionAdapter() {
                override void widgetSelected(SelectionEvent e) {
                    updateLaunchConfigurationDialog()
                }
            })
            if(button.data == UIUtil.KiCoLaunchTargetDirectoryOptions.KIELER_GEN) {
                targetDirectoryKielerGen = button
            } else if(button.data == UIUtil.KiCoLaunchTargetDirectoryOptions.SAME_AS_INPUT) {
                targetDirectorySameAsInput = button
            }
        }
    }

    /**
     * Creates a group and composite with the input controls for wrapper code generation.
     * 
     * @param parent The parent composite 
     */
    private def void createWrapperCodeComponent(Composite parent) {
        val group = UIUtil.createGroup(parent, "Wrapper code generation", 4)

        // Create input file control
        wrapperCodeTemplate = UIUtil.createTextField(group, "Input file",
            EnumSet.of(UIUtil.Buttons.RESOURCE_BUTTON, UIUtil.Buttons.VARIABLE_BUTTON), this)
        wrapperCodeTemplate.addModifyListener(new ModifyListener() {
            override modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog()
            }
        })
        wrapperCodeTemplate.toolTipText =  "Path to a template of a file, which will contain wrapper code.\n"
            + "The path may contain placeholders such as ${" + LaunchConfiguration.MAIN_FILE_NAME_VARIABLE + "}."

        // Create control for directory with snippet definitions
        wrapperCodeSnippets = UIUtil.createTextField(group, "Snippets directory",
            EnumSet.of(UIUtil.Buttons.CONTAINER_BUTTON, UIUtil.Buttons.VARIABLE_BUTTON), this)
        wrapperCodeSnippets.addModifyListener(new ModifyListener() {
            override modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog()
            }
        })
        wrapperCodeSnippets.toolTipText = "Directory path containing wrapper code snippets"
    }

    /**
     * {@inheritDoc}
     */
    override getName() {
        return "Compilation"
    }
    
    /**
     * {@inheritDoc}
     */
    override initializeFrom(ILaunchConfiguration configuration) {
        super.initializeFrom(configuration)
        // Ignore the following changes in the UI
        doNotApplyUIChanges = true
        
        // Update project reference
        project = LaunchConfiguration.findProject(launchData.projectName)
        
        // Set files to be compiled
        list.input = launchData.files

        // Set target language
        if (targetLanguage.input != null) {
            for (transformation : targetLanguage.input as Set<Transformation>) {
                if (transformation.id == launchData.targetLanguage) {
                    targetLanguage.selection = new StructuredSelection(transformation)
                }
            }
        }

        // Set file extension
        targetLanguageFileExtension.text = launchData.targetLanguageFileExtension

        // Set target template
        targetTemplate.text = launchData.targetTemplate

        // Set target directory
        if(launchData.targetDirectory.isNullOrEmpty()) {
            targetDirectorySameAsInput.selection = true
        } else {
            targetDirectoryKielerGen.selection = true
        }

        // Set wrapper code
        wrapperCodeTemplate.text = launchData.wrapperCodeTemplate
        wrapperCodeSnippets.text = launchData.wrapperCodeSnippetDirectory
        
        // Reset current selection
        currentData = null

        updateEnabled()
        
        // Don't ignore UI changes anymore
        doNotApplyUIChanges = false
    }
    
    /**
     * {@inheritDoc}
     */
    override performApply(ILaunchConfigurationWorkingCopy configuration) {
         if(doNotApplyUIChanges) {
            return
        }
        
        // Set target language
        val selection = targetLanguage.selection as IStructuredSelection
        if (selection != null) {
            val trans = selection.firstElement as Transformation
            if (trans != null) {
                launchData.targetLanguage = trans.id
            }
        }

        // Set file extension
        launchData.targetLanguageFileExtension = targetLanguageFileExtension.text

        // Set target template.
        launchData.targetTemplate = targetTemplate.text

        // Set target directory
        if(targetDirectoryKielerGen.selection) {
            launchData.targetDirectory = LaunchConfiguration.BUILD_DIRECTORY
        } else {
            launchData.targetDirectory = ""
        }

        // Set wrapper code
        launchData.wrapperCodeTemplate = wrapperCodeTemplate.text
        launchData.wrapperCodeSnippetDirectory = wrapperCodeSnippets.text

         // Flush data to configuration
        KiCoLaunchData.saveToConfiguration(configuration, launchData)
        
        // Check the user input for consistency
        checkConsistency()
    }

    /**
     * Checks if the current input makes sense and set an error message accordingly.
     * 
     * @return true if the input is valid
     */
    private def boolean checkConsistency() {
        errorMessage = checkErrors()
        return errorMessage == null
    }
    
    /**
     * Checks the input for consistency and returns an error message accordingly.
     * 
     * @return an error message or null if there is no error
     */
    private def String checkErrors(){
        if (project != null) {
            // All files exist in this project
            for (data : list.input as List<FileCompilationData>) {
                val file = new File(project.location + File.separator + data.projectRelativePath)
                if (!file.exists)
                    return "File '" + data.projectRelativePath + "' does not exist in the specified project"
            }
        }
        
        return null
    }
    
    /**
     * Enable or disable all controls that work on the currently selected file data.
     * Enable list control iff the project is set correct.
     */
    private def void updateEnabled() {
        // Enable controls that need an existing project specified
        val List<Control> controls = #[list.list, targetLanguage.combo, targetLanguageFileExtension, targetTemplate,
            wrapperCodeSnippets, wrapperCodeTemplate]
        UIUtil.enableControlsOnSameLevel(controls, project != null)
    }

    /**
     * Implementation of IProjectHolder.
     */
    override getProject() {
        return project
    }
}