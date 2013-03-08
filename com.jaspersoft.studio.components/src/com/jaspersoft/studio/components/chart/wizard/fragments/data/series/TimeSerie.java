/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.components.chart.wizard.fragments.data.series;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.charts.JRTimeSeries;
import net.sf.jasperreports.charts.design.JRDesignTimeSeries;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class TimeSerie implements ISeriesFactory<JRTimeSeries> {

	public JRDesignTimeSeries createSerie() {
		return createSerie(new JRDesignExpression("\"SERIE 1\""), null);
	}

	@Override
	public JRDesignTimeSeries createSerie(JRDesignExpression expr,
			JRTimeSeries prev) {
		JRDesignTimeSeries f = new JRDesignTimeSeries();
		f.setSeriesExpression(expr);
		if (prev == null) {
			f.setTimePeriodExpression(new JRDesignExpression("0"));
			f.setValueExpression(new JRDesignExpression("0"));
		} else {
			f.setTimePeriodExpression(prev.getTimePeriodExpression());
			f.setValueExpression(prev.getValueExpression());
			f.setLabelExpression(prev.getLabelExpression());
		}
		return f;
	}

	public String getColumnText(Object element, int columnIndex) {
		JRTimeSeries dcs = (JRTimeSeries) element;
		switch (columnIndex) {
		case 0:
			if (dcs.getSeriesExpression() != null
					&& dcs.getSeriesExpression().getText() != null)
				return dcs.getSeriesExpression().getText();
		}
		return ""; //$NON-NLS-1$
	}

	public Object getValue(JRTimeSeries element, String property) {
		JRTimeSeries prop = (JRTimeSeries) element;
		if ("NAME".equals(property)) { //$NON-NLS-1$
			return prop.getSeriesExpression();
		}
		return ""; //$NON-NLS-1$
	}

	public void modify(JRTimeSeries element, String property, Object value) {
		JRDesignTimeSeries data = (JRDesignTimeSeries) element;
		if ("NAME".equals(property) && value instanceof JRExpression) //$NON-NLS-1$
			data.setSeriesExpression((JRExpression) value);
	}

	private List<JRTimeSeries> vlist;

	public List<JRTimeSeries> getList() {
		return vlist;
	}

	public void setList(List<JRTimeSeries> vlist) {
		this.vlist = new ArrayList<JRTimeSeries>(vlist);
	}
}
