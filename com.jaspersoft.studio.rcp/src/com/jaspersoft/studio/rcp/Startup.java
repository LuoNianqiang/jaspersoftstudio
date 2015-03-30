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
package com.jaspersoft.studio.rcp;

import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.wizard.project.ProjectUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.rcp.messages.Messages;

/**
 *
 */
public class Startup implements IStartup {

	public void earlyStartup() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(FileUtils.DEFAULT_PROJECT); //$NON-NLS-1$
		IProgressMonitor monitor = new NullProgressMonitor();
		boolean defaultPrjCreatedOnce = JaspersoftStudioPlugin.getInstance().getPreferenceStore().getBoolean(FileUtils.DEFAULT_PROJECT_PROPERTY);
		try {
			if (!project.exists() && !defaultPrjCreatedOnce) {
				project.create(monitor);
				project.open(monitor);
				ProjectUtil.createJRProject(monitor, project);
				IProjectDescription description = project.getDescription();
				description.setName(Messages.Startup_jss_project);
				project.setDescription(description, monitor);
			}
			JaspersoftStudioPlugin.getInstance().getPreferenceStore().setValue(FileUtils.DEFAULT_PROJECT_PROPERTY, true);
			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
			registry.setDefaultEditor("*.properties", "com.essiembre.rbe.eclipse.editor.ResourceBundleEditor");
		} catch (CoreException e) {
			e.printStackTrace();
		} finally {
			monitor.done();
		}
	}

}
