/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.tool.commands.GC2AppCondCommand;
import org.eclipse.emf.henshin.ocl2ac.tool.optimizer.IntegrationChecker;
import nestedcondition.NestedConstraint;

public class WizardGC2AppCond extends Shell {
	private static final String HENSHIN = ".henshin";
	private Table tableNestedConstraints;
	private Table tableRules;
	private NestedConstraint selectedNestedConstraint = null;
	private Rule selectedRule = null;
	private IFile henshinFile = null;
	private IFile constraintFile = null;
	private final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

	/**
	 * Create the shell.
	 * 
	 * @param display
	 * @param mapAllRules
	 */
	public WizardGC2AppCond(final Display display, IFile henshinFile,
			HashMap<Integer, NestedConstraint> hashmapAllNestedconstraints, HashMap<Integer, Rule> hashmapAllRules,
			IFile constraintFile) {
		super(display, SWT.SHELL_TRIM);
		this.henshinFile = henshinFile;
		this.constraintFile = constraintFile;
		setLayout(new RowLayout(SWT.HORIZONTAL));
		Group groupNGCs = new Group(this, SWT.NONE);
		groupNGCs.setLayout(new RowLayout(SWT.HORIZONTAL));
		groupNGCs.setLayoutData(new RowData(489, 240));
		groupNGCs.setText("Nested Graph Constraints");

		Label lblngc = new Label(groupNGCs, SWT.NONE);
		lblngc.setText("Please select a nested graph constraint");

		tableNestedConstraints = new Table(groupNGCs, SWT.BORDER | SWT.FULL_SELECTION);
		tableNestedConstraints.setLayoutData(new RowData(452, 180));
		tableNestedConstraints.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		tableNestedConstraints.setHeaderVisible(true);
		tableNestedConstraints.setLinesVisible(true);

		TableColumn clnID_NC = new TableColumn(tableNestedConstraints, SWT.NONE);
		clnID_NC.setWidth(46);
		clnID_NC.setText("ID");

		TableColumn clnName_NC = new TableColumn(tableNestedConstraints, SWT.NONE);
		clnName_NC.setWidth(431);
		clnName_NC.setText("Nested Graph Constraint (C)");

		fillTableNestedConstraints(hashmapAllNestedconstraints);

		tableNestedConstraints.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableNestedConstraints.getItemCount() > 0) {
					TableItem[] selectedItem = tableNestedConstraints.getSelection();
					Integer nestedConstraintID = Integer.parseInt(selectedItem[0].getText(0));
					selectedNestedConstraint = hashmapAllNestedconstraints.get(nestedConstraintID);
				}

			}
		});

		Group groupRules = new Group(this, SWT.NONE);
		groupRules.setLayoutData(new RowData(490, 240));
		groupRules.setText("Rules");
		groupRules.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label lblRule = new Label(groupRules, SWT.NONE);
		lblRule.setText("Please select a rule");

		tableRules = new Table(groupRules, SWT.BORDER | SWT.FULL_SELECTION);
		tableRules.setLayoutData(new RowData(452, 180));
		tableRules.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

		});
		tableRules.setHeaderVisible(true);
		tableRules.setLinesVisible(true);

		TableColumn clnID_rule = new TableColumn(tableRules, SWT.NONE);
		clnID_rule.setWidth(48);
		clnID_rule.setText("ID");

		TableColumn clnName_Rule = new TableColumn(tableRules, SWT.NONE);
		clnName_Rule.setWidth(429);
		clnName_Rule.setText("Rule (R)");
		fillTableRules(hashmapAllRules);
		Group groupButtons = new Group(this, SWT.NONE);
		groupButtons.setLayoutData(new RowData(490, 120));

		tableRules.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableRules.getItemCount() > 0) {
					TableItem[] selectedItem = tableRules.getSelection();
					Integer ruleID = Integer.parseInt(selectedItem[0].getText(0));
					selectedRule = (Rule) hashmapAllRules.get(ruleID);
				}

			}
		});
		groupButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		// integrate
		Button btnIntegrate = new Button(groupButtons, SWT.NONE);
		btnIntegrate.setLayoutData(new RowData(SWT.DEFAULT, 35));
		btnIntegrate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Integrate and Lefting
				if (selectedNestedConstraint != null && selectedRule != null) {
					System.out.println(selectedNestedConstraint + "  " + selectedRule);
					GC2AppCondCommand ial = new GC2AppCondCommand(henshinFile);
					setNamesToRuleNodes(selectedRule);
					ial.enableOptimizer = false;

					ial.integrateAndleft(selectedNestedConstraint, selectedRule);
					long translationTime = ial.translationTime;
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Integration", "Integration is finished. The integration time is: ("
									+ (translationTime / (double) 1000) + ") second(s).");
					close();

				}
			}
		});
		btnIntegrate.setText("Integrate");

		// integrate all
		Button btnIntegrateAllConstraints = new Button(groupButtons, SWT.NONE);
		btnIntegrateAllConstraints.setLayoutData(new RowData(SWT.DEFAULT, 35));
		btnIntegrateAllConstraints.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedRule != null) {
					long translationTime = 0;
					setNamesToRuleNodes(selectedRule);

					if (hashmapAllNestedconstraints != null) {
						Iterator<Integer> keySetIterator = hashmapAllNestedconstraints.keySet().iterator();
						while (keySetIterator.hasNext()) {
							Integer key = keySetIterator.next();
							NestedConstraint nestedConstraint = hashmapAllNestedconstraints.get(key);
							GC2AppCondCommand ial = new GC2AppCondCommand(henshinFile);
							ial.enableOptimizer = false;
							ial.integrateAndleftInPlace(nestedConstraint, selectedRule);
							translationTime += ial.translationTime;
						}
					}

					try {
						selectedRule.getModule().eResource().save(null);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.err.println("Error on persisting the rule");
					}

					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Integration", "Integration is finished. The integration time is: ("
									+ (translationTime / (double) 1000) + ") second(s).");
					close();
				}
			}
		});
		btnIntegrateAllConstraints.setText("Integrate all constraints \r\ninto a rule");

		// Check-for-Integration
		Button btnCheckforintegration = new Button(groupButtons, SWT.NONE);
		btnCheckforintegration.setLayoutData(new RowData(SWT.DEFAULT, 35));
		btnCheckforintegration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (selectedNestedConstraint != null && selectedRule != null) {
					IntegrationChecker op = new IntegrationChecker();
					boolean mustIntegrate = op.mustIntegrate(selectedRule, selectedNestedConstraint);

					if (mustIntegrate) {
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Info (required)", "The constraint SHOULD be integrated into the rule.");
						boolean yes = MessageDialog.openQuestion(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Question",
								"Do you want to continue integrating it?");
						if (yes) {

							// call the optimizer
							System.out.println("The constraint " + selectedNestedConstraint.getName()
									+ " should be integrated into rule " + selectedRule.getName());
							GC2AppCondCommand ial = new GC2AppCondCommand(henshinFile);
							setNamesToRuleNodes(selectedRule);
							System.out.println(this.getClass().getName() + " enables " + ial.getClass().getName());
							ial.enableOptimizer = true;

							ial.integrateAndleft(selectedNestedConstraint, selectedRule);
							long translationTime = ial.translationTime;
							MessageDialog.openInformation(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Integration",
									"Integration is finished. The integration time is: ("
											+ (translationTime / (double) 1000) + ") second(s).");
							close();
						}

					} else {
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Info (not required)",
								"There is NO need to integrate the selected constraint into the selected rule.");
						close();

					}
				}
			}
		});
		btnCheckforintegration.setText("Construct Optimized Validity-Preserving AC (C, R)");

		// optimize all
		Button btnIntegrateAllRequired = new Button(groupButtons, SWT.NONE);
		btnIntegrateAllRequired.setLayoutData(new RowData(SWT.DEFAULT, 35));
		btnIntegrateAllRequired.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (selectedRule != null) {
					String infoRequiredConstraint = "";
					int numberOfIntegrated = 0;
					long translationTime = 0;
					setNamesToRuleNodes(selectedRule);

					if (hashmapAllNestedconstraints != null) {
						Iterator<Integer> keySetIterator = hashmapAllNestedconstraints.keySet().iterator();
						while (keySetIterator.hasNext()) {
							Integer key = keySetIterator.next();
							NestedConstraint nestedConstraint = hashmapAllNestedconstraints.get(key);

							IntegrationChecker op = new IntegrationChecker();
							boolean mustIntegrate = op.mustIntegrate(selectedRule, nestedConstraint);
							if (mustIntegrate) {

								infoRequiredConstraint += nestedConstraint.getName() + System.lineSeparator();

								System.out.println("(" + nestedConstraint.getName()
										+ ") constraint is required for the rule (" + selectedRule.getName() + ")");

								// call the optimizer
								System.out.println("The constraint " + selectedNestedConstraint.getName()
										+ " should be integrated into rule " + selectedRule.getName());
								GC2AppCondCommand ial = new GC2AppCondCommand(henshinFile);
								setNamesToRuleNodes(selectedRule);
								System.out.println(this.getClass().getName() + " enables " + ial.getClass().getName());
								ial.enableOptimizer = true;
								ial.integrateAndleftInPlace(nestedConstraint, selectedRule);
								translationTime += ial.translationTime;
								numberOfIntegrated++;
							}
						}
					}

					try {
						selectedRule.getModule().eResource().save(null);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.err.println("Error on persisting the rule");
					}

					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Integration",
							"Integration is finished. The integration time is: (" + (translationTime / (double) 1000)
									+ ") second(s)." + System.lineSeparator() + System.lineSeparator()
									+ "Number of the required constraints are (" + numberOfIntegrated + "):"
									+ System.lineSeparator() + infoRequiredConstraint);

					close();
				}

			}
		});
		btnIntegrateAllRequired.setText("Construct Optimized Validity-Preserving AC (All Cs, R)");

		createContents();

		tableNestedConstraints.notifyListeners(SWT.Selection, new Event());
		tableRules.notifyListeners(SWT.Selection, new Event());
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Integrator Wizard");
		setSize(511, 728);

	}

	private void fillTableNestedConstraints(HashMap<Integer, NestedConstraint> hashmapAllNestedconstraints) {
		if (hashmapAllNestedconstraints != null) {
			tableNestedConstraints.removeAll();
			Iterator<Integer> keySetIterator = hashmapAllNestedconstraints.keySet().iterator();
			while (keySetIterator.hasNext()) {
				Integer key = keySetIterator.next();
				TableItem item = new TableItem(tableNestedConstraints, SWT.NONE);
				item.setText(0, key.toString());
				item.setText(1, hashmapAllNestedconstraints.get(key).getName());
			}
			tableNestedConstraints.select(0);
			TableItem[] selectedItem = tableNestedConstraints.getSelection();
			Integer ruleID = Integer.parseInt(selectedItem[0].getText(0));
			selectedNestedConstraint = hashmapAllNestedconstraints.get(ruleID);
		}

	}

	private void fillTableRules(HashMap<Integer, Rule> hashmapAllRules) {
		if (hashmapAllRules != null) {
			tableRules.removeAll();
			Iterator<Integer> keySetIterator = hashmapAllRules.keySet().iterator();
			while (keySetIterator.hasNext()) {
				Integer key = keySetIterator.next();
				TableItem item = new TableItem(tableRules, SWT.NONE);
				item.setText(0, key.toString());
				item.setText(1, ((Rule) hashmapAllRules.get(key)).getName());
			}
			tableRules.select(0);
			TableItem[] selectedItem = tableRules.getSelection();
			Integer ruleID = Integer.parseInt(selectedItem[0].getText(0));
			selectedRule = (Rule) hashmapAllRules.get(ruleID);
		}

	}

	private void setNamesToRuleNodes(Rule rule) {
		// Nodes in LHS and RHS should have same names
		for (Mapping m : rule.getMappings()) {
			if (m.getImage() != null && m.getOrigin() != null) {
				if (m.getImage().getName() == null && m.getOrigin().getName() == null) {
					String nodeName = "rp" + randomAlphaNumeric(3);
					m.getOrigin().setName(nodeName);
					m.getImage().setName(nodeName);
				}

			}
		}

		// Nodes in NC should have same names
		for (NestedCondition nc : rule.getLhs().getNestedConditions()) {
			for (Mapping ncM : nc.getMappings()) {
				if (ncM.getImage() != null && ncM.getOrigin() != null) {
					if (ncM.getImage().getName() == null && ncM.getOrigin().getName() == null) {
						String nodeName = "rf" + randomAlphaNumeric(3);
						ncM.getOrigin().setName(nodeName);
						ncM.getImage().setName(nodeName);
					}
				}
			}
		}

		for (Node node : rule.getLhs().getNodes()) {
			if (node.getName() == null)
				node.setName("rd" + randomAlphaNumeric(3));
		}

		for (Node node : rule.getRhs().getNodes()) {
			if (node.getName() == null)
				node.setName("rc" + randomAlphaNumeric(3));
		}
	}

	private String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
