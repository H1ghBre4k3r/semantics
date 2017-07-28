/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse Rich Client
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2009 by + Kiel University + Department of Computer Science +
 * Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL). See the file
 * epl-v10.html for the license text.
 */

package de.cau.cs.kieler.kvis.ui.svg;

import java.util.Iterator;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.SVGDocumentLoader;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.apache.batik.swing.svg.SVGUserAgent;
import org.apache.batik.util.ParsedURL;

/**
 * Modifies the regular JSVGCanvas in such a way that the EclipseDocumentLoader class gets used
 * instead of the standard DocumentLoader.
 * 
 * @author Stephan Knauer (skn) - skn[at]informatik.uni-kiel.de
 * @kieler.rating 2010-02-17 proposed yellow
 * @kieler.ignore deprecated project
 */
public final class EclipseJSVGCanvas extends JSVGCanvas {

    /**
     * Generated serialVersionID.
     */
    private static final long serialVersionUID = -3324506235308202723L;

    /**
     * This one is a single loadingStatusListener so we can keep an eye on the svg document loading
     * status.
     */
    private final SVGLoadingStatusListener loadingStatusListener = new SVGLoadingStatusListener();

    /**
     * This method needs to be private in order to create only one single instance.
     * 
     * @param userAgent
     * @param b1
     * @param b2
     */
    public EclipseJSVGCanvas(SVGUserAgent userAgent, boolean eventsEnabled, boolean selectableText) {
        super(userAgent, eventsEnabled, selectableText);
        // Add the loadingStatusListener to the single instance
        this.addSVGDocumentLoaderListener(loadingStatusListener);
    }

    /**
     * Returns the SVGLoadingStatusLister for the single svgCanvas instance. So we only need one for
     * whole xKEV.
     * 
     * @return loadingStatusListener
     */
    public SVGLoadingStatusListener getSVGLoadingStatusListener() {
        return loadingStatusListener;
    }

    /**
     * Loads the SVGDoument from the given url.
     * 
     * @param url
     *            The path and filename which should be loaded.
     */
    public void loadSVGDocument(final String url) {
        String oldURI = null;
        if (svgDocument != null) {
            oldURI = svgDocument.getURL();
        }
        final ParsedURL newURI = new ParsedURL(oldURI, url);
        String url2 = newURI.toString();
        fragmentIdentifier = newURI.getRef();

        loader = new EclipseDocumentLoader(userAgent);
        nextDocumentLoader = new SVGDocumentLoader(url2, loader);
        nextDocumentLoader.setPriority(Thread.MIN_PRIORITY);

        Iterator<?> it = svgDocumentLoaderListeners.iterator();
        while (it.hasNext()) {
            nextDocumentLoader.addSVGDocumentLoaderListener((SVGDocumentLoaderListener) it.next());
        }
        startDocumentLoader();
    }

    /**
     * Starts a loading thread.
     */
    private void startDocumentLoader() {
        documentLoader = nextDocumentLoader;
        nextDocumentLoader = null;
        documentLoader.start();
    }
}
