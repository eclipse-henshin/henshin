package org.eclipse.emf.henshin.statespace.equality;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.statespace.Model;

/**
 * This class provides functionality to canonicalize
 * models when using graph-equality. The idea is to
 * sort in synchrony the hash code tree of the model
 * and the containment tree of the model. The sorting
 * is simply achieved by sorting the hash codes in
 * every children list in ascending order. Canonicalized
 * models can be checked more efficiently for graph
 * equality.
 * 
 * @author Christian Krause
 */
public class GraphModelCanonicalizer {
	
	public static void canonicalize(Model model, HashCodeTree tree) {
		
		// Set the tree cursor to the root node:
		tree.goToRoot();
		
		// Canonicalize all elements:
		EList<EObject> contents = model.getResource().getContents();
		if (!contents.isEmpty()) {
			tree.goDown();
			canonicalize(contents, tree);
			tree.goToRoot();
		}
		
	}
	
	/*
	 * Recursively canonicalize a list. This assumes that the cursor in the
	 * tree is set to the hash code of the first element in the list!!!
	 */
	@SuppressWarnings("unchecked")
	private static void canonicalize(EList<EObject> objects, HashCodeTree tree) {
		
		// List must not be empty:
		int size = objects.size();
		if (size==0) {
			return;
		}
		
		// Locally store the hash codes of the list elements:
		int[] hashcodes = new int[size];
		hashcodes[0] = tree.getHashCode();
		
		// We do a synchronized bubble-sort:
		for (int i2=1; i2<size; i2++) {
			
			// Go one to the right:
			goRight(tree, 1);
			
			// Get the current hash code:
			hashcodes[i2] = tree.getHashCode();
			
			// Now bubble it to the front:
			int shifts = 0;
			int dummyHash, x;
			EObject o1, o2;
			
			for (int i1=i2-1; i1>=0; i1--) {
				x = i1+1;
				if (hashcodes[i1] > hashcodes[x]) {
					
					// Shift in the tree:
					shiftLeft(tree);
					
					// Shift in the hash-codes array:
					dummyHash = hashcodes[i1];
					hashcodes[i1] = hashcodes[x];
					hashcodes[x] = dummyHash;
					
					// Shift in the model:
					o1 = objects.get(i1);
					o2 = objects.get(x);
					objects.remove(x);
					objects.set(i1, o2);
					objects.add(x, o1);
					
					// Remember how many shifts we did:
					shifts++;
				}
			}
			
			// We need to move the cursor in the tree to the last position:
			goRight(tree, shifts);
		}
		
		// Move the tree cursor back the start:
		goLeft(tree, size-1);
		
		// Now recursively sort all children:
		for (int i=0; i<size; i++) {

			EObject object = objects.get(i);
			boolean wentDown = false;
			
			for (EReference containment : object.eClass().getEAllContainments()) {
				EList<EObject> children;
				if (containment.isMany()) {
					children = (EList<EObject>) object.eGet(containment);
				} else {
					children = new BasicEList<EObject>();
					EObject child = (EObject) object.eGet(containment);
					if (child!=null) {
						children.add(child);
					}
				}
				// Recursive canonicalization if there are any children:
				if (!children.isEmpty()) {
					if (!wentDown) {
						goDown(tree);
						wentDown = true;
					}
					canonicalize(children, tree);
				}
			}
			// Go up again if we went down:
			if (wentDown) {
				goUp(tree);
			}
			
			// Move the tree cursor one right (without throwing an exception!):
			tree.goRight();
			
		}
		
	}
	
	
	// ******* Helper methods ******** //
	
	private static void goLeft(HashCodeTree tree, int steps) {
		for (int i=0; i<steps; i++) {
			if (!tree.goLeft()) {
				throwTreeException();
			}
		}
	}

	private static void goRight(HashCodeTree tree, int steps) {
		for (int i=0; i<steps; i++) {
			if (!tree.goRight()) {
				throwTreeException();
			}
		}
	}

	private static void goDown(HashCodeTree tree) {
		if (!tree.goDown()) {
			throwTreeException();
		}
	}

	private static void goUp(HashCodeTree tree) {
		if (!tree.goUp()) {
			throwTreeException();
		}
	}

	private static void shiftLeft(HashCodeTree tree) {
		if (!tree.shiftLeft()) {
			throwTreeException();
		}
	}
	
	private static void throwTreeException() {
		throw new RuntimeException("Error canonicalizing model: hash code tree is structurally different from model!");
	}
	
}
