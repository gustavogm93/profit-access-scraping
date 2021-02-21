package ar.com.pa.enums;

public enum Periods {
	
	Quarter(4);
	
	private int months;
	
	private Periods(int months) {this.months = months;}
	public int getMonths() {return months;}
}

