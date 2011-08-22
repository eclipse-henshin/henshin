package org.eclipse.emf.henshin.statespace.equality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A very generic match generator. 
 * @author Christian Krause
 */
public class MatchIterator {
	
	// Size of the patterns:
	private int size;
	
	// Number of different codes.
	private int codes;
	
	// Indizes for all codes in pattern 1:
	private int[][] indizes1;

	// Indizes for all codes in pattern 2:
	private int[][] indizes2;
	
	// Permutations are used as representations for matches:
	private int[][] permutations;
	
	// The next match:
	private int[] next;

	/**
	 * Default constructor. The following conditions apply for the arguments:
	 * the patterns contain only the numbers between 0..(codes-1). The number
	 * of occurrences of every code in pattern 1 should match the number of
	 * occurrences of the same code in pattern 2. Otherwise, no match can be found.
	 * @param pattern1 Pattern 1.
	 * @param pattern2 Pattern 2.
	 * @param codes Number of codes.
	 */
	public MatchIterator(int[] pattern1, int[] pattern2, int codes) {
		
		// Patterns must have the same size:
		if (pattern1.length!=pattern2.length) {
			next = null;
			return;
		}
		this.size = pattern1.length;
		this.codes = codes;
		
		// Generate the index look-up tables for the patterns:
		indizes1 = generateIndizes(pattern1, codes);
		indizes2 = generateIndizes(pattern2, codes);
		
		// Now make sure that every code has the same number 
		// of occurrences in both patterns. We initialize the
		// permutations with identities.
		permutations = new int[codes][];
		for (int i=0; i<codes; i++) {
			if (indizes1[i].length==indizes2[i].length) {
				permutations[i] = identity(new int[indizes1[i].length]);
			} else {
				next = null;	// error, no match possible.
				return;
			}
		}
		
		// Now compute the first match based on the identities:
		next = generateMatch();
		
	}
	
	/*
	 * Generate the index look-up table for a pattern.
	 */
	private int[][] generateIndizes(int[] pattern, int codes) {
		List<List<Integer>> indizes = new ArrayList<List<Integer>>();
		for (int i=0; i<codes; i++) {
			indizes.add(new ArrayList<Integer>());
		}
		for (int i=0; i<pattern.length; i++) {
			indizes.get(pattern[i]).add(i);
		}
		int[][] result = new int[codes][];
		for (int i=0; i<codes; i++) {
			result[i] = new int[indizes.get(i).size()];
			for (int j=0; j<result[i].length; j++) {
				result[i][j] = indizes.get(i).get(j);
			}
		}
		return result;
	}
	
	/*
	 * Compute the identity permutation of a given size.
	 */
	private static int[] identity(int[] array) {
		for (int i=0; i<array.length; i++) {
			array[i] = i;
		}
		return array;
	}
	
	/*
	 * Try to compute the successor of a permutation.
	 */
	private static boolean increase(int[] permutation) {
		
		// Check the size:
		if (permutation.length<2) {
			return false;
		}
		
		// Find the left value:
		int left = permutation.length-2;
		while ((permutation[left]>permutation[left+1]) && (left>=1)) {
			left--;
		}
		if ((left==0) && (permutation[left]>permutation[left+1])) {
			return false;
		}
		
		// Find the right value:
		int right = permutation.length-1;
		while (permutation[left]>permutation[right]) {
			right--;
		}
		
		// Swap left and right:
		int temp = permutation[left];
		permutation[left] = permutation[right];
		permutation[right] = temp;

		// Order the rest:
		int x = left+1;
		int y = permutation.length-1;
		while (x<y) {
			temp = permutation[x];
			permutation[x++] = permutation[y];
			permutation[y--] = temp;
		}
		return true;
		
	}
	
	/*
	 * Compute a match based on the current permutations.
	 */
	private int[] generateMatch() {
		int[] match = new int[size];
		for (int c=0; c<codes; c++) {
			for (int i=0; i<indizes1[c].length; i++) {
				match[indizes1[c][i]] = indizes2[c][permutations[c][i]];
			}
		}
		return match;
	}
	
	/**
	 * Check whether there is another match available.
	 * @return <code>true</code> if another match is available.
	 */
	public boolean hasNext() {
		return next!=null;
	}
	
	/**
	 * Get the next match.
	 * @return the next match or <code>null</code>.
	 */
	public int[] next() {
		
		// Use the prepared match result:
		int[] result = next;
		
		// Reset the next match:
		next = null;
		
		// Increase the permutations:
		for (int i=0; i<codes; i++) {
			if (increase(permutations[i])) {
				for (int j=0; j<i; j++) {
					identity(permutations[j]);
				}
				// Generate the next match:
				next = generateMatch();
				break;
			}
		}
		
		// Return the current match:
		return result;
		
	}
	
	/*
	 * Test for generating permutations.
	 */
	public static void main(String[] args) {
		int size = 1;
		for (int i=1; i<10; i++) {
			System.out.println("\nGenerating permutations of size " + i);
			int[] permutation = identity(new int[i]);
			int generated = 0;
			do {
				generated++;
				System.out.println(Arrays.toString(permutation));
			} while (increase(permutation));
			size = size * i;
			if (size!=generated) {
				System.out.println("Number of generated permutations is wrong!!! (is " + 
						generated + ", expected " + size + ")");
				return;
			} else {
				System.out.println("Successfully generated " + generated + " permutations.");
			}
		}
	}
}
