/*
 * Jaspersoft Open Studio - Eclipse-based JasperReports Designer. Copyright (C) 2005 - 2010 Jaspersoft Corporation. All
 * rights reserved. http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of Jaspersoft Open Studio.
 * 
 * Jaspersoft Open Studio is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Jaspersoft Open Studio is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with Jaspersoft Open Studio. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.table.part.editpolicy;

import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardBaseColumn;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.jaspersoft.studio.property.SetValueCommand;
import com.jaspersoft.studio.table.model.MCell;
import com.jaspersoft.studio.table.model.MColumn;

/**
 * The Class BandMoveEditPolicy.
 * 
 * @author Chicu Veaceslav
 */
public class TableCellMoveEditPolicy extends GraphicalEditPolicy {

	/** The feedback. */
	private IFigure feedback;

	/** The handle. */
	private IFigure handle;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPolicy#activate()
	 */
	public void activate() {
		super.activate();
		// setHandle(new CellResizeHandle((GraphicalEditPart) getHost()));
		setHandle(new CellResizeHandle((GraphicalEditPart) getHost(), PositionConstants.SOUTH));
		getLayer(LayerConstants.HANDLE_LAYER).add(getHandle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPolicy#deactivate()
	 */
	public void deactivate() {
		if (getHandle() != null) {
			getLayer(LayerConstants.HANDLE_LAYER).remove(getHandle());
			setHandle(null);
		}
		if (feedback != null) {
			removeFeedback(feedback);
			feedback = null;
		}
		super.deactivate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPolicy#understandsRequest(Request)
	 */
	public boolean understandsRequest(Request request) {
		if (REQ_RESIZE.equals(request.getType()))
			return true;
		return false;
	}

	/**
	 * Erase change bounds feedback.
	 * 
	 * @param request
	 *          the request
	 */
	protected void eraseChangeBoundsFeedback(ChangeBoundsRequest request) {
		if (feedback != null) {
			removeFeedback(feedback);
		}
		feedback = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPolicy#eraseSourceFeedback(Request)
	 */
	public void eraseSourceFeedback(Request request) {
		if (REQ_RESIZE.equals(request.getType()))
			eraseChangeBoundsFeedback((ChangeBoundsRequest) request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 */
	public Command getCommand(Request request) {
		if (REQ_RESIZE.equals(request.getType()))
			return getResizeCommand((ChangeBoundsRequest) request);
		return null;
	}

	/**
	 * Gets the resize command.
	 * 
	 * @param request
	 *          the request
	 * @return the resize command
	 */
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		if (request.getResizeDirection() == PositionConstants.SOUTH
				|| request.getResizeDirection() == PositionConstants.EAST) {
			MColumn model = (MColumn) getHost().getModel();
			StandardBaseColumn jrdesign = (StandardBaseColumn) model.getValue();

			CompoundCommand c = new CompoundCommand("Change Cell Size"); //$NON-NLS-1$

			if (request.getSizeDelta().width != 0) {
				int width = jrdesign.getWidth() + request.getSizeDelta().width;
				if (width < 0)
					width = 0;

				SetValueCommand setCommand = new SetValueCommand();
				setCommand.setTarget(model);
				setCommand.setPropertyId(StandardBaseColumn.PROPERTY_WIDTH);
				setCommand.setPropertyValue(width);
				c.add(setCommand);
			}
			if (request.getSizeDelta().height != 0 && model instanceof MCell) {
				MCell mc = (MCell) model;
				int height = (Integer) mc.getPropertyValue(DesignCell.PROPERTY_HEIGHT) + request.getSizeDelta().height;
				if (height < 0)
					height = 0;

				SetValueCommand setCommand = new SetValueCommand();
				setCommand.setTarget(model);
				setCommand.setPropertyId(DesignCell.PROPERTY_HEIGHT);
				setCommand.setPropertyValue(height);
				c.add(setCommand);
			}
			return c;
		}
		return null;
	}

	/**
	 * Sets the handle.
	 * 
	 * @param handle
	 *          the new handle
	 */
	private void setHandle(IFigure handle) {
		this.handle = handle;
	}

	/**
	 * Gets the handle.
	 * 
	 * @return the handle
	 */
	private IFigure getHandle() {
		return handle;
	}

}
