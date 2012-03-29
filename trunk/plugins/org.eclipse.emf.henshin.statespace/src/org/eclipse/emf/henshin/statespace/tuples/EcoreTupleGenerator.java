package org.eclipse.emf.henshin.statespace.tuples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.tuples.EcoreColumnSorter.ColumnInfo;
import org.eclipse.emf.henshin.statespace.tuples.EcoreColumnSorter.ColumnType;

/**
 * Ecore-based tuple generator.
 * @author Christian Krause
 */
public class EcoreTupleGenerator implements TupleGenerator {
	
	// Supported attribute types:
	private final static List<EDataType> SUPPORTED_ATTRIBUTE_TYPES;
	static {
		SUPPORTED_ATTRIBUTE_TYPES = new ArrayList<EDataType>();
		SUPPORTED_ATTRIBUTE_TYPES.add(EcorePackage.eINSTANCE.getEInt());
		SUPPORTED_ATTRIBUTE_TYPES.add(EcorePackage.eINSTANCE.getEBoolean());
	}

	// Size of the tuples:
	private int tupleSize;

	// State space index to be used to obtain the state models.
	private StateSpaceIndex stateSpaceIndex;

	// Used types:
	private List<EClass> usedTypes;

	// For each tree layer starting from the root, 
	// this array stores the maximum local widths.
	private int[] maxLocalWidths;
	
	// Maximum numbers of links:
	private Map<EReference,Integer> maxLinks;

	// Maximum number of objects
	private int maxObjects;

	// Maximum size of an object:
	private int maxObjectSize;
	
	// Cached object layers:
	private EObject[][] cachedLayers;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.tuples.TupleGenerator#createTuple(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	public Tuple createTuple(State state) throws StateSpaceException {
		
		Model model = stateSpaceIndex.getModel(state);
		
		// Paste the model contents into the cached layers:
		Object[] rootLayer = model.getResource().getContents().toArray();
		Arrays.fill(cachedLayers[0], null);
		System.arraycopy(rootLayer, 0, cachedLayers[0], 0, rootLayer.length);
		
		for (int y=1; y<cachedLayers.length; y++) {
			Arrays.fill(cachedLayers[y], null);
			for (int x=0; x<cachedLayers[y-1].length; x++) {
				EObject obj = cachedLayers[y-1][x];
				if (obj!=null) {
					Object[] children = obj.eContents().toArray();
					int pos = x * maxLocalWidths[y];
					System.arraycopy(children, 0, cachedLayers[y], pos, children.length);
				}
			}
		}
		
		// We need the objects also as a sorted list:
		List<EObject> objects = new ArrayList<EObject>(maxObjects);
		for (int y=0; y<cachedLayers.length; y++) {
			for (int x=0; x<cachedLayers[y].length; x++) {
				objects.add(cachedLayers[y][x]);
			}
		}
		
		// Column sorter:
		EcoreColumnSorter sorter = new EcoreColumnSorter();
		
		// Now we can construct the tuple:
		Tuple tuple = new Tuple(tupleSize);
		int[] data = tuple.data();
		int node = 0;
		for (int y=0; y<cachedLayers.length; y++) {
			for (int x=0; x<cachedLayers[y].length; x++) {
				
				// The next object:
				EObject obj = cachedLayers[y][x];
				if (obj==null) continue;
				EClass type = obj.eClass();
				
				// -----------------------------------------
				//if (!type.getName().startsWith("Phil")) {
				//	node++;
				//	continue;
				//}
				// -----------------------------------------
				
				// Paste its data into the tuple:
				int pos = node * maxObjectSize;
				
				// Object type:
				data[pos] = usedTypes.indexOf(type);
				sorter.setColumnInfo(pos, new ColumnInfo(ColumnType.TYPE, y, x, 0));
				pos++;
				
				// Attributes:
				for (EAttribute att : type.getEAllAttributes()) {
					if (att.isMany() || !SUPPORTED_ATTRIBUTE_TYPES.contains(att.getEAttributeType())) {
						throw new StateSpaceException("Unsupported attribute: " + att.getName());
					}
					Object value = obj.eGet(att);
					if (value instanceof Integer) {
						data[pos] = (Integer) value;
					} else {
						data[pos] = ((Boolean) value) ? 1 : 0;						
					}
					sorter.setColumnInfo(pos, new ColumnInfo(ColumnType.ATTRIBUTE, y, x, 0));
					pos++;
				}
				
				// Links:
				List<EReference> refs = type.getEAllReferences();
				for (int k=0; k<refs.size(); k++) {
					EReference ref = refs.get(k);
					if (ref.isMany()) {
						List<?> links = (List<?>) obj.eGet(ref);
						for (int l=0; l<links.size(); l++) {
							data[pos+l] = objects.indexOf(links.get(l))+1;
						}						
					} else {
						Object link = obj.eGet(ref);
						if (link!=null) {
							data[pos] = objects.indexOf(link)+1;
						}
					}
					pos += maxLinks.get(ref);
				}
				
				// Next node:
				node++;
			}
		}
		
		return tuple;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.tuples.TupleGenerator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	@Override
	public void initialize(StateSpaceIndex index, IProgressMonitor monitor) throws StateSpaceException {
		
		monitor.beginTask("Initialize tuple generator", index.getStateSpace().getStates().size());
		stateSpaceIndex = index;
		usedTypes = new ArrayList<EClass>();
		maxLocalWidths = new int[0];
		maxLinks = new HashMap<EReference,Integer>();
		
		// Iterate over all states and update the info:
		for (State state : stateSpaceIndex.getStateSpace().getStates()) {
			Model model = stateSpaceIndex.getModel(state);
			updateInfo(model);
			monitor.worked(1);
		}
		
		// Weird case were all models are empty?
		if (maxLocalWidths.length==0) {
			monitor.done();
			return;
		}
		
		/* === Compute the total tuple width === */
		
		// Find out which type needs the most space:
		maxObjectSize = 0;
		for (EClass type : usedTypes) {
			
			// 1 to store the type, and 1 for each attribute:
			int space = 1 + type.getEAllAttributes().size();
			
			// Add maximum link counts for all references:
			for (EReference ref : type.getEAllReferences()) {
				space += maxLinks.get(ref);
			}
			
			// Larger than the previous node types?
			maxObjectSize = Math.max(maxObjectSize, space);
			
		}
		
		// Calculate the maximum number of objects in each layer:
		int[] maxLayerWidths = new int[maxLocalWidths.length];
		maxLayerWidths[0] = maxLocalWidths[0];
		for (int i=1; i<maxLayerWidths.length; i++) {
			maxLayerWidths[i] = maxLayerWidths[i-1] * maxLocalWidths[i];
		}

		// Calculate the maximum number of objects:
		maxObjects = 0;
		for (int i=0; i<maxLayerWidths.length; i++) {
			maxObjects += maxLayerWidths[i];
		}

		// Now just multiply the two to get the tuple size:
		tupleSize = maxObjects * maxObjectSize;
		
		// Initialize the cache for object layers:
		cachedLayers = new EObject[maxLayerWidths.length][];
		for (int i=0; i<maxLayerWidths.length; i++) {
			cachedLayers[i] = new EObject[maxLayerWidths[i]];
		}		
		
		// Done.
		monitor.done();

	}

	/**
	 * Update the following fields based on the given model:
	 * <ul>
	 * <li>maxDepth</li>
	 * <li>maxLocalWidth</li>
	 * <li>maxLinks</li>
	 * <li>usedTypes</li>
	 * </ul>
	 * @param model Model.
	 */
	private void updateInfo(Model model) {

		// The root layer:
		List<EObject> layer = model.getResource().getContents();

		// Empty model?
		if (layer.isEmpty()) {
			return; // nothing to do
		}
		
		// Update maximum local width for the root layer:
		if (maxLocalWidths.length==0) {
			maxLocalWidths = new int[1];
		}
		maxLocalWidths[0] = Math.max(maxLocalWidths[0], layer.size());

		// Breadth-first traversal:
		int level = 0;
		while (!layer.isEmpty()) {
			
			List<EObject> nextLayer = new ArrayList<EObject>();
			int maxChildren = 0;
			for (EObject obj : layer) {
				
				// Update used types:
				EClass type = obj.eClass();
				if (!usedTypes.contains(type)) {
					usedTypes.add(type);
				}
				
				// Update maximum link counts:
				for (EReference ref : type.getEAllReferences()) {
					Object tar = obj.eGet(ref);
					int links = ref.isMany() ? ((List<?>) tar).size() : ((tar!=null) ? 1 : 0);
					Integer oldLinks = maxLinks.get(ref);
					if (oldLinks==null) {
						maxLinks.put(ref, links);
					} else {
						maxLinks.put(ref, Math.max(links, oldLinks));
					}
				}
				
				// Collect children and record maximum children count:
				List<EObject> children = obj.eContents();
				maxChildren = Math.max(maxChildren, children.size());
				nextLayer.addAll(children);
				
			}
			
			// No children? -> Stop.
			if (maxChildren==0) {
				break;
			}
			
			// Update the maximum local width for the children:
			level++;
			if (maxLocalWidths.length<=level) {
				maxLocalWidths = Arrays.copyOf(maxLocalWidths, level+1);
			}
			maxLocalWidths[level] = Math.max(maxLocalWidths[level], maxChildren);
			
			// Switch to the next layer:
			layer = nextLayer;
			
		}
		
	}
		
}
