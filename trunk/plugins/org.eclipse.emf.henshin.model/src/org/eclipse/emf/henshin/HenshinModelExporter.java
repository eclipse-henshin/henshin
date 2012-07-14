package org.eclipse.emf.henshin;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Interface for Henshin model exporters.
 * @author Christian Krause
 */
public interface HenshinModelExporter {

	/**
	 * Perform an export operation.
	 * @param system Transformation system to be exported.
	 * @param uri URI where the transformation system should be exported to.
	 */
	IStatus doExport(TransformationSystem system, URI uri);
	
	/**
	 * Get the name of this exporter.
	 * @return The name.
	 */
	String getExporterName();
	
	/**
	 * Get the list of file extensions supported by this exporter.
	 * @return List of file extensions.
	 */
	String[] getExportFileExtensions();

}
