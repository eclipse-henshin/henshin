/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.ui;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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

import org.eclipse.emf.henshin.ocl2ac.tool.commands.LaxCond2AppCondCommand;
import org.eclipse.emf.henshin.ocl2ac.tool.optimizer.IntegrationChecker;
import laxcondition.Condition;

public class WizardLaxCond2AppCond extends Shell {
	private static final String HENSHIN = ".henshin";
	private Table tableCompactConditions;
	private Table tableRules;
	private Condition selectedCompactCondition = null;
	private Rule selectedRule = null;
	private IFile henshinFile = null;
	private IFile compactConditionModelFile = null;
	private final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

	/**
	 * Create the shell.
	 * 
	 * @param display
	 * @param mapAllRules
	 */
	public WizardLaxCond2AppCond(final Display display, IFile henshinFile,
			HashMap<Integer, Condition> hashmapAllLaxCondition, HashMap<Integer, Rule> hashmapAllRules,
			IFile compactConditionModelFile) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new RowLayout(SWT.VERTICAL));
		this.henshinFile = henshinFile;
		this.compactConditionModelFile = compactConditionModelFile;
		Group grpRepairActions = new Group(this, SWT.NONE);
		grpRepairActions.setLayout(new GridLayout(1, false));
		grpRepairActions.setLayoutData(new RowData(487, 248));
		grpRepairActions.setText("Compact Graph Constraints");

		Group groupRATop = new Group(grpRepairActions, SWT.NONE);
		GridData gd_groupRATop = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_groupRATop.heightHint = 23;
		gd_groupRATop.widthHint = 471;
		groupRATop.setLayoutData(gd_groupRATop);

		Label lblNewLabel_1 = new Label(groupRATop, SWT.NONE);
		lblNewLabel_1.setBounds(10, 16, 283, 20);
		lblNewLabel_1.setText("Please select a compact graph constraint");

		tableCompactConditions = new Table(grpRepairActions, SWT.BORDER | SWT.FULL_SELECTION);
		tableCompactConditions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		GridData gd_tableAction = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tableAction.heightHint = 180;
		tableCompactConditions.setLayoutData(gd_tableAction);
		tableCompactConditions.setHeaderVisible(true);
		tableCompactConditions.setLinesVisible(true);

		TableColumn clnID_NC = new TableColumn(tableCompactConditions, SWT.NONE);
		clnID_NC.setWidth(46);
		clnID_NC.setText("ID");

		TableColumn clnName_NC = new TableColumn(tableCompactConditions, SWT.NONE);
		clnName_NC.setWidth(425);
		clnName_NC.setText("Compact Graph Constraint");

		fillTableCompactConditions(hashmapAllLaxCondition);

		tableCompactConditions.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableCompactConditions.getItemCount() > 0) {
					TableItem[] selectedItem = tableCompactConditions.getSelection();
					Integer nestedConstraintID = Integer.parseInt(selectedItem[0].getText(0));
					selectedCompactCondition = hashmapAllLaxCondition.get(nestedConstraintID);
				}

			}
		});

		Group grpTargets = new Group(this, SWT.NONE);
		grpTargets.setLayout(new GridLayout(1, false));
		grpTargets.setLayoutData(new RowData(487, 239));
		grpTargets.setText("Rules");

		Group groupTargtTop = new Group(grpTargets, SWT.NONE);
		GridData gd_groupTargtTop = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_groupTargtTop.heightHint = 36;
		gd_groupTargtTop.widthHint = 477;
		groupTargtTop.setLayoutData(gd_groupTargtTop);

		Label lblPleaseSelectA = new Label(groupTargtTop, SWT.NONE);
		lblPleaseSelectA.setBounds(10, 24, 213, 25);
		lblPleaseSelectA.setText("Please select a rule");

		tableRules = new Table(grpTargets, SWT.BORDER | SWT.FULL_SELECTION);
		tableRules.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

		});

		GridData gd_tableTargets = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tableTargets.heightHint = 187;
		tableRules.setLayoutData(gd_tableTargets);
		tableRules.setHeaderVisible(true);
		tableRules.setLinesVisible(true);

		TableColumn clnID_rule = new TableColumn(tableRules, SWT.NONE);
		clnID_rule.setWidth(48);
		clnID_rule.setText("ID");

		TableColumn clnName_Rule = new TableColumn(tableRules, SWT.NONE);
		clnName_Rule.setWidth(429);
		clnName_Rule.setText("Rule");
		fillTableRules(hashmapAllRules);
		Group groupFooter = new Group(this, SWT.NONE);
		groupFooter.setLayoutData(new RowData(488, 59));

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

		Button btnIntegrate = new Button(groupFooter, SWT.NONE);
		btnIntegrate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Integrate and Lefting
				if (selectedCompactCondition != null && selectedRule != null) {

					System.out.println(selectedCompactCondition + "  " + selectedRule);

					LaxCond2AppCondCommand ilcc2ac = new LaxCond2AppCondCommand(henshinFile);
					setNamesToRuleNodes(selectedRule);
					ilcc2ac.integrateAndleft(selectedCompactCondition, selectedRule);

					long translationTime = ilcc2ac.translationTime;
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Integration", "Integration is finished. The integration time is: ("
									+ (translationTime / (double) 1000) + ") second(s).");
					close();

				}
			}
		});
		btnIntegrate.setBounds(10, 24, 114, 34);
		btnIntegrate.setText("Integrate");

		Button btnCheckforintegration = new Button(groupFooter, SWT.NONE);
		btnCheckforintegration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (selectedCompactCondition != null && selectedRule != null) {

					IntegrationChecker op = new IntegrationChecker();
					boolean mustIntegrate = op.mustIntegrateWithoutAttribute(selectedRule, selectedCompactCondition);
					if (mustIntegrate) {

						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Info (required)", "The constraint SHOULD be integrated into the rule.");

						boolean yes = MessageDialog.openQuestion(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Question",
								"Do you want to integrate it now?");
						if (yes) {
							System.out.println(selectedCompactCondition + "  " + selectedRule);
							LaxCond2AppCondCommand ilcc2ac = new LaxCond2AppCondCommand(henshinFile);
							setNamesToRuleNodes(selectedRule);
							ilcc2ac.integrateAndleft(selectedCompactCondition, selectedRule);
							long translationTime = ilcc2ac.translationTime;
							MessageDialog.openInformation(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Integration",
									"Integration is finished. The integration time is: ("
											+ (translationTime / (double) 1000) + ") second(s).");
							close();
						}

					} else {
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Info (not required)",
								"There is NO need to integrate the selected constraint into the selected rule since the rule application will not violate the constraint.");

						boolean yes = MessageDialog.openQuestion(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Question",
								"Do you want to integrate it anyway?");
						if (yes) {
							System.out.println(selectedCompactCondition + "  " + selectedRule);
							LaxCond2AppCondCommand ilcc2ac = new LaxCond2AppCondCommand(henshinFile);
							setNamesToRuleNodes(selectedRule);
							ilcc2ac.integrateAndleft(selectedCompactCondition, selectedRule);
							long translationTime = ilcc2ac.translationTime;
							MessageDialog.openInformation(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Integration",
									"Integration is finished. The integration time is: ("
											+ (translationTime / (double) 1000) + ") second(s).");
							close();
						}
					}
				}

			}
		});
		btnCheckforintegration.setBounds(142, 24, 155, 34);
		btnCheckforintegration.setText("Check-for-Integration");

		createContents();

		tableCompactConditions.notifyListeners(SWT.Selection, new Event());
		tableRules.notifyListeners(SWT.Selection, new Event());
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Integrator Wizard (Shift + Left)");
		setSize(520, 689);

	}

	private void fillTableCompactConditions(HashMap<Integer, Condition> hashmapAllCompactConditions) {
		if (hashmapAllCompactConditions != null) {
			tableCompactConditions.removeAll();
			Iterator<Integer> keySetIterator = hashmapAllCompactConditions.keySet().iterator();
			while (keySetIterator.hasNext()) {
				Integer key = keySetIterator.next();
				TableItem item = new TableItem(tableCompactConditions, SWT.NONE);
				item.setText(0, key.toString());
				item.setText(1, hashmapAllCompactConditions.get(key).getName());
			}
			tableCompactConditions.select(0);
			TableItem[] selectedItem = tableCompactConditions.getSelection();
			Integer ruleID = Integer.parseInt(selectedItem[0].getText(0));
			selectedCompactCondition = hashmapAllCompactConditions.get(ruleID);
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
