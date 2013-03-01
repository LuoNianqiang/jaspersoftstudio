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
package com.jaspersoft.studio.components.chart.wizard.fragments.data;

import java.util.List;

import net.sf.jasperreports.charts.JRTimeSeries;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.charts.type.TimePeriodEnum;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementDataset;
import net.sf.jasperreports.engine.export.draw.DrawVisitor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.components.chart.wizard.fragments.data.dialog.SeriesDialog;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.series.TimeSerie;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.widget.DatasetSeriesWidget;
import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.property.dataset.ExpressionWidget;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.utils.EnumHelper;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class DSTimeSeries extends ADSComponent {

	private JRDesignTimeSeriesDataset dataset;
	private ExpressionWidget valueWidget;
	private ExpressionWidget timePeriod;
	private ExpressionWidget labelWidget;
	private Combo seriesCombo;
	private Combo timePeriodCombo;

	public DSTimeSeries(Composite composite, DatasetSeriesWidget dsWidget) {
		super(composite, dsWidget);
	}

	@Override
	public String getName() {
		return "Time Series Dataset";
	}

	@Override
	public void setData(DrawVisitor drawVisitor, JRDesignElement jrChart,
			JRDesignElementDataset eDataset,
			JasperReportsConfiguration jrContext) {
		Assert.isTrue(eDataset instanceof JRDesignTimeSeriesDataset);
		super.setData(drawVisitor, jrChart, eDataset, jrContext);
		dataset = (JRDesignTimeSeriesDataset) eDataset;
		setSeries(0);

		TimePeriodEnum tpe = TimePeriodEnum.getByValue(dataset.getTimePeriod());
		if (tpe == null) {
			timePeriodCombo.select(5);
		} else {
			TimePeriodEnum[] tpevalues = TimePeriodEnum.values();
			for (int i = 0; i < tpevalues.length; i++) {
				if (tpe.equals(tpevalues[i])) {
					timePeriodCombo.select(i + 1);
					break;
				}
			}
		}
	}

	private void setSeries(int selection) {
		List<JRTimeSeries> seriesList = dataset.getSeriesList();
		if (!seriesList.isEmpty()) {
			String[] srnames = new String[seriesList.size()];
			for (int i = 0; i < seriesList.size(); i++) {
				JRTimeSeries cs = seriesList.get(i);
				JRExpression se = cs.getSeriesExpression();
				srnames[i] = se != null && se.getText() != null ? se.getText()
						: "";
			}
			seriesCombo.setItems(srnames);
			seriesCombo.select(selection);
			handleSelectSeries(selection);
		} else {
			seriesCombo.setItems(new String[0]);
			handleSelectSeries(-1);
		}
	}

	private void handleSelectSeries(int selection) {
		JRTimeSeries serie = null;
		if (selection >= 0 && selection < dataset.getSeriesList().size())
			serie = dataset.getSeriesList().get(selection);

		valueWidget.bindObject(serie, "ValueExpression");
		timePeriod.bindObject(serie, "TimePeriodExpression");
		labelWidget.bindObject(serie, "LabelExpression");
	}

	protected Control createChartTop(Composite composite) {
		Composite yCompo = new Composite(composite, SWT.NONE);
		yCompo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		yCompo.setLayout(new GridLayout(10, false));

		Label lbl = new Label(yCompo, SWT.NONE);
		lbl.setText("Series");

		seriesCombo = new Combo(yCompo, SWT.READ_ONLY | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		seriesCombo.setLayoutData(gd);
		seriesCombo.setItems(new String[] { "series 1" });
		seriesCombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				handleSelectSeries(seriesCombo.getSelectionIndex());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		final Button btn = new Button(yCompo, SWT.PUSH | SWT.FLAT);
		btn.setText("...");
		btn.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				TimeSerie serie = new TimeSerie();
				SeriesDialog dlg = new SeriesDialog(btn.getShell(), serie);
				dlg.setExpressionContext(expContext);
				List<JRTimeSeries> oldList = dataset.getSeriesList();
				int oldsel = seriesCombo.getSelectionIndex();
				JRTimeSeries selected = null;
				if (oldsel >= 0)
					selected = oldList.get(oldsel);
				serie.setList(oldList);
				if (dlg.open() == Window.OK) {
					List<JRTimeSeries> newlist = serie.getList();
					for (JRTimeSeries item : dataset.getSeries())
						dataset.removeTimeSeries(item);
					for (JRTimeSeries item : serie.getList())
						dataset.addTimeSeries(item);

					int sel = selected != null
							&& newlist.indexOf(selected) >= 0 ? newlist
							.indexOf(selected) : 0;
					setSeries(sel);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return yCompo;
	}

	@Override
	protected Control createChartLeft(Composite parent) {
		Composite yCompo = new Composite(parent, SWT.NONE);
		yCompo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		yCompo.setLayout(new GridLayout(3, false));

		valueWidget = new ExpressionWidget(yCompo, "Value");
		return yCompo;
	}

	@Override
	protected Control createChartBottom(Composite parent) {
		Composite yCompo = new Composite(parent, SWT.NONE);
		yCompo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		yCompo.setLayout(new GridLayout(10, false));

		timePeriod = new ExpressionWidget(yCompo, "Time Period Expression");

		Label lbl = new Label(yCompo, SWT.NONE);
		lbl.setText("TimePeriod");

		timePeriodCombo = new Combo(yCompo, SWT.SINGLE | SWT.READ_ONLY
				| SWT.BORDER);
		timePeriodCombo.setItems(EnumHelper.getEnumNames(
				TimePeriodEnum.values(), NullEnum.NULL));
		timePeriodCombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if (timePeriodCombo.getSelectionIndex() == 0)
					dataset.setTimePeriod(null);
				else {
					TimePeriodEnum tpe = TimePeriodEnum.values()[timePeriodCombo
							.getSelectionIndex() - 1];
					dataset.setTimePeriod(tpe.getTimePeriod());
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return yCompo;
	}

	@Override
	protected Control createChartRight(Composite parent) {
		Composite yCompo = new Composite(parent, SWT.NONE);
		yCompo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		yCompo.setLayout(new GridLayout(3, false));

		labelWidget = new ExpressionWidget(yCompo, "Label");
		return yCompo;
	}

	@Override
	public void setExpressionContext(ExpressionContext expContext) {
		super.setExpressionContext(expContext);
		this.labelWidget.setExpressionContext(expContext);
		this.timePeriod.setExpressionContext(expContext);
		this.valueWidget.setExpressionContext(expContext);
	}
	
}
