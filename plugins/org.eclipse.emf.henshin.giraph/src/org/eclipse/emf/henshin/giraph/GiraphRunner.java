package org.eclipse.emf.henshin.giraph;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class GiraphRunner {

	private int aggregateVertices;

	private int aggregateEdges;

	private boolean success;

	public synchronized boolean run(IFile launchFile) throws CoreException {

		aggregateVertices = 0;
		aggregateEdges = 0;

		final PrintStream outStream = System.out;
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		final PrintStream bufStream = new PrintStream(buffer) {
			@Override
			public synchronized void write(byte b[], int off, int len) {
				super.write(b, off, len);
				outStream.write(b, off, len);
			}
		};

		try {
			System.setOut(bufStream);

			AntRunner runner = new AntRunner();
			Object ret = runner.run(new String[] { "-f", launchFile.getLocation().toOSString() });
			success = AntRunner.EXIT_OK.equals(ret);

			bufStream.close();
			buffer.flush();

		} catch (Exception e) {
			success = false;
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.emf.henshin.giraph", "Error running Giraph",
					e));
		} finally {
			System.setOut(outStream);
		}

//		System.out.println("BUFFERED CONTENT:");
//		System.out.println(buffer);

		return success;

	}

	public int getAggregateVertices() {
		return aggregateVertices;
	}

	public int getAggregateEdges() {
		return aggregateEdges;
	}

	public boolean isSuccess() {
		return success;
	}

}
