package org.eclipse.fordiac.ide.hierarchymanager.ui.refactoring;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.listeners.HierachyManagerUpdateListener;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

public class DeleteLibraryElementParticipant extends DeleteParticipant {

	private RootLevel plantHierarchy;

	private IFile file;

	@Override
	protected boolean initialize(final Object element) {

		if (element instanceof final IFile file) {

			this.file = file;

			plantHierarchy = (RootLevel) HierachyManagerUpdateListener.loadPlantHierachy(file.getProject());

			return plantHierarchy != null;
		}

		return true;
	}

	@Override
	public String getName() {
		return "Delete element of plant hierarchy";
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

			final List<Leaf> leaves = HierarchyManagerUtil.searchLeaf(plantHierarchy,
					leaf -> leaf.getContainerFileName().contains(file.getName()));

			if (!leaves.isEmpty()) {
				return new SafePlantElementDeletionChange(plantHierarchy);
			}

			return null;
		} finally {
			pm.done();
		}
	}

}
