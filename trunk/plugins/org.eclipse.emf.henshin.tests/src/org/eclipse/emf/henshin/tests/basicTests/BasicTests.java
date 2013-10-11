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
package org.eclipse.emf.henshin.tests.basicTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ MatchTests.class, CreateNodes.class, DeleteNodes.class,
		 ParameterTests.class, TransformationUnitTests.class,
		EngineOptionsTests.class })
public class BasicTests {
	
}
