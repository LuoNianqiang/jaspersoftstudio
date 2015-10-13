/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 ******************************************************************************/
package com.jaspersoft.studio.components.customvisualization.model.command;

import java.io.IOException;
import java.util.List;

import net.sf.jasperreports.components.map.StandardItem;
import net.sf.jasperreports.components.map.StandardItemData;
import net.sf.jasperreports.components.map.StandardItemProperty;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.viewer.BrowserUtils;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.jasperreports.customvisualization.design.CVDesignComponent;
import com.jaspersoft.studio.components.customvisualization.CVComponentUtil;
import com.jaspersoft.studio.components.customvisualization.messages.Messages;
import com.jaspersoft.studio.components.customvisualization.model.MCustomVisualization;
import com.jaspersoft.studio.components.customvisualization.ui.ComponentDatasetDescriptor;
import com.jaspersoft.studio.components.customvisualization.ui.ComponentDescriptor;
import com.jaspersoft.studio.components.customvisualization.ui.ComponentPropertyDescriptor;
import com.jaspersoft.studio.components.customvisualization.ui.ComponentSectionDescriptor;
import com.jaspersoft.studio.components.customvisualization.ui.UIManager;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.MElementGroup;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.model.band.MBand;
import com.jaspersoft.studio.model.command.CreateElementCommand;
import com.jaspersoft.studio.model.frame.MFrame;
import com.jaspersoft.studio.utils.Misc;

/**
 * Create command for the Custom Visualization component element.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class CreateCustomVisualizationCommand extends CreateElementCommand {

	public CreateCustomVisualizationCommand(ANode destNode,
			MGraphicElement srcNode, Rectangle position, int index) {
		super(destNode, srcNode, position, index);
	}

	public CreateCustomVisualizationCommand(MBand destNode,
			MGraphicElement srcNode, int index) {
		super(destNode, srcNode, index);
	}

	public CreateCustomVisualizationCommand(MElementGroup destNode,
			MGraphicElement srcNode, int index) {
		super(destNode, srcNode, index);
	}

	public CreateCustomVisualizationCommand(MFrame destNode,
			MGraphicElement srcNode, int index) {
		super(destNode, srcNode, index);
	}

	public CreateCustomVisualizationCommand(MFrame destNode,
			MGraphicElement srcNode, Rectangle position, int index) {
		super(destNode, srcNode, position, index);
	}

	@Override
	protected void createObject() {
		if (jConfig != null) {
			try {
				Runtime.getRuntime().exec(
						CVComponentUtil.PHANTOMJS_VERSIONCHECK_CMD);
			} catch (IOException e) {
				// PhantomJS is not configured, show warning if necessary
				Boolean showWarning = jConfig.getPropertyBoolean(
						CVComponentUtil.PHANTOJS_WARNING_PROPERTY, true);
				if (Boolean.TRUE.equals(showWarning)) {
					String phantomJSInstallPath = jConfig
							.getProperty(CVComponentUtil.PHANTOMJS_INSTALLATION_PATH_PROPERTY);
					if (StringUtils.isBlank(phantomJSInstallPath)) {
						new HyperlinkInfoDialog(
								UIUtils.getShell(),
								Messages.CreateCustomVisualizationCommand_WarningTitle,
								NLS.bind(
										Messages.CreateCustomVisualizationCommand_WarningMessage,
										CVComponentUtil.PHANTOMJS_INSTALLATION_PATH_PROPERTY),
								CVComponentUtil.PHANTOMJS_URL_DOWNLOAD).open();
					}
				}
			}
		}
		if (jrElement == null) {
			srcNode = new MCustomVisualization();
			jrElement = srcNode.createJRElement(jasperDesign);

			// let's check in our preferences if we have some modules
			// if yes, let's start a wizard
			List<ComponentDescriptor> modules = UIManager.getModules(jConfig);
			if (!Misc.isNullOrEmpty(modules)) {
				CVCWizard wizard = new CVCWizard(modules);
				wizard.setConfig(jConfig);
				WizardDialog d = new WizardDialog(UIUtils.getShell(), wizard);
				if (d.open() == Dialog.OK) {
					ComponentDescriptor m = wizard.getModule();
					CVDesignComponent cvComp = (CVDesignComponent) ((JRDesignComponentElement) jrElement)
							.getComponent();
					if (m.getSections() != null)
						for (ComponentSectionDescriptor csd : m.getSections()) {
							if (csd.getProperties() != null)
								for (ComponentPropertyDescriptor cpd : csd
										.getProperties()) {
									if (cpd.isMandatory()) {
										cvComp.addItemProperty(new StandardItemProperty(
												cpd.getName(), cpd
														.getDefaultValue(),
												null));
										if (cpd.getType().equalsIgnoreCase(
												"path"))
											UIManager.copyFile(m, jConfig,
													cpd.getDefaultValue());
									}
								}
						}
					// build default item data with default values
					List<ComponentDatasetDescriptor> ds = m.getDatasets();
					if (ds != null)
						for (ComponentDatasetDescriptor cdd : ds) {
							if (cdd.getCardinality() >= 0)
								for (int i = 0; i < cdd.getCardinality(); i++) {
									StandardItemData id = new StandardItemData();
									if (cdd.getSections() != null)
										for (ComponentSectionDescriptor csd : cdd
												.getSections())
											if (!Misc.isNullOrEmpty(csd
													.getProperties())) {
												StandardItem item = new StandardItem();
												id.addItem(item);
												for (ComponentPropertyDescriptor cpd : csd
														.getProperties())
													if (cpd.isMandatory())
														item.addItemProperty(new StandardItemProperty(
																cpd.getName(),
																cpd.getDefaultValue(),
																null));
											}
									cvComp.addItemData(id);
									if (ds.get(ds.size() - 1) == cdd)
										break;
								}

						}

				}
			}
		}
		if (jrElement != null)
			setElementBounds();
	}

	private class HyperlinkInfoDialog extends MessageDialog {

		private String url;

		public HyperlinkInfoDialog(Shell parentShell, String dialogTitle,
				String dialogMessage, String url) {
			super(parentShell, dialogTitle, null, dialogMessage,
					MessageDialog.WARNING,
					new String[] { IDialogConstants.OK_LABEL }, 0);
			this.url = url;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			final StyledText issueLink = new StyledText(parent, SWT.READ_ONLY);
			issueLink.setText(url);
			issueLink.setBackground(parent.getBackground());
			issueLink.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true,
					false, 2, 1));

			StyleRange style = new StyleRange();
			style.underline = true;
			style.underlineStyle = SWT.UNDERLINE_LINK;
			int[] ranges = { 0, url.length() };
			StyleRange[] styles = { style };
			issueLink.setStyleRanges(ranges, styles);

			issueLink.addListener(SWT.MouseDown, new Listener() {
				@Override
				public void handleEvent(Event event) {
					try {
						int offset = issueLink.getOffsetAtLocation(new Point(
								event.x, event.y));
						StyleRange style = issueLink
								.getStyleRangeAtOffset(offset);
						if (style != null && style.underline
								&& style.underlineStyle == SWT.UNDERLINE_LINK) {
							BrowserUtils.openExternalBrowser(url);
						}
					} catch (IllegalArgumentException e) {
						// no character under event.x, event.y
					}
				}
			});

			final Button doNotShowAgainBtn = new Button(parent, SWT.CHECK);
			doNotShowAgainBtn
					.setText(Messages.CreateCustomVisualizationCommand_ShowOrNot);
			doNotShowAgainBtn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (doNotShowAgainBtn.getSelection()) {
						jConfig.setProperty(
								CVComponentUtil.PHANTOJS_WARNING_PROPERTY,
								"false"); //$NON-NLS-1$
					} else {
						jConfig.setProperty(
								CVComponentUtil.PHANTOJS_WARNING_PROPERTY,
								"true"); //$NON-NLS-1$
					}
				}
			});

			return issueLink;
		}

	}
}
