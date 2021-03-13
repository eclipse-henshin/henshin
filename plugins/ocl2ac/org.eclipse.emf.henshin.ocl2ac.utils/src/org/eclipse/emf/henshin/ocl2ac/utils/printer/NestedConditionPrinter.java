/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import graph.util.extensions.Constants;
import graph.util.extensions.GraphPrinter;
import laxcondition.Operator;
import laxcondition.Quantifier;
import laxcondition.util.extensions.LaxConditionConstants;
import nestedcondition.Formula;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.QuantifiedCondition;
import nestedcondition.True;

public class NestedConditionPrinter {

	private NestedConstraint constraint;
	private StringBuffer result;
	private int openBrackets;
	private boolean shortversion;
	private GraphPrinter graphPrinter;
	private String texfolderPath;
	private String outputFilePath;

	public static final String RIGHTARROW = "\\hookrightarrow";

	public NestedConditionPrinter(NestedConstraint constraint, boolean shortversion) {
		this.constraint = constraint;
		this.openBrackets = 0;
		this.shortversion = shortversion;
	}

	public void printDocument() {
		this.graphPrinter = new GraphPrinter(null);
		result = new StringBuffer(graphPrinter.printPreambel2());
		result.append(Constants.BEGIN_DOCUMENT);

		String constraintNameInLatx = constraint.getName();
		if (constraintNameInLatx.contains("_"))
			constraintNameInLatx = constraintNameInLatx.replaceAll("_", "\\\\_");
		String version = null;
		if (shortversion)
			version = "ShortVersion";
		else
			version = "LongVersion";
		result.append(LaxConditionConstants.START2);
		printNestedCondition(constraint.getCondition());
		result.append(LaxConditionConstants.END2);
		result.append(Constants.END_DOCUMENT);
		saveFile();
	}

	private void printNestedCondition(NestedCondition condition) {
		if (condition instanceof QuantifiedCondition)
			printQuantifiedCondition((QuantifiedCondition) condition);
		if (condition instanceof True)
			printTrue((True) condition);
		if (condition instanceof Formula)
			printFormula((Formula) condition);
	}

	private void printQuantifiedCondition(QuantifiedCondition cond) {
		boolean isExists = cond.getQuantifier().equals(Quantifier.EXISTS);
		boolean isTrue = cond.getCondition() instanceof True;
		// print quantifier
		if (isExists) {
			result.append(LaxConditionConstants.EXISTS + Constants.SPACE);
		} else {
			result.append(LaxConditionConstants.FORALL + Constants.SPACE);
		}
		// print opening bracket
		result.append(left() + Constants.SPACE);
		// print graph
		if (!shortversion) {
			this.graphPrinter = new GraphPrinter(cond.getDomain());
			result.append(this.graphPrinter.printGraph());
			result.append(Constants.SPACE + RIGHTARROW + Constants.SPACE);
		}
		this.graphPrinter = new GraphPrinter(cond.getCodomain());
		result.append(this.graphPrinter.printGraph());
		// print nested condition
		if (!(shortversion && isTrue)) {
			result.append(Constants.SPACE + Constants.COMMA + Constants.SPACE + cr());
			printNestedCondition(cond.getCondition());
		}
		// print closing bracket
		result.append(Constants.SPACE + right());
	}

	private void printFormula(Formula formula) {
		if (formula.eContainer() instanceof Formula)
			result.append(Constants.BRACKETOPEN);
		if (formula.getOperator().equals(Operator.NOT)) {
			result.append(LaxConditionConstants.NEG + Constants.SPACE);
			printNestedCondition(formula.getArguments().get(0));
		}
		if (formula.getOperator().equals(Operator.IMPLIES)) {
			printNestedCondition(formula.getArguments().get(0));
			result.append(Constants.SPACE + cr() + LaxConditionConstants.IMPL + Constants.SPACE);
			printNestedCondition(formula.getArguments().get(1));
		}
		if (formula.getOperator().equals(Operator.EQUIVALENT)) {
			printNestedCondition(formula.getArguments().get(0));
			result.append(Constants.SPACE + cr() + LaxConditionConstants.EQUIVALENT + Constants.SPACE);
			printNestedCondition(formula.getArguments().get(1));
		}
		if (formula.getOperator().equals(Operator.AND)) {
			printNestedCondition(formula.getArguments().get(0));
			for (int i = 1; i < formula.getArguments().size(); i++) {
				result.append(Constants.SPACE + cr() + LaxConditionConstants.LAND + Constants.SPACE);
				printNestedCondition(formula.getArguments().get(i));
			}
		}
		if (formula.getOperator().equals(Operator.OR)) {
			printNestedCondition(formula.getArguments().get(0));
			for (int i = 1; i < formula.getArguments().size(); i++) {
				result.append(Constants.SPACE + cr() + LaxConditionConstants.LOR + Constants.SPACE);
				printNestedCondition(formula.getArguments().get(i));
			}
		}
		if (formula.getOperator().equals(Operator.XOR)) {
			printNestedCondition(formula.getArguments().get(0));
			result.append(Constants.SPACE + cr() + LaxConditionConstants.XOR + Constants.SPACE);
			printNestedCondition(formula.getArguments().get(1));
		}
		if (formula.eContainer() instanceof Formula)
			result.append(Constants.BRACKETCLOSE);
	}

	private void printTrue(True cond) {
		result.append(LaxConditionConstants.TRUE);
	}

	private String right() {
		openBrackets--;
		return LaxConditionConstants.RIGHT;
	}

	private String left() {
		openBrackets++;
		return LaxConditionConstants.LEFT;
	}

	private String cr() {
		String ret = Constants.NEWLINE;
		for (int i = 0; i < openBrackets; i++) {
			ret += LaxConditionConstants.CRRIGHT;
		}
		ret += LaxConditionConstants.CRCENTRE;
		for (int i = 0; i < openBrackets; i++) {
			ret += LaxConditionConstants.CRLEFT;
		}
		return ret + Constants.NEWLINE;
	}

	private void saveFile() {
		String nameMark = null;
		URI uri = getActualProject().getLocationURI();
		texfolderPath = uri.getPath().concat(Constants.TEXFOLDER);
		File texFolderFile = new File(texfolderPath);
		if (!texFolderFile.exists()) {
			CopyCommand copyFileCommand = new CopyCommand(uri);
			copyFileCommand.copy();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new GregorianCalendar().getTime();
		nameMark = "_" + sdf.format(date);
		if (shortversion)
			nameMark += "SV";
		else
			nameMark += "LV";

		outputFilePath = texfolderPath.concat("Constraint_" + constraint.getName() + nameMark + Constants.TEX);
		File file = new File(getOutputFilePath());
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			writer.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				getActualProject().refreshLocal(IProject.DEPTH_INFINITE, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("finally")
	private IProject getActualProject() {
		IProject actualProject = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			IEditorPart editorPart = window.getActivePage().getActiveEditor();
			if (editorPart != null) {
				IEditorInput input = editorPart.getEditorInput();
				if (input instanceof IFileEditorInput) {
					IFileEditorInput fileInput = (IFileEditorInput) input;
					actualProject = fileInput.getFile().getProject();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return actualProject;
		}
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

}
