package de.cau.cs.kieler.cview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.ErrorManager;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchWindowConfigurer;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.Preferences;

import com.google.inject.Guice;

import de.cau.cs.kieler.cview.extensions.CViewLanguageExtensions;
import de.cau.cs.kieler.cview.hooks.ICViewAnalysis;
import de.cau.cs.kieler.cview.hooks.ICViewExport;
import de.cau.cs.kieler.cview.hooks.ICViewLanguage;
import de.cau.cs.kieler.cview.model.cViewModel.Component;
import de.cau.cs.kieler.cview.model.cViewModel.Connection;
import de.cau.cs.kieler.cview.ui.SelectAnalysisDialog;
import de.cau.cs.kieler.klighd.kgraph.KEdge;
import de.cau.cs.kieler.klighd.ui.DiagramViewManager;
import de.cau.cs.kieler.klighd.ui.parts.DiagramViewPart;
import org.eclipse.core.runtime.Status;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class CViewPlugin extends AbstractUIPlugin {
//    public class CViewPlugin implements AbstractUIPlugin {
//}

    static final String CONSOLE_NAME = "C View Log";
    static final String CONSOLEVIEWID = "org.eclipse.ui.console.ConsoleView";

    static public String CVIEW_PREFERENCE_ID = "de.cau.cs.kieler.cview.preferences.active";
    static public String CVIEW_PREFERENCE_ENABLED = "active";
    // static public String KLIGHD_MODEL_VIEW = "de.cau.cs.kieler.klighd.ui.view.diagram";

    /** The Constant EXTENSION_POINT_ID. */
    public static final String ANALYSIS_HOOK_EXTENSION_POINT_ID = "de.cau.cs.kieler.cview.analysis";

    /** The Constant EXTENSION_POINT_ID. */
    public static final String EXPORT_HOOK_EXTENSION_POINT_ID = "de.cau.cs.kieler.cview.export";

    /** The Constant EXTENSION_POINT_ID. */
    public static final String LANGUAGE_HOOK_EXTENSION_POINT_ID = "de.cau.cs.kieler.cview.language";

    // The plug-in ID
    public static final String PLUGIN_ID = "de.cau.cs.kieler.cview"; //$NON-NLS-1$
    
    // TODO @cmot: Options for requiring
    public static String OPTION_COMBINE_CONNECTIONS = "de.cau.cs.kieler.cview.options.combineconnections";
	public static String OPTION_HIDE_UNCONNECTED = "de.cau.cs.kieler.cview.options.hideunconnected";
	public static String OPTION_FLATTEN_HIERARCHY = "de.cau.cs.kieler.cview.options.flattenhierarchy";
	
	// TODO @cmot: Threshold for when an analysis counts as "huge"
	// TODO: In preference page!
	public static int hugeAnalysis = 200;

    static ArrayList<ICViewAnalysis> analysisHooks = null;
    static ArrayList<ICViewExport> exportHooks = null;
    static ArrayList<ICViewLanguage> languageHooks = null;

    static HashMap<String, char[]> cacheFileRaw = new HashMap<String, char[]>();
    static HashMap<String, Object> cacheFileParsed = new HashMap<String, Object>();

    // Cache if language options are enabled, disabled
    static HashMap<String, Boolean> chacheLanguageOption = new HashMap<String, Boolean>();

    // True if monitor was canceled, enforces complete rebuild
    static public boolean monitorCanceled = false;
    
    // TODO @cmot: Option for showing a warning
    static public boolean showWarning = false;
    
    // TODO @cmot: Option for manual synthesis
    static public boolean isDiagramSynthesisActive = true;
    static public boolean refreshPressed = false;

    // TODO @cmot: Option for highlighting selection in CView
    static public boolean highlightSelection = false;
    
    // TODO @cmot: Option for deactivating the visualization
    static public boolean deactivateVisualization = false;
    
    static private CViewPlugin instance = null;
    
    // -------------------------------------------------------------------------

    public CViewPlugin() {
        CViewPlugin.instance = this;
    }
    
    private static CViewPlugin getInstance() {
        return  CViewPlugin.instance;
    }

    // -------------------------------------------------------------------------

    private static void cacheReset() {
        cacheFileRaw.clear();
        cacheFileParsed.clear();
    }

    public static IFile getIFile(String fileLocation) {
        IPath componentPath = new Path(fileLocation);
        return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(componentPath);
    }

    private static char[] cacheGetFileRaw(String fileLocation) {
        if (cacheFileRaw.containsKey(fileLocation)) {
            return cacheFileRaw.get(fileLocation);
        }
        return null;
    }

    private static Object cacheGetFileParsed(String fileLocation) {
        if (cacheFileParsed.containsKey(fileLocation)) {
            return cacheFileParsed.get(fileLocation);
        }
        return null;
    }

    private static void cachePutFileRaw(String fileLocation, char[] fileRaw) {
        cacheFileRaw.put(fileLocation, fileRaw);
    }

    private static void cachePutFileParsed(String fileLocation, Object fileParsed) {
        if (fileParsed == null) {
            // clear for all options
            cacheFileParsed.forEach((key, value) -> {
                if (key.endsWith("@" + fileLocation)) {
                    cacheFileParsed.put(key, null);
                }
            });
        } else {
            cacheFileParsed.put(fileLocation, fileParsed);
        }
    }

    public static Object getFileParsed(String fileLocation, String option) {
        Object returnValue = null;
        String option2 = option;
        if (option == null) {
            option2 = "";
        }
        if (modified(fileLocation)) {
            // for ALL (used/saved) options, null these
            cachePutFileParsed(fileLocation, null);
        }
        returnValue = cacheGetFileParsed(option2 + "@" +fileLocation);
        if (returnValue == null) {
            try {
                char[] content = getFileRaw(fileLocation);
                try {
                    ICViewLanguage language = CViewLanguageExtensions.getLanguage(fileLocation);
                    
                    IFile file = CViewPlugin.getIFile(fileLocation);
                    if (file != null) {
                        returnValue = language.parseFile(content, file, option2);
                        cachePutFileParsed(option2 + "@" + fileLocation, returnValue);
                    } else {
                        CViewPlugin.raiseError("Cannot parse file '"+fileLocation+"'.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    public static char[] getFileRaw(String fileLocation) {
        char[] returnValue = null;
        if (modified(fileLocation)) {
            cachePutFileRaw(fileLocation, null);
        }
        returnValue = cacheGetFileRaw(fileLocation);
        if (returnValue == null) {
            try {
                returnValue = readFile(fileLocation);
                cachePutFileRaw(fileLocation, returnValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    static HashMap<String, Long> cacheLastModified = new HashMap<String, Long>();

    static boolean modified(String filePath) {
        return modified(filePath, true);
    }

    static boolean modified(String filePath, boolean update) {
        File file = new File(filePath);
        Long lastModified = file.lastModified();
        Long lastModifiedCached = 0l;
        if (cacheLastModified.containsKey(filePath)) {
            lastModifiedCached = cacheLastModified.get(filePath);
        }
        if (lastModifiedCached != lastModified) {
            if (update) {
                cacheLastModified.put(filePath, lastModified);
            }
            return true;
        }
        return false;
    }

    public static Charset getEncoding() {
        Charset encoding = Charset.defaultCharset();
        return encoding;
    }

    static char[] readFile(String filePath) throws IOException {
        Charset encoding = getEncoding();
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream in = new FileInputStream(filePath);
                Reader reader = new InputStreamReader(in, encoding);
                Reader buffer = new BufferedReader(reader)) {
            int r;
            while ((r = reader.read()) != -1) {
                char ch = (char) r;
                stringBuilder.append(ch);
                // System.out.print(ch);
            }
        }
        // Byte[] bytes = readBytes.toArray(new Byte[readBytes.size()]);;
        return stringBuilder.toString().toCharArray();
    }

    // -------------------------------------------------------------------------

    @Override
    public void start(BundleContext context) throws Exception {
    }

    // -------------------------------------------------------------------------

    @Override
    public void stop(BundleContext context) throws Exception {
    }

    // -------------------------------------------------------------------------

    public static void setPrefInt(String key, int value) {
        setPrefString(key, value + "");
    }

    // -------------------------------------------------------------------------

    public static void setPrefBool(String key, boolean value) {
        if (value) {
            setPrefString(key, "1");
        } else {
            setPrefString(key, "0");
        }
    }

    // -------------------------------------------------------------------------

    public static void setPrefString(String key, String value) {
        IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(CVIEW_PREFERENCE_ID);
        preferences.put(CVIEW_PREFERENCE_ID + key, value);
        try {
            preferences.flush();
        } catch (Exception e) {
        }
    }

    // -------------------------------------------------------------------------

    public static int getPrefInt(String key, int defaultValue) {
        String returnValueString = getPrefString(key, defaultValue + "");
        int returnValue = defaultValue;
        try {
            returnValue = Integer.parseInt(returnValueString);
        } catch (Exception e) {
        }
        return returnValue;
    }

    // -------------------------------------------------------------------------

    public static boolean getPrefBool(String key, boolean defaultValue) {
        String defaultString = "0";
        if (defaultValue) {
            defaultString = "1";
        }
        String returnValue = getPrefString(key, defaultString);
        return (returnValue != null && returnValue.equals("1"));
    }
    // -------------------------------------------------------------------------

    public static String getPrefString(String key, String defaultValue) {
        Preferences preferences = InstanceScope.INSTANCE.getNode(CVIEW_PREFERENCE_ID);
        return (preferences.get(CVIEW_PREFERENCE_ID + key, defaultValue));
    }

    // -------------------------------------------------------------------------

    public static boolean isEnabled(String hookId) {
        Preferences preferences = InstanceScope.INSTANCE.getNode(CVIEW_PREFERENCE_ID);
        return (preferences.get(CVIEW_PREFERENCE_ENABLED + hookId, "enabled").equals("enabled"));
    }

    // -------------------------------------------------------------------------

    public static void setEnabled(String hookId, boolean enabled) {
        chacheLanguageOption.clear();

        IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(CVIEW_PREFERENCE_ID);

        if (enabled) {
            preferences.put(CVIEW_PREFERENCE_ENABLED + hookId, "enabled");
        } else {
            preferences.put(CVIEW_PREFERENCE_ENABLED + hookId, "disabled");
        }
        try {
            preferences.flush();
        } catch (Exception e) {
        }
    }

    // -------------------------------------------------------------------------

    public static void setEnabled(boolean enabled) {
        IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(CVIEW_PREFERENCE_ID);

        if (enabled) {
            preferences.put(CVIEW_PREFERENCE_ENABLED, "enabled");
        } else {
            preferences.put(CVIEW_PREFERENCE_ENABLED, "disabled");
        }
        try {
            preferences.flush();
        } catch (Exception e) {
        }
    }

    // -------------------------------------------------------------------------

    public static boolean isEnabled() {
        Preferences preferences = InstanceScope.INSTANCE.getNode(CVIEW_PREFERENCE_ID);
        return (preferences.get(CVIEW_PREFERENCE_ENABLED, "disabled").equals("enabled"));
    }

    // -------------------------------------------------------------------------

    public static void rebuildModelAndrefreshCView(boolean forceRebuid) {
        AbstractKLighDController.controller.rebuildModelAndrefreshCView(forceRebuid);
    }

    // -------------------------------------------------------------------------

    public static void refreshCView(boolean forceRebuid) {
        AbstractKLighDController.controller.refreshCView(forceRebuid);
    }
    
    public static void deleteCView() {
    	AbstractKLighDController.controller.setNoCView();
    }
    
    // -------------------------------------------------------------------------
    
    public static void rebuildModelNoRefresh(boolean forceRebuild) {
    	AbstractKLighDController.controller.rebuildModelNoRefresh(forceRebuild);
    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    public static boolean isOptionRequired(String optionID) {
        if (!chacheLanguageOption.containsKey(optionID)) {
            List<ICViewAnalysis> hooks = getRegisteredAnalysisHooks(true);
            for (ICViewAnalysis hook : hooks) {
                if (isEnabled(hook.getId())) {
                    for (String languageOptionID : hook.requireLanguageOptions()) {
                        if (languageOptionID.equals(optionID)) {
                            chacheLanguageOption.put(optionID, true);
                            return true;
                        }
                    }
                }
            }
            chacheLanguageOption.put(optionID, false);
        }
        return chacheLanguageOption.get(optionID);
    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    public static void clearSelectionExportHooks() {
        exportHooks = null;
    }

    public static List<ICViewExport> getRegisteredExportHooks(boolean forceReload) {
        if (exportHooks != null && !forceReload) {
            return exportHooks;
        }
        if (exportHooks == null || forceReload) {
            exportHooks = new ArrayList<ICViewExport>();
        }
        // Otherwise inspect the extensions
        IConfigurationElement[] extensions = Platform.getExtensionRegistry()
                .getConfigurationElementsFor(EXPORT_HOOK_EXTENSION_POINT_ID);
        // Remember all hook ids to raise error if double ids
        HashSet<String> extensionIDs = new HashSet<String>();
        // Walk thru every extension and instantiate the declared class, then put it into the cache
        for (IConfigurationElement extension : extensions) {
            try {
                ICViewExport instance = (ICViewExport) extension.createExecutableExtension("class");
                // Handle the case that wee need Google Guice for instantiation
                instance = (ICViewExport) getGuiceInstance(instance);
                String currentId = instance.getId();
                if (extensionIDs.contains(currentId)) {
                    raiseWarning("The CView Analysis ID '"+currentId+"' is already registered by "
                            + "another component. Make sure all analysis component have unique IDs!");
                } else {
                    extensionIDs.add(currentId);
                }
                exportHooks.add(instance);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return exportHooks;
    }

    // -------------------------------------------------------------------------

    public static List<String> getAllRegisteredExportHookIds() {
        ArrayList<String> returnList = new ArrayList<String>();
        List<ICViewExport> hooks = getRegisteredExportHooks(true);
        for (ICViewExport hook : hooks) {
            returnList.add(hook.getName() + " (" + hook.getId() + ")");
        }
        return returnList;
    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    public static void clearSelectionAnalysisHooks() {
        analysisHooks = null;
    }

    // -------------------------------------------------------------------------

    public static String extractId(String item) {
        if (item != null) {
            int start = item.lastIndexOf("(");
            if (start >= 0) {
                int end = item.lastIndexOf(")");
                if (end > start) {
                    return item.substring(start + 1, end);
                }
            }
        }
        return "";
    }

    // -------------------------------------------------------------------------

    public static List<String> filterSelectedRegisteredAnalysisHookIds(List<String> inputList) {
        ArrayList<String> returnList = new ArrayList<String>();
        List<ICViewAnalysis> hooks = getRegisteredAnalysisHooks(true);
        for (ICViewAnalysis hook : hooks) {
            for (String item : inputList) {
                String hookId = extractId(item);
                if (hookId.equals(hook.getId())) {
                    if (CViewPlugin.isEnabled(hook.getId())) {
                        returnList.add(item);
                    }
                }
            }
        }
        return returnList;
    }

    // -------------------------------------------------------------------------

    public static List<String> getAllRegisteredAnalysisHookIds() {
        ArrayList<String> returnList = new ArrayList<String>();
        List<ICViewAnalysis> hooks = getRegisteredAnalysisHooks(true);
        for (ICViewAnalysis hook : hooks) {
            returnList.add(hook.getName() + " (" + hook.getId() + ")");
        }
        return returnList;
    }

    // -------------------------------------------------------------------------

    public static List<ICViewAnalysis> getRegisteredAnalysisHooks(boolean forceReload) {
        if (analysisHooks != null && !forceReload) {
            return analysisHooks;
        }
        if (analysisHooks == null || forceReload) {
            analysisHooks = new ArrayList<ICViewAnalysis>();
        }
        // Otherwise inspect the extensions
        IConfigurationElement[] extensions = Platform.getExtensionRegistry()
                .getConfigurationElementsFor(ANALYSIS_HOOK_EXTENSION_POINT_ID);
        // Remember all hook ids to raise error if double ids
        HashSet<String> extensionIDs = new HashSet<String>();
        // Walk thru every extension and instantiate the declared class, then put it into the cache
        for (IConfigurationElement extension : extensions) {
            try {
                ICViewAnalysis instance =
                        (ICViewAnalysis) extension.createExecutableExtension("class");
                String currentId = instance.getId();
                //TODO: Remove
                if (extensionIDs.contains(currentId)) {
                    raiseWarning("The CView Analysis ID '"+currentId+"' is already registered by "
                            + "another component. Make sure all analysis component have unique IDs!");
                } else {
                    extensionIDs.add(currentId);
                }
                // Handle the case that wee need Google Guice for instantiation
                instance = (ICViewAnalysis) getGuiceInstance(instance);
                int prio = instance.priority();
                int putIndex = 0;
                // TODO: Verify
                for (ICViewAnalysis otherAnalysisHook : analysisHooks) {
                    if (otherAnalysisHook.priority() > prio) {
                        break;
                    }
                    putIndex++;
                }
                analysisHooks.add(putIndex, instance);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return analysisHooks;
    }

    // -------------------------------------------------------------------------

    /**
     * Gets the guice instance.
     * 
     * @param object
     *            the object
     * @return the guice instance
     */
    public static Object getGuiceInstance(Object object) {
        Object guiceInstance = Guice.createInjector().getInstance(object.getClass());
        return guiceInstance;
    }

    // -------------------------------------------------------------------------

    public static void clearConosle() {
        MessageConsole console = findConsole(CONSOLE_NAME);
        console.clearConsole();
        bringToFront();
    }

    // -------------------------------------------------------------------------

    public static void printlnConsole(String text) {
        MessageConsole console = findConsole(CONSOLE_NAME);
        MessageConsoleStream consoleStream = console.newMessageStream();
        consoleStream.println(text);
    }

    // -------------------------------------------------------------------------

    public static void printConsole(String text) {
        MessageConsole console = findConsole(CONSOLE_NAME);
        MessageConsoleStream consoleStream = console.newMessageStream();
        consoleStream.print(text);
    }

    // -------------------------------------------------------------------------

    private static MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++) {
            if (name.equals(existing[i].getName()))
                return (MessageConsole) existing[i];
        }
        // no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, null);
        // conMan.addConsoles(new IConsole[] { myConsole });
        IConsole[] consoles = { myConsole };
        conMan.addConsoles(consoles);
        return myConsole;
    }

    // -------------------------------------------------------------------------

    public static void setTitle(String title) {
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    DiagramViewPart view =
                            DiagramViewManager.getView(AbstractKLighDController.CVIEW_KLIGHD_ID);
                    view.setName(title);
                } catch (Exception e) {
                    // ignore if we cannot bring it to front
                }
            }
        });
    }

    // -------------------------------------------------------------------------

    private static void bringToFront() {
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    IViewPart vP = window.getActivePage().showView(CONSOLEVIEWID);
                    vP.setFocus();
                    // set done flag
                } catch (Exception e) {
                    // ignore if we cannot bring it to front
                }
            }
        });
    }

    // -------------------------------------------------------------------------

    public static void openMessageDialog(String title, String text, boolean error,
            boolean warning) {
        int type = SWT.ICON_INFORMATION;
        if (warning) {
            type = SWT.ICON_WARNING;
        }
        if (error) {
            type = SWT.ICON_ERROR;
        }
        final int type2 = type;

        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageBox dialog =
                            new MessageBox(Display.getCurrent().getShells()[0], type2 | SWT.OK);
                    dialog.setText(title);
                    dialog.setMessage(text);
                    dialog.open();
                } catch (Exception e) {
                    // ignore if we cannot bring it to front
                }
            }
        });

    }

    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    static HashMap<String, ICViewLanguage> languageHookChache = null;
    static ICViewLanguage defaultLanguage = new DefaultLanguage();

    public static ICViewLanguage getLanguageHook(Component component) {
        if (languageHookChache == null) {
            languageHookChache = new HashMap<String, ICViewLanguage>();
            for (ICViewLanguage language : getRegisteredLanguageHooks(false)) {
                Set<String> handledTypes = language.diagramHandleComponentCustomTypes();
                if (handledTypes != null) {
                    for (String handledType : handledTypes) {
                        if (languageHookChache.containsKey(handledType)) {
                            ICViewLanguage otherLanguage = languageHookChache.get(handledType);
                            // Error, already a language
                            raiseError("ERROR: Already a language (" + otherLanguage.getId()
                                    + ") that handles the following component custom ID: "
                                    + handledType + ". Ignoring " + language.getId());
                        } else {
                            // Put this hook here
                            languageHookChache.put(handledType, language);
                        }
                    }
                }
            }
        }
        String customTypeID = component.getCustomTypeID();
        if (languageHookChache.containsKey(customTypeID)) {
            return languageHookChache.get(customTypeID);
        } else {
            return defaultLanguage;
        }
    }

    public static List<ICViewLanguage> getRegisteredLanguageHooks(boolean forceReload) {
        if (languageHooks != null && !forceReload) {
            return languageHooks;
        }
        if (languageHooks == null || forceReload) {
            languageHooks = new ArrayList<ICViewLanguage>();
        }
        // Otherwise inspect the extensions
        IConfigurationElement[] extensions = Platform.getExtensionRegistry()
                .getConfigurationElementsFor(LANGUAGE_HOOK_EXTENSION_POINT_ID);
        // Remember all hook ids to raise error if double ids
        HashSet<String> extensionIDs = new HashSet<String>();
        // Walk thru every extension and instantiate the declared class, then put it into the cache
        for (IConfigurationElement extension : extensions) {
            try {
                ICViewLanguage instance =
                        (ICViewLanguage) extension.createExecutableExtension("class");
                // Handle the case that wee need Google Guice for instantiation
                instance = (ICViewLanguage) getGuiceInstance(instance);
                String currentId = instance.getId();
                if (extensionIDs.contains(currentId)) {
                    raiseWarning("The CView Analysis ID '"+currentId+"' is already registered by "
                            + "another component. Make sure all analysis component have unique IDs!");
                } else {
                    extensionIDs.add(currentId);
                }
                languageHooks.add(instance);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return languageHooks;
    }

    // -------------------------------------------------------------------------
    
//    if (monitor.canceled) {
//        return null;
//    }
    
    public static void raiseError(String text) {
        Status status = new Status(IStatus.ERROR, CViewPlugin.PLUGIN_ID, text);
        CViewPlugin.getInstance().getLog().log(status);
        printlnConsole("ERROR: " + text);
    }
    
    public static void raiseWarning(String text) {
        Status status = new Status(IStatus.WARNING, CViewPlugin.PLUGIN_ID, text);
        CViewPlugin.getInstance().getLog().log(status);
        printlnConsole("WARNING: " + text);
    }
    
    
}
