package org.eclipse.emf.henshin.giraph;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class GiraphRunner {

	private static final Pattern REGEX_AGGREGATE_VERTICES = Pattern
			.compile("\\sINFO\\s*mapred\\.JobClient:\\s*Aggregate\\svertices=(\\d+)");

	private static final Pattern REGEX_AGGREGATE_EDGES = Pattern
			.compile("\\sINFO\\s*mapred\\.JobClient:\\s*Aggregate\\sedges=(\\d+)");

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

		String log = buffer.toString();
		aggregateVertices = parseInt(REGEX_AGGREGATE_VERTICES, log);
		aggregateEdges = parseInt(REGEX_AGGREGATE_EDGES, log);

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

	private static int parseInt(Pattern pattern, String string) {
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			return Integer.parseInt(string.substring(matcher.start(1), matcher.end(1)));
		}
		return -1;
	}

}
