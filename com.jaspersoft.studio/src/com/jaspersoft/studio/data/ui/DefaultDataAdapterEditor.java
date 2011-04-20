/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.data.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.data.DataAdapter;
import com.jaspersoft.studio.data.DataAdapterEditor;

public class DefaultDataAdapterEditor implements DataAdapterEditor {
	
	  DefaultDataAdapterEditorComposite composite = null;
	  
	  public void setDataAdapter(DataAdapter dataAdapter) {
	  	  if (dataAdapter == null) { 
	  		    // dataAdapter should never be null
	  	  }
	  	  this.composite.setDataAdapter(dataAdapter);
	  }

	  public DataAdapter getDataAdapter() {
		    return this.composite.getDataAdapter();
	  }

	  public Composite getComposite(Composite parent, int style, WizardPage wizardPage) {
		    if (composite == null) {
			      composite = new DefaultDataAdapterEditorComposite(parent, style, wizardPage);
		    }
		    return composite;
	  }

	  public String getHelpContextId() {
		    return composite.getHelpContextId();
	  }
}
