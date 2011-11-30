/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.editor.filter;

import org.eclipse.emf.henshin.provider.filter.FilterProvider;
import org.eclipse.swt.widgets.ToolBar;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class FilterControlsViewer extends BaseFilterControlsViewer {
	
	public FilterControlsViewer(FilterProvider filter) {
		this.filterProvider = filter;
	}
	
	public void addControls(ToolBar tb) {
		
		// create ToolItem for Mappings
		//
		new ButtonController(new ButtonState(getString("_UI_FeatureFilter_ShowMappings"),
				getImage("full/obj16/Filter/MappingsShow.png")) {
			@Override
			void initState() {
				filterProvider.filterMappings(true);
			}
		}, new ButtonState(getString("_UI_FeatureFilter_HideMappings"),
				getImage("full/obj16/Filter/MappingsHide.png")) {
			@Override
			void initState() {
				filterProvider.filterMappings(false);
			}
		}, this.filterProvider.mappingsFiltered()).init(tb);
		
		// create ToolItem for Parameters
		//
		new ButtonController(new ButtonState(getString("_UI_FeatureFilter_ShowParameters"),
				getImage("full/obj16/Filter/ParametersShow.png")) {
			@Override
			void initState() {
				filterProvider.filterParameters(true);
			}
		}, new ButtonState(getString("_UI_FeatureFilter_HideParameters"),
				getImage("full/obj16/Filter/ParametersHide.png")) {
			@Override
			void initState() {
				filterProvider.filterParameters(false);
			}
		}, this.filterProvider.parametersFiltered()).init(tb);
		
		// createToolItem for ParameterMappings
		//
		new ButtonController(new ButtonState(getString("_UI_FeatureFilter_ShowParameterMappings"),
				getImage("full/obj16/Filter/ParameterMappingsShow.png")) {
			@Override
			void initState() {
				filterProvider.filterParameterMappings(true);
			}
		}, new ButtonState(getString("_UI_FeatureFilter_HideParameterMappings"),
				getImage("full/obj16/Filter/ParameterMappingsHide.png")) {
			@Override
			void initState() {
				filterProvider.filterParameterMappings(false);
			}
		}, this.filterProvider.parameterMappingsFiltered()).init(tb);
		
	}
	
}
