package org.eclipse.emf.henshin.model.exporters;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.HenshinModelExporter;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Henshin model exporter for AGG.
 * @author Christian Krause
 */
public class HenshinAGGExporter implements HenshinModelExporter {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.HenshinModelExporter#export(org.eclipse.emf.henshin.model.TransformationSystem, org.eclipse.emf.common.util.URI)
	 */
	@Override
	public void export(TransformationSystem system, URI uri) throws IOException {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.HenshinModelExporter#getExporterName()
	 */
	@Override
	public String getExporterName() {
		return "AGG";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.HenshinModelExporter#getExportFileExtensions()
	 */
	@Override
	public String[] getExportFileExtensions() {
		return new String[] { "ggx" };
	}

}
