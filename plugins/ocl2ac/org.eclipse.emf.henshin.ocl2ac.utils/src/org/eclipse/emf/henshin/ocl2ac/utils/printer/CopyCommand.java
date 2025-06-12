/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.henshin.ocl2ac.utils.Activator;

public class CopyCommand {

	public static final IPath TEX_FOLDER = org.eclipse.core.runtime.Path.fromOSString("supplementary");
	private URI uri;

	public CopyCommand(URI uri) {
		this.uri = uri;
	}

	public void copy() {
		String texFolderPath = getFullPath(TEX_FOLDER);
		Path srcDir = Path.of(texFolderPath);
		System.err.println(Files.exists(srcDir) + " " + texFolderPath);

		Path targetDir = Path.of(uri.getPath());
		System.err.println(Files.exists(targetDir));

		try {
			Files.walkFileTree(srcDir, new SimpleFileVisitor<>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					Files.createDirectories(getTarget(dir));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.copy(file, getTarget(file), StandardCopyOption.REPLACE_EXISTING,
							StandardCopyOption.COPY_ATTRIBUTES);
					return FileVisitResult.CONTINUE;
				}

				private Path getTarget(Path path) {
					return targetDir.resolve(srcDir.relativize(path));
				}
			});
			System.err.println("Copy From:" + srcDir);
			System.err.println("Copy To:" + targetDir);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getFullPath(IPath path) {
		URL url = FileLocator.find(Activator.getDefault().getBundle(), path, Collections.emptyMap());
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileUrl.getPath();
	}

}
