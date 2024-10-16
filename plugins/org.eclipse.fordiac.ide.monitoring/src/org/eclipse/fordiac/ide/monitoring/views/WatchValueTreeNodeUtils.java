/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, AIT, fortiss GmbH
 * 							 Johannes Kepler University,
 *				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Benjamin Muttenthaler - Extracted WatchValueTreeNode manipulation part from
 *   						 MonitoringEditPart to here
 *******************************************************************************/

package org.eclipse.fordiac.ide.monitoring.views;

import java.util.List;

import org.eclipse.fordiac.ide.model.StructTreeNode;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;

public final class WatchValueTreeNodeUtils {

	public static final String ASSIGN = ":="; //$NON-NLS-1$
	public static final String CLAMP_OP = "("; //$NON-NLS-1$
	public static final String CLAMP_CL = ")"; //$NON-NLS-1$
	public static final String DELIMITER = ","; //$NON-NLS-1$

	/**
	 * @param value ... online value to check
	 * @param type  ... type of the online value
	 * @param model ... the monitoring element
	 * @return all values of a datatype that inherits from ANY_BIT (except BOOL) is
	 *         represented as hex-decimal (doesn't matter if the value is within a
	 *         struct or not).
	 */
	public static String decorateHexValue(final String value, final DataType type, final MonitoringElement model) {
		// we want to convert every AnyBit type besides bool
		if (isHexDecoractionNecessary(value, type)) {
			return decorateHexNumber(value);
		} else if (isStruct(type)) {
			final WatchValueTreeNode dbgStruct = StructParser.createStructFromString(value, (StructuredType) type,
					model, new WatchValueTreeNode(model));
			adaptAnyBitValues(dbgStruct.getChildren());
			return buildTreeString(dbgStruct);
		}

		return value;
	}

	/**
	 * 
	 * @param value ... the online value to check
	 * @param type  ... type of the online value
	 * @return value datatype is inherited from ANY_BIT and not of datatype BOOL
	 */
	public static boolean isHexDecoractionNecessary(final String value, final DataType type) {
		return isHexValue(type) && isNumeric(value);
	}

	/**
	 * Decorates all values of a type that inherits from ANY_BIT (except BOOL)
	 * 
	 * @param childrens ... of a struct
	 */
	public static void adaptAnyBitValues(final List<StructTreeNode> childrens) {
		for (final StructTreeNode structTreeNode : childrens) {
			if (structTreeNode.hasChildren()) {
				adaptAnyBitValues(structTreeNode.getChildren());
			}
			final VarDeclaration structTreeNodeVar = structTreeNode.getVariable();
			final WatchValueTreeNode watchedValueTreeNode = (WatchValueTreeNode) structTreeNode;
			if (structTreeNodeVar != null
					&& isHexDecoractionNecessary(watchedValueTreeNode.getValue(), structTreeNodeVar.getType())) {
				watchedValueTreeNode.setValue(decorateHexNumber(watchedValueTreeNode.getValue()));
			}
		}
	}
	
	/**
	 * @param value ... to decorated
	 * @return the passed parameter as hex-decimal format
	 */
	public static String decorateHexNumber(final String value) {
		long parseInt;
		try {
			parseInt = Long.parseUnsignedLong(value);
		} catch (final NumberFormatException e) {
			parseInt = 0;
		}
		return convertIntegerToHexString(parseInt);
	}

	private static String buildTreeString(final WatchValueTreeNode dbgStruct) {
		return buildSubTreeString(dbgStruct.getChildren(), CLAMP_OP) + CLAMP_CL;
	}

	private static String buildSubTreeString(final List<StructTreeNode> list, String valString) {
		for (final StructTreeNode tn : list) {
			final WatchValueTreeNode wtn = (WatchValueTreeNode) tn;
			if (wtn.hasChildren()) {
				valString += wtn.getPinName() + ASSIGN + CLAMP_OP;
				valString += buildSubTreeString(wtn.getChildren(), valString) + CLAMP_CL + DELIMITER;
				continue;
			}
			if (wtn.getVariable() != null) {
				valString += wtn.getVariable().getName() + ASSIGN + wtn.getValue() + DELIMITER;
			}
		}
		return valString.substring(0, valString.length() - 1); // remove last delimiter
	}

	private static boolean isStruct(final DataType type) {
		return type instanceof StructuredType;
	}

		private static String convertIntegerToHexString(final long number) {
		return "16#" + Long.toHexString(number).toUpperCase(); //$NON-NLS-1$
	}

	private static boolean isNumeric(final String input) {
		return input.chars().allMatch(Character::isDigit);
	}

	// checks if the value should be converted to a hexstring
	private static boolean isHexValue(final DataType type) {
		return (type instanceof AnyBitType) && !(type instanceof BoolType);
	}

}
