package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;

/**
 * Default implementation of {@link MappingList}.
 * 
 * @author Christian Krause
 * 
 */
public class MappingListImpl extends EObjectContainmentEList<Mapping> implements MappingList {

	// Generated serial ID:
	private static final long serialVersionUID = -7095784906496813L;

	/**
	 * Default constructor
	 * @param owner Owner of this mapping list.
	 * @param featureID Feature ID.
	 */
	public MappingListImpl(InternalEObject owner, int featureID) {
		super(Mapping.class, owner, featureID);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#get(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Mapping get(Node origin, Node image) {
		for (Mapping m : this) {
			if (m.getOrigin()==origin && m.getImage()==image) {
				return m;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#add(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Mapping add(Node origin, Node image) {
		Mapping m = get(origin, image);
		if (m==null) {
			m = new MappingImpl();
			m.setOrigin(origin);
			m.setImage(image);
			super.add(m);
		}
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#remove(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Mapping remove(Node origin, Node image) {
		Mapping m = get(origin, image);
		if (m!=null) {
			remove(m);
		}
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public Node getImage(Node origin, Graph targetGraph) {
		for (Mapping m : this) {
			if (m.getOrigin()==origin && m.getImage()!=null && m.getImage().getGraph()==targetGraph) {
				return m.getImage();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Node getOrigin(Node image) {
		for (Mapping m : this) {
			if (m.getImage()==image && m.getOrigin()!=null) {
				return m.getOrigin();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(org.eclipse.emf.henshin.model.Edge, org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public Edge getImage(Edge origin, Graph targetGraph) {
		if (origin.getSource()==null || origin.getTarget()==null) {
			return null;
		}
		Node source = getImage(origin.getSource(), targetGraph);
		Node target = getImage(origin.getTarget(), targetGraph);
		if (source==null || target==null) {
			return null;
		}
		return source.getOutgoing(origin.getType(), target);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(org.eclipse.emf.henshin.model.Edge)
	 */
	@Override
	public Edge getOrigin(Edge image) {
		if (image.getSource()==null || image.getTarget()==null) {
			return null;
		}
		Node source = getOrigin(image.getSource());
		Node target = getOrigin(image.getTarget());
		if (source==null || target==null) {
			return null;
		}
		return source.getOutgoing(image.getType(), target);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(org.eclipse.emf.henshin.model.Attribute, org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public Attribute getImage(Attribute attribute, Graph targetGraph) {
		if (attribute.getNode()==null) {
			return null;
		}
		Node nodeImage = getImage(attribute.getNode(), targetGraph);
		if (nodeImage==null) {
			return null;
		}
		return nodeImage.getAttribute(attribute.getType());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(org.eclipse.emf.henshin.model.Attribute)
	 */
	@Override
	public Attribute getOrigin(Attribute attribute) {
		if (attribute.getNode()==null) {
			return null;
		}
		Node nodeOrigin = getOrigin(attribute.getNode());
		if (nodeOrigin==null) {
			return null;
		}
		return nodeOrigin.getAttribute(attribute.getType());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#removeInvalid()
	 */
	@Override
	public void removeInvalid() {
		for (int i=0; i<size(); i++) {
			Mapping m = get(i);
			if (m.getOrigin()==null || m.getImage()==null ||
				m.getOrigin().eContainer()==null || m.getImage().eContainer()==null) {
				remove(i--);
			}
		}
	}

}
