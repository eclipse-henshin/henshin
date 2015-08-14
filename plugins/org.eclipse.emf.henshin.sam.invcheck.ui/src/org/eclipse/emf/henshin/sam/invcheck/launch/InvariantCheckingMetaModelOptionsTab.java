package org.eclipse.emf.henshin.sam.invcheck.launch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

public class InvariantCheckingMetaModelOptionsTab extends
		AbstractLaunchConfigurationTab implements SelectionListener {

	private final int CONFIG_BUTTONS = 10;

	private Button fileOutputButton;
	
	private Map<String, Group> groupsComponentConfiguration;
	
	private Map<String, List<Button>> componentConfigButtons;
	
	private Map<String, List<IConfigurationElement>> configElements;

	/**
	 * The widgetSelected implementation.<br />
	 * <p>This methods refers to the data stored in the <code>Widget</code> which has caused
	 * this <code>SelectionEvent</code>.  The stored data is used to determine, which
	 * action to perform.</p>
	 * <p>The data stored in <code>Widgets</code> registered with this <code>SelectionListener</code>
	 * has to be an <code>Integer</code>.  Any other data as well as unknown integer values will
	 * result in a <code>RuntimeException</code></p>
	 * @param e
	 */
	public void widgetSelected(SelectionEvent e) {
		final int selectedID = ((Integer) e.widget.getData()).intValue();
		switch (selectedID) {
		case CONFIG_BUTTONS:
			updateLaunchConfigurationDialog();
			break;
		default:
			throw new RuntimeException("unknown selection event with id: "
					+ selectedID);
		}
	}

	/**
	 * Invokes {@link #widgetSelected(SelectionEvent)}
	 * @param e
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		final ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL);
		final Composite compositeMain = createComposite(sc, 1, 1,
				GridData.FILL_HORIZONTAL);
		setControl(sc);
		
		final Composite fileOutputComposite = createComposite(compositeMain, 1,
				1, GridData.FILL_HORIZONTAL);
		fileOutputButton = new Button(fileOutputComposite, SWT.CHECK);
		fileOutputButton.setToolTipText("Enable this button to print debug information to a file rather than to the console");
		fileOutputButton.setText("fileOutput");
		fileOutputButton.setData(new Integer(CONFIG_BUTTONS));
		fileOutputButton.addSelectionListener(this);
		
		this.groupsComponentConfiguration = new HashMap<String, Group>();//Group[classes.length];
		this.componentConfigButtons = new HashMap<String, List<Button>>();
		
		this.configElements = PAFActivator.getDefault().getFilterFactory().getAllConfigFields();
		this.configElements.putAll(PAFActivator.getDefault().getProducerFactory().getAllConfigFields());
		this.configElements.putAll(PAFActivator.getDefault().getConsumerFactory().getAllConfigFields());
		for (String clazz : this.configElements.keySet()) {
			final Group current = createGroup(compositeMain, "Config for " + clazz.replaceAll(".*\\.", ""), 2, 1, GridData.FILL_HORIZONTAL); 
			groupsComponentConfiguration.put(clazz, current);
			List<Button> buttons = new LinkedList<Button>();
			for (IConfigurationElement field : this.configElements.get(clazz)) {				
				final Button button = new Button(current, SWT.CHECK);
				button.setToolTipText("Configuration option");
				button.setText(field.getAttribute("name"));
				button.setData(this.CONFIG_BUTTONS);
				button.addSelectionListener(this);
				button.setData("id", field.getAttribute("name"));
				buttons.add(button);
			}
			this.componentConfigButtons.put(clazz, buttons);
		}
		
		compositeMain.setSize(compositeMain.computeSize(SWT.NONE, SWT.NONE));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setSize(compositeMain.getSize());
		sc.setContent(compositeMain);
	}

	protected Composite createComposite(Composite parent, int columns,
			int hspan, int fill) {
		Composite g = new Composite(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	protected Group createGroup(Composite parent, String text, int columns,
			int hspan, int fill) {
		Group g = new Group(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setText(text);
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	/**
	 * We consider an <code>ILaunchConfiguration</code> valid if it hat as a non
	 * empty verificationTaskURI and a non empty selectedFilters attribute.
	 * 
	 * @see AbstractLaunchConfigurationTab#isValid(ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		final boolean superIsValid = super.isValid(launchConfig);
		return superIsValid;
	}

	/**
	 * Initialize the LaunchConfigurationTab from the values stored in the
	 * passed <code>ILaunchConfiguration</code>.<br />
	 * Sets the value of the <code>testStoryDiagramURI</code> text field and
	 * fills the <code>filterList</code> with the names of the selected
	 * <code>Filter</code>s.
	 * 
	 * @see AbstractLaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		if (configuration != null) {			
			try {
				for (String s : this.componentConfigButtons.keySet()) {					
					for (Button b : this.componentConfigButtons.get(s)) {
						boolean defaultValue = false;
						for (IConfigurationElement elem : this.configElements.get(s)) {
							if (b.getData("id").equals(elem.getAttribute("name"))) {
								defaultValue = elem.getAttribute("defaultValue").equals("true") ? true : false;
							}
						}
						final boolean value = configuration.getAttribute(s + ":" + b.getData("id"), defaultValue);
						b.setSelection(value);
					}					
				}
			} catch (CoreException ce) {
				final Bundle bundle = Platform
						.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									InvariantCheckingLauncherConstants.PLUGIN_ID,
									"An exception occurred when checking configuration options", ce)); //$NON-NLS-1$
				}
			}
			try {
				final boolean check = configuration
						.getAttribute(
								InvariantCheckingLauncherConstants.fileOutput,
								false);
				fileOutputButton.setSelection(check);
			} catch (CoreException ce) {
				final Bundle bundle = Platform
						.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									InvariantCheckingLauncherConstants.PLUGIN_ID,
									"An exception occurred, probably the attribute fileOutput is not set", ce)); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Stores the values in the <code>ILaunchConfiguration</code>
	 * 
	 * @see AbstractLaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		List<String> componentOptions = new LinkedList<String>();
		for (String s : this.componentConfigButtons.keySet()) {
			for (Button b : this.componentConfigButtons.get(s)) {
				String key = s + ":" + b.getData("id");
				configuration.setAttribute(key, b.getSelection());
				componentOptions.add(key);
			}
			configuration.setAttribute(InvariantCheckingLauncherConstants.componentOptions, componentOptions);
		}
		configuration.setAttribute(InvariantCheckingLauncherConstants.fileOutput, fileOutputButton.getSelection());
		
		
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	public String getName() {
		return "Options";
	}

	protected Text createSingleText(Composite parent, int hspan) {
		Text t = new Text(parent, SWT.SINGLE | SWT.BORDER);
		t.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		t.setLayoutData(gd);
		return t;
	}

	protected static Combo createCombo(Composite parent, int style, int hspan,
			String[] items) {
		Combo c = new Combo(parent, style);
		c.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		c.setLayoutData(gd);
		if (items != null) {
			c.setItems(items);
		}
		// c.select(0);
		return c;
	}
}
