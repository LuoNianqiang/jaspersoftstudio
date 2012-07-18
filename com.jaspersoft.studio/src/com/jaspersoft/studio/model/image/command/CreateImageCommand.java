/*
 * JasperReports - Free Java Reporting Library. Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of JasperReports.
 * 
 * JasperReports is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JasperReports is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with JasperReports. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.model.image.command;

import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.MElementGroup;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.model.band.MBand;
import com.jaspersoft.studio.model.command.CreateElementCommand;
import com.jaspersoft.studio.model.frame.MFrame;

public class CreateImageCommand extends CreateElementCommand {

	public CreateImageCommand(ANode destNode, MGraphicElement srcNode, Rectangle position, int index) {
		super(destNode, srcNode, position, index);
	}

	public CreateImageCommand(MBand destNode, MGraphicElement srcNode, int index) {
		super(destNode, srcNode, index);
	}

	public CreateImageCommand(MElementGroup destNode, MGraphicElement srcNode, int index) {
		super(destNode, srcNode, index);
	}

	public CreateImageCommand(MFrame destNode, MGraphicElement srcNode, int index) {
		super(destNode, srcNode, index);
	}

	/**
	 * Creates the object.
	 */
	@Override
	protected void createObject() {
		if (getJrElement() == null) {
			if (srcNode.getValue() == null)
				jrElement = srcNode.createJRElement(srcNode.getJasperDesign());
			else
				jrElement = (JRDesignElement) srcNode.getValue();

			if (jrElement != null)
				setElementBounds();

			FilteredResourcesSelectionDialog fd = new FilteredResourcesSelectionDialog(Display.getCurrent().getActiveShell(),
					false, ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
			fd.setInitialPattern("*.png");//$NON-NLS-1$
			if (fd.open() == Dialog.OK) {
				IFile file = (IFile) fd.getFirstResult();

				JRDesignExpression jre = new JRDesignExpression();
				if (file.getFileExtension().equals("svg")) {
					jre.setText("net.sf.jasperreports.renderers.BatikRenderer.getInstanceFromLocation(\"" + file.getProjectRelativePath().toPortableString() + "\")");//$NON-NLS-1$ //$NON-NLS-2$
				} else

					jre.setText("\"" + file.getProjectRelativePath().toPortableString() + "\"");//$NON-NLS-1$ //$NON-NLS-2$
				((JRDesignImage) jrElement).setExpression(jre);
			}
		}
	}
}
