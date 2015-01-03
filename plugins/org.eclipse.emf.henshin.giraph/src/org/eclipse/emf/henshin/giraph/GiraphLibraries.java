package org.eclipse.emf.henshin.giraph;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

public class GiraphLibraries {

	public static final Map<URI, URI> LIBRARIES;

	static {
		Map<URI, URI> libs = new LinkedHashMap<URI, URI>();
		libs.put(
				URI.createURI("http://repo.maven.apache.org/maven2/org/apache/giraph/giraph-core/1.1.0/giraph-core-1.1.0.jar"),
				URI.createURI("http://repo.maven.apache.org/maven2/org/apache/giraph/giraph-core/1.1.0/giraph-core-1.1.0-sources.jar"));
		libs.put(
				URI.createURI("http://repo.maven.apache.org/maven2/org/apache/giraph/giraph-examples/1.1.0/giraph-examples-1.1.0.jar"),
				URI.createURI("http://repo.maven.apache.org/maven2/org/apache/giraph/giraph-examples/1.1.0/giraph-examples-1.1.0-sources.jar"));
		libs.put(
				URI.createURI("http://repo.maven.apache.org/maven2/org/apache/hadoop/hadoop-core/0.20.203.0/hadoop-core-0.20.203.0.jar"),
				null);
		libs.put(URI.createURI("http://central.maven.org/maven2/log4j/log4j/1.2.15/log4j-1.2.15.jar"), null);
		libs.put(URI.createURI("http://central.maven.org/maven2/org/json/json/20090211/json-20090211.jar"), null);
		LIBRARIES = Collections.unmodifiableMap(libs);
	}

}
