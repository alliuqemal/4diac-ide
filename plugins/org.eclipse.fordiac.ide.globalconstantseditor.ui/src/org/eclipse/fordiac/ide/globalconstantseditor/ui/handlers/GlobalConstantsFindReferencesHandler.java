/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.search.ModelSearchQuery;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.xtext.ui.editor.findrefs.FindReferencesHandler;

@SuppressWarnings("restriction")
public class GlobalConstantsFindReferencesHandler extends FindReferencesHandler {

	@Override
	protected void findReferences(final EObject target) {
		if (target instanceof final STVarDeclaration varDec) {
			// @formatter:off
			final EList<EObject> contents = EcoreUtil.getRootContainer(varDec).eResource().getContents();
			final String packageName = PackageNameHelper.getPackageName((LibraryElement)contents.get(1));
			final ModelQuerySpec searchSpec = new ModelQuerySpec(
					varDec.getQualifiedName(),
					false,
					false,
					true,
					false,
					false,
					true,
					true, // search initial value
					SearchScope.PROJECT,
					getProject(varDec));
			// @formatter:on

			final ModelSearchQuery searchJob = new ModelSearchQuery(searchSpec);
			NewSearchUI.runQueryInBackground(searchJob, NewSearchUI.getSearchResultView());
		} else {
			super.findReferences(target);
		}
	}

	private static IProject getProject(final STVarDeclaration varDec) {
		final var resource = varDec.eResource();
		final IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(resource.getURI().toPlatformString(true)));
		return file.getProject();
	}

}
