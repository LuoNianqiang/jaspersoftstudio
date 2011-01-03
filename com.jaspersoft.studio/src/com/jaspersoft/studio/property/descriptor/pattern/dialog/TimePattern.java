/*
 * Jaspersoft Open Studio - Eclipse-based JasperReports Designer.
 * Copyright (C) 2005 - 2010 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of Jaspersoft Open Studio.
 *
 * Jaspersoft Open Studio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jaspersoft Open Studio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Jaspersoft Open Studio. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.property.descriptor.pattern.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.messages.Messages;

public class TimePattern extends DatePattern {

	public TimePattern(Composite parent) {
		super(parent);
		setDescription(Messages.TimePattern_description);
	}

	@Override
	protected List<String> getDefaults() {
		if (dList == null) {
			dList = new ArrayList<String>();
			dList.add("h:mm a"); //$NON-NLS-1$
			dList.add("h:mm:ss a"); //$NON-NLS-1$
			dList.add("h:mm:ss a z"); //$NON-NLS-1$
			dList.add("HH:mm a"); //$NON-NLS-1$
			dList.add("HH:mm:ss a"); //$NON-NLS-1$
			dList.add("HH:mm:ss zzzz"); //$NON-NLS-1$
			setPattern(dList.get(0));
		}
		return dList;
	}

}
