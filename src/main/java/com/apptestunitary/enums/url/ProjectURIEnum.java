package com.apptestunitary.enums.url;

public enum ProjectURIEnum {

	URL_PROJECT("project/"), URL_PROJECT_SAVE_LIST("project/saveList/"), URL_PROJECT_UPDATE_LIST("project/updateList/"),
	URL_GET_BY_NAME("project/nameProject/"),
	URL_GET_BY_NAME_AND_DATE_OF_LAST_EDITION("project/nameAndDateOfLastEdition/"),
	URL_GET_BY_NAME_AND_REGISTRATION_DATE("project/nameAndRegistrationDate/");

	private String url;

	private ProjectURIEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
