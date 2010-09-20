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
package com.jaspersoft.studio.property;

import java.text.MessageFormat;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * The Class SetValueCommand.
 */
public class SetValueCommand extends Command {

	/** The property value. */
	protected Object propertyValue;

	/** The property name. */
	protected Object propertyName;

	/** The undo value. */
	protected Object undoValue;

	/** The reset on undo. */
	protected boolean resetOnUndo;

	/** The target. */
	protected IPropertySource target;

	/**
	 * Instantiates a new sets the value command.
	 */
	public SetValueCommand() {
		super(""); //$NON-NLS-1$
	}

	/**
	 * Instantiates a new sets the value command.
	 * 
	 * @param propLabel
	 *          the prop label
	 */
	public SetValueCommand(String propLabel) {
		super(MessageFormat.format("Set {0} Property", new Object[] { propLabel }).trim());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		undoValue = getTarget().getPropertyValue(propertyName);
		getTarget().setPropertyValue(propertyName, propertyValue);
	}

	/**
	 * Gets the target.
	 * 
	 * @return the target
	 */
	public IPropertySource getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 * 
	 * @param aTarget
	 *          the new target
	 */
	public void setTarget(IPropertySource aTarget) {
		target = aTarget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		execute();
	}

	/**
	 * Sets the property id.
	 * 
	 * @param pName
	 *          the new property id
	 */
	public void setPropertyId(Object pName) {
		propertyName = pName;
	}

	/**
	 * Sets the property value.
	 * 
	 * @param val
	 *          the new property value
	 */
	public void setPropertyValue(Object val) {
		propertyValue = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (resetOnUndo)
			getTarget().resetPropertyValue(propertyName);
		else
			getTarget().setPropertyValue(propertyName, undoValue);
	}

}
