/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import org.eclipse.emf.henshin.ocl2ac.utils.Activator;

public class CopyCommand {

	public static final String TEX_FOLDER = "supplementary";
	private URI uri;

	public CopyCommand(URI uri) {
		this.uri = uri;
	}

	public void copy() {
		String texFolderPath = getFullPath(TEX_FOLDER);
		File srcDir = new File(texFolderPath);
		System.err.println(srcDir.exists() + " " + texFolderPath);

		File targetDir = new File(uri.getPath());
		System.err.println(targetDir.exists());

		try {
			FileUtils.copyDirectory(srcDir, targetDir, true);
			System.err.println("Copy From:" + srcDir);
			System.err.println("Copy To:" + targetDir);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getFullPath(String path) {
		URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(path), Collections.EMPTY_MAP);
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileUrl.getPath();
	}

}
