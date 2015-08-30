package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationFactory;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Trace;
import org.eclipse.emf.henshin.sam.paf.AbstractConsumer;
import org.eclipse.emf.henshin.sam.paf.FilterDispatcher;
import org.eclipse.emf.henshin.sam.paf.IPipe;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class VerificationResultConsumer extends
		AbstractConsumer<GraphVerificationData> {
	
	@ResultDictEntry(entryName="stopOnWitness")
	private boolean stopOnWitness;
	private boolean printDebug;
	private int i = 0;
	private FilterDispatcher filterDispatcher;

	//private Set<VerificationResult> verificationResults;
	private Set<GraphVerificationData> verificationResults;

	private boolean running = true;

	private Set<IPipe<GraphVerificationData>> closedPipes;
	
	@ResultDictEntry(entryName="GTS is safe")
	private boolean isSafe = false;
	
	@ResultDictEntry(entryName="Counterexamples found")
	private long noOfResults = 0;
	
	private Map<String, IPipe<GraphVerificationData>> inputPipes;

	//public VerificationResult[] getVerificationResults() {
	public GraphVerificationData[] getVerificationResults() {
		if (this.running) {
			//log.warn("Attempt to read verificationResults while Thread is still running. the returned results might not be complete");
		}
		GraphVerificationData[] result = null;
		if (this.verificationResults != null) {
			GraphVerificationData[] tmpResult = new GraphVerificationData[this.verificationResults.size()];
//			int index = 0;
//			for (Iterator<VerificationResult> iter = this.verificationResults
//					.iterator(); iter.hasNext();) {
//				result[index] = iter.next();
//				index++;
//			}
			result = this.verificationResults.toArray(tmpResult);
		}
		return result;
	}
	
	public Map<String, IPipe<GraphVerificationData>> getInputPipes() {
		if (this.inputPipes == null) {
			this.inputPipes = new HashMap<String, IPipe<GraphVerificationData>>();
		}
		return this.inputPipes;
	}

	public void consume() {		
		if (this.verificationResults == null) {
			this.verificationResults = new HashSet<GraphVerificationData>();
		}
		try {
			for (Iterator<IPipe<GraphVerificationData>> iter = this.getInputPipes().values().iterator(); iter.hasNext();) {
				final IPipe<GraphVerificationData> pipe = iter.next();
				if (!closedPipes.contains(pipe)) {
					try {
						final GraphVerificationData gvd = pipe.dequeue();
						//final VerificationResult vr = convertToResult(gvd);
						
						
						this.verificationResults.add(gvd);/*						
						
						i++;
						
						Trace t = SamtraceFactory.eINSTANCE.createTrace();
						
						Annotation anno = SamannotationFactory.eINSTANCE.createAnnotation();
						anno.setSource(InvariantCheckingCore.ORIGINAL_FORBIDDEN_PATTERN_ANNOTATION_SOURCE);
						anno.setTarget(gvd.pair.first);
						t.getAnnotations().add(anno);
						//System.out.println("forbidden: " + ((NegatedCondition) gvd.pair.first.eContainer().eContainer()).getName());
						
						
						// remove NACs from target graph pattern
						/*
						Graph tgp = gvd.targetGraph;
						if (tgp.eClass() == SamrulesPackage.eINSTANCE.getRuleGraph() && ((RuleGraph) tgp).getCondition() != null) {
							for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(tgp).getNacs()) {
								for (Edge e : nac.getEdges()) {
									e.setSource(null);
									e.setTarget(null);
								}
							}
						}
						((RuleGraph) tgp).setCondition(null);*//*
						
						DiscreteTraceEntry src = SamtraceFactory.eINSTANCE.createDiscreteTraceEntry();
						src.setCurrentRule(gvd.pair.second);						
						src.setState(gvd.sourceGraph);
						Match ruleApplicability = SamtraceFactory.eINSTANCE.createMatch();
						for (Node n : gvd.sourceGraph.getNodes()) {
							PatternNode pn = (PatternNode) n;
							if (pn.getSameInRule() != null) {
								ruleApplicability.getNodeMatching().put(pn.getSameInRule(), pn);
							}
						}
						for (Edge e : gvd.sourceGraph.getEdges()) {
							PatternEdge pe = (PatternEdge) e;
							if (pe.getSameInRule() != null) {
								ruleApplicability.getEdgeMatching().put(pe.getSameInRule(), pe);
							}
						}
						src.setCurrentMatch(ruleApplicability);
						DiscreteTraceEntry tar = SamtraceFactory.eINSTANCE.createDiscreteTraceEntry();
						tar.setCurrentRule(gvd.pair.second);
						tar.setState(gvd.targetGraph);
						t.getEntries().add(src);
						t.getEntries().add(tar);
						
						Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("samtrace",	new XMIResourceFactoryImpl());
						SamtracePackage.eINSTANCE.getTrace();
						SamtracePackage.eINSTANCE.getMatch();
						URI gtsURI = filterDispatcher.getFilterInput().eResource().getURI();
						
						String resURI = gtsURI.trimSegments(1).toString();
						resURI = resURI + "/" + gvd.pair.second.getName() + i + ".samtrace";
						//ResourceSet rs1 = filterDispatcher.getFilterInput().eResource().getResourceSet();
						ResourceSet rs1 = new ResourceSetImpl();
						
						URI uri1 = URI.createURI(resURI);
						Resource r1 = rs1.createResource(uri1);
						//EObject o1 = r1.getContents().get(0);
						r1.getContents().clear();
						r1.getContents().add(t);
						try {
							r1.save(null);					
						} catch (IOException e) {
							System.out.println(e);
						}
						*/
						
						
						
						
						
						
						if (stopOnWitness){
							this.filterDispatcher.setContinueComputation(false);
						}
						return;
					} catch (IllegalStateException ise) {
						closedPipes.add(pipe);
						running = closedPipes.size() < this.getInputPipes().size();
					}
				} else {
					running = closedPipes.size() < this.getInputPipes().size();
				}
			}
		} catch (InterruptedException e) {
			running = false;
		}
	}


	public String getName() {
		return VerificationResultConsumer.class.getName();
	}

	public void run() {
		this.printDebug = this.getOption("printDebug");
		this.stopOnWitness = this.getOption("stopOnWitness");
		running = this.filterDispatcher.isContinueComputation();
		this.closedPipes = new HashSet<IPipe<GraphVerificationData>>(
				this.getInputPipes().size());		
		while (running) {
			//System.out.println("result ...");
			running = this.filterDispatcher.isContinueComputation();
			this.consume();
		}		
		for (Iterator<IPipe<GraphVerificationData>> iter = this.getInputPipes().values().iterator(); iter.hasNext(); iter.next()
				.decrementReaders())
			;
		
		// new
		this.noOfResults = i;		
		this.isSafe = (i == 0);
		
		
		//if (true) return;
		if (this.verificationResults != null && this.verificationResults.size() > 0) {
			this.noOfResults = this.verificationResults.size();
			this.noOfResults = this.verificationResults.size();
			this.isSafe = false;
			Assert.isNotNull(filterDispatcher.getFilterInput());
			//Assert.isTrue(filterDispatcher.getFilterInput().eClass() == MetamodelPackage.eINSTANCE.getVerificationTask());
			int i = 0;
			for (GraphVerificationData gvd : this.verificationResults) {
				i++;
				
				Trace t = SamtraceFactory.eINSTANCE.createTrace();
				
				Annotation anno = SamannotationFactory.eINSTANCE.createAnnotation();
				anno.setSource(InvariantCheckerPlugin.ORIGINAL_FORBIDDEN_PATTERN_ANNOTATION_SOURCE);
				anno.setTarget(gvd.pair.first);
				t.getAnnotations().add(anno);
				//System.out.println("forbidden: " + ((NegatedCondition) gvd.pair.first.eContainer().eContainer()).getName());
				
				DiscreteTraceEntry src = SamtraceFactory.eINSTANCE.createDiscreteTraceEntry();
				src.setCurrentRule(gvd.pair.second);
				src.setState(gvd.sourceGraph);
				Match ruleApplicability = SamtraceFactory.eINSTANCE.createMatch();
				for (Node n : gvd.sourceGraph.getNodes()) {
					PatternNode pn = (PatternNode) n;
					if (pn.getSameInRule() != null) {
						ruleApplicability.getNodeMatching().put(pn.getSameInRule(), pn);
					}
				}
				for (Edge e : gvd.sourceGraph.getEdges()) {
					PatternEdge pe = (PatternEdge) e;
					if (pe.getSameInRule() != null) {
						ruleApplicability.getEdgeMatching().put(pe.getSameInRule(), pe);
					}
				}
				src.setCurrentMatch(ruleApplicability);
				DiscreteTraceEntry tar = SamtraceFactory.eINSTANCE.createDiscreteTraceEntry();
				tar.setCurrentRule(gvd.pair.second);
				tar.setState(gvd.targetGraph);
				t.getEntries().add(src);
				t.getEntries().add(tar);
				
				Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("samtrace",	new XMIResourceFactoryImpl());
				SamtracePackage.eINSTANCE.getTrace();
				SamtracePackage.eINSTANCE.getMatch();
				URI gtsURI = filterDispatcher.getFilterInput().eResource().getURI();
				
				String resURI = gtsURI.trimSegments(1).toString();
				resURI = resURI + "/" + gvd.pair.second.getName() + i + ".samtrace";
				ResourceSet rs1 = filterDispatcher.getFilterInput().eResource().getResourceSet();
				
				URI uri1 = URI.createURI(resURI);
				Resource r1 = rs1.createResource(uri1);
				//EObject o1 = r1.getContents().get(0);
				r1.getContents().clear();
				r1.getContents().add(t);
				try {
					r1.save(null);					
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		} else {
			this.isSafe = true;
			this.noOfResults = 0;
		}
	}

	public FilterDispatcher getFilterDispatcher() {
		return this.filterDispatcher;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public boolean setFilterDispatcher(FilterDispatcher value) {
		boolean changed = false;
		if (value != this.filterDispatcher) {
			if (this.filterDispatcher != null) {
				FilterDispatcher oldDisp = this.filterDispatcher;
				this.filterDispatcher = null;
				oldDisp.removeFromIDispatchable(this);
			}
			this.filterDispatcher = value;
			changed = true;
			if (value != null) {
				value.addToIDispatchable(this);
			}
		}
		return changed;
	}
}
