package com.apptestunitary.enums.url;

public enum PersonURIEnum {

	URL_PERSON("person/"), URL_GET_BY_NAME_PERSON("person/namePerson/"),
	URL_GET_BY_NAME_PERSON_AND_DATE_OF_LAST_EDITION("person/namePersonAndDateOfLastEdition/"),
	URL_GET_BY_NAME_PERSON_AND_REGISTRATION_DATE("person/namePersonAndRegistrationDate/");

	private String url;

	private PersonURIEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
