/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.cassandra;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.engine.JasperReportsContext;

import org.eclipse.swt.graphics.Image;

import com.jaspersoft.connectors.cassandra.adapter.CassandraDataAdapter;
import com.jaspersoft.connectors.cassandra.adapter.CassandraDataAdapterImpl;
import com.jaspersoft.connectors.cassandra.adapter.CassandraDataAdapterService;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterFactory;
import com.jaspersoft.studio.data.adapter.IDataAdapterCreator;
import com.jaspersoft.studio.data.cassandra.messages.Messages;

/**
 * @author gtoffoli
 * 
 */
public class CassandraDataAdapterFactory implements DataAdapterFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#createDataAdapter()
	 */
	public DataAdapterDescriptor createDataAdapter() {
		CassandraDataAdapterDescriptor descriptor = new CassandraDataAdapterDescriptor();
		descriptor.getDataAdapter().setHostname("HOST"); //$NON-NLS-1$
		descriptor.getDataAdapter().setKeyspace("KEYSPACE"); //$NON-NLS-1$
		descriptor.getDataAdapter().setPort(9042); 
		return descriptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jaspersoft.studio.data.DataAdapterFactory#getDataAdapterClassName()
	 */
	public String getDataAdapterClassName() {
		return CassandraDataAdapterImpl.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getDescription()
	 */
	public String getLabel() {
		return Messages.CassandraDataAdapterFactory_0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getDescription()
	 */
	public String getDescription() {
		return Messages.CassandraDataAdapterFactory_1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getIcon(int)
	 */
	public Image getIcon(int size) {
		if (size == 16) {
			return Activator.getDefault().getImage("icons/cassandra.png"); //$NON-NLS-1$
		}
		return null;
	}

	public DataAdapterService createDataAdapterService(JasperReportsContext jasperReportsContext, DataAdapter dataAdapter) {
		if (dataAdapter instanceof CassandraDataAdapter)
			return new CassandraDataAdapterService(jasperReportsContext, (CassandraDataAdapter) dataAdapter);
		return null;
	}

	@Override
	public IDataAdapterCreator iReportConverter() {
		return null;
	}

	@Override
	public boolean isDeprecated() {
		return false;
	}
}
