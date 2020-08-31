/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTValidation;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class ValidationProvider implements IFBTValidation {
	@Override
	public void invokeValidation(INamedElement namedElement) {
		ValidationHelper.validate(namedElement);
	}
}
