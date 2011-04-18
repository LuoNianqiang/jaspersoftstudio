package com.jaspersoft.studio.editor.preview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fill.AsynchronousFillHandle;
import net.sf.jasperreports.engine.fill.AsynchronousFilllListener;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFileEditorInput;

import com.jaspersoft.studio.data.DataAdapter;
import com.jaspersoft.studio.editor.JRPrintEditor;
import com.jaspersoft.studio.editor.preview.actions.ReloadAction;
import com.jaspersoft.studio.editor.preview.actions.ShowParametersAction;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.preferences.util.PropertiesHelper;
import com.jaspersoft.studio.repository.RepositoryManager;
import com.jaspersoft.studio.utils.ErrorUtil;
import com.jaspersoft.studio.utils.SelectionHelper;

public class PreviewEditor extends JRPrintEditor {

	protected JasperPrint loadJRObject(InputStream in) throws JRException {
		return getJasperPrint();
	}

	private JasperDesign jasperDesign;

	public void setJasperDesign(JasperDesign jasperDesign) {
		this.jasperDesign = jasperDesign;
	}

	public JasperDesign getJasperDesign() {
		return jasperDesign;
	}

	// $TODO complete this class to use DataAdapters and remove the AMDataSource.
	// private AMDatasource datasource;

	/**
	 * This is the cached DataAdapter used by this report.
	 */
	private DataAdapter dataAdapter;

	private Map<String, Object> jasperParameter = new Hashtable<String, Object>();

	private Throwable fillError = null;
	private DatasourceComboItem dataSourceWidget;
	private ReloadAction reloadAction;
	private ShowParametersAction showParametersAction;

	/**
	 * Run a report in a Job, asking for parameters. Parameters are cached. DataAdapter is cached.
	 * 
	 * @param myDataAdapter
	 *          the {@link com.jaspersoft.studio.data.DataAdapter DataAdapter} to use in order to run the report.
	 */
	public void runReport(DataAdapter myDataAdapter) {

		if (!isNotRunning())
			return; // Nothing to do, the report is already executing, we don't want to stop it.

		// Cache the DataAdapter used for this report only if it is not null.
		if (myDataAdapter != null) {
			// $TODO should we save the reference in the JRXML ?
			dataAdapter = myDataAdapter;
		}

		// If the DataAdapter of this preview editor is still null, abort the report execution
		if (dataAdapter == null) {
			unsetReportDocument(Messages.PreviewEditor_no_datasource, true);
			return;
		}

		// At this point, dataAdapter is not null.

		String dsName = dataAdapter.getName();

		// $TODO move askParameters inside the Job and check for classloading related problems
		// The idea is to allow a dataadapter to contribute the parameters before they are being asked to the user.
		int pdresult = askParameters();
		if (pdresult != Window.OK)
			return;

		String jobName = Messages.PreviewEditor_preview_a
				+ ": " + getJasperDesign().getName() + Messages.PreviewEditor_preview_b + "[" + dsName + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		// create job with a different classloader!
		try {
			IProject prj = ((IFileEditorInput) getEditorInput()).getFile().getProject();
			ClassLoader cl = RepositoryManager.getClassLoader4Project(null, prj);
			Class<?> jbclass = cl.loadClass("com.jaspersoft.studio.preferences.util.PropertiesHelper");
			Constructor<?>[] c = jbclass.getDeclaredConstructors();
			PropertiesHelper jb = (PropertiesHelper) c[0].newInstance(prj);

			jb.getClass().getClassLoader();

			ClassLoader cl2 = RepositoryManager.getClassLoader4Project(null, prj);
			Class<?> jbclass2 = cl.loadClass("com.jaspersoft.studio.preferences.util.PropertiesHelper");
			Constructor<?>[] c2 = jbclass.getDeclaredConstructors();
			PropertiesHelper jb2 = (PropertiesHelper) c[0].newInstance(prj);

			jb2.getClass().getClassLoader();

			boolean b = jb == jb2;
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				unsetReportDocument(Messages.PreviewEditor_reloading, false);
				monitor.beginTask(Messages.PreviewEditor_starting, IProgressMonitor.UNKNOWN);
				InputStream io = null;
				fillError = null;

				// In case of the data adapter is of type JDBC, we are in charge to close the
				// connection provided, so we store it in this variable
				java.sql.Connection connection = null;

				try {
					IFile file = ((IFileEditorInput) getEditorInput()).getFile();

					Thread.currentThread().setContextClassLoader(
							RepositoryManager.getClassLoader4Project(monitor, file.getProject()));
					SimpleFileResolver fileResolver = SelectionHelper.getFileResolver(file);

					new PropertiesHelper(file.getProject()).getProperties();

					ByteArrayOutputStream out = new ByteArrayOutputStream();
					JRSaver.saveObject(getJasperDesign(), out);
					JasperDesign jd = (JasperDesign) JRLoader.loadObject(new ByteArrayInputStream(out.toByteArray()));

					setJasperPrint(null);
					AsynchronousFillHandle fh = null;
					JasperReport jasperReport = JasperCompileManager.compileReport(jd);

					jasperParameter.put(JRParameter.REPORT_FILE_RESOLVER, fileResolver);

					// We let the data adapter to contribute its parameters.
					dataAdapter.getSpecialParameters(jasperParameter);

					// We create the fillHandle to run the report based on the type of data adapter....
					if (dataAdapter.isJDBCConnection()) {
						connection = dataAdapter.getConnection();
						fh = AsynchronousFillHandle.createHandle(jasperReport, jasperParameter, connection);
					} else if (dataAdapter.isJRDataSource()) {
						fh = AsynchronousFillHandle.createHandle(jasperReport, jasperParameter,
								dataAdapter.getJRDataSource(jasperReport));
					} else {
						fh = AsynchronousFillHandle.createHandle(jasperReport, jasperParameter,
								dataAdapter.getJRDataSource(jasperReport));
					}

					if (fillReport(fh, monitor) == Status.CANCEL_STATUS)
						return Status.CANCEL_STATUS;

					setReportDocument(true);
				} catch (final Throwable e) {
					unsetReportDocument(ErrorUtil.getStackTrace(e), true);
				} finally {
					if (io != null)
						try {
							io.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							setNotRunning(true);
						}
					});
					monitor.done();

					// If the data adapter contributed a JDBC connection, by contract we are in charge to close it.
					if (connection != null) {
						try {
							connection.close();
						} catch (Exception exx) {
							// if an error occurs while closing the connection, we just ignore it.
						}
					}
					// Allow the data adapter to cleanup its state
					dataAdapter.disposeSpecialParameters(jasperParameter);

				}
				return Status.OK_STATUS;
			}
		};

		// Ready to run our Job!
		job.setPriority(Job.LONG);
		job.schedule();
	}

	private boolean showParameters = false;

	private int askParameters() {
		ParametersDialog pd = new ParametersDialog(Display.getCurrent().getActiveShell(), jasperDesign, jasperParameter);
		if (showParameters || pd.canShowParameters()) {
			return pd.open();
		}
		return Window.OK;
	}

	public boolean isShowParameters() {
		return showParameters;
	}

	public void setShowParameters(boolean showParameters) {
		this.showParameters = showParameters;
	}

	public void setNotRunning(boolean norun) {
		super.setNotRunning(norun);
		dataSourceWidget.refresh(true);
		reloadAction.setEnabled(norun);
		showParametersAction.setEnabled(norun);
	}

	protected void refreshToolbar() {
		super.refreshToolbar();
		IToolBarManager tbManager = getTbManager();

		tbManager.add(new Separator());
		reloadAction = new ReloadAction(this);
		tbManager.appendToGroup("DATASOURCEGROUP", reloadAction); //$NON-NLS-1$

		dataSourceWidget = new DatasourceComboItem(this);
		tbManager.appendToGroup("DATASOURCEGROUP", dataSourceWidget); //$NON-NLS-1$

		// ToolBarContributionItem2 i = new ToolBarContributionItem2();
		// i.

		showParametersAction = new ShowParametersAction(this);
		// ToolItem item = new ToolItem(((ToolBarManager) tbManager).getControl(), SWT.CHECK);
		// item.setText(showParametersAction.getText());
		// item.setImage(JaspersoftStudioPlugin.getImage(showParametersAction.getImageDescriptor()));
		// item.setHotImage(JaspersoftStudioPlugin.getImage(showParametersAction.getHoverImageDescriptor()));
		// item.setToolTipText(showParametersAction.getToolTipText());
		// item.addSelectionListener(new SelectionListener() {
		//
		// public void widgetSelected(SelectionEvent e) {
		// showParametersAction.run();
		// }
		//
		// public void widgetDefaultSelected(SelectionEvent e) {
		//
		// }
		// });

		tbManager.appendToGroup("DATASOURCEGROUP", showParametersAction); //$NON-NLS-1$

		tbManager.update(true);
	}

	private IStatus fillReport(AsynchronousFillHandle fh, IProgressMonitor monitor) throws JRException,
			InterruptedException {
		Assert.isTrue(fh != null);
		IProgressMonitor sm = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN,
				SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);
		try {
			sm.beginTask(Messages.PreviewEditor_fill_report, IProgressMonitor.UNKNOWN);

			fh.addListener(new AsynchronousFilllListener() {

				public void reportFinished(JasperPrint jPrint) {
					setJasperPrint(jPrint);
				}

				public void reportFillError(Throwable t) {
					handleFillException(t);
				}

				public void reportCancelled() {
					unsetReportDocument(Messages.PreviewEditor_report_fill_canceled, true);
				}
			});
			fh.startFill();
			while (true && getJasperPrint() == null && fillError == null) {
				if (sm.isCanceled()) {
					fh.cancellFill();
					return Status.CANCEL_STATUS;
				}
				Thread.sleep(500);
				sm.worked(10);
			}
			if (fillError != null)
				throw new JRException(fillError);

		} finally {
			sm.done();
		}
		return Status.OK_STATUS;
	}

	private void handleFillException(Throwable t) {
		fillError = t;
	}

}
