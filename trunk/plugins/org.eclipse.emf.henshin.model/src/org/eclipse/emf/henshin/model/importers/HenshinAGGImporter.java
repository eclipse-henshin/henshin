package org.eclipse.emf.henshin.model.importers;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.HenshinModelImporter;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Henshin model importer for AGG.
 * @author Christian Krause
 */
public class HenshinAGGImporter implements HenshinModelImporter {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.HenshinModelImporter#export(org.eclipse.emf.common.util.URI, java.util.List)
	 */
	@Override
	public TransformationSystem export(URI uri, List<EPackage> packages) throws IOException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.HenshinModelImporter#getImporterName()
	 */
	@Override
	public String getImporterName() {
		return "AGG";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.HenshinModelImporter#getImportFileExtensions()
	 */
	@Override
	public String[] getImportFileExtensions() {
		return new String[] { "ggx" };
	}

}
