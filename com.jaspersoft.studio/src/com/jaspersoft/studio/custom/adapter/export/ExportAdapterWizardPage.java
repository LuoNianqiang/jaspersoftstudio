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
package com.jaspersoft.studio.custom.adapter.export;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.pde.core.IModel;
import org.eclipse.pde.internal.ui.wizards.exports.PluginExportWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.jaspersoft.studio.messages.Messages;

/**
 * Custom page to provide the informations to export a data adapter
 * plugin project as a jar file. It hides some values from the original
 * page, to allow the adapter to be exported easily
 * 
 * @author Orlandin Marco
 *
 */
@SuppressWarnings("restriction")
public class ExportAdapterWizardPage extends PluginExportWizardPage {
	
	/**
	 * Map to retrieve a project from its model
	 */
	private HashMap<IModel, IProject> modelProjectsMap = new HashMap<IModel, IProject>();
	
	/**
	 * Save the qualifier id for the manifest that is automatically generated
	 */
	private String storedQualifier;
	
	public ExportAdapterWizardPage(IStructuredSelection selection) {
		super(selection);
		setTitle(Messages.ExportAdapterWizardPage_title);
		setDescription(Messages.ExportAdapterWizardPage_description);
	}

	/**
	 * Keep only the path controls and dispose the others, but before
	 * store the qualifier generated by the option controls
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		//force the option controls to generate the qualifier
		getDialogSettings().put("qualifier", true); //$NON-NLS-1$
		storedQualifier = super.getQualifier();
		fTabFolder.getTabList()[1].dispose();
		fTabFolder.getTabList()[1].dispose();
		fTabFolder.getItems()[1].dispose();
		fTabFolder.getItems()[1].dispose();
		pageChanged();
	}
	
	/**
	 * Create the destination tab with an extended one
	 */
	protected void createDestinationTab(TabFolder folder) {
		fDestinationTab = new SimpleDestinationTab(this);
		TabItem item = new TabItem(folder, SWT.NONE);
		item.setControl(fDestinationTab.createControl(folder));
		item.setText(Messages.ExportAdapterWizardPage_destinationTab);
	}
	
	/**
	 * Save the setting only of the destination tab, since the other
	 * are disposed
	 */
	@Override
	protected void saveSettings(IDialogSettings settings) {
		((SimpleDestinationTab)fDestinationTab).saveSettings(settings);
	}
	
	/**
	 * Return the stored qualifier
	 */
	protected String getQualifier() {
		return storedQualifier;
	}
	
	/**
	 * Return the destination path of the jar file
	 */
	@Override
	public String getDestination() {
		return super.getDestination();
	}
	
	/**
	 * Return the file name of the jar file
	 */
	@Override
	public String getFileName() {
		return super.getFileName();
	}
	
	/**
	 * Validate the content of the page
	 */
	@Override
	protected void pageChanged() {
		if (getMessage() != null)
			setMessage(null);
		String error = getSelectedItems().length > 0 ? null : Messages.ExportAdapterWizardPage_noElementError;
		if (error == null)
			error = ((SimpleDestinationTab)fDestinationTab).validate();
		setErrorMessage(error);
		setPageComplete(error == null);
	}
	
	/**
	 * When the viewer is initialized then 
	 * the model-project map is cleaned and recreated
	 */
	@Override
	protected void initializeViewer() {
		modelProjectsMap.clear();
		super.initializeViewer();
	}
	
	/**
	 * When searching a model for an object, if the object
	 * is an IProject then store the association between
	 * the model and the project. Doing this will be possible
	 * during the finish phase to recover the project from the model
	 */
	@Override
	protected IModel findModelFor(IAdaptable object) {
		if (object instanceof IProject){
			IModel model = super.findModelFor(object);
			modelProjectsMap.put(model, (IProject)object);
			return model;
		}
		return super.findModelFor(object);
	}
	
	/**
	 * Return the project from the model of the project
	 * itself
	 * 
	 * @param model a not null model of a project
	 * @return the project
	 */
	public IProject getProjectForModel(IModel model){
		return modelProjectsMap.get(model);
	}


	// Fix some getter methods to return a static value according to the adapter plugin project export
	
	protected boolean doExportSource() {
		return false;
	}

	protected boolean doExportSourceBundles() {
		return false;
	}

	protected boolean useJARFormat() {
		return true;
	}

	protected boolean allowBinaryCycles() {
		return true;
	}

	protected boolean useWorkspaceCompiledClasses() {
		return false;
	}

	protected boolean doGenerateAntFile() {
		return false;
	}
	
	protected String getAntBuildFileName() {
		return null;
	}
}
