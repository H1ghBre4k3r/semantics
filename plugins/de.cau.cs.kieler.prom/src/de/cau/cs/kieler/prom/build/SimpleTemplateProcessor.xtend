/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.prom.build

import com.google.common.io.Files
import de.cau.cs.kieler.prom.PromPlugin
import de.cau.cs.kieler.prom.templates.TemplateManager

/**
 * @author aas
 *
 */
class SimpleTemplateProcessor extends TemplateProcessor {
    new() {
        super()
    }
    
    override process() {
        val templateFile = project.getFile(template.stringValue)
        val targetFile = project.getFile(target.stringValue)
        
        val name = Files.getNameWithoutExtension(templateFile.name)
        val generator = new TemplateManager(project)
        val generatedCode = generator.processTemplate(templateFile.projectRelativePath.toOSString, 
                #{TemplateManager.FILE_NAME_VARIABLE -> name} )
        
        // Save output
        val result = new FileGenerationResult
        if(!generatedCode.isNullOrEmpty) {
            PromPlugin.createResource(targetFile, generatedCode, true)
            result.addCreatedFile(targetFile)
        }
        return result
    }
}