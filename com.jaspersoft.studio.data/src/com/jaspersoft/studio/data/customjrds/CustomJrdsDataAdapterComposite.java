/*
 * JasperReports - Free Java Reporting Library. Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of JasperReports.
 * 
 * JasperReports is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JasperReports is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with JasperReports. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.data.customjrds;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.ds.DataSourceDataAdapter;

import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.messages.Messages;
import com.jaspersoft.studio.swt.widgets.ClassType;
import com.jaspersoft.studio.swt.widgets.ClasspathComponent;

public class CustomJrdsDataAdapterComposite extends ADataAdapterComposite {

	private ClassType textFactoryClass;
	private Text textMethodToCall;
	private ClasspathComponent cpath;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CustomJrdsDataAdapterComposite(Composite parent, int style) {

		/*
		 * UI ELEMENTS
		 */
		super(parent, style);
		setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText(Messages.CustomJrdsDataAdapterComposite_0);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		lblNewLabel.setLayoutData(gd);

		textFactoryClass = new ClassType(this,
				Messages.CustomJrdsDataAdapterComposite_1);
		textFactoryClass
				.setClassType(Messages.CustomJrdsDataAdapterComposite_2);

		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setText(Messages.CustomJrdsDataAdapterComposite_3);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		lblNewLabel_1.setLayoutData(gd);

		textMethodToCall = new Text(this, SWT.BORDER);
		textMethodToCall.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		textMethodToCall.setLayoutData(gd);

		cpath = new ClasspathComponent(this);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		cpath.getControl().setLayoutData(gd);
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(SWTObservables.observeText(
				textFactoryClass.getControl(), SWT.Modify), PojoObservables
				.observeValue(dataAdapter, "factoryClass")); //$NON-NLS-1$

		bindingContext.bindValue(
				SWTObservables.observeText(textMethodToCall, SWT.Modify),
				PojoObservables.observeValue(dataAdapter, "methodToCall")); //$NON-NLS-1$

		DataSourceDataAdapter dsDataAdapter = (DataSourceDataAdapter) dataAdapter;

		cpath.setClasspaths(dsDataAdapter.getClasspath());
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null) {
			dataAdapterDesc = new CustomJrdsDataAdapterDescriptor();
		}

		DataSourceDataAdapter dsDataAdapter = (DataSourceDataAdapter) dataAdapterDesc
				.getDataAdapter();

		dsDataAdapter.setFactoryClass(textFactoryClass.getClassType().trim());
		dsDataAdapter.setMethodToCall(textMethodToCall.getText().trim());

		dsDataAdapter.setClasspath(cpath.getClasspaths());

		return dataAdapterDesc;
	}
}
