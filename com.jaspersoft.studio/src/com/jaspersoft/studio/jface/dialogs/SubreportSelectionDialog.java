/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved. http://www.jaspersoft.com.
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package com.jaspersoft.studio.jface.dialogs;

import org.eclipse.swt.widgets.Shell;

/**
 * Dialog proposed when an image needs to be selected.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class SubreportSelectionDialog extends FileSelectionDialog {

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SubreportSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * @return the title for the dialog
	 */
	protected String getDialogTitle() {
		return "Select a subreport";
	}

	/**
	 * Returns an array of strings containing the title for the modes section, plus the title of every mode.
	 * <p>
	 * 
	 * Default implementation would return 6 strings, including 1 title and the following 5 modes:
	 * <ol>
	 * <li>workspace resource;</li>
	 * <li>absolute path in filesystem;</li>
	 * <li>URL;</li>
	 * <li>no image;</li>
	 * <li>custom expression</li>
	 * </ol>
	 * 
	 * @return the title and labels for the group of modes
	 */
	protected String[] getImageModesAndHeaderTitles() {
		return new String[] { "Subreport selection mode", "Workspace resource (an element inside the workspace)",
				"Absolute Path in the filesystem (use only for quick testing, never use in real reports)",
				"URL (a remote URL referring to a subreport, will be the expression value)",
				"No subreport (no subreport reference will be set)",
				"Custom expression (enter an expression for the subreport using the expression editor)" };
	}

	@Override
	protected String getFileExtension() {
		return "*.jrxml";
	}

	@Override
	protected String[] getFileExtensions() {
		return new String[] { "*.jrxml", "*.*" };
	}
}
