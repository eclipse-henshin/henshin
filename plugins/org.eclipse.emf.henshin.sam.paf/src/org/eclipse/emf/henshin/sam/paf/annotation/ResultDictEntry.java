/**
 * 
 */
package org.eclipse.emf.henshin.sam.paf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;

/**
 * <p>Use this annotation to mark fields of your <code>IFilter</code> that should be automatically added to 
 * the <code>IFilter<code>'s result dictionary.</p>
 * <p>This <code>annotation</code> is used by the <code>prepareResult</code> implementation of {@link FilterSkeleton}.</p>
 * @author basil
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ResultDictEntry {
	public String entryName() default "Unnamed Entry";
}
