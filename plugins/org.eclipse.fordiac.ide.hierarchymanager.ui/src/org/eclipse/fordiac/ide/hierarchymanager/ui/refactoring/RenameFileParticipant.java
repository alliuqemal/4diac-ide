/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Qemal Alliu - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.listeners.HierachyManagerUpdateListener;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class RenameFileParticipant extends RenameParticipant {

	private List<IFile> files = new ArrayList<>();

	private RootLevel plantHierarchy;

	private static List<String> ALLOWED_FILE_EXTENSIONS = List.of("sub", "sys");

	@Override
	protected boolean initialize(final Object element) {

		Display.getDefault().syncExec(() -> {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			PlantHierarchyView view = null;

			if (page != null) {
				view = (PlantHierarchyView) page.findView("org.eclipse.fordiac.ide.hierarchymanager.view"); //$NON-NLS-1$
			}

			if (view != null) {
				plantHierarchy = (RootLevel) view.getCommonViewer().getInput();
			} else if (element instanceof final IResource resource) {
				plantHierarchy = (RootLevel) HierachyManagerUpdateListener.loadPlantHierachy(resource.getProject());
			}
		});

		if (element instanceof final IFile file) {
			this.files = new ArrayList<>(List.of(file));
			return true;
		}
		if (element instanceof final IFolder folder) {
			try {
				storeFilesFromFolder(folder);
				return true;
			} catch (final CoreException e) {
				return false;
			}
		}

		return false;
	}

	private void storeFilesFromFolder(final IFolder folder) throws CoreException {
		for (final IResource resource : folder.members()) {
			if (resource.getType() == IResource.FILE && ALLOWED_FILE_EXTENSIONS.contains(resource.getFileExtension())) {
				files.add((IFile) resource);
			} else if (resource.getType() == IResource.FOLDER) {
				storeFilesFromFolder((IFolder) resource);
			}
		}
	}

	@Override
	public String getName() {
		return "Fix references on plant hierarchy"; //$NON-NLS-1$
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {

		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		try {
			pm.beginTask("Creating change...", 1); //$NON-NLS-1$

		} finally {
			pm.done();
		}

		return null;
	}
}
