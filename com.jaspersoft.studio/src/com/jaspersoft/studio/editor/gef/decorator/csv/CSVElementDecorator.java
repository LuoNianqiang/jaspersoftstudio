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
package com.jaspersoft.studio.editor.gef.decorator.csv;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.RetargetAction;

import com.jaspersoft.studio.editor.action.csv.CSVAction;
import com.jaspersoft.studio.editor.action.csv.CSVColDataAction;
import com.jaspersoft.studio.editor.action.csv.CSVRootAction;
import com.jaspersoft.studio.editor.gef.decorator.text.TextElementDecorator;
import com.jaspersoft.studio.editor.gef.figures.ComponentFigure;
import com.jaspersoft.studio.editor.gef.parts.FigureEditPart;
import com.jaspersoft.studio.editor.report.AbstractVisualEditor;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.MGraphicElement;

/**
 * Define the action related to the CSV export, it extends a TextElementDecorator to print the textual tag on the
 * elements
 * 
 * @author Orlandin Marco
 * 
 */
public class CSVElementDecorator extends TextElementDecorator {

	/**
	 * The XSL contributor for the text decoration
	 */
	private CSVDecorator decorator = new CSVDecorator();

	private List<String> actionIDs;

	/**
	 * Add or remove the XSL contributor from the text element decorator
	 */
	@Override
	public void setupFigure(ComponentFigure fig, FigureEditPart editPart) {
		super.setupFigure(fig, editPart);
		getDecorator().removeDecorator(decorator);
		if (editPart.getjConfig().getPropertyBoolean(ShowCSVTagsAction.ID, false)) {
			getDecorator().addDecorator(decorator);
		}
	}

	/**
	 * Create the action related to the CSV exporting
	 * @param registry
	 * @param part
	 * @param selectionActions
	 */
	private void registerCSVActions(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		
		IAction action = new CSVColDataAction(part, Messages.CSVElementDecorator_CreateColumn);
		registry.registerAction(action);
		selectionActions.add(action.getId());
		
		action = new CSVRootAction(part, CSVAction.RECORD_DELIMITER, "true", Messages.CSVElementDecorator_UseAsRecordDelimiter); //$NON-NLS-1$
		registry.registerAction(action);
		selectionActions.add(action.getId());
		
		action = new CSVRootAction(part, CSVAction.FIELD_DELIMITER, "true", Messages.CSVElementDecorator_UseAsFieldDelimiter); //$NON-NLS-1$
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}


	@Override
	public void registerActions(ActionRegistry registry, List<String> selectionActions, GraphicalViewer gviewer,
			AbstractVisualEditor part) {
		gviewer.setProperty(ShowCSVTagsAction.ID, true);
		IAction action = new ShowCSVTagsAction(gviewer, part.getJrContext());
		registry.registerAction(action);
		registerCSVActions(registry, part, selectionActions);
	}

	@Override
	public void buildContextMenu(ActionRegistry registry, EditPartViewer viewer, IMenuManager menu) {
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		if (sel.getFirstElement() instanceof EditPart) {
			EditPart ep = (EditPart) sel.getFirstElement();
			if (!(ep.getModel() instanceof MGraphicElement))
				return;
		}
		MenuManager submenu = new MenuManager(Messages.CSVElementDecorator_CSVMenuLabel);

		IAction action;
		// Adding actions for the Fit
		action = registry.getAction(CSVAction.COL_DATA);
		submenu.add(action);
		action = registry.getAction(CSVAction.FIELD_DELIMITER);
		submenu.add(action);
		action = registry.getAction(CSVAction.RECORD_DELIMITER);
		submenu.add(action);


		menu.add(submenu);
	}

	@Override
	public RetargetAction[] buildMenuActions() {
		return new RetargetAction[] { new RetargetAction(ShowCSVTagsAction.ID, Messages.CSVElementDecorator_ShowDecorationLabel, IAction.AS_CHECK_BOX) };
	}

	@Override
	public void contribute2Menu(ActionRegistry registry, MenuManager menuManager) {
		menuManager.add(registry.getAction(ShowCSVTagsAction.ID));
	}

	@Override
	public List<String> getActionIDs() {
		if (actionIDs == null) {
			actionIDs = new ArrayList<String>(1);
			actionIDs.add(ShowCSVTagsAction.ID);
		}
		return actionIDs;
	}

}
