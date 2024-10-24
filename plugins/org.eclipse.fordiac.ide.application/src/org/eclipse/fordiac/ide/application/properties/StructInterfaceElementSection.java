/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed issues in type changes for subapp interface elements
 *   Bianca Wiesmayr - adapted section for struct pins
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.ChangeSubAppIETypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StructInterfaceElementSection extends org.eclipse.fordiac.ide.gef.properties.InterfaceElementSection {
	private TreeViewer connectionsTree;
	private Group group;
	private Button openEditorButton;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createConnectionDisplaySection(parent);

		createStructSpecificElements();
	}

	private StructManipulator getStructManipulator() {
		if (null == getType()) {
			return null;
		}
		return (StructManipulator) getType().getFBNetworkElement();
	}

	private StructuredType getStructuredType() {
		final var structManipulator = getStructManipulator();
		return structManipulator != null ? structManipulator.getStructType() : null;
	}

	private void createStructSpecificElements() {
		final Composite comp = typeCombo.getParent();
		openEditorButton = new Button(comp, SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		openEditorButton.addListener(SWT.Selection, ev -> {
			final IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench != null) {
				final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
				if (activeWorkbenchWindow != null) {
					openStructEditor(activeWorkbenchWindow);
				}
			}
		});

		typeCombo.addListener(SWT.Selection, ev -> {
			if (null != getStructManipulator()) {
				final int index = typeCombo.getSelectionIndex();
				final String newStructName = typeCombo.getItem(index);
				disableButtonForAnyType();
				final var structuredType = getStructuredType();
				final var typeName = structuredType != null ? structuredType.getName() : null;
				final boolean newStructSelected = !newStructName.contentEquals(typeName);
				if (newStructSelected) {
					final StructuredType newStruct = getDataTypeLib().getStructuredType(newStructName);
					final ChangeStructCommand cmd = new ChangeStructCommand(getStructManipulator(), newStruct);
					commandStack.execute(cmd);
					selectNewStructPin(cmd.getNewMux());
					refresh();
				}
			}
		});
	}

	private void openStructEditor(final IWorkbenchWindow activeWorkbenchWindow) {
		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

		final var structuredType = getStructuredType();
		final var paletteEntry = structuredType != null ? structuredType.getPaletteEntry() : null;
		final IFile file = paletteEntry != null ? paletteEntry.getFile() : null;
		final var fileName = file != null ? file.getName() : null;
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fileName);
		try {
			activePage.openEditor(new FileEditorInput(file), desc.getId());
		} catch (final PartInitException e) {
			ApplicationPlugin.getDefault().logError(e.getMessage(), e);
		}
	}

	private void selectNewStructPin(final StructManipulator fb) {
		final GraphicalViewer viewer = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			viewer.flush();
			Object obj;
			if (fb instanceof Multiplexer) {
				obj = viewer.getEditPartRegistry().get(fb.getInterface().getOutputVars().get(0));
			} else {
				obj = viewer.getEditPartRegistry().get(fb.getInterface().getInputVars().get(0));
			}
			viewer.select((EditPart) obj);
			setInput(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor(),
					viewer.getSelection());
		}
	}

	private void createConnectionDisplaySection(final Composite parent) {
		group = getWidgetFactory().createGroup(parent, Messages.InterfaceElementSection_ConnectionGroup);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		connectionsTree = new TreeViewer(group, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 100;
		gridData.widthHint = 80;
		connectionsTree.getTree().setLayoutData(gridData);
		connectionsTree.setContentProvider(new ConnectionContentProvider());
		connectionsTree.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		connectionsTree.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		new AdapterFactoryTreeEditor(connectionsTree.getTree(), getAdapterFactory());

		final Button delConnection = getWidgetFactory().createButton(group, "", SWT.PUSH); //$NON-NLS-1$
		delConnection.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true));
		delConnection.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		delConnection.setToolTipText(Messages.InterfaceElementSection_DeleteConnectionToolTip);
		delConnection.addListener(SWT.Selection, event -> {
			final Object selection = ((TreeSelection) connectionsTree.getSelection()).getFirstElement();
			if (selection instanceof Connection) {
				executeCommand(new DeleteConnectionCommand((Connection) selection));
				connectionsTree.refresh();
			}
		});
	}

	@Override
	public void refresh() {
		super.refresh();
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			if (getType().isIsInput()) {
				group.setText(Messages.InterfaceElementSection_InConnections);
			} else {
				group.setText(Messages.InterfaceElementSection_OutConnections);
			}
			connectionsTree.setInput(getType());
			disableButtonForAnyType();
			typeCombo.setEnabled(true);
			fillTypeCombobox();
		}
		commandStack = commandStackBuffer;
	}

	private void disableButtonForAnyType() {
		final var structuredType = getStructuredType();
		final var typeName = structuredType != null ? structuredType.getName() : ""; //$NON-NLS-1$
		openEditorButton.setEnabled(!"ANY_STRUCT".contentEquals(typeName)); //$NON-NLS-1$
	}

	private void fillTypeCombobox() {
		final StructManipulator structManipulator = getStructManipulator();
		if (null != structManipulator) {
			final String structName = structManipulator.getStructType().getName();
			typeCombo.removeAll();
			for (final StructuredType dtp : getDataTypeLib().getStructuredTypesSorted()) {
				typeCombo.add(dtp.getName());
				if (dtp.getName().contentEquals(structName)) {
					typeCombo.select(typeCombo.getItemCount() - 1);
				}
			}
		}
	}

	@Override
	protected void setInputCode() {
		connectionsTree.setInput(null);
	}

	private static class ConnectionContentProvider implements ITreeContentProvider {
		private IInterfaceElement element;

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof IInterfaceElement) {
				element = ((IInterfaceElement) inputElement);
				if ((element.isIsInput() && (null != element.getFBNetworkElement()))
						|| (!element.isIsInput() && (null == element.getFBNetworkElement()))) {
					return element.getInputConnections().toArray();
				}
				return element.getOutputConnections().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof Connection) {
				final Object[] objects = new Object[2];
				if (element.isIsInput()) {
					objects[0] = null != ((Connection) parentElement).getSourceElement()
							? ((Connection) parentElement).getSourceElement()
									: element;
					objects[1] = ((Connection) parentElement).getSource();
				} else {
					objects[0] = null != ((Connection) parentElement).getDestinationElement()
							? ((Connection) parentElement).getDestinationElement()
									: element;
					objects[1] = ((Connection) parentElement).getDestination();
				}
				return objects;
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			if (element instanceof Connection) {
				return this.element;
			}
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof Connection) {
				return (null != ((Connection) element).getSource())
						&& (null != ((Connection) element).getDestination());
			}
			return false;
		}
	}

	@Override
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return new ChangeSubAppIETypeCommand(data, newType);
	}
}
