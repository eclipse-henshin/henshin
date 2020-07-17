package org.eclipse.emf.henshin.monitoring.kieker.util;

public class Restriction {
	private long restrictedTo;
	private long numerOfRestrictions; //how often restricted
	
	public Restriction(long restrictedTo,long number){
		this.restrictedTo=restrictedTo;
		this.numerOfRestrictions=number;
	}
	
	public void updateRestriction(long restriction,long number){
		this.restrictedTo=this.restrictedTo+restriction;
		this.numerOfRestrictions=this.numerOfRestrictions+number;
	}
	
	public long getAverage() {
		return this.restrictedTo/this.numerOfRestrictions;
	}
	
}
