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
package com.jaspersoft.studio.toolbars;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;

import com.jaspersoft.studio.components.crosstab.model.MCrosstab;
import com.jaspersoft.studio.components.crosstab.model.columngroup.action.CreateColumnGroupAction;
import com.jaspersoft.studio.editor.action.SetWorkbenchAction;

/**
 * Toolbar button to create a column group on the selected crosstab
 * 
 * @author Orlandin Marco
 *
 */
public class CreateColumnGroupContributionItem extends CommonToolbarHandler{
	
	/**
	 * The button to press to activate the action
	 */
	private ToolItem button;

	/**
	 * Action that will be executed to add the group, executed when the button is pressed
	 */
	private SetWorkbenchAction createColumnAction = new CreateColumnGroupAction(null){

		@Override
		protected ISelection getSelection() {
			return getLastRawSelection();
		}
	};
	
	/**
	 * Listener for the button, when it pressed the action is runned 
	 */
	private SelectionAdapter pushButtonPressed = new SelectionAdapter() {
	
		public void widgetSelected(SelectionEvent e) {
			createColumnAction.setWorkbenchPart(getWorkbenchPart());
			createColumnAction.run();
		}
	};
	
	/**
	 * Set the state of the button depending on the actual selection
	 */
	private void setEnablement(){
		if (getWorkbenchPart() != null){
			if (button != null && !button.isDisposed()){
				createColumnAction.setLazyEnablementCalculation(true);
				createColumnAction.setWorkbenchPart(getWorkbenchPart());
				button.setEnabled(createColumnAction.isEnabled());
			}
		}
	}

	@Override
	public boolean isVisible() {
		if (!super.isVisible()) return false;
		if (getSelectionForType(MCrosstab.class).size() == 1){
			setEnablement();
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean fillWithToolItems(ToolBar parent) {
		button = new ToolItem(parent, SWT.PUSH);
		button.setImage(ResourceManager.getImage(createColumnAction.getImageDescriptor()));
		button.setToolTipText(createColumnAction.getToolTipText());
		button.addSelectionListener(pushButtonPressed);
		getToolItems().add(button);
		setEnablement();
		return true;
	}
}
