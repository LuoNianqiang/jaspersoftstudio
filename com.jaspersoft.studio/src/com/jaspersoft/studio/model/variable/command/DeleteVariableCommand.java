/*
 * Jaspersoft Open Studio - Eclipse-based JasperReports Designer.
 * Copyright (C) 2005 - 2010 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of Jaspersoft Open Studio.
 *
 * Jaspersoft Open Studio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jaspersoft Open Studio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Jaspersoft Open Studio. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.model.variable.command;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignVariable;

import org.eclipse.gef.commands.Command;

import com.jaspersoft.studio.model.variable.MVariable;
import com.jaspersoft.studio.model.variable.MVariables;

// TODO: Auto-generated Javadoc
/**
 * link nodes & together.
 * 
 * @author Chicu Veaceslav
 */
public class DeleteVariableCommand extends Command {
	
	/** The jr dataset. */
	private JRDesignDataset jrDataset;
	
	/** The jr variable. */
	private JRDesignVariable jrVariable;
	
	/** The element position. */
	private int elementPosition = 0;

	/**
	 * Instantiates a new delete variable command.
	 * 
	 * @param destNode
	 *          the dest node
	 * @param srcNode
	 *          the src node
	 */
	public DeleteVariableCommand(MVariables destNode, MVariable srcNode) {
		super();
		this.jrDataset = (JRDesignDataset) destNode.getValue();
		this.jrVariable = (JRDesignVariable) srcNode.getValue();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		elementPosition = jrDataset.getVariablesList().indexOf(jrVariable);
		jrDataset.removeVariable(jrVariable);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if (jrDataset == null || jrVariable == null)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		try {
			if (elementPosition < 0 || elementPosition > jrDataset.getVariablesList().size())
				jrDataset.addVariable(jrVariable);
			else
				jrDataset.addVariable(elementPosition, jrVariable);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
