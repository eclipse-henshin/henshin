package org.eclipse.emf.henshin;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Interface for Henshin model importers. Importers are identified by their name.
 * 
 * @author Christian Krause
 */
public interface HenshinModelImporter {

	/**
	 * Perform an import operation.
	 * 
	 * @param uri URI where the transformation system should be imported from.
	 * @param packages List of packages to be used for the import.
	 * @return The imported transformation system.
	 * @throws IOException On errors.
	 */
	TransformationSystem export(URI uri, List<EPackage> packages) throws IOException;
	
	/**
	 * Get the name of this importer.
	 * @return The name.
	 */
	String getImporterName();
	
	/**
	 * Get the list of file extensions supported by this importer.
	 * @return List of file extensions.
	 */
	String[] getImportFileExtensions();

}
