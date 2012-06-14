package com.jaspersoft.studio.property.dataset.dialog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.jaspersoft.studio.data.IDataPreviewInfoProvider;
import com.jaspersoft.studio.data.reader.DatasetReader;
import com.jaspersoft.studio.data.reader.DatasetReaderListener;
import com.jaspersoft.studio.messages.Messages;

/**
 * Data preview table widget.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class DataPreviewTable implements DatasetReaderListener{

	private TableViewer tviewer;
	private Table wtable;
	private Composite composite;
	private Composite tableContainer;
	private IDataPreviewInfoProvider previewInfoProvider;
	private Combo recordsNumCombo;
	private Job refreshPrevieDataJob;
	private DatasetReader dataReader;
	private boolean statusOK;
	private Button refreshPreviewBtn;
	private Button cancelPreviewBtn;
	private ProgressBar progressBar;
	private Label infoMsg;
	private Composite infoComposite;
	
	public DataPreviewTable(Composite parent, IDataPreviewInfoProvider previewInfoProvider){
		this.previewInfoProvider=previewInfoProvider;
		createControl(parent);
	}

	/*
	 * Creates the widget area.
	 */
	private void createControl(Composite parent) {
		composite=new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4,false));
		composite.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				statusOK=false;
				if(refreshPrevieDataJob!=null){
					refreshPrevieDataJob.cancel();
				}
			}
		});
		
		refreshPreviewBtn = new Button(composite, SWT.PUSH);
		refreshPreviewBtn.setText(Messages.DataPreviewTable_PreviewButton);
		refreshPreviewBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		refreshPreviewBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshDataPreview();
				refreshPreviewBtn.setEnabled(false);
				cancelPreviewBtn.setEnabled(true);
			}
		});
		
		cancelPreviewBtn = new Button(composite, SWT.PUSH);
		cancelPreviewBtn.setText(Messages.DataPreviewTable_CancelButton);
		cancelPreviewBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		cancelPreviewBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancelDataPreview();
				refreshPreviewBtn.setEnabled(true);
				cancelPreviewBtn.setEnabled(false);
			}
		});
		cancelPreviewBtn.setEnabled(false);
		
		recordsNumCombo = new Combo(composite, SWT.READ_ONLY);
		recordsNumCombo.setItems(new String[]{
				Messages.DataPreviewTable_RecordsNum100, Messages.DataPreviewTable_RecordsNum500, Messages.DataPreviewTable_RecordsNum1000, Messages.DataPreviewTable_RecordsNumAll});
		recordsNumCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		recordsNumCombo.select(0);
		
		infoComposite = new Composite(composite,SWT.NONE);
		GridLayout infoCmpGL = new GridLayout(2,false);
		infoCmpGL.marginHeight=0;
		infoCmpGL.marginWidth=0;
		infoComposite.setLayout(infoCmpGL);
		infoComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		infoMsg = new Label(infoComposite, SWT.NONE);
		infoMsg.setText(Messages.DataPreviewTable_Ready);
		infoMsg.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		
		progressBar = new ProgressBar(infoComposite, SWT.INDETERMINATE | SWT.BORDER);
		GridData progBarGD = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		progBarGD.horizontalIndent=5;
		progBarGD.widthHint=100;
		progBarGD.exclude=true;
		progressBar.setLayoutData(progBarGD);
		progressBar.setVisible(false);
		
		tableContainer = new Composite(composite,SWT.NONE);
		tableContainer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,4,1));
		tableContainer.setLayout(new TableColumnLayout());
		
		tviewer=new TableViewer(tableContainer, SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		wtable=tviewer.getTable();
		wtable.setHeaderVisible(true);
		wtable.setLinesVisible(true);

		tviewer.setContentProvider(ArrayContentProvider.getInstance());
	}
	
	/**
	 * @return the main control
	 */
	public Composite getControl(){
		return this.composite;
	}
	
	/*
	 * Notifies the need of a table refresh due 
	 * to information modification, i.e. table columns
	 * modification.
	 */
	private void refreshDataPreview(){
		// Refresh layout for the table		
		updateTableLayout();
		final int recordsCountSelected = getRecordsCountSelected();
		refreshPrevieDataJob = new Job(Messages.DataPreviewTable_PreviewDataJobTitle) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				dataReader = new DatasetReader();
				dataReader.setColumns(getColumns());
				dataReader.setDataAdapterDescriptor(previewInfoProvider.getDataAdapterDescriptor());
				// FIXME - TEMPORARY FIX THAT SHOULD BE REMOVED!
				// Using JFace Databinding for fields list of the dataset 
				// makes the internal fields map to be out of synch.
				// Modifications should occur using the JRDesignDataset#add and #remove methods.
				JRDesignDataset clonedDS=(JRDesignDataset) previewInfoProvider.getDesignDataset().clone();
				clonedDS.getFieldsList().clear();
				clonedDS.getFieldsMap().clear();
				for(JRDesignField f : previewInfoProvider.getFieldsForPreview()){
					try {
						clonedDS.addField(f);
					} catch (JRException e) {
						// Do not care, duplication
						// should never happen.
					}
				}
				dataReader.setDesignDataset(clonedDS);
				dataReader.setMaxRecords(recordsCountSelected);
				dataReader.addDatasetReaderListener(DataPreviewTable.this);
				dataReader.start(previewInfoProvider.getJasperReportsConfig());
				return Status.OK_STATUS;
			}
		};
		statusOK=true;
		infoMsg.setText(Messages.DataPreviewTable_GettingData);
		((GridData)progressBar.getLayoutData()).exclude=false;
		progressBar.setVisible(true);
		refreshPrevieDataJob.schedule();
		infoComposite.layout();
	}
	
	/*
	 * Cancel a pending data preview task.
	 */
	private void cancelDataPreview(){
		// Clean up
		if(refreshPrevieDataJob!=null){
			refreshPrevieDataJob.cancel();
			if(dataReader.isRunning()){
				invalidate();
				dataReader.stop();
			}
			// Flush event queue
			while(Display.getDefault().readAndDispatch());
			// Remove all table items if any
			wtable.removeAll();
			tviewer.setInput(null);
		}
		infoMsg.setText(Messages.DataPreviewTable_Ready);
		((GridData)progressBar.getLayoutData()).exclude=true;
		progressBar.setVisible(false);
		infoComposite.layout();
	}

	/*
	 * Gets the number of max records for the output preview. 
	 */
	private int getRecordsCountSelected() {
		switch (recordsNumCombo.getSelectionIndex()) {
		case 0:
			return 100;
		case 1:
			return 500;
		case 2:
			return 1000;
		case 3:
			return -1;
		default:
			return 100;
		}
	}

	/*
	 * Gets the column names.
	 */
	private List<String> getColumns(){
		List<String> columns=new ArrayList<String>();
		for(JRDesignField f : previewInfoProvider.getFieldsForPreview()){
			columns.add(f.getName());
		}
		return columns;
	}
	
	/*
	 * Update the table layout.
	 */
	private void updateTableLayout(){
		if(composite.isVisible()){
			// Remove all table items if any
			wtable.removeAll();
			tviewer.setInput(null);
			
			// Dispose old columns if any
			for (TableColumn col : wtable.getColumns()){
				col.dispose();
			}
			
			TableColumnLayout tColLayout=new TableColumnLayout();
			tableContainer.setLayout(tColLayout);
			
			List<JRDesignField> fields = previewInfoProvider.getFieldsForPreview();
			if(fields.size()>0){
				for(JRDesignField f : fields){
					TableViewerColumn tvc=new TableViewerColumn(tviewer, SWT.NONE);
					tvc.getColumn().setText(f.getName());
					tvc.setLabelProvider(new ColumnLabelProvider());
					tColLayout.setColumnData(tvc.getColumn(), new ColumnWeightData(1,ColumnWeightData.MINIMUM_WIDTH,true));
					tvc.setLabelProvider(new CellLabelProvider() {
						@Override
						public void update(ViewerCell cell) {
							DataPreviewBean element = (DataPreviewBean) cell.getElement();
							Object value = element.getValue(cell.getColumnIndex());
							if(value!=null){
								cell.setText(value.toString());
							}
							else{
								cell.setText(""); //$NON-NLS-1$
							}
						}
					});
				}
				
			}
			
			tableContainer.layout();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.data.reader.DatasetReaderListener#newRecord(java.lang.Object[])
	 */
	public void newRecord(final Object[] values) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if(!wtable.isDisposed() && statusOK){
					tviewer.add(new DataPreviewBean(values));
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.data.reader.DatasetReaderListener#finished()
	 */
	public void finished() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				dataReader.removeDatasetReaderListener(DataPreviewTable.this);
				dataReader=null;
				refreshPreviewBtn.setEnabled(true);
				cancelPreviewBtn.setEnabled(false);
				progressBar.setVisible(false);
				((GridData)progressBar.getLayoutData()).exclude=true;
				infoMsg.setText(MessageFormat.format(Messages.DataPreviewTable_ReadyReadData, new Object[]{wtable.getItemCount()}));
				infoComposite.layout();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.data.reader.DatasetReaderListener#isValidStatus()
	 */
	public boolean isValidStatus() {
		return statusOK;
	}

	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.data.reader.DatasetReaderListener#invalidate()
	 */
	public void invalidate() {
		this.statusOK=false;
	}
	
	/*
	 * Bean to represent the read record for previewing.
	 */
	private class DataPreviewBean {
		private Object[] values;
		
		public DataPreviewBean(Object[] values) {
			this.values=values;
		}
		
		public Object getValue(int index){
			return this.values[index];
		}
	}
	
}
