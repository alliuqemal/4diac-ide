/**
 * *******************************************************************************
 *  Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.model.libraryElement.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.fordiac.ide.model.data.provider.FordiacEditPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class TransientInterfaceListItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

	public TransientInterfaceListItemProvider(final AdapterFactory adapterFactory, final InterfaceList iterfaceList) {
		super(adapterFactory);
		iterfaceList.eAdapters().add(this);
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		return super.getChildren(target);
	}

	@Override
	public Collection<?> getNewChildDescriptors(final Object object, final EditingDomain editingDomain, final Object sibling) {
		return super.getNewChildDescriptors(target, editingDomain, sibling);
	}

	@Override
	public Object getParent(final Object object) {
		return target;
	}

	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/InterfaceList")); //$NON-NLS-1$
	}

	@Override
	public ResourceLocator getResourceLocator() {
		return FordiacEditPlugin.INSTANCE;
	}

}
