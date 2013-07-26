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
package com.jaspersoft.studio.data.sql.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.wb.swt.SWTResourceManager;

import com.jaspersoft.studio.data.sql.QueryWriter;
import com.jaspersoft.studio.data.sql.model.query.from.MFrom;
import com.jaspersoft.studio.data.sql.model.query.from.MFromTable;
import com.jaspersoft.studio.data.sql.ui.gef.layout.GraphLayoutManager;
import com.jaspersoft.studio.data.sql.ui.gef.policy.FromContainerEditPolicy;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.model.util.ModelVisitor;

public class FromEditPart extends AbstractGraphicalEditPart {

	private static final Insets INSETS = new Insets(10, 10, 10, 10);

	@Override
	protected IFigure createFigure() {
		// FreeformLayeredPane fig = new FreeformLayeredPane();
		// FreeformLayer fig = new FreeformLayer();
		RectangleFigure fig = new RectangleFigure() {
			@Override
			public Insets getInsets() {
				return INSETS;
			}
		};
		// fig.setLocation(new Point(0, 0));
		// fig.setSize(10000, 10000);
		fig.setLayoutManager(new GraphLayoutManager(this));
		// FreeformLayout layout = new FreeformLayout();
		// layout.setPositiveCoordinates(true);
		// fig.setLayoutManager(layout);
		fig.setBackgroundColor(SWTResourceManager.getColor(248, 248, 255));
		fig.setOpaque(true);
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new FromContainerEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, null);
	}

	@Override
	protected List getModelChildren() {
		final List<ANode> list = new ArrayList<ANode>();
		new ModelVisitor<ANode>((INode) getModel()) {

			@Override
			public boolean visit(INode n) {
				if (n instanceof MFromTable)
					list.add((ANode) n);
				return true;
			}
		};
		return list;
	}

	protected void refreshVisuals() {
		MFrom mfrom = (MFrom) getModel();
		getFigure().setToolTip(new Label(QueryWriter.writeSubQuery(mfrom.getParent())));
		getParent();
	}

}
