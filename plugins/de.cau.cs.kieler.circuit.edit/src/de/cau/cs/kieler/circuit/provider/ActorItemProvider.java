/**
 */
package de.cau.cs.kieler.circuit.provider;


import de.cau.cs.kieler.circuit.Actor;
import de.cau.cs.kieler.circuit.CircuitFactory;
import de.cau.cs.kieler.circuit.CircuitPackage;

import de.cau.cs.kieler.core.annotations.AnnotationsFactory;
import de.cau.cs.kieler.core.annotations.AnnotationsPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.cau.cs.kieler.circuit.Actor} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ActorItemProvider extends LinkableItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActorItemProvider(AdapterFactory adapterFactory) {
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

			addNamePropertyDescriptor(object);
			addTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_NamedObject_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_NamedObject_name_feature", "_UI_NamedObject_type"),
				 AnnotationsPackage.Literals.NAMED_OBJECT__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Actor_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Actor_type_feature", "_UI_Actor_type"),
				 CircuitPackage.Literals.ACTOR__TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
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
			childrenFeatures.add(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS);
			childrenFeatures.add(CircuitPackage.Literals.ACTOR__INNER_ACTORS);
			childrenFeatures.add(CircuitPackage.Literals.ACTOR__INNER_LINKS);
			childrenFeatures.add(CircuitPackage.Literals.ACTOR__PORTS);
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
	 * This returns Actor.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Actor"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Actor)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Actor_type") :
			getString("_UI_Actor_type") + " " + label;
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

		switch (notification.getFeatureID(Actor.class)) {
			case CircuitPackage.ACTOR__NAME:
			case CircuitPackage.ACTOR__TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case CircuitPackage.ACTOR__ANNOTATIONS:
			case CircuitPackage.ACTOR__INNER_ACTORS:
			case CircuitPackage.ACTOR__INNER_LINKS:
			case CircuitPackage.ACTOR__PORTS:
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
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createStringAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createReferenceAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createBooleanAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createIntAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createFloatAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createContainmentAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createImportAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createTypedStringAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(AnnotationsPackage.Literals.ANNOTATABLE__ANNOTATIONS,
				 AnnotationsFactory.eINSTANCE.createCommentAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(CircuitPackage.Literals.ACTOR__INNER_ACTORS,
				 CircuitFactory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(CircuitPackage.Literals.ACTOR__INNER_LINKS,
				 CircuitFactory.eINSTANCE.createLink()));

		newChildDescriptors.add
			(createChildParameter
				(CircuitPackage.Literals.ACTOR__PORTS,
				 CircuitFactory.eINSTANCE.createPort()));
	}

}