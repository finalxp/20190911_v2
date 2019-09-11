package com.example.model;

public class TreePoint {
	private String ID;//
	private String NAME;
	private String PARENTID;
	private String ISLEAF;
	private int DISPLAY_ORDER;
	
	
	@Override
	public String toString() {
		return "TreePoint [ID=" + ID + ", NAME=" + NAME + ", PARENTID=" + PARENTID + ", ISLEAF=" + ISLEAF + ", DISPLAY_ORDER=" + DISPLAY_ORDER + "]";
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getPARENTID() {
		return PARENTID;
	}
	public void setPARENTID(String pARENTID) {
		PARENTID = pARENTID;
	}
	public String getISLEAF() {
		return ISLEAF;
	}
	public void setISLEAF(String iSLEAF) {
		ISLEAF = iSLEAF;
	}
	public int getDISPLAY_ORDER() {
		return DISPLAY_ORDER;
	}
	public void setDISPLAY_ORDER(int dISPLAY_ORDER) {
		DISPLAY_ORDER = dISPLAY_ORDER;
	}
	
}
