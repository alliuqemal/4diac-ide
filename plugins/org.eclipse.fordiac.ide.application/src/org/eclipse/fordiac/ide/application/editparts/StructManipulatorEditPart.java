/*******************************************************************************
 * Copyright (c) 2020, 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation
 *   Prankur Agarwal - add edit part for Struct Type
 *   				 - add edit policy for changing struct type
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.application.policies.StructuredManipulatorLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;

public abstract class StructManipulatorEditPart extends AbstractFBNElementEditPart {
	protected StructManipulatorEditPart() {
		super();
	}

	@Override
	protected IFigure createFigureForModel() {
		return new FBNetworkElementFigure(getModel(), this);
	}

	@Override
	public StructManipulator getModel() {
		return (StructManipulator) super.getModel();

	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> elements = super.getModelChildren();
		final StructManipulator model = getModel();
		if (model.getStructType() != null) {
			elements.add(model.getStructType());
		} else {
			elements.add(GenericTypes.ANY_STRUCT);
		}
		return elements;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof StructuredTypeEditPart) {
			getFigure().getMiddle().add(((StructuredTypeEditPart) childEditPart).getFigure(),
					new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof StructuredTypeEditPart) {
			getFigure().getMiddle().remove(((StructuredTypeEditPart) childEditPart).getFigure());
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	protected Adapter createInterfaceAdapter() {
		return new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				switch (notification.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.MOVE:
				case Notification.REMOVE:
					refresh();
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new StructuredManipulatorLayoutEditPolicy());
	}
}