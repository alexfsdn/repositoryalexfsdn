package com.apptestunitary.enums;

public enum DataFormatoEnum {

	YEAR_DAY_HOUR_MINUTES_SECOND_SSS("yyyy-MM-dd HH:mm:ss.SSS");

	private String label;

	private DataFormatoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
