/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateEditPart;
import org.eclipse.swt.graphics.Color;

/**
 * Export wizard for generating TikZ diagrams from state spaces.
 * @author Christian Krause
 */
public class StateSpaceTikZExportWizard extends AbstractStateSpaceExportWizard {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.actions.AbstractStateSpaceExportWizard#doExport(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.resources.IFile, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void doExport(StateSpace stateSpace, IFile file, IProgressMonitor monitor) throws Exception {
		
		// The header:
		StringBuffer result = new StringBuffer();
		result.append("\\begin{tikzpicture}[scale=0.5,->,>=stealth',bend angle=15,font=\\small]\n");
		result.append("\\definecolor{normfill}{rgb}{" + color2String(StateEditPart.COLOR_DEFAULT) + "}\n");
		result.append("\\definecolor{initfill}{rgb}{" + color2String(StateEditPart.COLOR_INITIAL) + "}\n");
		result.append("\\definecolor{openfill}{rgb}{" + color2String(StateEditPart.COLOR_OPEN) + "}\n");
		result.append("\\definecolor{termfill}{rgb}{" + color2String(StateEditPart.COLOR_TERMINAL) + "}\n");
		result.append("\\tikzstyle{state}=[circle,draw,fill=normfill,inner sep=1pt,minimum size=4mm]\n");
		result.append("\\tikzstyle{init}=[fill=initfill]\n");
		result.append("\\tikzstyle{open}=[fill=openfill]\n");
		result.append("\\tikzstyle{term}=[fill=termfill]\n\n");
		
		// Print the states:
		for (State state : stateSpace.getStates()) {
			int[] location = state.getLocation();
			int index = state.getIndex();
			result.append("\\node at(" + location[0] + "pt," + (-location[1]) + "pt) [state");
			if (state.isInitial()) result.append(",init");
			else if (state.isOpen()) result.append(",open");
			else if (state.isTerminal()) result.append(",term");
			result.append("] (s" + index + ") {" + index + "};\n");
		}
		
		result.append("\n");
		
		// Print the transitions:
		for (State state : stateSpace.getStates()) {
			int source = state.getIndex();
			for (Transition transition : state.getOutgoing()) {
				int target = transition.getTarget().getIndex();
				String label = transition.getRule().getName();
				result.append("\\draw (s" + source + ") edge node[auto] {" + label + "} (s" + target + ");\n");
			}
		}
		
		// End:
		result.append("\n\\end{tikzpicture}\n");
		
		// Create the file:
		file.create(new ByteArrayInputStream(result.toString().getBytes()), true, monitor);
		monitor.done();
		
	}
	
	private String color2String(Color color) {
		return (((double) color.getRed()) / 255.0) + "," + (((double) color.getGreen()) / 255.0) + "," + (((double) color.getBlue()) / 255.0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.parts.AbstractStateSpaceExportWizard#getDescription()
	 */
	@Override
	protected String getDescription() {
		return "Export State Space as a TikZ diagram.";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.parts.AbstractStateSpaceExportWizard#getFileExtension()
	 */
	@Override
	protected String getFileExtension() {
		return "tex";
	}

}
