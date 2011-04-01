package com.jaspersoft.studio.property.section.text;

import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.type.LineSpacingEnum;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.utils.EnumHelper;

/**
 * The location section on the location tab.
 * 
 * @author Chicu Veaceslav
 */
public class TextSection extends AbstractSection {

	private CCombo lineSpace;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		GridLayout layout = new GridLayout(50, false);
		composite.setLayout(layout);

		CLabel label = getWidgetFactory().createCLabel(composite, Messages.common_text + ":", SWT.RIGHT); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.widthHint = 100;
		label.setLayoutData(gd);

		lineSpace = new CCombo(composite, SWT.BORDER | SWT.FLAT | SWT.READ_ONLY);
		lineSpace.setItems(EnumHelper.getEnumNames(LineSpacingEnum.values(), NullEnum.INHERITED));
		lineSpace.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				changeProperty(JRBaseStyle.PROPERTY_LINE_SPACING, new Integer(lineSpace.getSelectionIndex()));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		lineSpace.setToolTipText(Messages.TextSection_line_spacing_tool_tip);
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		isRefreshing = true;
		APropertyNode element = getElement();
		if (element != null) {
			lineSpace.select(((Integer) element.getPropertyValue(JRBaseStyle.PROPERTY_LINE_SPACING)).intValue());
		}
		isRefreshing = false;
	}
}
