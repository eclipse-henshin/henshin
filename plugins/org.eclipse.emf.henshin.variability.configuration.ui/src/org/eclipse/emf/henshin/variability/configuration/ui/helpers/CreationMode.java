package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

/**
 * This enumeration lists the different creation modes for variability aware model elements.<p>
 * {@link BASE}: Create the element in the base rule, i.e. no presence condition is set for the element.<br>
 * {@link CONFIGURATION}: Create the element in the selected configuration, regardless of what is being viewed in the editor.
 * {@link SELECTION}: Create the element in the current editor selection.
 * 
 * @author Stefan Schulz
 *
 */
public enum CreationMode {
	BASE,
	CONFIGURATION,
	SELECTION
}
