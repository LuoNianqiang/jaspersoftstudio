/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * 
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package com.jaspersoft.studio.property.section.style.inerithance;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.jaspersoft.studio.JSSCompoundCommand;
import com.jaspersoft.studio.model.APropertyNode;

/**
 * Class to manage the events of the mouse click on the delete button, used to delete
 * an attribute, on an attribute of the selected element
 * 
 * @author Orlandin Marco
 * 
 */
public class ElementClickListener extends MouseAdapter{

	/**
	 * Key of the attribute to remove
	 */
	private String property;
	
	/**
	 * The parent section
	 */
	private StylesListSection parentSection;
	
	/**
	 * 
	 * Create the handler for the delete button on the StylesListSection for the attribute
	 * of the selected element
	 * 
	 * @param property Key of the attribute to remove
	 * @param parentSection The parent section
	 */
	public ElementClickListener(String property, StylesListSection parentSection) {
		this.property = property;
		this.parentSection = parentSection;
	}

	/**
	 * Set the property of the element binded to this event to null, using the manipulation commands (so the operation
	 * can be undone)
	 */
	public void mouseUp(MouseEvent e) {
			List<APropertyNode> selectedElements = parentSection.getElements();
			JSSCompoundCommand cc = new JSSCompoundCommand("Set " + property, selectedElements.get(0)); //$NON-NLS-1$
			for(APropertyNode targetElement : selectedElements){
				Command c = parentSection.generateSetAttributeCommand(targetElement, property);
				if (c != null)
					cc.add(c);
			}
			if (!cc.getCommands().isEmpty()) {
				parentSection.executeAndRefresh(cc);
			}
	}
}