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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.Xor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.util.GraphAdapter;
import graph.Graph;
import graph.util.extensions.Constants;
import graph.util.extensions.GraphPrinter;
import laxcondition.util.extensions.LaxConditionConstants;

public class HenshinNACPrinter {

	private org.eclipse.emf.henshin.model.Formula henFormula;
	private static final String HENSHIN = ".henshin";
	private StringBuffer result;
	private int openBrackets;
	private boolean shortversion;
	private GraphPrinter graphPrinter;
	private Rule henRule;
	private EPackage ePackage;
	private String texfolderPath;
	private String outputFilePath;
	private boolean isRHS = false;

	public static final String RIGHTARROW = "\\hookrightarrow";

	public HenshinNACPrinter(Rule henRule, EPackage ePackage, boolean shortversion) {
		this.openBrackets = 0;
		this.shortversion = shortversion;
		this.henRule = henRule;
		this.henFormula = this.henRule.getLhs().getFormula();
		this.ePackage = ePackage;
	}

	public void setHenFormula(Formula henFormula) {
		this.henFormula = henFormula;
		this.isRHS = true;
	}

	public void printDocument() {
		this.graphPrinter = new GraphPrinter(null);
		result = new StringBuffer(graphPrinter.printPreambel2());
		result.append(Constants.BEGIN_DOCUMENT);
		String henshinRuleNameInLatex = this.henRule.getName();
		if (henshinRuleNameInLatex.contains("_"))
			henshinRuleNameInLatex = henshinRuleNameInLatex.replaceAll("_", "\\\\_");
		String header = null;
		if (shortversion)
			header = "ShortVersion";
		else
			header = "LongVersion";

		if (isRHS) {
			header += "-RightApplicationCondition-Rule-";
		} else
			header += "-NegativeApplicationCondition-Rule-";

		result.append(LaxConditionConstants.START2);
		printHenFormula(this.henFormula);

		for (AttributeCondition henAttributeCondition : this.henRule.getAttributeConditions()) {
			result.append(Constants.SPACE + cr() + LaxConditionConstants.LAND + cr() + Constants.SPACE);
			// print opening bracket
			result.append(left() + Constants.SPACE);
			result.append(henAttributeCondition.getConditionText());
			// print closing bracket
			result.append(Constants.SPACE + right());
		}

		result.append(LaxConditionConstants.END2);
		result.append(Constants.END_DOCUMENT);
		saveFile();
	}

	private void printHenFormula(org.eclipse.emf.henshin.model.Formula formula) {
		if (formula instanceof org.eclipse.emf.henshin.model.NestedCondition)
			printHenNestedCondition((org.eclipse.emf.henshin.model.NestedCondition) formula);

		if (formula instanceof org.eclipse.emf.henshin.model.UnaryFormula)
			printHenUnaryFormula((UnaryFormula) formula);

		if (formula instanceof org.eclipse.emf.henshin.model.BinaryFormula)
			printHenBinaryFormula((BinaryFormula) formula);
	}

	private void printHenNestedCondition(org.eclipse.emf.henshin.model.NestedCondition henNCond) {

		// print quantifier
		result.append(LaxConditionConstants.EXISTS + Constants.SPACE);

		// print opening bracket
		result.append(left() + Constants.SPACE);

		// TODO test the correctness
		// print graph P0
		if (!shortversion) {
			GraphAdapter graphAdapter = new GraphAdapter(henNCond.getHost(), this.ePackage);
			graphAdapter.adaptFromHenshin();
			Graph printGraph = graphAdapter.getGraph();
			this.graphPrinter = new GraphPrinter(printGraph);
			result.append(this.graphPrinter.printGraph());
			result.append(Constants.SPACE + RIGHTARROW + Constants.SPACE);
		}

		GraphAdapter graphAdapter = new GraphAdapter(henNCond.getConclusion(), this.ePackage);
		graphAdapter.adaptFromHenshin();
		Graph printGraph = graphAdapter.getGraph();
		this.graphPrinter = new GraphPrinter(printGraph);
		result.append(this.graphPrinter.printGraph());

		// print nested condition
		if (henNCond.getConclusion().getFormula() != null) {
			result.append(Constants.SPACE + Constants.COMMA + Constants.SPACE + cr());
			printHenFormula(henNCond.getConclusion().getFormula());
		}

		// print closing bracket
		result.append(Constants.SPACE + right());
	}

	private void printHenUnaryFormula(UnaryFormula formula) {
		// result.append(Constants.BRACKETOPEN);
		result.append(LaxConditionConstants.NEG + Constants.SPACE);
		printHenFormula(formula.getChild());
		// result.append(Constants.BRACKETCLOSE);
	}

	private void printHenBinaryFormula(BinaryFormula formula) {
		result.append(Constants.BRACKETOPEN);
		if (formula instanceof And) {
			printHenFormula(formula.getLeft());
			result.append(Constants.SPACE + cr() + LaxConditionConstants.LAND + cr() + Constants.SPACE);
			printHenFormula(formula.getRight());
		}

		if (formula instanceof Or) {
			printHenFormula(formula.getLeft());
			result.append(Constants.SPACE + cr() + LaxConditionConstants.LOR + Constants.SPACE);
			printHenFormula(formula.getRight());
		}

		if (formula instanceof Xor) {
			printHenFormula(formula.getLeft());
			result.append(Constants.SPACE + cr() + LaxConditionConstants.XOR + Constants.SPACE);
			printHenFormula(formula.getRight());
		}

		result.append(Constants.BRACKETCLOSE);
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

	/**
	 * 
	 */
	private void saveFile() {
		String nameMark = null;
		IFile ifile = null;
		try {
			ifile = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(this.henRule.eResource().getURI().path()));
			nameMark = ifile.getName().replaceFirst(HENSHIN, "");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			Date date = new GregorianCalendar().getTime();
			nameMark += "_" + sdf.format(date);
			if (shortversion)
				nameMark += "SV";
			else
				nameMark += "LV";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (nameMark == null)
			nameMark = this.henRule.getName();

		URI uri = getActualProject().getLocationURI();
		texfolderPath = uri.getPath().concat(Constants.TEXFOLDER);
		File texFolderFile = new File(texfolderPath);

		if (!texFolderFile.exists()) {
			CopyCommand copyFileCommand = new CopyCommand(uri);
			copyFileCommand.copy();
		}

		if (isRHS) {
			outputFilePath = getFolderPath().concat("RAC_of_" + nameMark + Constants.TEX);
			isRHS = false;
		} else
			outputFilePath = getFolderPath().concat("NAC_of_" + nameMark + Constants.TEX);

		File file = new File(outputFilePath);
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

	public String getFolderPath() {
		return texfolderPath;
	}
}
