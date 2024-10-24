/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Base FB Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getInternalVars <em>Internal
 * Vars</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getInternalFbs <em>Internal
 * Fbs</em>}</li>
 * </ul>
 *
 * @generated */
public class BaseFBTypeImpl extends FBTypeImpl implements BaseFBType {
	/** The cached value of the '{@link #getInternalVars() <em>Internal Vars</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInternalVars()
	 * @generated
	 * @ordered */
	protected EList<VarDeclaration> internalVars;

	/** The cached value of the '{@link #getInternalFbs() <em>Internal Fbs</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInternalFbs()
	 * @generated
	 * @ordered */
	protected EList<FB> internalFbs;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected BaseFBTypeImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.BASE_FB_TYPE;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EList<VarDeclaration> getInternalVars() {
		if (internalVars == null) {
			internalVars = new EObjectContainmentEList<VarDeclaration>(VarDeclaration.class, this,
					LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS);
		}
		return internalVars;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EList<FB> getInternalFbs() {
		if (internalFbs == null) {
			internalFbs = new EObjectContainmentEList<FB>(FB.class, this,
					LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS);
		}
		return internalFbs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
			return ((InternalEList<?>) getInternalVars()).basicRemove(otherEnd, msgs);
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
			return ((InternalEList<?>) getInternalFbs()).basicRemove(otherEnd, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
			return getInternalVars();
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
			return getInternalFbs();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
			getInternalVars().clear();
			getInternalVars().addAll((Collection<? extends VarDeclaration>) newValue);
			return;
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
			getInternalFbs().clear();
			getInternalFbs().addAll((Collection<? extends FB>) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
			getInternalVars().clear();
			return;
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
			getInternalFbs().clear();
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
			return internalVars != null && !internalVars.isEmpty();
		case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
			return internalFbs != null && !internalFbs.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

} // BaseFBTypeImpl
