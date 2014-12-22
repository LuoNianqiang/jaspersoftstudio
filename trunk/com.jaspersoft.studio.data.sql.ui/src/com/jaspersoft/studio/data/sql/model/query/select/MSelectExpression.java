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
package com.jaspersoft.studio.data.sql.model.query.select;

import net.sf.jasperreports.engine.JRConstants;

import com.jaspersoft.studio.data.sql.model.query.AMQueryAliased;
import com.jaspersoft.studio.model.ANode;

public class MSelectExpression extends AMQueryAliased<String> {

	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MSelectExpression(ANode parent, String value) {
		super(parent, value, null);
	}

	public MSelectExpression(ANode parent, String value, int index) {
		super(parent, value, null, index);
	}

	@Override
	public String getToolTip() {
		return getValue() + addAlias();
	}

}
