package org.eclipse.emf.henshin;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Interface for Henshin model exporters. Exporters are identified by their name.
 * 
 * @author Christian Krause
 */
public interface HenshinModelExporter {

	/**
	 * Perform an export operation.
	 * 
	 * @param system Transformation system to be exported.
	 * @param uri URI where the transformation system should be exported to.
	 * @throws Exception On errors.
	 */
	void export(TransformationSystem system, URI uri) throws IOException;
	
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
