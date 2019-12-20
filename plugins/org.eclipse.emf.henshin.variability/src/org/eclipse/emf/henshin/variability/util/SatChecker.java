package org.eclipse.emf.henshin.variability.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.variability.matcher.FeatureExpression;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ClauseCollector;
import aima.core.logic.propositional.visitors.ConvertToCNF;
import aima.core.logic.propositional.visitors.SymbolCollector;

/**
 * 
 * @author Daniel Strüber
 * @author Sven Peldszus
 *
 */
public class SatChecker {
	
	private List<String> solution;

	public static ISolver createModelIterator(String expr, Map<Integer, String> symbolsToIndices) {
		Sentence cnf = ConvertToCNF.convert(FeatureExpression.getExpr(expr));
	
		Set<PropositionSymbol> symbols = SymbolCollector.getSymbolsFrom(cnf);
		Set<Clause> clauses = ClauseCollector.getClausesFrom(cnf);
		Map<PropositionSymbol, Integer> indices = getSymbol2IndexMap(symbols);
		for (PropositionSymbol symbol : symbols) {
			symbolsToIndices.put(indices.get(symbol), symbol.getSymbol());
		}

		int numberOfVariables = symbols.size();
		int numberOfClauses = clauses.size();

		ISolver solver = new ModelIterator(SolverFactory.newDefault());
		solver.setDBSimplificationAllowed(false);
		solver.newVar(numberOfVariables);
		solver.setExpectedNumberOfClauses(numberOfClauses);

		for (Clause clause : clauses) {
			if (clause.isFalse()) {
				return null;
			}
			if (!clause.isTautology()) {
				int[] clauseArray = convertToArray(clause, indices);
				try {
					solver.addClause(new VecInt(clauseArray));
				} catch (ContradictionException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return solver;
	}
	
	public Boolean isSatisfiable(String expr) {
		Sentence cnf = FeatureExpression.getExpr(expr);
		return isSatisfiable(cnf);
	}

	public Boolean isSatisfiable(Sentence expr) {
		Sentence cnf = ConvertToCNF.convert(expr);
		Set<PropositionSymbol> symbols = SymbolCollector.getSymbolsFrom(cnf);
		Set<Clause> clauses = ClauseCollector.getClausesFrom(cnf);
		Map<PropositionSymbol, Integer> indices = getSymbol2IndexMap(symbols);
		
		int numberOfVariables = symbols.size();
		int numberOfClauses = clauses.size();

		ISolver solver = SolverFactory.newDefault();
		solver.newVar(numberOfVariables);
		solver.setExpectedNumberOfClauses(numberOfClauses);

		for (Clause clause : clauses) {
			if(clause.isFalse()){
				return Boolean.FALSE;
			}
			if (!clause.isTautology()) {
				int[] clauseArray = convertToArray(clause, indices);
				try {
					solver.addClause(new VecInt(clauseArray));
				} catch (ContradictionException e) {
//					e.printStackTrace();
					return Boolean.FALSE;
				} 
			}
		}

		try {
			
			boolean satisfiable = solver.isSatisfiable();
			if(satisfiable){
				solution = new LinkedList<>();
				int[] model = solver.findModel();
				for(PropositionSymbol key : indices.keySet()){
					int index = indices.get(key);
					for(int i : model){
						if(i > 0 && i == index){
							solution.add(key.getSymbol());
						}
					}
				}
			}
			return Boolean.valueOf(satisfiable);
		} catch (TimeoutException e) {
			throw new RuntimeException("Timeout during evaluation of satisfiability.");
		}
	}


	public Boolean isContradiction(Sentence expr) {
		return isSatisfiable(expr) ? Boolean.FALSE : Boolean.TRUE;
	}

	public Boolean isContradiction(String expr) {
		return isSatisfiable(expr).booleanValue() ? Boolean.FALSE : Boolean.TRUE;
	}

	private static Map<PropositionSymbol, Integer> getSymbol2IndexMap(Set<PropositionSymbol> symbols) {
		Map<PropositionSymbol, Integer> list2Index = new HashMap<PropositionSymbol,Integer>(symbols.size());
		int counter = 1;
		for (PropositionSymbol symbol : symbols) {
			list2Index.put(symbol, Integer.valueOf(counter));
			counter++;
		}
		return list2Index;
	}

	private static int[] convertToArray(Clause clause, Map<PropositionSymbol, Integer> indices) {
		Set<Literal> literals = clause.getLiterals();
		int[] result = new int[literals.size()];
		int counter = 0;
		for (Literal literal : literals) {
			int sign = literal.isPositiveLiteral() ? 1 : -1;
			PropositionSymbol symbol = literal.getAtomicSentence();
			int index = indices.get(symbol).intValue();
			result[counter] = sign * index;
			counter++;
		}
		return result;
	}

	public List<String> getSolution() {
		return solution;
	}

}