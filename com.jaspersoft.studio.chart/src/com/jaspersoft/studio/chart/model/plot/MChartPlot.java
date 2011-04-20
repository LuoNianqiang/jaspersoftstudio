/*
 * Jaspersoft Open Studio - Eclipse-based JasperReports Designer. Copyright (C) 2005 - 2010 Jaspersoft Corporation. All
 * rights reserved. http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of Jaspersoft Open Studio.
 * 
 * Jaspersoft Open Studio is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Jaspersoft Open Studio is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with Jaspersoft Open Studio. If not,
 * see <http://www.gnu.org/licenses/>.
 */
/*
 * Jaspersoft Open Studio - Eclipse-based JasperReports Designer. Copyright (C) 2005 - 2010 Jaspersoft Corporation. All
 * rights reserved. http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of iReport.
 * 
 * iReport is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * iReport is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with iReport. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.chart.model.plot;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRChartPlot.JRSeriesColor;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.jfree.chart.plot.PlotOrientation;

import com.jaspersoft.studio.chart.messages.Messages;
import com.jaspersoft.studio.chart.property.descriptor.seriescolor.SeriesColorPropertyDescriptor;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.property.descriptor.DoublePropertyDescriptor;
import com.jaspersoft.studio.property.descriptor.FloatPropertyDescriptor;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.property.descriptor.color.ColorPropertyDescriptor;
import com.jaspersoft.studio.utils.Colors;

public class MChartPlot extends APropertyNode {

	public MChartPlot(JRChartPlot value) {
		super();
		setValue(value);
	}

	@Override
	public void createPropertyDescriptors(List<IPropertyDescriptor> desc, Map<String, Object> defaultsMap) {
		ColorPropertyDescriptor backcolorD = new ColorPropertyDescriptor(JRBaseChartPlot.PROPERTY_BACKCOLOR, Messages.MChartPlot_backcolor,
				NullEnum.INHERITED);
		backcolorD.setDescription(Messages.MChartPlot_backcolor_description);
		desc.add(backcolorD);

		FloatPropertyDescriptor backAlphaD = new FloatPropertyDescriptor(JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA,
				Messages.MChartPlot_background_alpha_percent);
		backAlphaD.setDescription(Messages.MChartPlot_background_alpha_percent_description);
		desc.add(backAlphaD);

		FloatPropertyDescriptor foreAlphaD = new FloatPropertyDescriptor(JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA,
				Messages.MChartPlot_foreground_alpha_percent);
		foreAlphaD.setDescription(Messages.MChartPlot_foreground_alpha_percent_description);
		desc.add(foreAlphaD);

		DoublePropertyDescriptor labelRotationD = new DoublePropertyDescriptor(JRBaseChartPlot.PROPERTY_LABEL_ROTATION,
				Messages.MChartPlot_label_rotation);
		labelRotationD.setDescription(Messages.MChartPlot_label_rotation_description);
		desc.add(labelRotationD);

		ComboBoxPropertyDescriptor orientationD = new ComboBoxPropertyDescriptor(JRBaseChartPlot.PROPERTY_ORIENTATION,
				Messages.MChartPlot_orientation, new String[] { Messages.MChartPlot_horizontal, Messages.MChartPlot_vertical });
		orientationD.setDescription(Messages.MChartPlot_orientation_description);
		desc.add(orientationD);

		SeriesColorPropertyDescriptor scpd = new SeriesColorPropertyDescriptor(JRBaseChartPlot.PROPERTY_SERIES_COLORS,
				Messages.MChartPlot_series_colors);
		scpd.setDescription(Messages.MChartPlot_series_colors_description);
		desc.add(scpd);

		defaultsMap.put(JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA, null);
		defaultsMap.put(JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA, null);
	}

	private static IPropertyDescriptor[] descriptors;
	private static Map<String, Object> defaultsMap;

	@Override
	public Map<String, Object> getDefaultsMap() {
		return defaultsMap;
	}

	@Override
	public IPropertyDescriptor[] getDescriptors() {
		return descriptors;
	}

	@Override
	public void setDescriptors(IPropertyDescriptor[] descriptors1, Map<String, Object> defaultsMap1) {
		descriptors = descriptors1;
		defaultsMap = defaultsMap1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id) {
		JRBaseChartPlot jrElement = (JRBaseChartPlot) getValue();
		if (id.equals(JRBaseChartPlot.PROPERTY_BACKCOLOR))
			return Colors.getSWTRGB4AWTGBColor(jrElement.getOwnBackcolor());
		if (id.equals(JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA))
			return jrElement.getBackgroundAlphaFloat();
		if (id.equals(JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA))
			return jrElement.getForegroundAlphaFloat();
		if (id.equals(JRBaseChartPlot.PROPERTY_LABEL_ROTATION))
			return jrElement.getLabelRotationDouble();
		if (id.equals(JRBaseChartPlot.PROPERTY_ORIENTATION)) {
			if (jrElement.getOrientation().equals(PlotOrientation.HORIZONTAL))
				return 0;
			else
				return 1;
		}
		if (id.equals(JRBaseChartPlot.PROPERTY_SERIES_COLORS))
			return jrElement.getSeriesColors();

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value) {
		JRBaseChartPlot jrElement = (JRBaseChartPlot) getValue();
		if (id.equals(JRBaseChartPlot.PROPERTY_BACKCOLOR)) {
			if (value instanceof RGB)
				jrElement.setBackcolor(Colors.getAWT4SWTRGBColor((RGB) value));
		} else if (id.equals(JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA))
			jrElement.setBackgroundAlpha((Float) value);
		else if (id.equals(JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA))
			jrElement.setForegroundAlpha((Float) value);
		else if (id.equals(JRBaseChartPlot.PROPERTY_LABEL_ROTATION))
			jrElement.setLabelRotation((Double) value);
		else if (id.equals(JRBaseChartPlot.PROPERTY_ORIENTATION)) {
			if (value.equals(new Integer(0)))
				jrElement.setOrientation(PlotOrientation.HORIZONTAL);
			else
				jrElement.setOrientation(PlotOrientation.VERTICAL);
		} else if (id.equals(JRBaseChartPlot.PROPERTY_SERIES_COLORS)) {
			jrElement.clearSeriesColors();
			SortedSet set = (SortedSet) value;
			for (Iterator it = set.iterator(); it.hasNext();) {
				jrElement.addSeriesColor((JRSeriesColor) it.next());
			}
		}
	}

	public ImageDescriptor getImagePath() {
		return null;
	}

	public String getDisplayText() {
		return null;
	}

}
