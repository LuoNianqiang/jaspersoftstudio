/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.formatting.actions;

import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.property.SetValueCommand;
import com.jaspersoft.studio.messages.Messages;

public class EqualsVSpaceAction extends AbstractFormattingAction{

	/** The Constant ID. */
	public static final String ID = "samevspace"; //$NON-NLS-1$
	
	public EqualsVSpaceAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.EqualsVSpaceAction_actionName);
		setToolTipText(Messages.EqualsVSpaceAction_actionDescription);
		setId(ID);
		setImageDescriptor(JaspersoftStudioPlugin.getInstance().getImageDescriptor("icons/resources/elem_add_vspace.png"));  //$NON-NLS-1$
	}

	@Override
	protected boolean calculateEnabled() {
		return getOperationSet().size()>1;
	}
	
	public static CompoundCommand generateCommand(List<APropertyNode> nodes){
		CompoundCommand command = new CompoundCommand();    
    if (nodes.isEmpty()) return command;

    List<APropertyNode> sortedElements = sortYX( nodes );
    
    int gap = 0;
    int usedSpace = 0;
    JRDesignElement jrElement = (JRDesignElement)sortedElements.get(0).getValue();
    int minY = jrElement.getY();
    int maxY = minY + jrElement.getHeight();
    for (APropertyNode element : sortedElements)
    {
    		jrElement = (JRDesignElement)element.getValue();
        if (minY > jrElement.getY()) minY = jrElement.getY();
        if (maxY < jrElement.getY()+jrElement.getHeight()) maxY = jrElement.getY()+jrElement.getHeight();
        usedSpace += jrElement.getHeight();
    }
    
    gap = (maxY - minY - usedSpace)/(nodes.size()-1);
    
    int actualY = minY;
    
    for (int i=0; i<sortedElements.size(); ++i)
    {
        APropertyNode element = sortedElements.get(i);
        jrElement = (JRDesignElement)element.getValue();
        if (i == 0) {
            actualY = jrElement.getY() + jrElement.getHeight() + gap;
            continue;
        }
        int newY;
        if (i == sortedElements.size() - 1)
        {
            // Trick to avoid calculations errors.
        	SetValueCommand setCommand = new SetValueCommand();
    			setCommand.setTarget(element);
    			setCommand.setPropertyId(JRDesignElement.PROPERTY_Y);
    			newY = maxY - jrElement.getHeight();
    			setCommand.setPropertyValue(newY);
  	      command.add(setCommand);
        }
        else
        {
        	SetValueCommand setCommand = new SetValueCommand();
    			setCommand.setTarget(element);
    			setCommand.setPropertyId(JRDesignElement.PROPERTY_Y);
    			newY = actualY;
    			setCommand.setPropertyValue(newY);
  	      command.add(setCommand);
        }
        actualY = newY + jrElement.getHeight() + gap;
    }
    
    
		return command;
	}

	protected Command createAlignmentCommand() {
			List<APropertyNode> nodes = getOperationSet();
			CompoundCommand command = null;
			if (nodes.isEmpty()) 
				command = new CompoundCommand();
			else {
				command = generateCommand(nodes);
			}
			command.setDebugLabel(getText());
			return command;
	}
	
}
