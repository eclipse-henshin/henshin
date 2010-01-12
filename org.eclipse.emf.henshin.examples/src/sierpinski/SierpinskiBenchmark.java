package sierpinski;

import java.util.List;

import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.common.util.ModelHelper;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.RuleMatch;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;

import sierpinski.model.Sierpinski.VertexContainer;
import sierpinski.model.Sierpinski.impl.SierpinskiPackageImpl;

/**
 * A benchmark constructing multiple levels of a sierpinski triangle.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Sierpinski_triangle">Sierpinski
 *      Triangle</a>
 */
public class SierpinskiBenchmark {
    public static void main(String[] args) {

        HenshinPackageImpl.init();
        SierpinskiPackageImpl.init();

        ModelHelper.registerFileExtension("henshin");
        ModelHelper.registerFileExtension("sierpinski");

        // load the transformation rules
        TransformationSystem ts = (TransformationSystem) ModelHelper
                .loadFile("src/sierpinski/model/sierpinski.henshin");

        // load a minimal first level sierpinski triangle
        VertexContainer container = (VertexContainer) ModelHelper
                .loadFile("src/sierpinski/instances/start.sierpinski");

        // initialize the henshin interpreter
        EmfGraph graph = new EmfGraph();
        graph.addRoot(container);
        EmfEngine engine = new EmfEngine(graph);

        // load a rule
        Rule addTriangleRule = ModelHelper.getRuleByName(ts, "AddTriangle");

        // compute different sierpinski levels
        int i = 0;
        do {
            i++;
            long startTime = System.currentTimeMillis();
            RuleApplication addTriangle = new RuleApplication(engine,
                    addTriangleRule);
            List<RuleMatch> matches = addTriangle.findAllMatches();
            System.out.println("Level: " + i);
            System.out.println("Rule applications:" + matches.size());
            for (RuleMatch match : matches) {
                addTriangle = new RuleApplication(engine, addTriangleRule);
                addTriangle.setMatch(match);
                addTriangle.apply();
            }
            long runtime = System.currentTimeMillis() - startTime;
            // System.out.println("Rule applications: "+matches.size());

            System.out.println("Time: " + runtime + "ms");
            System.out.println("Nodes: " + container.getVertices().size());
            System.out.println();
        } while (true);
    }
}
