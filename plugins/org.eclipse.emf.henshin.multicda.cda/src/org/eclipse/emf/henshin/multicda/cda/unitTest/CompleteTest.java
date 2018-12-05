package org.eclipse.emf.henshin.multicda.cda.unitTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.ChangeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.ChangeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.framework.CdaWorker;
import org.eclipse.emf.henshin.multicda.cda.framework.CpaWorker;
import org.eclipse.emf.henshin.multicda.cda.framework.Options;
import org.eclipse.emf.henshin.multicda.cda.framework.ResultCreator;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class CompleteTest {

	private static CdaWorker cda;
	private static CpaWorker cpa;
	private static String henshin = "testData/jevsTests/complete.henshin";
	private static Options options = new Options(Options.INITIAL);
	private static boolean execute = true;

	@AfterClass
	public static void results() {
		System.out.println("Finished...");
	}

	@Test
	public void testOne() {
		execute = false;
		if (!execute) {
			for (int i = 0; i < 1; i++)
				computeReasons();
			time /= 20;
			System.out.println(time(time));
		} else {
			options.remove(Options.PRINT_RESULT);
			options.remove(Options.PRINT_HEADER);
		}
	}

	private static String time(long time) {
		long milis = time % 1000;
		long seconds = (time / 1000) % 60;
		long minutes = (time / 60000) % 60;
		long hours = (time / 3600000) % 24;
		long days = (time / 86400000) % 365;
		long years = (time / 31536000000L);

		return (years == 0 ? "" : years + "y, ") + (days == 0 ? "" : days + "d, ") + (hours == 0 ? "" : hours + "h, ")
				+ (minutes == 0 ? "" : minutes + "m, ") + (seconds + "s, " + milis + "ms");
	}

	private static long time = 0;

	private static Set<Span> computeReasons() {
		String r1 = "d2";
		String r2 = "d2";
		henshin = "testData/main/test.henshin";
		// henshin = "testData/jevsTests/attributes.henshin";
		Set<Span> cdaResult = new TreeSet<>();
		Set<CriticalPair> cpaResult = new HashSet<>();
		boolean executeAll = false;
		boolean executeCpa = false;
		boolean dependency = true;
		boolean compare = executeCpa && false;

		if (executeAll)
			options.remove(Options.PRINT_RESULT, Options.PRINT_HEADER);

		options.remove(Options.DEPENDENCY);
		long cdaTime = System.currentTimeMillis();
		if (executeAll)
			cda = new CdaWorker(henshin, options);
		else
			cda = new CdaWorker(henshin, r1, r2, options);
		cda.print();
		cdaTime = System.currentTimeMillis() - cdaTime;
		cdaResult.addAll(cda.getAtoms());
		cdaResult.addAll(cda.getMinimalReasons());
		cdaResult.addAll(cda.getResult());
		long cpaTime = System.currentTimeMillis();
		if (executeCpa) {
			if (executeAll)
				cpa = new CpaWorker(henshin, options);
			else
				cpa = new CpaWorker(henshin, new String[] { r1 }, new String[] { r2 }, options);
			cpaTime = System.currentTimeMillis() - cpaTime;
			cpaResult.addAll(cpa.getResult());
		}
		if (compare)
			cda.compare(cpa.getResult());
		ResultCreator.create(cda, "Conflicts");
		if (dependency) {
			System.out.println("----------------------------------------------------------------");
			options.add(Options.DEPENDENCY);
			long tempTime = System.currentTimeMillis();
			if (executeAll)
				cda = new CdaWorker(henshin, options);
			else
				cda = new CdaWorker(henshin, r1, r2, options);
			cdaTime += System.currentTimeMillis() - tempTime;

			cdaResult.addAll(cda.getAtoms());
			cdaResult.addAll(cda.getResult());
			cdaResult.addAll(cda.getMinimalReasons());
			if (executeCpa) {
				tempTime = System.currentTimeMillis();
				if (executeAll)
					cpa = new CpaWorker(henshin, options);
				else
					cpa = new CpaWorker(henshin, new String[] { r1 }, new String[] { r2 }, options);
				cdaTime += System.currentTimeMillis() - tempTime;
				cpaResult.addAll(cpa.getResult());
			}
			ResultCreator.create(cda, "Dependencies");
		}
		// CdaWorker.print(cdaResult);
		System.out.println("Reasons found: " + cdaResult.size());
		if (executeCpa)
			System.out.println("Critical Pairs found: " + cpaResult.size());
		if (compare)
			cda.compare(cpa.getResult());
		System.out.println("\nCDA Time: " + time(cdaTime));
		time += cdaTime;
		if (executeCpa)
			System.out.println("CPA Time: " + time(cpaTime));
		System.out.println();
		System.out.println("______________________________________________________________________________");
		return cdaResult;
	}

	private static void test(Atom a) {
		TreeSet<Span> s = new TreeSet<>();
		s.add(new DeleteConflictAtom(a));
		s.add(new CreateConflictAtom(a));
		s.add(new ChangeConflictAtom(a));
		s.add(new CreateDependencyAtom(a));
		s.add(new DeleteDependencyAtom(a));
		s.add(new ChangeDependencyAtom(a));

		s.add(new DeleteConflictAtom(a).setForbid(true));
		s.add(new CreateConflictAtom(a).setForbid(true));
		s.add(new ChangeConflictAtom(a).setForbid(true));
		s.add(new CreateDependencyAtom(a).setForbid(true));
		s.add(new DeleteDependencyAtom(a).setForbid(true));
		s.add(new ChangeDependencyAtom(a).setForbid(true));

		s.add(new DeleteConflictAtom(a).setRequire(true));
		s.add(new CreateConflictAtom(a).setRequire(true));
		s.add(new ChangeConflictAtom(a).setRequire(true));
		s.add(new CreateDependencyAtom(a).setRequire(true));
		s.add(new DeleteDependencyAtom(a).setRequire(true));
		s.add(new ChangeDependencyAtom(a).setRequire(true));

		s.add(new DeleteConflictReason(a).setMinimalReason(true));
		s.add(new CreateConflictReason(a).setMinimalReason(true));
		s.add(new ChangeConflictReason(a).setMinimalReason(true));
		s.add(new CreateDependencyReason(a).setMinimalReason(true));
		s.add(new DeleteDependencyReason(a).setMinimalReason(true));
		s.add(new ChangeDependencyReason(a).setMinimalReason(true));

		s.add(new DeleteConflictReason(a).setMinimalReason(true).setForbid(true));
		s.add(new CreateConflictReason(a).setMinimalReason(true).setForbid(true));
		s.add(new ChangeConflictReason(a).setMinimalReason(true).setForbid(true));
		s.add(new CreateDependencyReason(a).setMinimalReason(true).setForbid(true));
		s.add(new DeleteDependencyReason(a).setMinimalReason(true).setForbid(true));
		s.add(new ChangeDependencyReason(a).setMinimalReason(true).setForbid(true));

		s.add(new DeleteConflictReason(a).setMinimalReason(true).setRequire(true));
		s.add(new CreateConflictReason(a).setMinimalReason(true).setRequire(true));
		s.add(new ChangeConflictReason(a).setMinimalReason(true).setRequire(true));
		s.add(new CreateDependencyReason(a).setMinimalReason(true).setRequire(true));
		s.add(new DeleteDependencyReason(a).setMinimalReason(true).setRequire(true));
		s.add(new ChangeDependencyReason(a).setMinimalReason(true).setRequire(true));

		s.add(new DeleteConflictReason(a));
		s.add(new CreateConflictReason(a));
		s.add(new ChangeConflictReason(a));
		s.add(new CreateDependencyReason(a));
		s.add(new DeleteDependencyReason(a));
		s.add(new ChangeDependencyReason(a));

		s.add(new DeleteConflictReason(a).setForbid(true));
		s.add(new CreateConflictReason(a).setForbid(true));
		s.add(new ChangeConflictReason(a).setForbid(true));
		s.add(new CreateDependencyReason(a).setForbid(true));
		s.add(new DeleteDependencyReason(a).setForbid(true));
		s.add(new ChangeDependencyReason(a).setForbid(true));

		s.add(new DeleteConflictReason(a).setRequire(true));
		s.add(new CreateConflictReason(a).setRequire(true));
		s.add(new ChangeConflictReason(a).setRequire(true));
		s.add(new CreateDependencyReason(a).setRequire(true));
		s.add(new DeleteDependencyReason(a).setRequire(true));
		s.add(new ChangeDependencyReason(a).setRequire(true));

		CdaWorker.print(s);
	}
}