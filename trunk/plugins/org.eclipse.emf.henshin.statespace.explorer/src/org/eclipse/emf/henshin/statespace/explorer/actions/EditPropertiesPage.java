package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard page for editing state space properties.
 * @author Christian Krause
 */
public class EditPropertiesPage extends WizardPage {
	
	// Key-value pairs.
	private List<String> keys, values;
	
	// Dirty flag.
	private boolean dirty;
	
	// Table.
	private Table table;
	
	// Dummy state space:
	private StateSpace dummyStateSpace;
	
	/**
	 * Default constructor.
	 */
	protected EditPropertiesPage(StateSpace stateSpace) {
		super("Edit State Space Properties");
		setDescription("Edit the state space properties");
		dirty = false;
		dummyStateSpace = StateSpaceFactory.eINSTANCE.createStateSpace();
		dummyStateSpace.getRules().addAll(stateSpace.getRules());
		dummyStateSpace.getProperties().putAll(stateSpace.getProperties());
	}
	
	/*
	 * Update the table.
	 */
	private void updateTable() {
		
		// Get and sort the properties.
		keys = new ArrayList<String>(dummyStateSpace.getProperties().keySet());
		Collections.sort(keys);
		values = new ArrayList<String>();
		for (String key : keys) {
			values.add(dummyStateSpace.getProperties().get(key));
		}
		
		// Reset table contents:
		table.removeAll();
		
		// Fill the table:
		for (int i=0; i<keys.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { keys.get(i), values.get(i) });
		}		

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2,false));
		
		table = createTable(container);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		updateTable();
		validate();
		
		Composite buttons = new Composite(container, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.BEGINNING));
		buttons.setLayout(new GridLayout(1,false));
		
		createButton(buttons, "Add");
		createButton(buttons, "Remove");
		createButton(buttons, "Initialize");
		//createButton(buttons, "Validate");
		
		setControl(container);
		
	}
	
	private Table createTable(Composite parent) {
		
		// Create the table:
		final Table table = new Table(parent, SWT.BORDER | SWT.MULTI);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		// Two columns:
		TableColumn keyColumn = new TableColumn(table, SWT.NONE);
		keyColumn.setText("Key");
		keyColumn.setWidth(100);
		
		TableColumn valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText("Value");
		valueColumn.setWidth(200);
		
		// Create the editors:
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		table.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final int row = index;
					final TableItem item = table.getItem(index);
					for (int i = 0; i < table.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(table, SWT.BORDER);
							Listener textListener = new Listener() {
								public void handleEvent(final Event e) {
									switch (e.type) {
									case SWT.FocusOut:
										setCellText(column,row,text.getText());
										text.dispose();
										break;
									case SWT.Traverse:
										switch (e.detail) {
										case SWT.TRAVERSE_RETURN:
											setCellText(column,row,text.getText());
										case SWT.TRAVERSE_ESCAPE:
											text.dispose();
											e.doit = false;
										}
										break;
									}
								}
							};
							text.addListener(SWT.Traverse, textListener);
							text.addListener(SWT.FocusOut, textListener);
							editor.setEditor(text, item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							return;
						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible)
						return;
					index++;
				}
			}
		});
		
		return table;
			  
	}
	
	/*
	 * Set the text of a give cell.
	 */
	private void setCellText(int column, int row, String text) {
		TableItem item = table.getItem(row);
		item.setText(column, text);
		if (column==0) {
			keys.set(row, text);
		} else {
			values.set(row, text);
		}
		dirty = true;
		validate();
	}
	
	/*
	 * Add a new property.
	 */
	public void add() {
		keys.add("key" + keys.size());
		values.add("value" + values.size());
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { keys.get(keys.size()-1), values.get(values.size()-1) });
		dirty = true;
	}

	/*
	 * Remove the currently selected property.
	 */
	public void remove() {
		int[] indices = table.getSelectionIndices();
		if (indices==null) return;
		table.remove(indices);
		List<String> keysToRemove = new ArrayList<String>();
		List<String> valuesToRemove = new ArrayList<String>();
		for (int i=0; i<indices.length; i++) {
			keysToRemove.add(keys.get(indices[i]));
			valuesToRemove.add(values.get(indices[i]));
		}
		keys.removeAll(keysToRemove);
		values.removeAll(valuesToRemove);
		dirty = true;
		validate();
	}

	/*
	 * Initialize properties.
	 */
	public void initialize() {
		
		// Sync the properties to the dummy state space:
		dummyStateSpace.getProperties().clear();
		dummyStateSpace.getProperties().putAll(getResult());
		
		// Initialize properties:
		StateSpacePlugin.INSTANCE.getPropertiesManager().initialize(dummyStateSpace);
		
		// Update the table with the new values:
		updateTable();
		dirty = true;
		
	}

	/*
	 * Validate properties.
	 */
	public void validate() {
		
		// Sync the properties to the dummy state space:
		dummyStateSpace.getProperties().clear();
		dummyStateSpace.getProperties().putAll(getResult());
		
		// Validate the properties:
		IStatus result = StateSpacePlugin.INSTANCE.getPropertiesManager().validate(dummyStateSpace);
		if (result.getSeverity()==IStatus.OK) {
			setMessage(null);
			setErrorMessage(null);
			setPageComplete(true);
		} else if (result.getSeverity()==IStatus.ERROR) {
			setMessage(null);
			setErrorMessage(result.getMessage());
			setPageComplete(false);			
		} else {
			setMessage(result.getMessage(), result.getSeverity());			
			setErrorMessage(null);
			setPageComplete(true);
		}
		
	}

	
	/**
	 * Get the properties.
	 * @return The properties.
	 */
	public Map<String,String> getResult() {
		Map<String,String> result = new LinkedHashMap<String,String>();
		for (int i=0; i<keys.size(); i++) {
			result.put(keys.get(i), values.get(i));
		}
		return result;
	}
	
	/**
	 * Check if the properties were changed.	
	 * @return Dirty flag.
	 */
	public boolean isDirty() {
		return dirty;
	}
	
	/*
	 * Private helper method for creating buttons.
	 */
	private Button createButton(Composite parent, final String name) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(name);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final EditPropertiesPage thisPage = this;
		button.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				try {
					Method method = EditPropertiesPage.class.getMethod(name.toLowerCase());
					if (method!=null) method.invoke(thisPage);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		return button;
	}
	
}
