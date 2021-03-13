/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.pivot.BooleanLiteralExp;
import org.eclipse.ocl.pivot.CallExp;
import org.eclipse.ocl.pivot.Class;
import org.eclipse.ocl.pivot.CollectionItem;
import org.eclipse.ocl.pivot.CollectionKind;
import org.eclipse.ocl.pivot.CollectionLiteralExp;
import org.eclipse.ocl.pivot.CollectionLiteralPart;
import org.eclipse.ocl.pivot.CollectionType;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.DataType;
import org.eclipse.ocl.pivot.EnumLiteralExp;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.IfExp;
import org.eclipse.ocl.pivot.IntegerLiteralExp;
import org.eclipse.ocl.pivot.Iteration;
import org.eclipse.ocl.pivot.IteratorExp;
import org.eclipse.ocl.pivot.LiteralExp;
import org.eclipse.ocl.pivot.Model;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.Operation;
import org.eclipse.ocl.pivot.OperationCallExp;
import org.eclipse.ocl.pivot.OrderedSetType;
import org.eclipse.ocl.pivot.PivotFactory;
import org.eclipse.ocl.pivot.PivotPackage;
import org.eclipse.ocl.pivot.PrimitiveLiteralExp;
import org.eclipse.ocl.pivot.Property;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.RealLiteralExp;
import org.eclipse.ocl.pivot.SetType;
import org.eclipse.ocl.pivot.StringLiteralExp;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.ocl.pivot.TypeExp;
import org.eclipse.ocl.pivot.UnlimitedNaturalLiteralExp;
import org.eclipse.ocl.pivot.Variable;
import org.eclipse.ocl.pivot.VariableExp;

import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.Constants;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.TranslatorResourceSet;
import graph.Attribute;
import graph.Edge;
import graph.Graph;
import graph.GraphFactory;
import graph.Node;
import laxcondition.Condition;
import laxcondition.Formula;
import laxcondition.LaxCondition;
import laxcondition.LaxconditionFactory;
import laxcondition.Operator;
import laxcondition.QuantifiedLaxCondition;
import laxcondition.Quantifier;

public class Translator {

	protected IFile oclasFile = null;
	protected IFile ecoreFile = null;
	public EList<Constraint> invariants = null;
	private EPackage typeModel = null;
	protected Model oclModel = null;
	protected LaxconditionFactory laxconditionFactory = null;
	protected GraphFactory graphFactory = null;
	protected PivotFactory oclFactory = null;
	protected int varIndex;
	protected List<String> varNames;

	public Translator() {
	}

	public Translator(IFile oclasFile, IFile ecoreFile) {
		this.oclasFile = oclasFile;
		this.ecoreFile = ecoreFile;
		this.invariants = new BasicEList<Constraint>();
		this.laxconditionFactory = LaxconditionFactory.eINSTANCE;
		this.graphFactory = GraphFactory.eINSTANCE;
		this.oclFactory = PivotFactory.eINSTANCE;
		this.varIndex = 1;
		varNames = new ArrayList<String>();
		initModels();
		prepareOCLModel();
		refactorOCLModel();
	}

	protected void initModels() {
		URI uriOclAS = URI.createPlatformResourceURI(this.oclasFile.getFullPath().toString(), true);
		URI uriEcore = URI.createPlatformResourceURI(this.ecoreFile.getFullPath().toString(), true);
		if (uriOclAS != null && uriEcore != null) {
			// Load the input models and the corresponding invariants
			PivotPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("*.oclas", new XMIResourceFactoryImpl());
			ResourceSet resSet = new ResourceSetImpl();
			Resource resourceOclAS = resSet.getResource(uriOclAS, true);
			this.oclModel = (Model) resourceOclAS.getContents().get(0);
			// Load Ecore for meta/type model references
			Resource resourceEcore = resSet.getResource(uriEcore, true);
			this.setTypeModel((EPackage) resourceEcore.getContents().get(0));
		}
	}

	protected void prepareOCLModel() {
		TreeIterator<EObject> iter = this.oclModel.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (eObject instanceof Class) {
				invariants.addAll(((Class) eObject).getOwnedInvariants());
			}
			if (eObject instanceof OperationCallExp) {
				OperationCallExp operationCallExp = (OperationCallExp) eObject;
				Operation op = operationCallExp.getReferredOperation();
				// put operation name to operation call
				operationCallExp.setName(op.getName());
			}
			if (eObject instanceof IteratorExp) {
				IteratorExp iteratorExp = (IteratorExp) eObject;
				Iteration iteration = iteratorExp.getReferredIteration();
				// put iteration name to iteration call
				iteratorExp.setName(iteration.getName());
			}
			// refactor literals
			if (eObject instanceof LiteralExp) {
				LiteralExp literalExp = (LiteralExp) eObject;
				refactorLiteralExpression(literalExp);
			}
		}
	}

	protected void refactorOCLModel() {
		// Definition 17
		TreeIterator<EObject> iter = this.oclModel.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = null;
			eObject = iter.next();
			if (eObject instanceof OperationCallExp) {
				OperationCallExp operationCallExp = (OperationCallExp) eObject;
				// replace 'includes' and 'excludes'
				if (operationCallExp.getName().equals(Constants.INCLUDES)
						|| operationCallExp.getName().equals(Constants.EXCLUDES)) {
					refactorIncludesExcludes(operationCallExp);
				}
				// replace 'including' and 'excluding'
				if (operationCallExp.getName().equals(Constants.INCLUDING)
						|| operationCallExp.getName().equals(Constants.EXCLUDING)) {
					refactorIncludingExcluding(operationCallExp);
				}
				// replace '<>'
				if (operationCallExp.getName().equals(Constants.NOTEQUALS)) {
					refactorNotEquals(operationCallExp);
				}
				// replace 'isEmpty'
				if (operationCallExp.getName().equals(Constants.ISEMPTY)) {
					refactorIsEmpty(operationCallExp);
				}
				// transform size comparison to 'greater than'
				if (isSizeComparison(operationCallExp)) {
					homogenizeSizeComparison(operationCallExp);
					System.out.println(operationCallExp);
				}
			}
			if (eObject instanceof IteratorExp) {
				IteratorExp iteratorExp = (IteratorExp) eObject;
				// replace 'any'
				if (iteratorExp.getName().equals(Constants.ANY)) {
					refactorAny(iteratorExp);
				}
				// replace 'one'
				if (iteratorExp.getName().equals(Constants.ONE)) {
					refactorOne(iteratorExp);
				}
			}
		}
	}

	protected void refactorAny(IteratorExp iteratorExp) {
		iteratorExp.setName(Constants.SELECT);
		Class clazz = iteratorExp.getOwnedIterators().get(0).getType().isClass();
		OrderedSetType setType = oclFactory.createOrderedSetType();
		setType.setElementType(clazz);
		iteratorExp.setType(setType);
	}

	protected void refactorOne(IteratorExp iteratorExp) {
		OperationCallExp equalsExpr = oclFactory.createOperationCallExp();
		equalsExpr.setName(Constants.EQUALS);
		OperationCallExp sizeExpr = oclFactory.createOperationCallExp();
		sizeExpr.setName(Constants.SIZE);
		equalsExpr.setOwnedSource(sizeExpr);
		IntegerLiteralExp litExpr = oclFactory.createIntegerLiteralExp();
		litExpr.setName(Integer.toString(1));
		equalsExpr.getOwnedArguments().add(litExpr);
		EObject container = iteratorExp.eContainer();
		if (container instanceof CallExp) {
			CallExp expr = (CallExp) container;
			if (expr.getOwnedSource() == iteratorExp) {
				expr.setOwnedSource(equalsExpr);
			}
		}
		if (container instanceof OperationCallExp) {
			OperationCallExp expr = (OperationCallExp) container;
			if (expr.getOwnedArguments().get(0) == iteratorExp) {
				expr.getOwnedArguments().clear();
				expr.getOwnedArguments().add(equalsExpr);
			}
		}
		if (container instanceof ExpressionInOCL) {
			ExpressionInOCL expr = (ExpressionInOCL) container;
			expr.setOwnedBody(equalsExpr);
		}
		if (container instanceof IteratorExp) {
			IteratorExp expr = (IteratorExp) container;
			if (expr.getOwnedBody() == iteratorExp) {
				expr.setOwnedBody(equalsExpr);
			}
		}
		if (container instanceof IfExp) {
			IfExp expr = (IfExp) container;
			if (expr.getOwnedCondition() == iteratorExp) {
				expr.setOwnedCondition(equalsExpr);
			}
			if (expr.getOwnedThen() == iteratorExp) {
				expr.setOwnedThen(equalsExpr);
			}
			if (expr.getOwnedElse() == iteratorExp) {
				expr.setOwnedElse(equalsExpr);
			}
		}
		sizeExpr.setOwnedSource(iteratorExp);
		Class clazz = iteratorExp.getOwnedIterators().get(0).getType().isClass();
		OrderedSetType setType = oclFactory.createOrderedSetType();
		setType.setElementType(clazz);
		iteratorExp.setType(setType);
		iteratorExp.setName(Constants.SELECT);
		homogenizeSizeComparison(equalsExpr);
	}

	protected void homogenizeSizeComparison(OperationCallExp operationCallExp) {
		OCLExpression sourceExpr = operationCallExp.getOwnedSource();
		String operation = operationCallExp.getName();
		OCLExpression argumentExpr = operationCallExp.getOwnedArguments().get(0);
		int literal = Integer.parseInt(argumentExpr.getName());
		// transform 'greater'
		if (operation.equals(Constants.GREATER)) {
			operationCallExp.setName(Constants.GREATEROREQUALS);
			literal++;
			operationCallExp.getOwnedArguments().get(0).setName(Integer.toString(literal));
		}
		// transform 'equals'
		if (operation.equals(Constants.EQUALS)) {
			OperationCallExp greaterEqualsExpr1 = oclFactory.createOperationCallExp();
			greaterEqualsExpr1.setName(Constants.GREATEROREQUALS);
			operationCallExp.setOwnedSource(greaterEqualsExpr1);
			greaterEqualsExpr1.setOwnedSource(sourceExpr);
			operationCallExp.getOwnedArguments().clear();
			greaterEqualsExpr1.getOwnedArguments().add(argumentExpr);
			operationCallExp.setName(Constants.AND);
			OperationCallExp notExpr = oclFactory.createOperationCallExp();
			notExpr.setName(Constants.NOT);
			operationCallExp.getOwnedArguments().add(notExpr);
			Copier copier = new Copier();
			OperationCallExp greaterEqualsExpr2 = (OperationCallExp) copier.copy(greaterEqualsExpr1);
			copier.copyReferences();
			literal++;
			greaterEqualsExpr2.getOwnedArguments().get(0).setName(Integer.toString(literal));
			notExpr.setOwnedSource(greaterEqualsExpr2);
		}
		// transform 'less equals'
		if (operation.equals(Constants.LESSEROREQUALS)) {
			operationCallExp.setName(Constants.LESSER);
			operation = Constants.LESSER;
			literal++;
			operationCallExp.getOwnedArguments().get(0).setName(Integer.toString(literal));
		}
		// transform 'less'
		if (operation.equals(Constants.LESSER)) {
			operationCallExp.setName(Constants.NOT);
			operationCallExp.getOwnedArguments().clear();
			OperationCallExp greaterEqualsExpr = oclFactory.createOperationCallExp();
			greaterEqualsExpr.setName(Constants.GREATEROREQUALS);
			operationCallExp.setOwnedSource(greaterEqualsExpr);
			greaterEqualsExpr.setOwnedSource(sourceExpr);
			greaterEqualsExpr.getOwnedArguments().add(argumentExpr);
		}
		// transform 'not equals'
		if (operation.equals(Constants.NOTEQUALS)) {
			operationCallExp.setName(Constants.NOT);
			operationCallExp.getOwnedArguments().clear();
			OperationCallExp equalsExpr = oclFactory.createOperationCallExp();
			equalsExpr.setName(Constants.EQUALS);
			operationCallExp.setOwnedSource(equalsExpr);
			equalsExpr.setOwnedSource(sourceExpr);
			equalsExpr.getOwnedArguments().add(argumentExpr);
			homogenizeSizeComparison(equalsExpr);
		}
	}

	protected void refactorIsEmpty(OperationCallExp operationCallExp) {
		OCLExpression expr1 = operationCallExp.getOwnedSource();
		OCLExpression expr2 = null;
		if (operationCallExp.getOwnedArguments().size() > 0)
			expr2 = operationCallExp.getOwnedArguments().get(0);
		OperationCallExp notEmptyExpression = PivotFactory.eINSTANCE.createOperationCallExp();
		notEmptyExpression.setName(Constants.NOTEMPTY);
		operationCallExp.setName(Constants.NOT);
		operationCallExp.setOwnedSource(notEmptyExpression);
		if (expr2 != null)
			operationCallExp.getOwnedArguments().remove(expr2);
		notEmptyExpression.setOwnedSource(expr1);
		if (expr2 != null)
			notEmptyExpression.getOwnedArguments().add(expr2);
	}

	protected void refactorNotEquals(OperationCallExp operationCallExp) {
		OCLExpression expr1 = operationCallExp.getOwnedSource();
		OCLExpression expr2 = null;
		if (operationCallExp.getOwnedArguments().size() > 0)
			expr2 = operationCallExp.getOwnedArguments().get(0);
		if (!(expr1.getType() instanceof DataType) && !(expr2.getType() instanceof DataType)) {
			OperationCallExp equalsExpression = PivotFactory.eINSTANCE.createOperationCallExp();
			equalsExpression.setName(Constants.EQUALS);
			operationCallExp.setName(Constants.NOT);
			operationCallExp.setOwnedSource(equalsExpression);
			if (expr2 != null)
				operationCallExp.getOwnedArguments().remove(expr2);
			equalsExpression.setOwnedSource(expr1);
			if (expr2 != null)
				equalsExpression.getOwnedArguments().add(expr2);
		}
	}

	protected void refactorIncludingExcluding(OperationCallExp operationCallExp) {
		if (operationCallExp.getName().equals(Constants.INCLUDING)) {
			operationCallExp.setName(Constants.UNION);
		} else {
			operationCallExp.setName(Constants.DIFFERENCE);
		}
		PivotFactory oclFactory = PivotFactory.eINSTANCE;
		CollectionLiteralExp collectionLiteralExp = oclFactory.createCollectionLiteralExp();
		collectionLiteralExp.setKind(CollectionKind.SET);
		CollectionItem item = oclFactory.createCollectionItem();
		OCLExpression expr = operationCallExp.getOwnedArguments().get(0);
		operationCallExp.getOwnedArguments().remove(expr);
		item.setOwnedItem(expr);
		collectionLiteralExp.getOwnedParts().add(item);
		operationCallExp.getOwnedArguments().add(collectionLiteralExp);
	}

	protected void refactorIncludesExcludes(OperationCallExp operationCallExp) {
		if (operationCallExp.getName().equals(Constants.INCLUDES)) {
			operationCallExp.setName(Constants.INCLUDESALL);
		} else {
			operationCallExp.setName(Constants.EXCLUDESALL);
		}
		PivotFactory oclFactory = PivotFactory.eINSTANCE;
		CollectionLiteralExp collectionLiteralExp = oclFactory.createCollectionLiteralExp();
		collectionLiteralExp.setKind(CollectionKind.SET);
		CollectionItem item = oclFactory.createCollectionItem();
		OCLExpression expr = operationCallExp.getOwnedArguments().get(0);
		operationCallExp.getOwnedArguments().remove(expr);
		item.setOwnedItem(expr);
		collectionLiteralExp.getOwnedParts().add(item);
		operationCallExp.getOwnedArguments().add(collectionLiteralExp);
	}

	protected void refactorLiteralExpression(LiteralExp literalExp) {
		if (literalExp instanceof StringLiteralExp) {
			StringLiteralExp exp = (StringLiteralExp) literalExp;
			exp.setName(exp.getStringSymbol());
		}
		if (literalExp instanceof BooleanLiteralExp) {
			BooleanLiteralExp exp = (BooleanLiteralExp) literalExp;
			exp.setName(Boolean.toString(exp.isBooleanSymbol()));
		}
		if (literalExp instanceof RealLiteralExp) {
			RealLiteralExp exp = (RealLiteralExp) literalExp;
			exp.setName(Double.toString(exp.getRealSymbol().doubleValue()));
		}
		if (literalExp instanceof UnlimitedNaturalLiteralExp) {
			UnlimitedNaturalLiteralExp exp = (UnlimitedNaturalLiteralExp) literalExp;
			exp.setName(Integer.toString(exp.getUnlimitedNaturalSymbol().intValue()));
		}
		if (literalExp instanceof IntegerLiteralExp) {
			IntegerLiteralExp exp = (IntegerLiteralExp) literalExp;
			exp.setName(Integer.toString(exp.getIntegerSymbol().intValue()));
		}
	}

	public long translate() {
		long start = System.currentTimeMillis();
		Date date = new GregorianCalendar().getTime();
		for (Constraint inv : invariants) {
			Condition condition = null;
			try {
				condition = laxconditionFactory.createCondition();
				condition.setName(inv.getName());
				condition.setTypeGraph(this.getTypeModel());
				condition.setLaxCondition(translate_I(inv));
				persistLaxCondition(date, condition);
			} catch (Exception e) {
				System.err.println("The constraint " + condition.getName() + " is not well translated");
				e.getStackTrace();
			}
		}
		long stop = System.currentTimeMillis();
		return (stop - start);
	}

	protected LaxCondition translate_I(Constraint inv) {
		System.out.println("translate_I");
		QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
		laxCondition.setQuantifier(Quantifier.FORALL);
		Graph graph = graphFactory.createGraph();
		graph.setTypegraph(this.getTypeModel());
		Node node = graphFactory.createNode();
		ExpressionInOCL exprInOcl = null;
		exprInOcl = (ExpressionInOCL) inv.getOwnedSpecification();
		node.setName(exprInOcl.getOwnedContext().getName());
		node.setType(getEClass(exprInOcl.getOwnedContext().getType().isClass()));
		graph.getNodes().add(node);
		laxCondition.setGraph(graph);
		System.out.println(laxCondition.toString());
		System.out.println(exprInOcl.getOwnedBody().toString());
		LaxCondition translate_E = translate_E(exprInOcl.getOwnedBody());
		System.out.println(translate_E.toString());
		laxCondition.setCondition(translate_E);
		return laxCondition;
	}

	protected LaxCondition translate_E(OCLExpression expr) {
		System.out.println(this.getClass().getName() + " translate_E()");
		// Rule 2 (a)
		if (expr instanceof BooleanLiteralExp) {
			System.out.println("Rule 2 a");
			if (expr.getName().equals(Constants.TRUE)) {
				return laxconditionFactory.createTrue();
			}
			if (expr.getName().equals(Constants.FALSE)) {
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.NOT);
				formula.getArguments().add(laxconditionFactory.createTrue());
				return formula;
			}
		}
		// Rules 2(b), 2(c), 2(d), 2(e), 4(a), 4(b)
		if (expr instanceof OperationCallExp) {
			OperationCallExp opCallExpr = (OperationCallExp) expr;
			// Rule 2 (b)
			if (opCallExpr.getName().equals(Constants.NOT)) {
				System.out.println("Rule 2 b");
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.NOT);
				formula.getArguments().add(translate_E(opCallExpr.getOwnedSource()));
				return formula;
			}
			// Rule 2 (c)
			if (opCallExpr.getName().equals(Constants.AND)) {
				System.out.println("Rule 2 c");
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.AND);
				formula.getArguments().add(translate_E(opCallExpr.getOwnedSource()));
				formula.getArguments().add(translate_E(opCallExpr.getOwnedArguments().get(0)));
				return formula;
			}
			// Rule 2 (d)
			if (opCallExpr.getName().equals(Constants.OR)) {
				System.out.println("Rule 2 d");
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.OR);
				formula.getArguments().add(translate_E(opCallExpr.getOwnedSource()));
				formula.getArguments().add(translate_E(opCallExpr.getOwnedArguments().get(0)));
				return formula;
			}
			// Rule 2 (e)
			if (opCallExpr.getName().equals(Constants.IMPLIES)) {
				System.out.println("Rule 2 e");
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.OR);
				Formula notFormula = laxconditionFactory.createFormula();
				notFormula.setOp(Operator.NOT);
				notFormula.getArguments().add(translate_E(opCallExpr.getOwnedSource()));
				formula.getArguments().add(notFormula);
				formula.getArguments().add(translate_E(opCallExpr.getOwnedArguments().get(0)));
				return formula;
			}
			// Rule 4 (a)

			if (opCallExpr.getName().equals(Constants.INCLUDESALL)) {
				System.out.println("Rule 4 a");
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.FORALL);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(getNextVarName());
				Type srcOpCallExprType = opCallExpr.getOwnedSource().getType();

				
				if (srcOpCallExprType instanceof OrderedSetType)
					node.setType(getEClass(((OrderedSetType) srcOpCallExprType).getElementType().isClass()));
				else if (srcOpCallExprType instanceof SetType)
					node.setType(getEClass(((SetType) srcOpCallExprType).getElementType().isClass()));
				else
					node.setType(getEClass(((CollectionType) srcOpCallExprType).getElementType().isClass()));

				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.IMPLIES);
				Node setNode1 = getSetNode(node);
				formula.getArguments().add(translate_S(opCallExpr.getOwnedArguments().get(0), setNode1));
				Node setNode2 = getSetNode(node);
				formula.getArguments().add(translate_S(opCallExpr.getOwnedSource(), setNode2));
				laxCondition.setCondition(formula);
				return laxCondition;
			}
			// Rule 4 (b)
			if (opCallExpr.getName().equals(Constants.EXCLUDESALL)) {
				System.out.println("Rule 4 b");
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.FORALL);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(getNextVarName());
				Type srcOpCallExprType = opCallExpr.getOwnedSource().getType();

				if (srcOpCallExprType instanceof OrderedSetType)
					node.setType(getEClass(((OrderedSetType) srcOpCallExprType).getElementType().isClass()));
				else if (srcOpCallExprType instanceof SetType)
					node.setType(getEClass(((SetType) srcOpCallExprType).getElementType().isClass()));
				else
					node.setType(getEClass(((CollectionType) srcOpCallExprType).getElementType().isClass()));

				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.IMPLIES);
				Node setNode1 = getSetNode(node);
				formula.getArguments().add(translate_S(opCallExpr.getOwnedArguments().get(0), setNode1));
				Node setNode2 = getSetNode(node);
				Formula notFormula = laxconditionFactory.createFormula();
				notFormula.setOp(Operator.NOT);
				notFormula.getArguments().add(translate_S(opCallExpr.getOwnedSource(), setNode2));
				formula.getArguments().add(notFormula);
				laxCondition.setCondition(formula);
				return laxCondition;
			}
			// Rule 5
			if (opCallExpr.getName().equals(Constants.NOTEMPTY)) {
				System.out.println("Rule 5");
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.EXISTS);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(getNextVarName());
				Type srcOpCallExprType = opCallExpr.getOwnedSource().getType();

				if (srcOpCallExprType instanceof OrderedSetType)
					node.setType(getEClass(((OrderedSetType) srcOpCallExprType).getElementType().isClass()));
				else if (srcOpCallExprType instanceof SetType)
					node.setType(getEClass(((SetType) srcOpCallExprType).getElementType().isClass()));
				else
					node.setType(getEClass(((CollectionType) srcOpCallExprType).getElementType().isClass()));

				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Node setNode = getSetNode(node);
				laxCondition.setCondition(translate_S(opCallExpr.getOwnedSource(), setNode));
				return laxCondition;
			}
			// Rule 6
			if ((opCallExpr.getName().equals(Constants.GREATEROREQUALS)) && isSizeComparison(opCallExpr)) {

				OperationCallExp sourceOpCallExpr = (OperationCallExp) opCallExpr.getOwnedSource();

				Type srcOpCallExprType = sourceOpCallExpr.getOwnedSource().getType();

				EClass type = null;
				if (srcOpCallExprType instanceof OrderedSetType)
					type = getEClass(((OrderedSetType) srcOpCallExprType).getElementType().isClass());
				else if (srcOpCallExprType instanceof SetType)
					type = getEClass(((SetType) srcOpCallExprType).getElementType().isClass());
				else
					type = getEClass(((CollectionType) srcOpCallExprType).getElementType().isClass());

				int literal = Integer.parseInt(opCallExpr.getOwnedArguments().get(0).getName());
				// Rule 6 (a)
				if (literal > 0) {
					System.out.println("Rule 6 a");
					String[] vars = new String[literal];
					for (int i = 0; i < literal; i++) {
						vars[i] = getNextVarName();
					}
					QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition.setQuantifier(Quantifier.EXISTS);
					Graph graph = graphFactory.createGraph();
					graph.setTypegraph(this.getTypeModel());
					for (int i = 0; i < literal; i++) {
						Node node = graphFactory.createNode();
						node.setName(vars[i]);
						node.setType(type);
						graph.getNodes().add(node);
					}
					laxCondition.setGraph(graph);
					if (literal == 1) {
						Node setNode1 = graphFactory.createNode();
						setNode1.setName(vars[0]);
						setNode1.setType(type);
						laxCondition.setCondition(translate_S(sourceOpCallExpr.getOwnedSource(), setNode1));
					} else {
						Formula formula = laxconditionFactory.createFormula();
						formula.setOp(Operator.AND);
						for (int i = 0; i < literal; i++) {
							Node setNode = graphFactory.createNode();
							setNode.setName(vars[i]);
							setNode.setType(type);
							formula.getArguments().add(translate_S(sourceOpCallExpr.getOwnedSource(), setNode));
						}
						laxCondition.setCondition(formula);
					}
					return laxCondition;
				}
				// Rule 6 (b)
				if (literal == 0) {
					System.out.println("Rule 6 b");
					return laxconditionFactory.createTrue();
				}
			}
			// Rule 7
			if (opCallExpr.getName().equals(Constants.EQUALS)) {
				OCLExpression expr1 = opCallExpr.getOwnedSource();
				OCLExpression expr2 = opCallExpr.getOwnedArguments().get(0);
				// Rule 7 (a)
				if (expr1.getType() instanceof Class && expr2.getType() instanceof Class
						&& !(expr1.getType() instanceof DataType) && !(expr2.getType() instanceof DataType)
						&& !(expr1.getType() instanceof OrderedSetType)
						&& !(expr2.getType() instanceof OrderedSetType)) {
					System.out.println("Rule 7 a");
					QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition.setQuantifier(Quantifier.EXISTS);
					Graph graph = graphFactory.createGraph();
					graph.setTypegraph(this.getTypeModel());
					Node node = graphFactory.createNode();
					node.setName(getNextVarName());
					node.setType(getEClass(expr1.getType().isClass()));
					graph.getNodes().add(node);
					laxCondition.setGraph(graph);
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.AND);
					Node setNode1 = getSetNode(node);
					Node setNode2 = getSetNode(node);
					formula.getArguments().add(translate_N(expr1, setNode1));
					formula.getArguments().add(translate_N(expr2, setNode2));
					laxCondition.setCondition(formula);
					return laxCondition;
				}
				// Rule 7 (b)
				if (expr1.getType() instanceof OrderedSetType && expr2.getType() instanceof OrderedSetType) {
					System.out.println("Rule 7 b");
					QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition.setQuantifier(Quantifier.FORALL);
					Graph graph = graphFactory.createGraph();
					graph.setTypegraph(this.getTypeModel());
					Node node = graphFactory.createNode();
					node.setName(getNextVarName());

					Type expr1Type = expr1.getType();

					if (expr1Type instanceof OrderedSetType)
						node.setType(getEClass(((OrderedSetType) expr1Type).getElementType().isClass()));
					else if (expr1Type instanceof SetType)
						node.setType(getEClass(((SetType) expr1Type).getElementType().isClass()));
					else
						node.setType(getEClass(((CollectionType) expr1Type).getElementType().isClass()));

					graph.getNodes().add(node);
					laxCondition.setGraph(graph);
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.EQUIVALENT);
					Node setNode1 = getSetNode(node);
					Node setNode2 = getSetNode(node);
					formula.getArguments().add(translate_S(expr1, setNode1));
					formula.getArguments().add(translate_S(expr2, setNode2));
					laxCondition.setCondition(formula);
					return laxCondition;
				}
			}
			// Rules 8 and 9
			if (isComparisonOperator(opCallExpr)) {
				OCLExpression expr1 = opCallExpr.getOwnedSource();
				OCLExpression expr2 = opCallExpr.getOwnedArguments().get(0);
				// Rule 8
				if (expr1 instanceof PropertyCallExp && expr2 instanceof PrimitiveLiteralExp) {
					System.out.println("Rule 8");
					EClass type = getEClass(((PropertyCallExp) expr1).getOwnedSource().getType().isClass());
					QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition.setQuantifier(Quantifier.EXISTS);
					Graph graph = graphFactory.createGraph();
					graph.setTypegraph(this.getTypeModel());
					Node node = graphFactory.createNode();
					node.setName(getNextVarName());
					node.setType(type);
					graph.getNodes().add(node);
					laxCondition.setGraph(graph);
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.AND);
					Node setNode = getSetNode(node);
					formula.getArguments().add(translate_N(((PropertyCallExp) expr1).getOwnedSource(), setNode));
					QuantifiedLaxCondition innerCondition = laxconditionFactory.createQuantifiedLaxCondition();
					innerCondition.setQuantifier(Quantifier.EXISTS);
					Graph innerGraph = graphFactory.createGraph();
					innerGraph.setTypegraph(this.getTypeModel());
					Node innerNode = graphFactory.createNode();
					innerNode.setName(node.getName());
					innerNode.setType(node.getType());
					Attribute attribute = graphFactory.createAttribute();
					attribute.setType(getEAttribute(type, (PropertyCallExp) expr1));
					attribute.setOp(opCallExpr.getName());
					attribute.setValue(expr2.getName());
					innerNode.getAttributes().add(attribute);
					innerGraph.getNodes().add(innerNode);
					innerCondition.setGraph(innerGraph);
					innerCondition.setCondition(laxconditionFactory.createTrue());
					formula.getArguments().add(innerCondition);
					laxCondition.setCondition(formula);
					return laxCondition;
				}

				// Rule 8 a 
				if (expr1 instanceof PropertyCallExp && expr2 instanceof EnumLiteralExp) {
					System.out.println("Rule 8 a NN");
					EClass type = getEClass(((PropertyCallExp) expr1).getOwnedSource().getType().isClass());
					QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition.setQuantifier(Quantifier.EXISTS);
					Graph graph = graphFactory.createGraph();
					graph.setTypegraph(this.getTypeModel());
					Node node = graphFactory.createNode();
					node.setName(getNextVarName());
					node.setType(type);
					graph.getNodes().add(node);
					laxCondition.setGraph(graph);
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.AND);
					Node setNode = getSetNode(node);
					formula.getArguments().add(translate_N(((PropertyCallExp) expr1).getOwnedSource(), setNode));
					QuantifiedLaxCondition innerCondition = laxconditionFactory.createQuantifiedLaxCondition();
					innerCondition.setQuantifier(Quantifier.EXISTS);
					Graph innerGraph = graphFactory.createGraph();
					innerGraph.setTypegraph(this.getTypeModel());
					Node innerNode = graphFactory.createNode();
					innerNode.setName(node.getName());
					innerNode.setType(node.getType());
					Attribute attribute = graphFactory.createAttribute();
					attribute.setType(getEAttribute(type, (PropertyCallExp) expr1));
					attribute.setOp(opCallExpr.getName());

					EnumLiteralExp enm = (EnumLiteralExp) expr2;
					attribute.setValue(enm.getReferredLiteral().getName());

					innerNode.getAttributes().add(attribute);
					innerGraph.getNodes().add(innerNode);
					innerCondition.setGraph(innerGraph);
					innerCondition.setCondition(laxconditionFactory.createTrue());
					formula.getArguments().add(innerCondition);
					laxCondition.setCondition(formula);
					return laxCondition;
				}
				// Rule 9 
				if (expr1 instanceof PropertyCallExp && expr2 instanceof PropertyCallExp) {
					String varName = getNextVarName();
					if (varNames == null)
						varNames = new ArrayList<String>();
					if (!varNames.contains(varName))
						varNames.add(varName);

					EClass type1 = getEClass(((PropertyCallExp) expr1).getOwnedSource().getType().isClass());
					QuantifiedLaxCondition laxCondition1 = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition1.setQuantifier(Quantifier.EXISTS);

					laxcondition.Variable variable = laxconditionFactory.createVariable();
					variable.setName(varName);
					laxCondition1.getVariables().add(variable);

					Graph graph1 = graphFactory.createGraph();
					graph1.setTypegraph(this.getTypeModel());
					Node node1 = graphFactory.createNode();
					node1.setName(getNextVarName());
					node1.setType(type1);
					graph1.getNodes().add(node1);
					laxCondition1.setGraph(graph1);
					Formula formula1 = laxconditionFactory.createFormula();
					formula1.setOp(Operator.AND);
					Node navigationNode1a = getSetNode(node1);
					Attribute attribute1a = graphFactory.createAttribute();
					attribute1a.setType(getEAttribute(type1, (PropertyCallExp) expr1));
					attribute1a.setOp(opCallExpr.getName());
					attribute1a.setValue(varName);
					navigationNode1a.getAttributes().add(attribute1a);
					LaxCondition innerCondition1a = translate_N(((PropertyCallExp) expr1).getOwnedSource(),
							navigationNode1a);
					formula1.getArguments().add(innerCondition1a);
					Node navigationNode1b = getSetNode(node1);
					Attribute attribute1b = graphFactory.createAttribute();
					attribute1b.setType(getEAttribute(type1, (PropertyCallExp) expr1));
					attribute1b.setOp(Constants.EQUALS);
					attribute1b.setValue(varName);
					navigationNode1b.getAttributes().add(attribute1b);
					LaxCondition innerCondition1b = translate_N(((PropertyCallExp) expr2).getOwnedSource(),
							navigationNode1b);
					formula1.getArguments().add(innerCondition1b);
					laxCondition1.setCondition(formula1);
					// lax condition #2
					EClass type2a = getEClass(((PropertyCallExp) expr1).getOwnedSource().getType().isClass());
					EClass type2b = getEClass(((PropertyCallExp) expr2).getOwnedSource().getType().isClass());
					QuantifiedLaxCondition laxCondition2 = laxconditionFactory.createQuantifiedLaxCondition();
					laxCondition2.setQuantifier(Quantifier.EXISTS);
					Graph graph2 = graphFactory.createGraph();
					graph2.setTypegraph(this.getTypeModel());
					Node node2a = graphFactory.createNode();
					node2a.setName(getNextVarName());
					node2a.setType(type2a);
					Node node2b = graphFactory.createNode();
					node2b.setName(getNextVarName());
					node2b.setType(type2b);
					graph2.getNodes().add(node2a);
					graph2.getNodes().add(node2b);
					laxCondition2.setGraph(graph2);
					Formula formula2 = laxconditionFactory.createFormula();
					formula2.setOp(Operator.AND);
					Node navigationNode2a = getSetNode(node2a);
					Attribute attribute2a = graphFactory.createAttribute();
					attribute2a.setType(getEAttribute(type2a, (PropertyCallExp) expr1));
					attribute2a.setOp(opCallExpr.getName());
					attribute2a.setValue(varName);
					navigationNode2a.getAttributes().add(attribute2a);
					LaxCondition innerCondition2a = translate_N(((PropertyCallExp) expr1).getOwnedSource(),
							navigationNode2a);
					formula2.getArguments().add(innerCondition2a);
					Node navigationNode2b = getSetNode(node2b);
					Attribute attribute2b = graphFactory.createAttribute();
					attribute2b.setType(getEAttribute(type2b, (PropertyCallExp) expr2));
					attribute2b.setOp(Constants.EQUALS);
					attribute2b.setValue(varName);
					navigationNode2b.getAttributes().add(attribute2b);
					LaxCondition innerCondition2b = translate_N(((PropertyCallExp) expr2).getOwnedSource(),
							navigationNode2b);
					formula2.getArguments().add(innerCondition2b);
					laxCondition2.setCondition(formula2);
					// Rule 9 (b) - same sources
					if (isIsomorphic(((PropertyCallExp) expr1).getOwnedSource(),
							((PropertyCallExp) expr2).getOwnedSource())) {
						System.out.println("Rule 9 b");
						return laxCondition1;
					}
					// Rule 9 (c) - different clans
					if (isClanDisjoint(((PropertyCallExp) expr1).getOwnedSource(),
							((PropertyCallExp) expr2).getOwnedSource())) {
						System.out.println("Rule 9 c");
						return laxCondition2;
					}
					// Rule 9 (a) - general case
					System.out.println("Rule 9 a");
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.OR);
					formula.getArguments().add(laxCondition1);
					formula.getArguments().add(laxCondition2);
					return formula;
				}
			}
			// Rule 10 (a)
			if (opCallExpr.getName().equals(Constants.ISKINDOF)) {
				System.out.println("Rule 10 a");
				OCLExpression sourceExpr = opCallExpr.getOwnedSource();
				OCLExpression typeExpr = opCallExpr.getOwnedArguments().get(0);
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.EXISTS);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(getNextVarName());
				node.setType(getEClass(((TypeExp) typeExpr).getReferredType().isClass()));
				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Node navNode = getSetNode(node);
				navNode.setType(getEClass(sourceExpr.getType().isClass()));
				laxCondition.setCondition(translate_N(sourceExpr, navNode));
				return laxCondition;
			}
			// Rule 10 (b)
			if (opCallExpr.getName().equals(Constants.ISTYPEOF)) {
				System.out.println("Rule 10 b");
				OCLExpression sourceExpr = opCallExpr.getOwnedSource();
				OCLExpression typeExpr = opCallExpr.getOwnedArguments().get(0);
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.EXISTS);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(getNextVarName());
				node.setType(getEClass(((TypeExp) typeExpr).getReferredType().isClass()));
				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Node navNode = getSetNode(node);
				navNode.setType(getEClass(sourceExpr.getType().isClass()));
				EList<EClass> subclasses = getAllSubclasses(node.getType());
				if (subclasses.isEmpty()) {
					laxCondition.setCondition(translate_N(sourceExpr, navNode));
				} else {
					Formula and = laxconditionFactory.createFormula();
					and.setOp(Operator.AND);
					and.getArguments().add(translate_N(sourceExpr, navNode));
					for (EClass subclass : subclasses) {
						Formula not = laxconditionFactory.createFormula();
						not.setOp(Operator.NOT);
						QuantifiedLaxCondition innerCondition = laxconditionFactory.createQuantifiedLaxCondition();
						innerCondition.setQuantifier(Quantifier.EXISTS);
						Graph innerGraph = graphFactory.createGraph();
						innerGraph.setTypegraph(this.getTypeModel());
						Node innerNode = graphFactory.createNode();
						innerNode.setName(getNextVarName());
						innerNode.setType(subclass);
						innerGraph.getNodes().add(innerNode);
						innerCondition.setGraph(innerGraph);
						innerCondition.setCondition(laxconditionFactory.createTrue());
						not.getArguments().add(innerCondition);
						and.getArguments().add(not);
					}
					laxCondition.setCondition(and);
				}
				return laxCondition;
			}
		}
		// Rule 2 (f)
		if (expr instanceof IfExp) {
			System.out.println("Rule 2 f");
			IfExp ifExpr = (IfExp) expr;
			Formula formula = laxconditionFactory.createFormula();
			formula.setOp(Operator.OR);
			Formula firstAnd = laxconditionFactory.createFormula();
			firstAnd.setOp(Operator.AND);
			firstAnd.getArguments().add(translate_E(ifExpr.getOwnedCondition()));
			firstAnd.getArguments().add(translate_E(ifExpr.getOwnedThen()));
			formula.getArguments().add(firstAnd);
			Formula secondAnd = laxconditionFactory.createFormula();
			secondAnd.setOp(Operator.AND);
			Formula notFormula = laxconditionFactory.createFormula();
			notFormula.setOp(Operator.NOT);
			notFormula.getArguments().add(translate_E(ifExpr.getOwnedCondition()));
			secondAnd.getArguments().add(notFormula);
			secondAnd.getArguments().add(translate_E(ifExpr.getOwnedElse()));
			formula.getArguments().add(secondAnd);
			return formula;
		}
		// Rules 3(a), 3(b)
		if (expr instanceof IteratorExp) {
			IteratorExp itExpr = (IteratorExp) expr;
			// Rule 3 (a)
			if (itExpr.getName().equals(Constants.EXISTS)) {
				System.out.println("Rule 3 a");
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.EXISTS);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(itExpr.getOwnedIterators().get(0).getName());
				node.setType(getEClass(itExpr.getOwnedIterators().get(0).getType().isClass()));
				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.AND);
				Node setNode = getSetNode(node);
				formula.getArguments().add(translate_S(itExpr.getOwnedSource(), setNode));
				formula.getArguments().add(translate_E(itExpr.getOwnedBody()));
				laxCondition.setCondition(formula);
				return laxCondition;
			}
			// Rule 3 (b)
			if (itExpr.getName().equals(Constants.FORALL)) {
				System.out.println("Rule 3 b");
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.FORALL);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node node = graphFactory.createNode();
				node.setName(itExpr.getOwnedIterators().get(0).getName());
				node.setType(getEClass(itExpr.getOwnedIterators().get(0).getType().isClass()));
				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.IMPLIES);
				Node setNode = getSetNode(node);
				formula.getArguments().add(translate_S(itExpr.getOwnedSource(), setNode));
				formula.getArguments().add(translate_E(itExpr.getOwnedBody()));
				laxCondition.setCondition(formula);
				return laxCondition;
			}
		}
		return null;
	}

	protected LaxCondition translate_N(OCLExpression expr, Node node) {
		// Rule 11
		if (expr instanceof OperationCallExp) {
			OperationCallExp opCallExpr = (OperationCallExp) expr;
			if (opCallExpr.getName().equals(Constants.ASTYPE)) {
				System.out.println("Rule 11");
				OCLExpression sourceExpr = opCallExpr.getOwnedSource();
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.EXISTS);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				Node graphNode = getSetNode(node);
				graph.getNodes().add(graphNode);
				laxCondition.setGraph(graph);
				Node navNode = getSetNode(node);
				navNode.setType(getEClass(sourceExpr.getType().isClass()));
				laxCondition.setCondition(translate_N(sourceExpr, navNode));
				return laxCondition;
			}
		}
		// Rule 12 (a)
		if (expr instanceof VariableExp) {
			System.out.println("Rule 12 a");
			VariableExp varExpr = (VariableExp) expr;
			String varName = varExpr.getReferredVariable().getName();
			String nodeName = node.getName();
			String newNodeName = varName + Constants.EQUALS + nodeName;
			node.setName(newNodeName);
			QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
			laxCondition.setQuantifier(Quantifier.EXISTS);
			Graph graph = graphFactory.createGraph();
			graph.setTypegraph(this.getTypeModel());
			graph.getNodes().add(node);
			laxCondition.setGraph(graph);
			laxCondition.setCondition(laxconditionFactory.createTrue());
			return laxCondition;
		}
		// Rule 12 (b)
		if (expr instanceof PropertyCallExp) {
			System.out.println("Rule 12 b");
			PropertyCallExp propCallExpr = (PropertyCallExp) expr;
			Property prop = propCallExpr.getReferredProperty();
			OCLExpression sourceExpr = propCallExpr.getOwnedSource();
			EClass sourceType = getEClass(sourceExpr.getType().isClass());
			EReference ref = getEReference(sourceType, prop);

			QuantifiedLaxCondition laxCondition1 = laxconditionFactory.createQuantifiedLaxCondition();
			laxCondition1.setQuantifier(Quantifier.EXISTS);
			Graph graph1 = graphFactory.createGraph();
			graph1.setTypegraph(this.getTypeModel());
			Node node1 = graphFactory.createNode();
			node1.setName(getNextVarName());
			node1.setType(sourceType);
			Edge edge1 = graphFactory.createEdge();
			edge1.setType(ref);
			edge1.setSource(node1);
			Node setNode = getSetNode(node);
			edge1.setTarget(setNode);
			graph1.getNodes().add(setNode);
			graph1.getNodes().add(node1);
			graph1.getEdges().add(edge1);
			laxCondition1.setGraph(graph1);
			laxCondition1.setCondition(translate_N(sourceExpr, getSetNode(node1)));
			// case 1 - different clans
			if (!getClan(node.getType()).contains(sourceType)) {
				return laxCondition1;
			}
			// case 2 - source is in clan
			else {
				// lax condition #2
				QuantifiedLaxCondition laxCondition2 = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition2.setQuantifier(Quantifier.EXISTS);
				Graph graph2 = graphFactory.createGraph();
				graph2.setTypegraph(this.getTypeModel());
				Node node2 = getSetNode(node1);
				Edge edge2 = graphFactory.createEdge();
				edge2.setType(ref);
				edge2.setSource(node2);
				edge2.setTarget(node2);
				graph2.getNodes().add(node2);
				graph2.getEdges().add(edge2);
				laxCondition2.setGraph(graph2);
				laxCondition2.setCondition(translate_N(sourceExpr, getSetNode(node1)));
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.OR);
				formula.getArguments().add(laxCondition1);
				formula.getArguments().add(laxCondition2);
				return formula;
			}
		}
		return laxconditionFactory.createTrue();
	}

	protected LaxCondition translate_S(OCLExpression expr, Node node) {
		// Rule 12 (c)
		System.out.println(expr);
		if (expr instanceof PropertyCallExp) {
			System.out.println("Rule 12 c");
			PropertyCallExp propCallExpr = (PropertyCallExp) expr;
			Property prop = propCallExpr.getReferredProperty();
			OCLExpression sourceExpr = propCallExpr.getOwnedSource();
			EClass sourceType = getEClass(sourceExpr.getType().isClass());
			EReference ref = getEReference(sourceType, prop);

			QuantifiedLaxCondition laxCondition1 = laxconditionFactory.createQuantifiedLaxCondition();
			laxCondition1.setQuantifier(Quantifier.EXISTS);
			Graph graph1 = graphFactory.createGraph();
			graph1.setTypegraph(this.getTypeModel());
			Node node1 = graphFactory.createNode();
			node1.setName(getNextVarName());
			node1.setType(sourceType);
			Edge edge1 = graphFactory.createEdge();
			edge1.setType(ref);
			edge1.setSource(node1);
			Node setNode = getSetNode(node);
			edge1.setTarget(setNode);
			graph1.getNodes().add(setNode);
			graph1.getNodes().add(node1);
			graph1.getEdges().add(edge1);
			laxCondition1.setGraph(graph1);
			laxCondition1.setCondition(translate_N(sourceExpr, getSetNode(node1)));
			// case 1 - different clans
			if (!getClan(node.getType()).contains(sourceType)) {
				return laxCondition1;
			}
			// case 2 - source is in clan
			else {
				// lax condition #2
				QuantifiedLaxCondition laxCondition2 = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition2.setQuantifier(Quantifier.EXISTS);
				Graph graph2 = graphFactory.createGraph();
				graph2.setTypegraph(this.getTypeModel());
				Node node2 = getSetNode(node1);
				Edge edge2 = graphFactory.createEdge();
				edge2.setType(ref);
				edge2.setSource(node2);
				edge2.setTarget(node2);
				graph2.getNodes().add(node2);
				graph2.getEdges().add(edge2);
				laxCondition2.setGraph(graph2);
				laxCondition2.setCondition(translate_N(sourceExpr, getSetNode(node1)));
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.OR);
				formula.getArguments().add(laxCondition1);
				formula.getArguments().add(laxCondition2);
				return formula;
			}
		}
		// Rules 13 and 14
		if (expr instanceof IteratorExp) {
			IteratorExp itExpr = (IteratorExp) expr;
			Variable iterator = itExpr.getOwnedIterators().get(0);
			OCLExpression expr1 = itExpr.getOwnedSource();
			OCLExpression expr2 = itExpr.getOwnedBody();
			// Rule 13 (a)
			if (itExpr.getName().equals(Constants.SELECT)) {
				System.out.println("Rule 13 a");
				iterator.setName(node.getName());
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.AND);
				formula.getArguments().add(translate_S(expr1, node));
				formula.getArguments().add(translate_E(expr2));
				return formula;
			}
			// Rule 13 (b)
			if (itExpr.getName().equals(Constants.REJECT)) {
				System.out.println("Rule 13 b");
				iterator.setName(node.getName());
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.AND);
				formula.getArguments().add(translate_S(expr1, node));
				Formula notFormula = laxconditionFactory.createFormula();
				notFormula.setOp(Operator.NOT);
				notFormula.getArguments().add(translate_E(expr2));
				formula.getArguments().add(notFormula);
				return formula;
			}
			// Rule 14
			if (itExpr.getName().equals(Constants.COLLECT)) {
				System.out.println("Rule 14");
				QuantifiedLaxCondition laxcondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxcondition.setQuantifier(Quantifier.EXISTS);
				Graph graph1 = graphFactory.createGraph();
				graph1.setTypegraph(getTypeModel());
				Node node1 = graphFactory.createNode();
				node1.setName(iterator.getName());
				node1.setType(getEClass(iterator.getType().isClass()));
				graph1.getNodes().add(node1);
				laxcondition.setGraph(graph1);
				Formula formula = laxconditionFactory.createFormula();
				formula.setOp(Operator.AND);
				Node node2 = getSetNode(node1);
				formula.getArguments().add(translate_S(expr1, node2));
				// Rule 14 (a)
				if (expr2.getType() instanceof OrderedSetType) {
					System.out.println(" from 14: a");
					formula.getArguments().add(translate_S(expr2, node));
				}
				// Rule 14 (b)
				else {
					System.out.println(" from 14: b");
					formula.getArguments().add(translate_N(expr2, node));
				}
				laxcondition.setCondition(formula);
				return laxcondition;
			}
		}
		// Rule 15
		if (expr instanceof OperationCallExp) {
			OperationCallExp opCallExpr = (OperationCallExp) expr;
			OCLExpression expr1 = opCallExpr.getOwnedSource();
			OCLExpression expr2 = null;

			// Rule 15 e We just ignore oclAsSet
			if (opCallExpr.getOwnedSource() != null) {
				if (opCallExpr.getName().equals(Constants.ASSET)) {
					System.out.println("Rule 15 e");
					return translate_S(opCallExpr.getOwnedSource(), node);
				}
			}

			if (!opCallExpr.getOwnedArguments().isEmpty()) {
				expr2 = opCallExpr.getOwnedArguments().get(0);
				if (expr1.getType() instanceof OrderedSetType && expr2.getType() instanceof OrderedSetType) {
					// Rule 15 (a)
					if (opCallExpr.getName().equals(Constants.UNION)) {
						System.out.println("Rule 15 (a)");
						Formula formula = laxconditionFactory.createFormula();
						formula.setOp(Operator.OR);
						Node setNode1 = getSetNode(node);
						formula.getArguments().add(translate_S(expr1, setNode1));
						Node setNode2 = getSetNode(node);
						formula.getArguments().add(translate_S(expr2, setNode2));
						return formula;
					}
					// Rule 15 (b)
					if (opCallExpr.getName().equals(Constants.INTERSECTION)) {
						System.out.println("Rule 15 (b)");
						Formula formula = laxconditionFactory.createFormula();
						formula.setOp(Operator.AND);
						Node setNode1 = getSetNode(node);
						formula.getArguments().add(translate_S(expr1, setNode1));
						Node setNode2 = getSetNode(node);
						formula.getArguments().add(translate_S(expr2, setNode2));
						return formula;
					}
					// Rule 15 (c)
					if (opCallExpr.getName().equals(Constants.DIFFERENCE)) {
						System.out.println("Rule 15 (c)");
						Formula formula = laxconditionFactory.createFormula();
						formula.setOp(Operator.AND);
						Node setNode1 = getSetNode(node);
						formula.getArguments().add(translate_S(expr1, setNode1));
						Node setNode2 = getSetNode(node);
						Formula notFormula = laxconditionFactory.createFormula();
						notFormula.setOp(Operator.NOT);
						notFormula.getArguments().add(translate_S(expr2, setNode2));
						formula.getArguments().add(notFormula);
						return formula;
					}
					// Rule 15 (d)
					if (opCallExpr.getName().equals(Constants.SYMMETRIC_DIFFERENCE)) {
						System.out.println("Rule 15 (d)");
						Formula formula = laxconditionFactory.createFormula();
						formula.setOp(Operator.XOR);
						Node setNode1 = getSetNode(node);
						formula.getArguments().add(translate_S(expr1, setNode1));
						Node setNode2 = getSetNode(node);
						formula.getArguments().add(translate_S(expr2, setNode2));
						return formula;
					}
				}
			}

			// Rule 16
			if (opCallExpr.getName().equals(Constants.ALL_INSTANCES)) {
				System.out.println("Rule 16");
				QuantifiedLaxCondition laxCondition = laxconditionFactory.createQuantifiedLaxCondition();
				laxCondition.setQuantifier(Quantifier.EXISTS);
				Graph graph = graphFactory.createGraph();
				graph.setTypegraph(this.getTypeModel());
				graph.getNodes().add(node);
				laxCondition.setGraph(graph);
				laxCondition.setCondition(laxconditionFactory.createTrue());
				return laxCondition;
			}
		}
		if (expr instanceof CollectionLiteralExp) {
			CollectionLiteralExp collectionLiteralExp = (CollectionLiteralExp) expr;
			// Rule 17
			if (collectionLiteralExp.getKind().equals(CollectionKind.SET)) {
				if (collectionLiteralExp.getOwnedParts().isEmpty()) {
					System.out.println("Rule 17 a");
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.NOT);
					formula.getArguments().add(laxconditionFactory.createTrue());
					return formula;
				}
				if (collectionLiteralExp.getOwnedParts().size() == 1) {
					System.out.println("Rule 17 b");
					CollectionItem item = (CollectionItem) collectionLiteralExp.getOwnedParts().get(0);
					return translate_N(item.getOwnedItem(), node);
				}
				if (collectionLiteralExp.getOwnedParts().size() > 1) {
					System.out.println("Rule 17 c");
					Formula formula = laxconditionFactory.createFormula();
					formula.setOp(Operator.OR);
					for (CollectionLiteralPart part : collectionLiteralExp.getOwnedParts()) {
						CollectionItem item = (CollectionItem) part;
						formula.getArguments().add(translate_N(item.getOwnedItem(), node));
					}
					return formula;
				}
			}
		}
		return laxconditionFactory.createTrue();
	}

	protected EReference getEReference(EClass eClass, Property prop) {
		for (EReference ref : eClass.getEAllReferences()) {
			if (ref.getName().equals(prop.getName())) {

				return ref;
			}
		}
		return null;
	}

	protected boolean isIsomorphic(OCLExpression expr1, OCLExpression expr2) {
		if (expr1 instanceof VariableExp && expr2 instanceof VariableExp) {
			VariableExp varExpr1 = (VariableExp) expr1;
			VariableExp varExpr2 = (VariableExp) expr2;
			return (varExpr1.getReferredVariable() == varExpr2.getReferredVariable());
		}
		if (expr1 instanceof PropertyCallExp && expr2 instanceof PropertyCallExp) {
			PropertyCallExp propExpr1 = (PropertyCallExp) expr1;
			PropertyCallExp propExpr2 = (PropertyCallExp) expr2;
			if (propExpr1.getReferredProperty() == propExpr2.getReferredProperty()) {
				return isIsomorphic(propExpr1.getOwnedSource(), propExpr2.getOwnedSource());
			} else {
				return false;
			}
		}
		return false;
	}

	protected boolean isClanDisjoint(OCLExpression expr1, OCLExpression expr2) {
		EClass eClass1 = getEClass(expr1.getType().isClass());
		EClass eClass2 = getEClass(expr2.getType().isClass());
		EList<EClass> clan1 = getClan(eClass1);
		EList<EClass> clan2 = getClan(eClass2);
		for (EClass eClass : clan1) {
			if (clan2.contains(eClass)) {
				return false;
			}
		}
		return true;
	}

	protected EList<EClass> getClan(EClass eClass) {
		EList<EClass> eClasses = new BasicEList<EClass>();
		eClasses.add(eClass);
		eClasses.addAll(getAllSubclasses(eClass));
		return eClasses;
	}

	protected EList<EClass> getAllSubclasses(EClass eClass) {
		EList<EClass> eClasses = new BasicEList<EClass>();
		if (eClass != null) {
			EPackage ePackage = eClass.getEPackage();
			TreeIterator<EObject> iter = ePackage.eAllContents();
			while (iter.hasNext()) {
				EObject eObject = iter.next();
				if (eObject instanceof EClass) {
					EClass clazz = (EClass) eObject;
					if (clazz.getEAllSuperTypes().contains(eClass)) {
						eClasses.add(clazz);
					}
				}
			}
		} else {
			System.err.println("Error: " + "Param is null- getAllSubclasses");
		}
		return eClasses;
	}

	protected EAttribute getEAttribute(EClass type, PropertyCallExp expr) {
		if (expr != null && type != null) {
			Property prop = expr.getReferredProperty();
			for (EAttribute attr : type.getEAllAttributes()) {
				if (attr.getName().equals(prop.getName())) {
					return attr;
				}
			}
		}
		return null;
	}

	protected boolean isComparisonOperator(OperationCallExp opCallExpr) {
		if (opCallExpr.getName().equals(Constants.EQUALS))
			return true;
		if (opCallExpr.getName().equals(Constants.NOTEQUALS))
			return true;
		if (opCallExpr.getName().equals(Constants.GREATER))
			return true;
		if (opCallExpr.getName().equals(Constants.GREATEROREQUALS))
			return true;
		if (opCallExpr.getName().equals(Constants.LESSER))
			return true;
		if (opCallExpr.getName().equals(Constants.LESSEROREQUALS))
			return true;
		return false;
	}

	protected boolean isSizeComparison(OperationCallExp opCallExpr) {
		if (opCallExpr.getOwnedSource() instanceof OperationCallExp) {
			OperationCallExp sourceOpCallExpr = (OperationCallExp) opCallExpr.getOwnedSource();
			if (sourceOpCallExpr.getName().equals(Constants.SIZE)) {
				OCLExpression argExpr = opCallExpr.getOwnedArguments().get(0);
				if (argExpr instanceof IntegerLiteralExp || argExpr instanceof RealLiteralExp
						|| argExpr instanceof UnlimitedNaturalLiteralExp) {
					return true;
				}
			}
		}
		return false;
	}

	protected String getNextVarName() {
		String varName = Constants.VAR;
		varName += this.varIndex;
		this.varIndex++;
		return varName;
	}

	protected Node getSetNode(Node node) {
		Node setNode = graphFactory.createNode();
		setNode.setName(node.getName());
		setNode.setType(node.getType());
		return setNode;
	}

	public void persistLaxCondition(Date date, Condition condition) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String path = oclasFile.getParent().getLocation().append(Constants.LAX_CONDITIONS + sdf.format(date))
				.toOSString();
		TranslatorResourceSet resourceSet = new TranslatorResourceSet(path);
		resourceSet.saveEObject(condition, condition.getName().concat(Constants.LAX_CONDITION));
		try {
			oclasFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected EClass getEClass(Class clazz) {
		for (EClassifier classifier : this.getTypeModel().getEClassifiers()) {
			if (classifier instanceof EClass && classifier.getName().equals(clazz.getName())) {
				return (EClass) classifier;
			}
		}
		return null;
	}

	public EPackage getTypeModel() {
		return typeModel;
	}

	protected void setTypeModel(EPackage typeModel) {
		this.typeModel = typeModel;
	}

}
