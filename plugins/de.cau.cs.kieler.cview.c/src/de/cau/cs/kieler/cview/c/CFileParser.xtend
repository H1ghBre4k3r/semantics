/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.cview.c

//import java.util.*
//import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
//import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.model.ILanguage
//import org.eclipse.cdt.core.parser.*
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.cdt.core.parser.FileContent
import java.util.HashMap
import org.eclipse.cdt.core.parser.ScannerInfo
import org.eclipse.cdt.core.parser.IncludeFileContentProvider
import org.eclipse.cdt.core.parser.DefaultLogService
import org.eclipse.cdt.core.model.ITranslationUnit
import org.eclipse.cdt.core.model.CoreModel
import org.eclipse.cdt.core.CCorePlugin
import org.eclipse.core.resources.IFile
import org.eclipse.core.runtime.Path
import de.cau.cs.kieler.cview.CViewPlugin

/**
 * @author cmot
 * 
 */
class CFileParser {

    def static IASTTranslationUnit parse(char[] code, IFile file, String option) throws Exception {
        var IASTTranslationUnit returnAst = null
        val location = file.rawLocation
        var backup = (option == CLanguage.OPTION_PARSE_ISOLATED)

        if (!backup) {
            try {
                // default mechanism
                val componentTU = CoreModel.^default.create(location) as ITranslationUnit
                val defaultCoreModel = CoreModel.getDefault()
                val componentProject = defaultCoreModel.CModel.getCProject(file.project.name)
                val componentIndex = CCorePlugin.indexManager.getIndex(componentProject)
                componentIndex.acquireReadLock
                returnAst = componentTU.getAST(componentIndex, ITranslationUnit.AST_SKIP_INDEXED_HEADERS)
            } catch (Exception e) {
                backup = true
            }

        }

        if (backup) {
            // backup mechanism  --- WARNING MIGHT NOT FIND INTERDEPENDENCIES BETWEEN FILES
            val fc = FileContent.create("/Path/ToResolveIncludePaths.cpp", code);
            val macroDefinitions = new HashMap<String, String>();
            val String[] includeSearchPaths = #[]
            val si = new ScannerInfo(macroDefinitions, includeSearchPaths);
            val ifcp = IncludeFileContentProvider.getEmptyFilesProvider();
            val idx = null;
            val options = ILanguage.OPTION_IS_SOURCE_UNIT;
            val log = new DefaultLogService();
            returnAst = GPPLanguage.getDefault().getASTTranslationUnit(fc, si, ifcp, idx, options, log);
            CViewPlugin.raiseWarning("No C project found for file '" + location +
                "', parsing in isolation only! Some interdependencies migh be missed.")
        }

        return returnAst
    }

}