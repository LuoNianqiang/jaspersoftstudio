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

import java.util.Map;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPropertiesHolder;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.property.section.AbstractSection;

/**
 * Interface to implement to provide a layout strategy inside JSS
 * 
 * @author Orlandin Marco
 */
public interface ILayout {
	
	/**
	 * Key used to save the name of the layout class that implement this in the properties
	 * of the container using this implementation 
	 */
	public static final String KEY = "com.jaspersoft.studio.layout";

	/**
	 * Return the name of this layout
	 */
	public abstract String getName();

	/**
	 * Return the tooltip of this layout
	 */
	public abstract String getToolTip();

	/**
	 * Return the icon path of this layout
	 */
	public abstract String getIcon();

	/**
	 * Layout all the elements of a container. The position of the elements must
	 * be changed by the implementation of this method and the returned map must have
	 * as key each of the element passed and as value the position that  the element had
	 * Before to be layouted
	 * 
	 * @param elements the elements to be layouted, must be not null and all the elements must belong
	 * to the same parent
	 * @param c the dimension of the parent
	 * @return the position of each elements before this layout operation changed their size\location
	 */
	public abstract Map<JRElement, Rectangle> layout(JRElement[] elements, Dimension c);
	
	/**
	 * If the layout provide graphical controls to configure the layout properties of an element,
	 * this is called to set the properties of the element inside the control. The implementation of 
	 * this will be called when a children of an element that is using this implementation as layout is
	 * selected
	 * 
	 * @param selectedElement the currently selected element, it is not null
	 * @param section the currently selected section, it is not null
	 */
	public void setData(ANode selectedElement, AbstractSection section);
	
	/**
	 * Create the controls provided to configure the child element of the container that is using
	 * as layout the implementation of this class
	 * 
	 * @param parent composite where to create the controls, it has a {@link GridLayout} with a single column
	 */
	public void createControls(Composite parent);
	
	/**
	 * The implementation of this will be called when a children of an element that is using this implementation as layout is
	 * selected, and check if that element should have additional control in the layout section because of the parent layout strategy
	 * 
	 * @param elementProperties the properties of the selected element
	 * @param parentProperties the properties of the parent
	 * 
	 * @return true if the element should have additional control in the layout section (created trough createControls implementation),
	 * false otherwise
	 */
	public boolean showAdditionalControls(JRPropertiesHolder elementProperties, JRPropertiesHolder parentProperties) ;
	
}
