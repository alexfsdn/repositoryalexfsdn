package com.apptestunitary.enums;

public enum StatusEmailSendEnum {

	NEW(1, "New"), SENT(2, "Sent"), NEXT_ATTEMPT(3, "Next Attempt");

	private int id;
	private String label;

	private StatusEmailSendEnum(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}
}
