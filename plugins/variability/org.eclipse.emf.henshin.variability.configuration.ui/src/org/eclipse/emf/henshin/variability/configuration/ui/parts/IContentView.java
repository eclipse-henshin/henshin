package org.eclipse.emf.henshin.variability.configuration.ui.parts;

/**
 * 
 * @author Stefan Schulz
 *
 * @param <E>
 */
public interface IContentView <E> {
	
	public void setContent(E newContent);
	
	public E getContent();

}
