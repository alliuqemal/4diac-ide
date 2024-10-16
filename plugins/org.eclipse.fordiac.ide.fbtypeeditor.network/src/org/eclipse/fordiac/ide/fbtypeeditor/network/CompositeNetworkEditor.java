/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.FBTypePaletteViewerProvider;
import org.eclipse.fordiac.ide.application.utilities.FbTypeTemplateTransferDropTargetListener;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.InterfaceContextMenuProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.ui.IEditorInput;

public class CompositeNetworkEditor extends FBNetworkEditor implements IFBTEditorPart {

	private CompositeFBType fbType;
	private CommandStack commandStack;
	private TypeLibrary typeLib;
	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			// only refresh propertypage (page) if the event is not an
			// REMOVING_ADAPTER event - otherwise, the remove adapter in the
			// dispose method (called when closing the editor) will fail
			if ((notification.getEventType() != Notification.REMOVING_ADAPTER) && (((notification.getNewValue() == null)
					&& (notification.getNewValue() != notification.getOldValue()))
					|| ((notification.getNewValue() != null)
							&& !(notification.getNewValue().equals(notification.getOldValue()))))) {
				super.notifyChanged(notification);
			}

		}
	};

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	protected CompositeNetworkEditPartFactory getEditPartFactory() {
		return new CompositeNetworkEditPartFactory(this);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// currently nothing needs to be done here
	}

	@Override
	public void dispose() {
		getModel().eAdapters().remove(adapter);
		super.dispose();
	}

	@Override
	protected void createActions() {
		super.createActions();
		InterfaceContextMenuProvider.createInterfaceEditingActions(this, getActionRegistry(), fbType);
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {

		final EditPart editPart = getEditPartForSelection(selectedElement);
		if (null != editPart) {
			getGraphicalViewer().select(editPart);
			return true;
		}
		return (selectedElement instanceof FBNetwork);
	}

	EditPart getEditPartForSelection(Object selectedElement) {
		@SuppressWarnings("rawtypes")
		final
		Map map = getGraphicalViewer().getEditPartRegistry();

		for (final Object key : map.keySet()) {
			if (((key instanceof FB) && (((FB) key) == selectedElement)) || //
					((key instanceof Connection) && (((Connection) key) == selectedElement))) {
				selectedElement = key;
				break;
			}
		}

		final Object obj = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
		if (obj instanceof EditPart) {
			return (EditPart) obj;
		}
		return null;
	}

	@Override
	protected Palette getPalette() {
		return typeLib.getBlockTypeLib();
	}

	@Override
	public AutomationSystem getSystem() {
		return null;
	}

	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof FBTypeEditorInput) {
			final FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof CompositeFBType) {
				fbType = (CompositeFBType) untypedInput.getContent();
				setModel(fbType.getFBNetwork());
				getModel().eAdapters().add(adapter);
				configurePalette(untypedInput);
			}
		}

		setTitleImage(FordiacImage.ICON_FB_NETWORK.getImage());
		setEditDomain(new FBTypeEditDomain(this, commandStack));
	}

	@Override
	protected void setEditorPartName(final IEditorInput input) {
		setPartName(FordiacMessages.FBNetwork);
	}

	protected void configurePalette(final FBTypeEditorInput fbTypeEditorInput) {
		typeLib = fbTypeEditorInput.getPaletteEntry().getTypeLibrary();
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return null; // we are filling the palette directly in the viewer so we don't need it here
	}

	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new FBTypePaletteViewerProvider(fbType.getPaletteEntry().getFile().getProject(), getEditDomain(),
				getPaletteNavigatorID());
	}

	@Override
	protected String getPaletteNavigatorID() {
		return "org.eclipse.fordiac.ide.compositefbpaletteviewer"; //$NON-NLS-1$
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new CFBNetworkcontextMenuProvider(this, getActionRegistry(), zoomManager, typeLib);
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		return new FbTypeTemplateTransferDropTargetListener(getGraphicalViewer(),
				fbType.getPaletteEntry().getFile().getProject());
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		try {
			final Map<String, Object> attributes = marker.getAttributes();
			if (FordiacMarkerHelper.markerTargetsFBNetworkElement(attributes)) {
				final Object location = attributes.get(IMarker.LOCATION);
				if (location instanceof String) {
					final FBNetworkElement fbne = getModel().getElementNamed((String) location);
					if (null != fbne) {
						selectElement(fbne);
					}
				}
			}
		} catch (final CoreException e) {
			Activator.getDefault().logError("Could not get marker attributes", e); //$NON-NLS-1$
		}
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		try {
			return FordiacMarkerHelper.markerTargetsFBNetworkElement(marker.getAttributes());
		} catch (final CoreException e) {
			Activator.getDefault().logError("Could not get marker attributes", e); //$NON-NLS-1$
		}
		return false;
	}

	@Override
	public void reloadType(final FBType type) {
		if (type instanceof CompositeFBType) {
			getModel().eAdapters().remove(adapter);
			fbType = ((CompositeFBType) type);
			setModel(fbType.getFBNetwork());
			if (getModel() != null) {
				getModel().eAdapters().add(adapter);
				getGraphicalViewer().setContents(getModel());
			} else {
				EditorUtils.CloseEditor.run(this);
			}
		}
	}

	@Override
	public Object getSelectableEditPart() {
		if (getGraphicalViewer() == null) {
			return null;
		}
		return getGraphicalViewer().getEditPartRegistry().get(getModel());
	}

}
