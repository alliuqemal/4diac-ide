package org.eclipse.fordiac.ide.hierarchymanager.ui.refactoring;

import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class SafePlantElementDeletionChange extends CompositeChange {

	public SafePlantElementDeletionChange(final RootLevel rootLevel) {
		super("--" + rootLevel.toString());
	}

}
