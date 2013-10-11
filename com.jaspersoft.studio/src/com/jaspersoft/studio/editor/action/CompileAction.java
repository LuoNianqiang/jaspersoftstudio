package com.jaspersoft.studio.editor.action;

import java.io.File;
import java.util.Map;

import net.sf.jasperreports.eclipse.builder.JasperReportsBuilder;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.JrxmlEditor;
import com.jaspersoft.studio.editor.report.AbstractVisualEditor;
import com.jaspersoft.studio.editor.report.ReportEditor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.utils.SelectionHelper;
import com.jaspersoft.studio.utils.SubreportsUtil;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class CompileAction extends SelectionAction {
	public static final String ID = "compileAction";

	public CompileAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(false);
	}

	protected void init() {
		super.init();
		setText("Compile Report");
		setToolTipText("Compile Report");
		setImageDescriptor(JaspersoftStudioPlugin.getInstance().getImageDescriptor("/icons/build_tab.gif"));
		setId(ID);
		setEnabled(true);
	}

	@Override
	public void run() {
		final JasperReportsConfiguration jConfig = getMDatasetToShow();
		if (jConfig != null) {
			Job job = new Job("Building report") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					IStatus status = doRun(jConfig, monitor, true);
					return status;
				}
			};
			job.setPriority(Job.SHORT);
			job.setSystem(true);
			job.schedule();
		}
	}

	private JasperReportsConfiguration getMDatasetToShow() {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof EditPart && ((EditPart) firstElement).getModel() instanceof ANode) {
				ANode currentNode = (ANode) ((EditPart) firstElement).getModel();
				return currentNode.getJasperConfiguration();
			}
		}
		final AbstractVisualEditor part = (AbstractVisualEditor) getWorkbenchPart();
		if (part instanceof ReportEditor) {
			ReportEditor rpeditor = (ReportEditor) part;
			return rpeditor.getJrContext();
		} else if (!part.getModel().getChildren().isEmpty()) {
			ANode firstChild = (ANode) part.getModel().getChildren().get(0);
			return firstChild.getJasperConfiguration();
		}
		IEditorPart activeJRXMLEditor = SelectionHelper.getActiveJRXMLEditor();
		if (activeJRXMLEditor != null && activeJRXMLEditor instanceof JrxmlEditor) {
			JrxmlEditor jrEditor = (JrxmlEditor) activeJRXMLEditor;
			return ((ANode) jrEditor.getModel()).getJasperConfiguration();
		}
		return null;
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	public static IStatus doRun(final JasperReportsConfiguration jConfig, IProgressMonitor monitor, boolean compileMain) {
		IFile mfile = (IFile) jConfig.get(FileUtils.KEY_FILE);
		if (mfile != null)
			try {
				// ATTENTION! this can generate possible errors, because we are not calling builders in the right order
				// we are also not looking very good for for subreports, because expression evaluation is not good
				// file.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);

				JasperReportsBuilder builder = new JasperReportsBuilder();
				if (compileMain)
					builder.compileJRXML(mfile, monitor);
				Map<File, IFile> fmap = SubreportsUtil.getSubreportFiles(jConfig, mfile, jConfig.getJasperDesign(), monitor);
				for (File f : fmap.keySet()) {
					IFile file = fmap.get(f);
					if (file != null) {
						builder.compileJRXML(file, monitor);
					} else {
						try {
							JasperCompileManager.compileReportToFile(f.getAbsolutePath());
						} catch (JRException e) {
							e.printStackTrace();
						}
					}
					if (monitor.isCanceled())
						break;
				}
			} catch (CoreException e) {
				return Status.CANCEL_STATUS;
			}
		return Status.OK_STATUS;
	}
}
