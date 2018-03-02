package org.eclipse.emf.henshin.examples.universitycourses;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class UniversityCourses {

	public static final String PATH = "src/org/eclipse/emf/henshin/examples/universitycourses";

	private static final String HENSHIN_FILE_NAME = "universityCourses.henshin";
	private static final String INPUT_INSTANCE_FILE_NAME = "exampleUniversity.xmi";
	private static final String RESULT_INSTANCE_FILE_NAME = "exampleUniversity_manageCourses-applied.xmi";
	private static final String UNIT_NAME = "manageCourses";
	private static final String PARAMETER_NAME = "startHour";
	private static final Object PARAMETER_VALUE = 8;
	
	public static void run(String path, boolean saveResult) {
		final HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		final Module module = resourceSet.getModule(HENSHIN_FILE_NAME, false);
		final EGraph graph = new EGraphImpl(resourceSet.getResource(INPUT_INSTANCE_FILE_NAME));
		final UnitApplication manageCoursesApplication = new UnitApplicationImpl(new EngineImpl());
		manageCoursesApplication.setEGraph(graph);
		manageCoursesApplication.setUnit(module.getUnit(UNIT_NAME));
		manageCoursesApplication.setParameterValue(PARAMETER_NAME, PARAMETER_VALUE);
		
		if (!manageCoursesApplication.execute(null)) {
			throw new RuntimeException("Error managing courses");
		} else if (saveResult) {
			resourceSet.saveEObject(graph.getRoots().get(0), RESULT_INSTANCE_FILE_NAME);
		}
	}
	
	public static void main(String[] args) {
		run(PATH, true);
	}
}
