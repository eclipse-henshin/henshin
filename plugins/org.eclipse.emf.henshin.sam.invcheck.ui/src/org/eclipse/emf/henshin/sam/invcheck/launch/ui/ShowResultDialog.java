package org.eclipse.emf.henshin.sam.invcheck.launch.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class ShowResultDialog extends Dialog {

	private final Map<String, Map<String, String>> theInput;
	
	public ShowResultDialog(Shell parentShell, Map<String, Map<String, String>> input) {
		super(parentShell);
		this.theInput = input;
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);
		//((GridLayout)composite.getLayout()).numColumns = 1;
		if (this.theInput == null || this.theInput.size() == 0) {
			Label l = new Label(composite, SWT.NONE);
			l.setText("Nothing to display. Sorry!");
		} else {
			Tree t = new Tree(composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			t.setHeaderVisible(true);
			t.setEnabled(true);
			TreeColumn filterTreeColumn = new TreeColumn(t, SWT.LEAD);
			filterTreeColumn.setText("Filter");
			filterTreeColumn.setWidth(100);
			filterTreeColumn.setResizable(true);
			TreeColumn propTreeColumn = new TreeColumn(t,SWT.LEAD);
			propTreeColumn.setText("Property");
			propTreeColumn.setWidth(100);
			propTreeColumn.setResizable(true);
			TreeColumn valTreeColumn = new TreeColumn(t, SWT.LEAD);
			valTreeColumn.setText("Value");
			valTreeColumn.setWidth(100);
			valTreeColumn.setResizable(true);
			
			for (Map.Entry<String, Map<String, String>> entry : theInput.entrySet()) {
				if (entry.getValue() != null && entry.getValue().size() > 0) {
					TreeItem tItem = new TreeItem(t, SWT.NONE);
					tItem.setText(new String[] { entry.getKey(), null, null });
					if (entry.getValue() == null
							|| entry.getValue().size() == 0) {
						tItem.setItemCount(0);
					} else {
						tItem.setExpanded(false);
						for (Map.Entry<String, String> props : entry.getValue()
								.entrySet()) {
							TreeItem dictTItem = new TreeItem(tItem, SWT.NONE);
							dictTItem.setItemCount(0);
							dictTItem.setText(new String[] { null,
									props.getKey(), props.getValue() });
						}
					}
				}
			}
			t.setLinesVisible(true);
		}
		this.getShell().addListener(SWT.Resize, new Listener() {
			
			public void handleEvent(Event event) {
				composite.layout(true);
				
			}
		});
		return composite;
	}

	public final static int EXPORT_ID = IDialogConstants.CLIENT_ID + 1;
	
	public final static String EXPORT_LABEL = "Export";
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, EXPORT_ID, EXPORT_LABEL, false);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId < IDialogConstants.CLIENT_ID)
			super.buttonPressed(buttonId);
		else if (EXPORT_ID == buttonId) {
			this.exportPressed();
		}
	}
	
	private void exportPressed() {
		ResourceDialog rDiag = new ResourceDialog(getShell(), "Choose file for export", SWT.SAVE);
		rDiag.setBlockOnOpen(true);
		if (rDiag.open() == ResourceDialog.OK && !rDiag.getURIs().isEmpty()) {
			URIConverter converter = new ExtensibleURIConverterImpl();
			OutputStream oStream = null;
			OutputStreamWriter osw = null;
			try {
				oStream = converter.createOutputStream(rDiag.getURIs().get(0));
				final StringBuffer fileContents = this.genrateOutput();
				osw = new OutputStreamWriter(oStream, Charset.defaultCharset());
				osw.write(fileContents.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (osw != null) osw.close();
					if (oStream != null) oStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private StringBuffer genrateOutput() {
		StringBuffer myBuffer = new StringBuffer();
		myBuffer.append("<html>\n<head>\n\t<title>Results</title>\n</head>\n<body>\n<h1>Results</h1>\n<ul>\n");
		for (Map.Entry<String, Map<String, String>> entry : theInput.entrySet()) {
			if (entry.getValue() != null && entry.getValue().size() > 0) {
				myBuffer.append("\t<li>").append(entry.getKey()).append("\n\t\t<ul>\n");
				for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
					myBuffer.append("\t\t\t<li><strong>").append(innerEntry.getKey()).append("</strong> ").append(innerEntry.getValue()).append("</li>\n");
				}
				myBuffer.append("\t\t</ul>\n\t</li>\n");
			}
		}
		myBuffer.append("</ul>\n</body>\n</html>");
		return myBuffer;
	}
}
