/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 * 				 2020			  Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fortelauncher;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fortelauncher.messages"; //$NON-NLS-1$

	public static String ForteLauncher_ERROR_WrongPort;
	public static String ForteLauncher_ERROR_CouldNotLaunchFORTE;
	public static String ForteLauncher_LABEL_PortParam;
	public static String FortePreferencePage_FORTEPreferencesPage;
	public static String FortePreferencePage_FORTELocation;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
