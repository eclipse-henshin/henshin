package org.eclipse.emf.henshin.statespace.impl;

@SuppressWarnings("all")
public class StateSpaceDebug {

	public static final boolean ENFORCE_DETERMINISTIC_EXPLORATION = false;

	public static final int CACHING_LEVEL = 1;
	
	public static final boolean NO_CACHING = (CACHING_LEVEL==0);
	public static final boolean NORMAL_CACHING = (CACHING_LEVEL==1);
	public static final boolean FULL_CACHING = (CACHING_LEVEL==2);
	
	public static final boolean VALIDATE_STATES = false;
	
	public static final boolean CHECK_ENGINE_DETERMINISM = false;
	
}