package org.eclipse.emf.henshin.statespace.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Resource implementation for state spaces.
 * @author Christian Krause
 */
public class StateSpaceResource extends ResourceImpl {
	
	/**
	 * File extension for state space files.
	 */
	public static final String FILE_EXTENSION = "statespace";
	
	/**
	 * Default constructor.
	 */
	public StateSpaceResource() {
		super();
	}
	
	/**
	 * Convenience constructor.
	 * @param uri URI of the resource.
	 */
	public StateSpaceResource(URI uri) {
		super(uri);
	}
	
	/**
	 * Get the state space contained in this resource.
	 * @return The state space.
	 */
	public StateSpace getStateSpace() {
		for (EObject item : getContents()) {
			if (item instanceof StateSpace) return (StateSpace) item;
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doSave(java.io.OutputStream, java.util.Map)
	 */
	@Override
	protected void doSave(OutputStream out, Map<?, ?> options) throws IOException {
		StateSpaceSerializer serializer = new StateSpaceSerializer();
		serializer.write(getStateSpace(), out);
		out.flush();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doLoad(java.io.InputStream, java.util.Map)
	 */
	@Override
	protected void doLoad(InputStream in, Map<?, ?> options) throws IOException {
		StateSpaceDeserializer deserializer = new StateSpaceDeserializer();
		deserializer.read(this, in);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#useZip()
	 */
	@Override
	protected boolean useZip() {
		return true;
	}
	
}
