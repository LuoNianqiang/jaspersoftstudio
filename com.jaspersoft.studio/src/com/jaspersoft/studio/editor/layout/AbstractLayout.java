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
package com.jaspersoft.studio.editor.layout;

import net.sf.jasperreports.engine.JRPropertiesHolder;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.property.section.AbstractSection;

/**
 * Empty class that provide some default implementation to the interface ILayout.
 * This implementation are for the layout that doesn't provide configuration interfaces
 * to the children of the container
 */
public abstract class AbstractLayout implements ILayout {
	
	@Override
	public void setData(ANode selectedElement, AbstractSection section) {	
	}
	
	@Override
	public void createControls(Composite parent) {
	}
	
	@Override
	public boolean showAdditionalControls(JRPropertiesHolder elementProperties, JRPropertiesHolder parentProperties) {
		return false;
	}
}