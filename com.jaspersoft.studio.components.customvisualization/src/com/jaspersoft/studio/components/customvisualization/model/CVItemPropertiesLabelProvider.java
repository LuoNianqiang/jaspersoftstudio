/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.customvisualization.model;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;

import com.jaspersoft.jasperreports.customvisualization.design.CVDesignComponent;
import com.jaspersoft.studio.components.customvisualization.messages.Messages;

/**
 * Label provider for the {@link CVDesignComponent#PROPERTY_ITEM_PROPERTIES}
 * property in the Advanced property tab.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class CVItemPropertiesLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if(element instanceof List<?>) {
			return NLS.bind(Messages.CVItemPropertiesLabelProvider_TextStr,((List<?>) element).size());
		}
		return super.getText(element);
	}
	
}
