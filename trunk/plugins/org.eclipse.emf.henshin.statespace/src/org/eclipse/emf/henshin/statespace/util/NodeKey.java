package org.eclipse.emf.henshin.statespace.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Wrapper class for EObjects that can be used as keys in hash-maps.
 * @author Christian Krause
 * @generated NOT
 */
public class NodeKey {
	
	/**
	 * A cache for node keys.
	 */
	public static class Cache extends LinkedHashMap<EObject,NodeKey> {
		
		private static final long serialVersionUID = 1L;
		private static final int DEFAULT_MAX_SIZE = 512;
		private int maxSize;
		
		// Whether attributes should be ignored.
		private boolean ignoreAttributes;
		
		public Cache(boolean ignoreAttributes, int maxSize) {
			this.maxSize = maxSize;
			this.ignoreAttributes = ignoreAttributes;
		}

		public Cache(boolean ignoreAttributes) {
			this(ignoreAttributes, DEFAULT_MAX_SIZE);
		}
		
		@Override
		public NodeKey get(Object object) {
			NodeKey key = super.get(object);
			if (key==null) {
				key = new NodeKey((EObject) object, ignoreAttributes);
				put((EObject) object,key);
			}
			return key;
		}
		
		@Override
		protected boolean removeEldestEntry(Map.Entry<EObject,NodeKey> eldest) {
			return size() > maxSize;
		}
		
	};

	/**
	 * The ten first prime numbers: used for computing hash codes.
	 */
	public static int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

	// EPackage's nsURI:
	private String nsURI;
	
	// Exponents used for computing the hash code:
	private int[] exponents;
	
	// The hash code:
	private int hashCode;

	/**
	 * Default and only constructor.
	 * @param node Node.
	 * @param ignoreAttributes Whether attributes should be ignored.
	 */
	public NodeKey(EObject node, boolean ignoreAttributes) {
		
		// Class and its features:
		EClass eclass = node.eClass();
		EList<EStructuralFeature> features = eclass.getEAllStructuralFeatures();
		
		// We have to remember the package name space URI:
		nsURI = node.eClass().getEPackage().getNsURI();
		
		// Initialize the exponents array:
		exponents = new int[features.size()+1];
		
		// Index 0 reserved for classifier ID.
		exponents[0] = asExponent(eclass.getClassifierID());
		
		// The rest is use for the actual features.
		for (int i=0; i<features.size(); i++) {				
			EStructuralFeature feature = features.get(i);
			int value = 0;
			if (feature.isMany()) {
				List<?> list = (List<?>) node.eGet(feature);
				if (feature instanceof EReference) {
					value = list.size();
				} else if (feature instanceof EAttribute) {
					value = ignoreAttributes ? 0 : list.hashCode();
				}
			} else {
				Object object = node.eGet(feature);
				if (object==null) {
					value = 0;
				} else if (feature instanceof EReference) {
					value = 1;
				} else if (feature instanceof EAttribute) {
					value = ignoreAttributes ? 0 : object.hashCode();
				}
			}
			exponents[i+1] = asExponent(value);
		}
		
		// Now we compute the hash code using the exponents and prime numbers.
		hashCode = 1;
		for (int i=0; i<exponents.length; i++) {
			hashCode = hashCode * intPow(PRIMES[i % PRIMES.length], exponents[i]);
		}
		if (nsURI!=null) {
			hashCode = hashCode + nsURI.hashCode();
		}
		
	}
	
	/** 
	 * Raise a integer to a given exponent. Unlike {@link java.lang.Math#pow(double, double)} 
	 * this implementation is base on integers, and hence, can produce overflows.
	 * @param base Base.
	 * @param exponent Exponent.
	 * @return Computed value.
	 */
	public static int intPow(int base, int exponent) {
		if (exponent==0) return 1;
		if (exponent==1) return base;
		if (exponent>1) {
			int result = intPow(base,exponent/2) * intPow(base,exponent/2);
			if (exponent % 2==1) result = result * base;
			return result;
		} else {
			throw new IllegalArgumentException("Negative exponent");
		}
	}
	
	/*
	 * Turn a value into a suitable exponent. The returned
	 * value range between 0..8, so that the exponents are
	 * always non-negative and small numbers.
	 */
	private int asExponent(int value) {
		return Math.abs(value) % 8;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof NodeKey)) {
			return false;
		}
		NodeKey key = (NodeKey) object;
		if ((nsURI==null && key.nsURI!=null) || (nsURI!=null && key.nsURI==null) || !nsURI.equals(key.nsURI)) {
			return false;
		}
		return Arrays.equals(exponents, key.exponents);
	}
	
}
