package org.eclipse.emf.henshin.sam.paf.internal.preferences;

import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.emf.henshin.sam.paf.preferences.PAFPreferenceConstants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PAFPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private IntegerFieldEditor pipeSizeEditor;
	private IntegerFieldEditor dequeueWaitEditor;
	private IntegerFieldEditor enqueWaitEditor;
	private IntegerFieldEditor threadLowEditor;
	private IntegerFieldEditor threadMedEditor;
	private IntegerFieldEditor threadHighEditor;
	
	public PAFPreferencePage() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		this.pipeSizeEditor = new IntegerFieldEditor(PAFPreferenceConstants.PIPE_SIZE, "The pipes' size", getFieldEditorParent());
		this.dequeueWaitEditor = new IntegerFieldEditor(PAFPreferenceConstants.DEQUEUE_WAIT, "Time in ms after that the dequeuing thread gets interrupted", getFieldEditorParent());
		this.enqueWaitEditor = new IntegerFieldEditor(PAFPreferenceConstants.ENQUEUE_WAIT, "Time in ms after that the enqueuing thread gets interrupted", getFieldEditorParent());
		this.threadLowEditor = new IntegerFieldEditor(PAFPreferenceConstants.THREADS_COMPLEXITY_LOW, "Number of threads for filter having LOW complexity", getFieldEditorParent());
		this.threadMedEditor = new IntegerFieldEditor(PAFPreferenceConstants.THREADS_COMPLEXITY_MEDIUM, "Number of threads for filters having MEDIUM complexity", getFieldEditorParent());
		this.threadHighEditor = new IntegerFieldEditor(PAFPreferenceConstants.THREADS_COMPLEXITY_HIGH, "Number of threads for filters having HIGH complexity", getFieldEditorParent());
		
		addField(this.pipeSizeEditor);
		
		addField(dequeueWaitEditor);
	
		addField(enqueWaitEditor);
		
		addField(threadLowEditor);
		
		addField(threadMedEditor);
		
		addField(threadHighEditor);
	}
	
	//@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(PAFActivator.getDefault().getPreferenceStore());
		setDescription("Set the preferences for the pipe and filter plug-in");
	}
}
