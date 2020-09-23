/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.kicool.ide.synthesis

import com.google.common.base.Predicate
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Iterables
import com.google.common.collect.Iterators
import com.google.common.collect.Maps
import com.google.common.collect.Multimap
import de.cau.cs.kieler.klighd.internal.util.KlighdInternalProperties
import de.cau.cs.kieler.klighd.kgraph.EMapPropertyHolder
import de.cau.cs.kieler.klighd.kgraph.KGraphElement
import de.cau.cs.kieler.klighd.kgraph.KGraphPackage
import de.cau.cs.kieler.klighd.kgraph.impl.IPropertyToObjectMapImpl
import de.cau.cs.kieler.klighd.krendering.KRendering
import de.cau.cs.kieler.klighd.util.KlighdPredicates
import java.util.Collection
import java.util.Collections
import java.util.Iterator
import java.util.Map
import org.eclipse.elk.graph.properties.IProperty
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EContentAdapter
import org.eclipse.emf.ecore.util.InternalEList
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * A specialized {@link EContentAdapter} realizing efficient source target element tracking by means
 * of {@link Map Maps}. 
 * Translated to xtend and added more methods/visibility for convenience. (ssm) 
 * 
 * @author ssm
 * @original author chsch
 */
public class SourceModelTrackingAdapterReplacement extends EContentAdapter {

    private static val Predicate<Object> CANDIDATES = KlighdPredicates.instanceOf(
            typeof(KGraphElement), typeof(KRendering), typeof(IPropertyToObjectMapImpl))
    
    private static val IProperty<Object> MODEL_ELEMENT = KlighdInternalProperties.MODEL_ELEMEMT

    private Object mapsMonitor = this
    @Accessors private Multimap<Object, EObject> sourceTargetsMap = ArrayListMultimap.create
    @Accessors private Map<EObject, Object> targetSourceMap = Maps.newHashMap


    /**
     * Returns the element in the input model that is represented by the given <code>viewElement</code>
     * in the diagram.<br>
     * <b>Note:</b> This method does not check whether <code>viewElement</code> is currently contained
     * in the view model (accessible via {@link #getViewModel()}).
     * 
     * @param viewElement
     *            the diagram element whose source element in the input (source, semantic, or
     *            business) model is requested
     * @return the element in the input model or <code>null</code> if no source element could be
     *         identified
     */
    public def Object getSourceElement(EObject viewElement) {
        if (viewElement === null) {
            return null;
        }
        
        synchronized (mapsMonitor) {
            return this.targetSourceMap.get(viewElement);
        }
    }

    /**
     * Returns the elements in the view model that represent the given <code>element</code> in the
     * diagram.<br>
     * <b>Note:</b> This method does not check whether <code>element</code> is currently contained
     * in the input model being represented (accessible via {@link #getInputModel()}).
     * 
     * @param element
     *            the object in the input (source, semantic, or business) model
     * @return a {@link Collection} of diagram elements representing the given <code>element</code>
     *         or <code>{@link Collections#emptyList()}</code> if no corresponding view model
     *         elements could be identified
     */
    public def Collection<EObject> getTargetElements(Object element) {        
        if (element === null) {
            return Collections.emptyList();
        }
        
        synchronized (mapsMonitor) {
            return this.sourceTargetsMap.get(element);
        }
    }


    /* --------------------- */
    /*   the internal part   */
    /* --------------------- */

    /**
     * {@inheritDoc}
     */
    override void setTarget(EObject newTarget) {
        basicSetTarget(newTarget)

        val Iterator<? extends Notifier> eContents = 
                if (resolve()) newTarget.eContents().iterator()
                        else (newTarget.eContents() as InternalEList<? extends Notifier>)
                                .basicIterator()

        for (val i = Iterators.filter(eContents, CANDIDATES); i.hasNext();) {
            val Notifier notifier = i.next()
            addAdapter(notifier)
        }
    }

    /**
     * {@inheritDoc}
     */
    override void addAdapter(Notifier notifier) {
        super.addAdapter(notifier)

        addTracedElement(notifier as EObject)
    }

    /**
     * {@inheritDoc}
     */
    override void removeAdapter(Notifier notifier) {
        super.removeAdapter(notifier);

        removeTracedElement(notifier as EObject)
    }

    /**
     * {@inheritDoc}
     */
    override void handleContainment(Notification notification) {

        switch (notification.getEventType()) {
        case Notification.SET: {},
        case Notification.UNSET: {},
        case Notification.ADD: {},
        case Notification.REMOVE: {
            if (CANDIDATES.apply(notification.getNewValue())
                    || CANDIDATES.apply(notification.getOldValue())) {

                val EObject newValue = notification.getNewValue as EObject
                if (newValue instanceof IPropertyToObjectMapImpl
                    && (newValue as IPropertyToObjectMapImpl).getKey() != MODEL_ELEMENT) {
                    return;
                } else {
                    super.handleContainment(notification);
                }
            }
        }
        case Notification.ADD_MANY: {
            // this case has been copied from the super implementation and augmented by the filter
            val Iterable<Notifier> newValues =
                     Iterables.filter(notification.getNewValue() as Iterable<?>,
                            CANDIDATES) as Iterable<Notifier>

            for (Notifier newOne : newValues) {
                this.addAdapter(newOne)
            }
        }
        case Notification.REMOVE_MANY: {
            // this case has been copied from the super implementation and augmented by the filter
            val Iterable<Notifier> oldValues =
                    Iterables.filter(notification.getOldValue() as Iterable<?>,
                            CANDIDATES) as Iterable<Notifier>

            for (Notifier oldOne : oldValues) {
                this.removeAdapter(oldOne)
            }
        }
        }
    }

    /**
     * {@inheritDoc}
     */
    override void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        
        switch (notification.getEventType()) {
        case Notification.SET: {},
        case Notification.ADD: {},
        case Notification.REMOVE: {},
        case Notification.UNSET: {}
        default: {
            return
        }
        }

        val int type = notification.getEventType
        val Object newValue = notification.getNewValue

        
        var EMapPropertyHolder notifier
        if (notification.getFeature() == KGraphPackage.eINSTANCE.getEMapPropertyHolder_Properties()
                && notification.getNotifier() instanceof EMapPropertyHolder) {
            notifier = notification.getNotifier as EMapPropertyHolder
        } else if (notification.getNotifier() instanceof IPropertyToObjectMapImpl) {
            notifier = (notification.getNotifier as EObject).eContainer() as EMapPropertyHolder
        } else {
            return
        }
        
        if (type == Notification.SET
                && (notification.getNotifier() as IPropertyToObjectMapImpl).getKey== MODEL_ELEMENT
                && newValue !== null) {
            addTracedElement(notifier)
            return

        } else if (type == Notification.ADD) {
            if (newValue instanceof IPropertyToObjectMapImpl
                    && (newValue as IPropertyToObjectMapImpl).getKey == MODEL_ELEMENT) {
                addTracedElement(notifier)
                return
            }

        } else if (type == Notification.REMOVE) {
            val Object oldValue = notification.getOldValue
            if (oldValue instanceof IPropertyToObjectMapImpl
                    && (oldValue as IPropertyToObjectMapImpl).getKey == MODEL_ELEMENT) {
                removeTracedElement(notifier)
                return;
            }
        }
    }


    private def void addTracedElement(EObject element) {
        synchronized (mapsMonitor) {

            val Object sourceElement = internalGetSourceElement(element)

            if (sourceElement !== null
                    && !sourceTargetsMap.containsEntry(sourceElement, element)) {
                // since during the additions of KGraphElements this method is called for
                //  their layout data as well, so entries might get duplicated
                // therefore, we here filter that explicitly out
                //  alternatively we might employ a HashMultiMap
                this.sourceTargetsMap.put(sourceElement, element)
                this.targetSourceMap.put(element, sourceElement)
            }
        }
    }

    private def Object internalGetSourceElement(EObject viewElement) {
        var Object model = null;
        if (KGraphPackage.eINSTANCE.getEMapPropertyHolder().isInstance(viewElement)) {
            model = (viewElement as EMapPropertyHolder).getProperty(MODEL_ELEMENT)
        }
        
        return model;
    }
    
    private def void removeTracedElement(EObject element) {
        synchronized (mapsMonitor) {
            val Object o = this.targetSourceMap.remove(element);
            this.sourceTargetsMap.remove(o, element);
        }
    }
}
