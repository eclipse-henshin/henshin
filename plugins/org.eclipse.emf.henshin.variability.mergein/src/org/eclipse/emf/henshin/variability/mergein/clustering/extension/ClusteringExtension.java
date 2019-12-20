//package org.eclipse.emf.henshin.variability.mergein.clustering.extension;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.Map;
//import java.util.List;
//
//import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
//
//public class ClusteringExtension {
//	Map<CloneGroup, List<CloneGroup>> basis2extensions = new HashMap<CloneGroup, List<CloneGroup>>();
//	Map<CloneGroup, List<CloneGroup>> extensions2bases = new HashMap<CloneGroup, List<CloneGroup>>();
//
//	
//	public void addExtension(CloneGroup basis, CloneGroup extension) {
//		List<CloneGroup> extensions = basis2extensions.get(basis);
//		if (extensions == null) {
//			extensions = new ArrayList<CloneGroup>();
//			basis2extensions.put(basis, extensions);
//		}
//		if (!extensions.contains(extension))
//			extensions.add(extension);
//		
//		List<CloneGroup> bases = extensions2bases.get(extension);
//		if (bases == null) {
//			bases = new ArrayList<CloneGroup>();
//			extensions2bases.put(extension, bases);
//		}
//		if (!bases.contains(basis))
//			bases.add(basis);
//	}
//
//	public List<CloneGroup> getExtensions(CloneGroup basis) {
//		return basis2extensions.get(basis);
//	}
//
//}
