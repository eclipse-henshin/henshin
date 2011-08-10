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
		
		// Initialize the tree:
		clear();
		
	}
	
	public void clear() {
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

	/**
	 * Create the given number of new children to the node.
	 * The children will be created at the end of the current
	 * children list. Their hash codes will be in initialized 
	 * with 0. The cursor is moved to the first new child!
	 * @param numChildren Number of children to be created.
	 * @return <code>true</code> if the children were created.
	 */
	public boolean createChildren(int numChildren) {
		
		// Check if the children number is not positive:
		if (numChildren<=0) {
			return false;
		}
		
		// Check if there are already children:
		boolean hasChildren = hasChildren();
		
		// Additional space that we need:
		int space = hasChildren ? numChildren : numChildren+2;

		// Check if we need to resize the data array:
		if (size+space > data.length) {
			int newLength = (size+space) * 2;
			LENGTH_SUM = LENGTH_SUM + newLength - data.length;
			data = Arrays.copyOf(data, newLength);
		}

		// Where the children will start:
		int start = hasChildren ? position+2 : position+1;
		
		// Make some space for the children:
		System.arraycopy(data, start, data, start+space, size-start);
		size += space;

		// Move the existing children to the front first:
		if (hasChildren) {
			int p = start;
			int children = 0;
			while (p<size) {
				int newValue = data[p+space];
				if (newValue==OPEN_MARKER) {
					children++;
				}
				else if (newValue==CLOSE_MARKER) {
					children--;
					if (children<0) {
						break;
					}
				}
				data[p++] = newValue;
			}
			start = p;			
		}
		
		// Initialize the new children with zeros:
		Arrays.fill(data, start, start+space, 0);
		
		// Add the OPEN and CLOSE markers:
		if (!hasChildren) {
			data[start] = OPEN_MARKER;
			data[start+space-1] = CLOSE_MARKER;
		}
		
		// Go to the first new child:
		position = hasChildren ? start : start+1;
		
		// Done.
		return true;
		
	}

	public void goToRoot() {
		position = 1;
	}

	public boolean goRight() {
		
		// Remember the old position:
		int oldposition = position;
		
		// Moving right, skipping all children:
		int children = 0;
		while (++position < size) {
			
			// Check if it is an OPEN:
			if (data[position]==OPEN_MARKER) {
				children++;
			}
			// Check if it is a CLOSE:
			else if (data[position]==CLOSE_MARKER) {
				if (children>0) {
					children--;
				} else {
					break;
				}
			}
			// Otherwise it is a node:
			else if (children==0) {
				return true;
			}			
		}
		
		// Problem finding the right element, so we reset:
		position = oldposition;
		return false;

	}

	public boolean goLeft() {
				
		// Remember the old position:
		int oldposition = position;

		// Moving left, skipping all children:
		int children = 0;
		while (--position >= 0) {

			// Check if it is a CLOSE:
			if (data[position]==CLOSE_MARKER) {
				children++;
			}
			// Check if it is an OPEN:
			else if (data[position]==OPEN_MARKER) {
				if (children>0) {
					children--;
				} else {
					break;
				}
			}
			// Otherwise it is a node:
			else if (children==0) {
				return true;
			}
		}

		// Problem finding the left element, so we reset:
		position = oldposition;
		return false;
		
	}

	public boolean goUp() {
		
		// Remember the old position:
		int oldposition = position;

		// Moving left, skipping all children:
		int children = 0;
		while (--position >= 0) {

			// Check if it is a CLOSE:
			if (data[position]==CLOSE_MARKER) {
				children++;
			}
			// Check if it is an OPEN:
			else if (data[position]==OPEN_MARKER) {
				children--;
			}
			// Otherwise it is a node:
			else if (children==-1) {
				return true;
			}
		}

		// Problem finding the left element, so we reset:
		position = oldposition;
		return false;
		
	}

	public boolean goDown() {
		
		// Moving down is possible only if there are children:
		if (data[position+1]==OPEN_MARKER) {
			position += 2;
			return true;
		}
		return false;
		
	}
	
	public boolean shiftLeft() {
		
		// Remember current position:
		int rightPosition = position;
		
		// Go left if possible:
		if (!goLeft()) {
			return false;
		}
		
		// Backup the content of the left node:
		int[] leftContent = Arrays.copyOfRange(data, position, rightPosition);
		
		// Copy the content of the right node to the left node:
		int children = 0;
		int current;
		for (current=rightPosition; current<size; current++) {
			if (current>rightPosition) {
				if (data[current]==OPEN_MARKER) {
					children++;
				} else if (data[current]==CLOSE_MARKER) {
					children--;
					if (children<0) {
						break;
					}
				}
				else if (children==0) {
					break;
				}
			}
			data[position+current-rightPosition] = data[current];
		}
		
		// Copy the content of the left node to the right node:
		System.arraycopy(leftContent, 0, data, position+current-rightPosition, leftContent.length);
		
		// Done.
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
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
				String hex = Integer.toHexString(data[i]);
				if (i==position) {
					result = result + "<" + hex + ">";
				} else {
					result = result + hex;
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
	
	/*
	 * Test method
	 */
	public static void main(String[] args) {
		
		System.out.println("Generating random trees...");
		for (int i=0; i<10000; i++) {
			HashCodeTree tree = new HashCodeTree();
			tree.setHashCode((int) (Math.random()*10));
			int iterations = (int) (Math.random()*30 + 10);
			for (int j=0; j<iterations; j++) {
				if (Math.random()<.1) {
					tree.createChildren((int) (Math.random()*3) + 2);
					tree.setHashCode((int) (Math.random()*10));
					tree.goRight();
					tree.goRight();
				}
				while (Math.random()<.9) {
					double dir = Math.random();
					if (dir<.1) tree.goLeft(); else
					if (dir<.4) tree.goRight(); else
					if (dir<.9) tree.goDown(); else
					tree.goUp();
				}
				tree.setHashCode((int) (Math.random()*9) + 1);
				if (Math.random()<0.25) {
					tree.goUp();
					tree.goRight();
					tree.goRight();
					tree.shiftLeft();
				}
				if (Math.random()<0.05) tree.goToRoot();
			}
			System.out.println(tree);			
		}
		
	}
	
}
