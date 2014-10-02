/**
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.provider;


import de.cau.cs.kieler.core.annotations.provider.AnnotatableItemProvider;
import de.cau.cs.kieler.core.kexpressions.KExpressionsFactory;
import de.cau.cs.kieler.scg.SCGraph;
import de.cau.cs.kieler.scg.ScgFactory;
import de.cau.cs.kieler.scg.ScgPackage;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.cau.cs.kieler.scg.SCGraph} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SCGraphItemProvider 
    extends AnnotatableItemProvider {
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SCGraphItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

        }
        return itemPropertyDescriptors;
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(ScgPackage.Literals.SC_GRAPH__NODES);
            childrenFeatures.add(ScgPackage.Literals.SC_GRAPH__DECLARATIONS);
            childrenFeatures.add(ScgPackage.Literals.SC_GRAPH__BASIC_BLOCKS);
            childrenFeatures.add(ScgPackage.Literals.SC_GRAPH__SCHEDULES);
            childrenFeatures.add(ScgPackage.Literals.SC_GRAPH__GUARDS);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns SCGraph.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/SCGraph"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_SCGraph_type");
    }
    

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(SCGraph.class)) {
            case ScgPackage.SC_GRAPH__NODES:
            case ScgPackage.SC_GRAPH__DECLARATIONS:
            case ScgPackage.SC_GRAPH__BASIC_BLOCKS:
            case ScgPackage.SC_GRAPH__SCHEDULES:
            case ScgPackage.SC_GRAPH__GUARDS:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createNode()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createConditional()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createSurface()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createDepth()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createAssignment()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createFork()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createJoin()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createEntry()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__NODES,
                 ScgFactory.eINSTANCE.createExit()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__DECLARATIONS,
                 KExpressionsFactory.eINSTANCE.createDeclaration()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__BASIC_BLOCKS,
                 ScgFactory.eINSTANCE.createBasicBlock()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__SCHEDULES,
                 ScgFactory.eINSTANCE.createSchedule()));

        newChildDescriptors.add
            (createChildParameter
                (ScgPackage.Literals.SC_GRAPH__GUARDS,
                 ScgFactory.eINSTANCE.createGuard()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return ScgEditPlugin.INSTANCE;
    }

}
