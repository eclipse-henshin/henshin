package org.eclipse.emf.henshin.sam.invcheck.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.sam.paf.PAFActivator;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

public class InvariantCheckingMetaModelMainTab extends
		AbstractLaunchConfigurationTab implements SelectionListener {

	private Text textStoryDiagramURI = null;
	private URI storyDiagramUri = null;

	private ResourceSet rs = new ResourceSetImpl();

	/**
	 * the <code>List</code> storing and presenting the selected filters.
	 */
	private org.eclipse.swt.widgets.List filterList = null;

	/**
	 * the <code>List</code> storing and presenting the selected producer.
	 */
	private Combo producerSelection = null;
	private Combo consumerSelection = null;

	private Button addFilterButton = null;
	private Button removeFilterButton = null;
	private Button validateButton;
	private Combo predefinedChainCombo;
	private Button predefinedChain;
	
	/*
	 * following a series of integer constants we use for the identification
	 * of the different widgets for event handling
	 */
	private final int BROWSE_STORY_DIAGRAM_BUTTON = 0;
	private final int USE_MANUAL_SELECTION_BUTTON = 1;
	private final int PRODUCER_SELECTION_COMBO = 2;
	private final int FILTER_LIST = 3;
	private final int ADD_FILTER_BUTTON = 4;
	private final int REMOVE_FILTER_BUTTON = 5;
	private final int CONSUMER_SELECTION_COMBO = 6;
	private final int PREDEFINED_FILTER_CHAIN_BUTTON = 7;
	private final int PREDEFINED_CHAIN_COMBO = 8;
	private final int VALIDATE_BUTTON = 9;
	private final int CONFIG_BUTTONS = 10;
	
	private Group groupProducerSelection;
	private Group groupFilterSelection;
	private Group groupConsumerSelection;
	
	private String[] getFilterChainName(IConfigurationElement[] elems) {
		String[] result = null;
		if (elems != null && elems.length > 0) {
			result = new String[elems.length];
			for (int i = 0; i < elems.length; i++) {
				result[i] = elems[i].getAttribute("name");
			}
		}
		return result;
	}

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
		case BROWSE_STORY_DIAGRAM_BUTTON:
			this.browseStoryDiagramPressed();
			break;
		case PRODUCER_SELECTION_COMBO:
		case CONSUMER_SELECTION_COMBO:
		case PREDEFINED_CHAIN_COMBO:
		case VALIDATE_BUTTON:
			updateLaunchConfigurationDialog();
			break;
		case FILTER_LIST:
			filterListSelected();
			break;
		case ADD_FILTER_BUTTON:
			addFilterPressed();
			break;
		case REMOVE_FILTER_BUTTON:			
			final int selectedIndex = filterList.getSelectionIndex();
			if (selectedIndex >= 0) {
				filterList.remove(selectedIndex);
				updateLaunchConfigurationDialog();
			}
			break;
		case PREDEFINED_FILTER_CHAIN_BUTTON:
			this.predefinedFilterChainPressed();
			break;
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

	private void predefinedFilterChainPressed() {
		if (getPredfinedChain().getSelection()) {
			// enable filterchain selection
			predefinedChainCombo.setEnabled(true);
			// disable manual building
			groupConsumerSelection.setEnabled(false);
			groupFilterSelection.setEnabled(false);
			groupProducerSelection.setEnabled(false);
		} else {
			// disable predefined filterchain selection
			predefinedChainCombo.setEnabled(false);
			// enable manual building
			groupConsumerSelection.setEnabled(true);
			groupFilterSelection.setEnabled(true);
			groupProducerSelection.setEnabled(true);
		}
		updateLaunchConfigurationDialog();
	}

	/**
	 * <p>Opens an FilterSelectionDialog,<br />
	 * which allows to choose from the list of all registered Filters the one to add to the filterList.</p>
	 * <p>This method is invoked by {@link #widgetSelected(SelectionEvent)}</p>
	 */
	private void addFilterPressed() {
		final PAFItemSelectionDialog fsDiag = new PAFItemSelectionDialog(
				getShell(), PAFType.Filter);
		fsDiag.setText("Please choose the filter to add ...");
		String filterName = fsDiag.open();
		if (filterName != null) {
			filterList.add(filterName);
			// filterList.pack();
			// groupFilterSelection.pack();
			updateLaunchConfigurationDialog();
		}
	}

	/**
	 * <p>Enables or disables the {@link #removeFilterButton}.<br />
	 * The <code>removeFilterButton</code> is enabled if and only if the <code>filterList</code> contains at least one element.</p>
	 * <p>This method is invoked by {@link #widgetSelected(SelectionEvent)}</p>
	 */
	private void filterListSelected() {
		if (filterList.getSelectionCount() == 0) {
			removeFilterButton.setEnabled(false);
		} else {
			removeFilterButton.setEnabled(true);
		}
	}

	/**
	 * Opens a {@link ResourceDialog} which allows to select an input element for the filter chain.
	 */
	private void browseStoryDiagramPressed() {
		final ResourceDialog d = new ResourceDialog(getShell(),
				"Select VerificationTask", SWT.OPEN | SWT.SINGLE);
		if (d.open() == ResourceDialog.OK && !d.getURIs().isEmpty()) {
			storyDiagramUri = d.getURIs().get(0);
			textStoryDiagramURI.setText(storyDiagramUri.toString());
			System.out.println(storyDiagramUri);
			Resource model = rs.getResource(storyDiagramUri, true);

			if (model.getContents().isEmpty()) {
				MessageDialog.openError(getShell(),
						"Error Loading VerificationTask",
						"The VerificationTask'" + storyDiagramUri.toString()
								+ "' could not be loaded or it is empty.");
				return;
			}
		} else {
			textStoryDiagramURI.setText("");
			storyDiagramUri = null;
		}

		updateLaunchConfigurationDialog();
	}

	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		final ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL);
		final Composite compositeMain = createComposite(sc, 1, 1,
				GridData.FILL_HORIZONTAL);
		setControl(sc);

		Group groupStoryDiagram = createGroup(compositeMain,
				"VerificationTask to verify", 5, 1, GridData.FILL_HORIZONTAL);

		textStoryDiagramURI = createSingleText(groupStoryDiagram, 4);
		textStoryDiagramURI.setToolTipText("Choose a VerificationTask file.");
		textStoryDiagramURI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, true, 4, 1));

		final Button browseStoryDiagram = createPushButton(groupStoryDiagram,
				"Browse...", null);
		browseStoryDiagram.setToolTipText("Browse for a VerificationTask.");
		browseStoryDiagram.addSelectionListener(this);
		browseStoryDiagram.setData(new Integer(BROWSE_STORY_DIAGRAM_BUTTON));

		final Button useManualSelection = new Button(compositeMain, SWT.RADIO);
		useManualSelection.setText("Build FilterChain manually");
		useManualSelection.setData(new Integer(USE_MANUAL_SELECTION_BUTTON));
		groupProducerSelection = createGroup(compositeMain,
				"Select producer to use", 2, 1, GridData.FILL_HORIZONTAL);
		producerSelection = createCombo(groupProducerSelection, SWT.READ_ONLY
				| SWT.DROP_DOWN, 1, PAFActivator.getDefault()
				.getProducerFactory().getAllProducerNames());
		producerSelection.addSelectionListener(this);
		producerSelection.setData(new Integer(PRODUCER_SELECTION_COMBO));

		groupFilterSelection = createGroup(compositeMain,
				"Select filters to use", 2, 1, GridData.FILL_HORIZONTAL);

		filterList = new org.eclipse.swt.widgets.List(groupFilterSelection,
				org.eclipse.swt.SWT.SINGLE | SWT.V_SCROLL);
		/*
		 * Try to influence the filterList's layout
		 */
		GridData flGd = new GridData();
		flGd.verticalAlignment = SWT.TOP;
		flGd.horizontalAlignment = SWT.LEFT;
		flGd.minimumWidth = 150;
		flGd.widthHint = 150;
		flGd.heightHint = 180;
		flGd.minimumHeight = 180;
		flGd.horizontalIndent = 10;
		flGd.verticalIndent = 10;
		filterList.setLayoutData(flGd);

		/*
		 * we add a <code>SelectionListener</code> that disables the
		 * removeFilterButton if no entry in the filterList is selected
		 */
		filterList.addSelectionListener(this);
		filterList.setData(new Integer(FILTER_LIST));
		
		/**
		 * this <code>Composite</code> holds the buttons for the filter
		 * selection <code>Group</code>.
		 */
		final Composite groupFilterSelectionButtons = createComposite(
				groupFilterSelection, 1, 1, GridData.FILL_VERTICAL);
		flGd = (GridData) groupFilterSelectionButtons.getLayoutData();
		flGd.horizontalIndent = 10;
		groupFilterSelectionButtons.setLayoutData(flGd);
		addFilterButton = createPushButton(groupFilterSelectionButtons, "+",
				null);
		addFilterButton.setToolTipText("Add a filter to the list of filters");
		addFilterButton.setEnabled(true);
		addFilterButton.setData(new Integer(ADD_FILTER_BUTTON));
		addFilterButton.addSelectionListener(this);

		removeFilterButton = createPushButton(groupFilterSelectionButtons, "-",
				null);
		removeFilterButton
				.setToolTipText("Remove the selected filter from the list of filters");
		removeFilterButton.setEnabled(false);
		removeFilterButton.setData(new Integer(REMOVE_FILTER_BUTTON));
		/*
		 * add an SelectionListener that removes the selected item from the
		 * filterList if the removeFilterButton is pressed by the user.
		 */
		removeFilterButton.addSelectionListener(this);

		groupConsumerSelection = createGroup(compositeMain,
				"Select consumer to use", 2, 1, GridData.FILL_HORIZONTAL);
		consumerSelection = createCombo(groupConsumerSelection, SWT.READ_ONLY
				| SWT.DROP_DOWN, 1, PAFActivator.getDefault()
				.getConsumerFactory().getAllConsumerNames());
		consumerSelection.setData(new Integer(CONSUMER_SELECTION_COMBO));
		consumerSelection.addSelectionListener(this);

		predefinedChain = new Button(compositeMain, SWT.RADIO);
		predefinedChain.setText("Use predfined FilterChain");
		predefinedChain.setData(new Integer(PREDEFINED_FILTER_CHAIN_BUTTON));
		predefinedChain.addSelectionListener(this);
		predefinedChainCombo = createCombo(compositeMain, SWT.READ_ONLY
				| SWT.DROP_DOWN, 1, this.getFilterChainName(PAFActivator
				.getDefault().getAllFilterChains()));
		predefinedChainCombo.setData(new Integer(PREDEFINED_CHAIN_COMBO));
		predefinedChainCombo.addSelectionListener(this);
		
		final Composite validateComposite = createComposite(compositeMain, 1,
				1, GridData.FILL_HORIZONTAL);
		validateButton = new Button(validateComposite, SWT.CHECK);
		validateButton
				.setToolTipText("Enable this checkbox if you want to validate your model prior to the filterchain execution");
		validateButton.setText("Validate model");
		validateButton.setData(new Integer(VALIDATE_BUTTON));
		validateButton.addSelectionListener(this);
		
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

	private Button getPredfinedChain() {
		return this.predefinedChain;
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
		try {
			final boolean usePredefinedChain = launchConfig.getAttribute(
					InvariantCheckingLauncherConstants.usePredefinedChain,
					false);
			final String tmpChain = launchConfig.getAttribute(
					InvariantCheckingLauncherConstants.predefinedChain, "");
			final boolean predefinedChainSet = tmpChain != null
					&& !tmpChain.equals("");
			String uri = launchConfig.getAttribute(
					InvariantCheckingLauncherConstants.verificationTaskURI, "");
			final boolean hasFile = uri != null && !uri.equals("");
			@SuppressWarnings("unchecked")
			final List<?> list = launchConfig.getAttribute(
					InvariantCheckingLauncherConstants.selectedFilters,
					new ArrayList());
			final boolean hasFilters = list != null && !list.isEmpty();
			String tmpStr = launchConfig.getAttribute(
					InvariantCheckingLauncherConstants.selectedConsumer, "");
			final boolean hasConsumer = tmpStr != null && !tmpStr.equals("");
			tmpStr = launchConfig.getAttribute(
					InvariantCheckingLauncherConstants.selectedProducer, "");
			final boolean hasProd = tmpStr != null && !tmpStr.equals("");
			return ((!usePredefinedChain && hasFilters && hasConsumer && hasProd) || (usePredefinedChain && predefinedChainSet))
					&& superIsValid && hasFile;
		} catch (CoreException e) {
			final Bundle bundle = Platform
					.getBundle("de.hpi.InvCheckEMF.launcher");
			if (bundle != null) {
				final ILog theLog = Platform.getLog(bundle);
				theLog
						.log(new Status(
								IStatus.ERROR,
								"de.hpi.InvCheckEMF.launcher",
								"An exception occurred, while reading configuration attributes",
								e));
			} else {
				e.printStackTrace(System.err);
			}
		}

		return false;
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
				storyDiagramUri = URI.createURI(configuration.getAttribute(
						InvariantCheckingLauncherConstants.verificationTaskURI,
						""));
				textStoryDiagramURI.setText(storyDiagramUri.toString());
			} catch (CoreException e) {
				final Bundle bundle = Platform
						.getBundle("de.hpi.InvCheckEMF.launcher");
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									"de.hpi.InvCheckEMF.launcher",
									"An exception occurred, probably the attribute verificationTaskURI is not set",
									e));
				}
			}
			try {
				@SuppressWarnings("unchecked")				
				final List<?> theList = configuration.getAttribute(
						InvariantCheckingLauncherConstants.selectedFilters,
						new ArrayList(1));
				this.filterList.removeAll();
				for (Object o : theList) {										
					this.filterList.add(o.toString());
				}
				filterList.deselectAll();
			} catch (CoreException e) {
				final Bundle bundle = Platform
						.getBundle("de.hpi.InvCheckEMF.launcher");
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									"de.hpi.InvCheckEMF.launcher",
									"An exception occurred, probably the attribute selectedFilters is not set",
									e));
				}
			}
			try {
				final String s = configuration
						.getAttribute(
								InvariantCheckingLauncherConstants.selectedProducer,
								"");
				int index = producerSelection.indexOf(s);
				producerSelection.select(index);
			} catch (CoreException e) {
				final Bundle bundle = Platform
						.getBundle("de.hpi.InvCheckEMF.launcher");
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									"de.hpi.InvCheckEMF.launcher",
									"An exception occurred, probably the attribute selectedProducer is not set",
									e));
				}
			}
			try {
				final String s = configuration
						.getAttribute(
								InvariantCheckingLauncherConstants.selectedConsumer,
								"");
				int index = consumerSelection.indexOf(s);
				consumerSelection.select(index);
			} catch (CoreException e) {
				final Bundle bundle = Platform
						.getBundle("de.hpi.InvCheckEMF.launcher");
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									"de.hpi.InvCheckEMF.launcher",
									"An exception occurred, probably the attribute selectedConsumer is not set",
									e));
				}
			}
			try {
				final boolean check = configuration
						.getAttribute(
								InvariantCheckingLauncherConstants.validateModel,
								false);
				validateButton.setSelection(check);
			} catch (CoreException ce) {
				final Bundle bundle = Platform
						.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									InvariantCheckingLauncherConstants.PLUGIN_ID,
									"An exception occurred, probably the attribute validateModel is not set", ce)); //$NON-NLS-1$
				}
			}
			try {
				final boolean predefinedChainValue = configuration
						.getAttribute(
								InvariantCheckingLauncherConstants.usePredefinedChain,
								false);
				this.predefinedChain.setSelection(predefinedChainValue);
			} catch (CoreException e) {
				final Bundle bundle = Platform
						.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									InvariantCheckingLauncherConstants.PLUGIN_ID,
									"An exception occurred, probably the attribute usePredefinedChain is not set", e)); //$NON-NLS-1$
				}
			}
			try {
				final String filterChain = configuration.getAttribute(
						InvariantCheckingLauncherConstants.predefinedChain, "");
				int index = -1;
				IConfigurationElement[] ice = PAFActivator.getDefault()
						.getAllFilterChains();
				for (int i = 0; i < ice.length && index < 0; i++) {
					String tmpName = ice[i].getNamespaceIdentifier()
							+ "/" + ice[i].getAttribute("name"); //$NON-NLS-1$ //$NON-NLS-2$
					if (filterChain.equals(tmpName)) {
						index = i;
					}
				}
				if (index >= 0)
					this.predefinedChainCombo.select(index);
				else
					this.predefinedChainCombo.deselectAll();
			} catch (CoreException ce) {
				final Bundle bundle = Platform
						.getBundle(InvariantCheckingLauncherConstants.PLUGIN_ID);
				if (bundle != null) {
					final ILog theLog = Platform.getLog(bundle);
					theLog
							.log(new Status(
									IStatus.ERROR,
									InvariantCheckingLauncherConstants.PLUGIN_ID,
									"An exception occurred, probably the attribute usePredefinedChain is not set", ce)); //$NON-NLS-1$
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
		if (storyDiagramUri != null) {
			String s = storyDiagramUri.toString();
			configuration.setAttribute(
					InvariantCheckingLauncherConstants.verificationTaskURI, s);
		}
		ArrayList<String> fList = new ArrayList<String>();
		if (filterList.getItemCount() > 0) {
			String[] filters = filterList.getItems();
			for (String f : filters) {
				fList.add(f);
			}
		}
		
		configuration.setAttribute(
				InvariantCheckingLauncherConstants.selectedFilters, fList);

		if (producerSelection.getSelectionIndex() > -1) {
			configuration.setAttribute(
					InvariantCheckingLauncherConstants.selectedProducer,
					producerSelection.getItem(producerSelection
							.getSelectionIndex()));
		}

		if (consumerSelection.getSelectionIndex() > -1) {
			configuration.setAttribute(
					InvariantCheckingLauncherConstants.selectedConsumer,
					consumerSelection.getItem(consumerSelection
							.getSelectionIndex()));
		}

		configuration.setAttribute(
				InvariantCheckingLauncherConstants.validateModel,
				validateButton.getSelection());		

		configuration.setAttribute(
				InvariantCheckingLauncherConstants.usePredefinedChain,
				predefinedChain.getSelection());

		if (predefinedChainCombo.getSelectionIndex() > -1) {
			final IConfigurationElement tmp = PAFActivator.getDefault()
					.getAllFilterChains()[predefinedChainCombo
					.getSelectionIndex()];
			String value = tmp.getNamespaceIdentifier() + "/"
					+ tmp.getAttribute("name");
			configuration.setAttribute(
					InvariantCheckingLauncherConstants.predefinedChain, value);
		}
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	public String getName() {
		return "Main";
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

	public enum PAFType {
		Producer, Filter, Consumer
	}

	/**
	 * The ProducerSelectionDialog is a model dialog that displays a list of
	 * <code>Producer</code>s registered the user can choose one from.
	 * 
	 * @author bb
	 * 
	 */
	protected class PAFItemSelectionDialog extends Dialog {

		public PAFItemSelectionDialog(Shell parent, PAFType type) {
			super(parent);
			this.type = type;
		}

		private PAFType type;

		private String result = null;

		public String open() {

			final Shell parent = getParent();
			final Shell shell = new Shell(parent, SWT.APPLICATION_MODAL
					| SWT.DIALOG_TRIM);
			shell.setText(getText());

			shell.setLayout(new GridLayout(1, false));
			GridData gd = new GridData(GridData.FILL_VERTICAL);
			shell.setLayoutData(gd);
			final org.eclipse.swt.widgets.List itemList = new org.eclipse.swt.widgets.List(
					shell, SWT.SINGLE);
			final String[] items;

			switch (type) {
			case Filter:
				items = PAFActivator.getDefault().getFilterFactory()
						.getAllFilterNames();
				break;
			case Producer:
				items = PAFActivator.getDefault().getProducerFactory()
						.getAllProducerNames();
				break;
			case Consumer:
				items = PAFActivator.getDefault().getConsumerFactory()
						.getAllConsumerNames();
				break;
			default:
				items = PAFActivator.getDefault().getConsumerFactory()
						.getAllConsumerNames();
			}

			for (String itemName : items) {
				itemList.add(itemName);
			}

			final Composite buttonComposite = createComposite(shell, 2, 1,
					GridData.FILL_HORIZONTAL);
			final Button cancelButton = createPushButton(buttonComposite,
					"Cancel", null);
			final Button okButton = createPushButton(buttonComposite, "OK",
					null);
			okButton.setEnabled(false);

			final Listener buttonListener = new Listener() {

				private void handleOK() {
					if (itemList.getSelectionCount() > 0) {
						result = itemList.getItem(itemList.getSelectionIndex());
					}
				}

				public void handleEvent(Event event) {
					if (event.widget == okButton) {
						handleOK();
						shell.close();
					} else if (event.widget == cancelButton) {
						shell.close();
					} else if (event.widget == itemList) {
						if (itemList.getSelectionCount() > 0) {
							okButton.setEnabled(true);
						} else {
							okButton.setEnabled(false);
						}
					}
				}
			};
			okButton.addListener(SWT.Selection, buttonListener);
			cancelButton.addListener(SWT.Selection, buttonListener);
			itemList.addListener(SWT.Selection, buttonListener);
			shell.pack();
			shell.open();
			final Display display = parent.getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}

			return result;
		}
	}

}
