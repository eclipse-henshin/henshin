package org.eclipse.emf.henshin.sam.invcheck;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.ContextGenerator;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class ProverDelegate implements IObjectActionDelegate {

	private ISelection selection;
	private Shell shell;
	
	@Override
	public void run(IAction action) {
		boolean success = false;
		if (selection instanceof TreeSelection) {
			Object b = ((TreeSelection) selection).getFirstElement();
			IFile f = (IFile) b;
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("samrules",	new XMIResourceFactoryImpl());
			SamrulesPackage.eINSTANCE.getGTS();
						
			ResourceSet rs = new ResourceSetImpl();
			URI uri = URI.createURI(f.getLocationURI().toString());
			Resource r = rs.getResource(uri, true);
			EObject o = r.getContents().get(0);
			if (o.eClass() == SamrulesPackage.eINSTANCE.getGTS()) {
				GTS gts = (GTS) o;
				
				ContextGenerator prover = new ContextGenerator();
				
				TypeGraph tg = gts.getTypes().get(0);
				
				Set<Graph> restrictingConstraints = new HashSet<Graph>();
				Set<RuleGraph> generationConstraints = new HashSet<RuleGraph>();
				LogicalGCCoupling constraints = (LogicalGCCoupling) gts.getTypes().get(0).getConditions().get(0);
				for (GraphCondition gc :  constraints.getOperands()) {
					NegatedCondition nc = (NegatedCondition) gc;
					if (nc.getName().equals("Pair2f-NoSend-NoFire")) {
					//if (nc.getName().equals("NoFirstEvent-NoInitState")) {
						Quantification quant = (Quantification) nc.getOperand();
						prover.setProofGoal((RuleGraph) quant.getContext());
						System.out.println("found proof goal");
					} else if (nc.getName().equals("NoActiveE-NoActiveS")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found NoActiveE-NoActiveS");
					} else if (nc.getName().equals("NoActiveS-NoActiveE")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found NoActiveS-NoActiveE");
					} else if (nc.getName().equals("noInitE-noInitS")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noInitE-noInitS");
					} else if (nc.getName().equals("noInitS-noInitE")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noInitS-noInitE");
					} else if (nc.getName().equals("noMessage-NoCom-TGG")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noMessage-NoCom-TGG");
					} else if (nc.getName().equals("noNextEvent-noPostState-TGG")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noNextEvent-noPostState-TGG");
					} else if (nc.getName().equals("noNextEvent-noPostState")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noNextEvent-noPostState");
					} else if (nc.getName().equals("noMessage-noCom")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noMessage-NoCom");
					} else if (nc.getName().equals("noPostState-noNextEvent")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noPostState-noNextEvent");
					} else if (nc.getName().equals("noCom-noMessage")) {
						Quantification quant = (Quantification) nc.getOperand();
						generationConstraints.add((RuleGraph) quant.getContext());
						System.out.println("found noCom-noMessage");
					} else {
						Quantification quant = (Quantification) nc.getOperand();
						Graph g = quant.getContext();
						if (SamrulesPackage.eINSTANCE.getRuleGraph() != g.eClass() || ((RuleGraph) g).getCondition() == null) {
							restrictingConstraints.add(g);	
							System.out.println("found " + nc.getName());
						} else {
							// can we do that?
							generationConstraints.add((RuleGraph) g);
						}
					}
				}
				prover.setGenerationConstraints(generationConstraints);
				prover.setRestrictingConstraints(restrictingConstraints);
				success = prover.proof();
			} else {
				success = false;
			}
			String dialogMessage = null;
			if (success) {
				dialogMessage = "Proof succesful";
			} else {
				dialogMessage = "Could not be proven";
			}		
			
			final Display disp = PlatformUI.getWorkbench().getDisplay();
			final MessageDialog md = new MessageDialog(shell, "Proof Result", disp.getSystemImage(SWT.ICON_INFORMATION), dialogMessage, 0, new String[] {"OK"}, 0);
			
			disp.asyncExec(new Runnable() {
				public void run() {
					md.open();	
				}
			});		

		}		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;		
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();		
	}

}
