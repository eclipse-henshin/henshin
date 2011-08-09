package org.eclipse.emf.henshin.statespace.equality;

import java.util.Arrays;

/**
 * A memory-efficient tree whose values are hash codes.
 * The two hash-codes {@value #OPEN_MARKER} and {@value #CLOSE_MARKER}
 * are not supported and are automatically replaced by {@link Integer#MAX_VALUE}.
 * Thus, in general it cannot be assumed that writing to a node and reading
 * it again produces the same value.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class HashCodeTree {

	// Marker for starting the children list:
	private static final int OPEN_MARKER = Integer.MAX_VALUE-2;
	
	// Marker for finishing the children list:
	private static final int CLOSE_MARKER = Integer.MAX_VALUE-1;

	// Track the total number of trees:
	private static long TREE_COUNT = 1;
	
	// Track the sum of the lengths of all trees:
	private static long LENGTH_SUM = 16;
	
	// The data array:
	private int[] data;
	
	// The size of the array (number of used entries)
	private int size;
	
	// The current position in the array:
	private int position;

	/**
	 * Default constructor. Initializes the tree
	 * with a root node whose value is 0.
	 */
	public HashCodeTree() {
		
		// Initialize the data array:
		data = new int[(int) (LENGTH_SUM / TREE_COUNT)];
		TREE_COUNT++;
		LENGTH_SUM += data.length;
		
		// Create the root node (value is 0)
		data[0]	= OPEN_MARKER;
		data[1]	= 0;
		data[2]	= CLOSE_MARKER;
		size = 3;
		position = 1;
		
	}

	public int getHashCode() {
		return data[position];
	}

	public void setHashCode(int hashcode) {
		data[position] = hashcode;
	}
	
	public boolean hasChildren() {
		return data[position+1]==OPEN_MARKER;
	}

	public void createChildren(int numChildren) {
		
		// Check if there are already children:
		if (data[position+1]==OPEN_MARKER) {
			throw new RuntimeException("Current node already has children");
		}

		// Where the children will start:
		int start = position+1;
		
		// Space that we need:
		int space = numChildren+2;

		// Check if we need to resize the data array:
		if (size+space > data.length) {
			int newLength = (size+space) * 2;
			LENGTH_SUM = LENGTH_SUM + newLength - data.length;
			data = Arrays.copyOf(data, newLength);
		}
		
		// Make some space for the children:
		System.arraycopy(data, start, data, start+space, size-position);
		size += space;
		
		// Initialize the children list:
		Arrays.fill(data, start, start+space, 0);
		data[start] = OPEN_MARKER;
		data[start+space-1] = CLOSE_MARKER;
		
	}

	public void moveToRoot() {
		position = 1;
	}

	public boolean moveRight() {
		
		// Remember the old position:
		int oldposition = position;
		
		// Moving right, skipping all children:
		int children = 0;
		do {
			position++;
			if (data[position]==OPEN_MARKER) {
				children++; 
			}
			else if (data[position]==CLOSE_MARKER) {
				children--; 
			}
		} while (children>0);
		
		// If we are at a CLOSE_MARKER then there was no right neighbor:
		if (data[position]==CLOSE_MARKER) {
			position = oldposition;
			return false;
		}
		
		// Otherwise we are good.
		return true;
	}

	public boolean moveLeft() {
				
		// Remember the old position:
		int oldposition = position;
		
		// Moving left, skipping all children:
		int children = 0;
		do {
			position--;
			if (data[position]==CLOSE_MARKER) {
				children++; 
			}
			else if (data[position]==OPEN_MARKER) {
				children--; 
			}
		} while (children>0);
		
		
		if (data[position]==OPEN_MARKER) {
			position = oldposition;
			return false;
		}
		return true;
	}

	public boolean moveUp() {
		int oldposition = position;
		int children = 0;
		do {
			position--;
			if (data[position]==CLOSE_MARKER) {
				children++; 
			}
			else if (data[position]==OPEN_MARKER) {
				children--; 
			}
		} while (children>-1);
		position--;
		if (data[position]==OPEN_MARKER) {
			position = oldposition;
			return false;
		}
		return true;
	}

	public boolean moveDown() {
		if (data[position+1]==OPEN_MARKER) {
			position += 2;
			return true;
		}
		return false;
	}
	
	public String toString() {
		String result = "";
		for (int i=0; i<size; i++) {
			switch (data[i]) {
			case OPEN_MARKER: 
				result = result + "(";
				break;
			case CLOSE_MARKER: 
				result = result + ")";
				break;
			default:
				if (i==position) {
					result = result + "_" + data[i] + "_";
				} else {
					result = result + data[i];
				}
			}
			if (data[i]!=OPEN_MARKER &&
				i<size-1 && 
				data[i+1]!=OPEN_MARKER && 
				data[i+1]!=CLOSE_MARKER) {
				result = result + ",";
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		HashCodeTree tree = new HashCodeTree();
		System.out.println(tree);
		tree.setHashCode(1);
		tree.createChildren(3);
		tree.moveDown();
		tree.setHashCode(2);
		tree.moveRight();
		tree.setHashCode(3);
		tree.moveRight();
		tree.setHashCode(4);
		tree.moveLeft();
		tree.moveLeft();
		tree.moveLeft();
		tree.createChildren(4);
		tree.moveDown();
		tree.setHashCode(5);
		tree.moveRight();
		tree.setHashCode(6);
		tree.moveRight();
		tree.setHashCode(7);
		tree.moveRight();
		tree.setHashCode(8);
		tree.moveUp();
		tree.moveUp();
		tree.moveUp();
		System.out.println(tree);
	}
	
}
