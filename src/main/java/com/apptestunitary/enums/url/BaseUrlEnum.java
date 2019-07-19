package com.apptestunitary.enums.url;

public enum BaseUrlEnum {
	URL_BASE("http://localhost:8080/app-test-unitary/");

	private String url;

	private BaseUrlEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
