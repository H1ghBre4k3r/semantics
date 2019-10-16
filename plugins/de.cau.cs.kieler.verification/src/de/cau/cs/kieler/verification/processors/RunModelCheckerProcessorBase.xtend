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
 package de.cau.cs.kieler.verification.processors

import com.google.common.io.Files
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.Processor
import de.cau.cs.kieler.kicool.deploy.ProjectInfrastructure
import de.cau.cs.kieler.kicool.environments.Environment
import de.cau.cs.kieler.verification.VerificationProperty
import java.util.List
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.xtext.util.StringInputStream

import de.cau.cs.kieler.verification.VerificationContext

import static extension de.cau.cs.kieler.verification.codegen.CodeGeneratorExtensions.*

import static extension de.cau.cs.kieler.verification.extensions.VerificationContextExtensions.*

/**
 * @author aas
 */
abstract class RunModelCheckerProcessorBase extends Processor<CodeContainer, Object> {
    
    protected def boolean isCanceled() {
        return compilationContext.startEnvironment.getProperty(Environment.CANCEL_COMPILATION)
    }
    
    protected def void throwIfCanceled() {
        if(isCanceled) {
            throw new Exception("Canceled by user")
        }
    }
    
    protected def IFile getFileInTemporaryProject(IPath path) {
        val tmpProject = ProjectInfrastructure.getTemporaryProject()
        val file = tmpProject.getFile(path)
        return file
    }
    
    protected def IFile saveText(IPath path, String text) {
        val file = getFileInTemporaryProject(path)
        file.saveText(text)
        return file
    }
    
    protected def IFile saveText(IFile file, String text) {
        if(file.exists) {
            file.delete(true, null)
        }
        file.createDirectories
        file.create(new StringInputStream(text), true, null)
        return file
    }
    
    protected def List<String> getTimeCommand() {
        if(OsUtil.isLinux) {
            return #["/usr/bin/time", "-f", "\n\nelapsed time: %e seconds, max memory in RAM: %M KB"]    
        } else {
            #[]
        }
    }
    
    protected def VerificationContext getVerificationContext() {
        return compilationContext.asVerificationContext
    }
    
    protected def IPath getOutputFolder() {
        val folderInKielerTemp = verificationContext.verificationModelFile.fullPath.toString.replace("/","-").replace(".","-")
        return new Path(folderInKielerTemp).append("kieler-gen").append("verification")
    }
    
    protected def IPath getOutputFile(String fileName) {
        return outputFolder.append(fileName)
    }
    
    protected def IPath getOutputFile(VerificationProperty property, String fileExtension) {
        var String name = verificationContext.verificationModelFile.nameWithoutExtension
        if(!property.name.isNullOrEmpty) {
            name += ("-" + property.name.toIdentifier + "")
        }
        name += fileExtension
        return getOutputFile(name)
    }

    protected def IPath getCounterexampleFilePath(VerificationProperty property) {
        return getOutputFile(property, ".ktrace")
    }
        
    protected static def String nameWithoutExtension(IFile file) {
        return Files.getNameWithoutExtension(file.name)
    }
    
    protected static def void createDirectories(IResource res) {
        if(res.exists) {
            return
        }
        if(res instanceof IWorkspace) {
            return
        }
        res.parent?.createDirectories
        if(res instanceof IFolder) {    
            res.create(false, false, null)
        }
    }
    
    protected def Process startVerificationProcess(ProcessBuilder processBuilder) {
        val process = processBuilder.start
        verificationContext.verificationProcess = process
        return process
    }
}
