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
package de.cau.cs.kieler.language.server.registration

import com.google.inject.Inject
import com.google.inject.Singleton
import de.cau.cs.kieler.annotations.ide.highlighting.AnnotationsHighlighting
import de.cau.cs.kieler.esterel.ide.highlighting.EsterelHighlighting
import de.cau.cs.kieler.kexpressions.ide.kext.highlighting.KExtHighlighting
import de.cau.cs.kieler.lustre.ide.highlighting.LustreHighlighting
import de.cau.cs.kieler.sccharts.ide.text.highlighting.SCTXHighlighting
import de.cau.cs.kieler.scl.ide.highlighting.SCLHighlighting
import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.ide.server.ILanguageServerAccess
import org.eclipse.xtext.ide.server.ILanguageServerExtension
import org.eclipse.xtext.ide.server.concurrent.RequestManager
import com.google.inject.Injector
import de.cau.cs.kieler.annotations.xtext.IHighlighting
import com.google.inject.TypeLiteral
import de.cau.cs.kieler.language.server.LanguageRegistration

/**
 * Implements methods to extend the LSP to allow compilation
 * 
 * @author sdo
 * 
 */
 @Singleton
class RegistrationLanguageServerExtension implements ILanguageServerExtension, CommandExtension {

    protected static val LOG = Logger.getLogger(RegistrationLanguageServerExtension)
    
    @Inject @Accessors(PUBLIC_GETTER) RequestManager requestManager
    
    @Inject Injector injector

    protected extension ILanguageServerAccess languageServerAccess
    
    override initialize(ILanguageServerAccess access) {
        this.languageServerAccess = access
    }
    def ILanguageServerAccess getLanguageServerAccess() {
        return languageServerAccess
    }
    
    override getLanguages() {
        val kgtKeywords = #['absolutePos','actions','anchor','areaData','background','bevel','bold','bottom',
        'bottomRightAnchor','center','chord','clipShape','columns','custom','dash','dashOffset','dashPattern',
        'decoratorData','dot','double','doubleClick','error','flat','flexibleHeight','flexibleWidth','fontName',
        'fontSize','foreground','grid','gridData','hAlign','height','horizontalAlignment','horizontalMargin','insets',
        'invisible','italic','junction','karc','kchildArea','kcustomRendering','kedge','kellipse','kgraph','kimage',
        'klabel','knode','kpolygon','kpolyline','kport','krectangle','krendering','krenderingLibrary','kroundedPolyline',
        'kroundedRectangle','kspline','kstylesTemplate','ktext','left','lineCap','lineJoin','lineStyle','lineWidth',
        'link','middleDoubleClick','middleSingleClick','middleSingleOrMultiClick','minCellHeight','minCellWidth',
        'minimalHeight','minimalWidth','miter','modifier','none','null','open','pie','pointData','points','pos','propagate',
        'properties','reference','referencePoint','relativePos','right','rotateWithLine','rotation','round','scale',
        'selection','shadow','single','singleClick','singleOrMultiClick','size','solid','square','squiggle','styles',
        'top','topLeftAnchor','underline','vAlign','verticalAlignment','verticalMargin','width','x','xoffset','y',
        'yoffset']
        val iHighlightings = LanguageRegistration.iHighlightings
        
        val list = new ArrayList()
        for (iHighlighting : iHighlightings) {
        	list.add(new Language(iHighlighting.getId, iHighlighting.name, iHighlighting.keywords))
        }
        list.add(new Language("kgt", "KGraph", kgtKeywords))
        return requestManager.runRead[ cancelIndicator |
            list
        ]
    }
}

@Data
class Language {
    String id
    String name
    List<String> keywords
    
}