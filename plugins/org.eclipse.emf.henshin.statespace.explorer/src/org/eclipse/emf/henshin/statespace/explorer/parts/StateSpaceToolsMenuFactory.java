package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

/**
 * Helper methods for the {@link StateSpaceToolsMenu}.
 * @author Christian Krause
 */
public class StateSpaceToolsMenuFactory {

	/**
	 * Create a new container composite for an expand bar item.
	 * @param bar Expand bar.
	 * @return Created composite.
	 */
	public static Composite newExpandItemComposite(Composite bar, int columns) {
		Composite composite = new Composite(bar, SWT.BORDER);
		composite.setLayout(new GridLayout(columns,false));
		return composite;
	}
	
	/**
	 * Create a new expand bar item.
	 * @param bar Expand bar.
	 * @param composite Content of the item.
	 * @param name Name of the item.
	 * @param index Index of the item.
	 * @return The created item.
	 */
	public static ExpandItem newExpandItem(ExpandBar bar, Composite composite, String name, int index) {
		ExpandItem item = new ExpandItem(bar, SWT.NONE, index);
		item.setText(name);
		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(composite);
		item.setExpanded(true);
		return item;
	}
	
	/**
	 * Create a new label.
	 * @param parent Parent composite.
	 * @param text Initial text.
	 * @param align Grid data alignment value.
	 * @return The new label.
	 */
	public static Label newLabel(Composite parent, String text, int align) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(align));
		return label;
	}
	
	/**
	 * Create two labels.
	 * @param parent Parent composite.
	 * @param text1 Text 1.
	 * @param text2 Text 2.
	 * @return Second label.
	 */
	public static Label newDoubleLabel(Composite parent, String text1, String text2) {
		newLabel(parent, text1, GridData.HORIZONTAL_ALIGN_END);
		return newLabel(parent, text2, GridData.FILL_HORIZONTAL);
	}
	
	/**
	 * Create a new scale. The parent must have a {@link GridLayout} with 3 columns.
	 * @param parent Parent composite.
	 * @param name Name of the property.
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * @return The scale.
	 */
	public static Scale newScale(Composite parent, String name, int min, int max, int inc, int pageInc, boolean wrap, String unit) {
		if (unit==null) unit = "";
		Label label = new Label(parent, SWT.NONE);
		label.setText(wrap ? name : name + " " + min + unit);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		if (wrap) data.horizontalSpan = 3;
		label.setLayoutData(data);
		if (wrap) {
			label = new Label(parent, SWT.NONE);
			label.setText(min + unit);
		}
		Scale scale = new Scale(parent, SWT.NONE);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scale.setIncrement(inc);
		scale.setPageIncrement(pageInc);
		scale.setMinimum(min);
		scale.setMaximum(max);
		scale.setSelection((max-min) / 2);
		label = new Label(parent, SWT.NONE);
		label.setText(max + unit);
		return scale;
	}
}
