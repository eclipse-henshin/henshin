/**
 * This package contains the {@link GraphMerger} component, the
 * {@link NACTranslator} component, the {@link RuleApplication} component and
 * the {@link IsomorphicPartMatcher} component. <br>
 * <br>
 * The typical execution of the InvariantChecking process in the context of the
 * components is as follows: <br>
 * <ol>
 * <li>Find intersections between right rule sides and
 * <a href="../../../../../doc/glossary.html">forbidden graph</a>s using the
 * IsomorphicPartMatcher. ({@link MatchSubgraphFilter})</li>
 * <li>Merge the right side of a rule and the forbidden subgraph. (
 * {@link GraphMergeFilter})</li>
 * <li>Translate the NACs from the forbidden subgraph to the
 * <a href="../../../../../doc/glossary.html">merged graph</a> using the
 * IsomorphicPartMatcher to find incomplete occurrences of NACs. (
 * {@link NACTranslationFilter})</li>
 * <li>Apply the rule reversely, creating a
 * <a href="../../../../../doc/glossary.html">source graph pattern</a> from the
 * <a href="../../../../../doc/glossary.html">target graph pattern</a> and the
 * rule. Check the correct application using the IsomorphicPartMatcher. (
 * {@link RuleApplicationFilter})</li>
 * <li>Perform several checks to ensure the validity of the counterexample using
 * the IsomorphicPartMatcher. ({@link HybridPropertyFilter},
 * {@link HybridGraphRuleFilter}, {@link HybridUrgentGraphRuleFilter})</li>
 * </ol>
 * <br>
 * For a list of certain terms frequently used in the explanation of the
 * components, refer to the
 * <a href="../../../../../doc/glossary.html">glossary</a>.
 * 
 */
package org.eclipse.emf.henshin.sam.invcheck.algorithm;