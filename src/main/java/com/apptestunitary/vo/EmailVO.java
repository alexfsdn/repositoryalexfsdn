package com.apptestunitary.vo;

public class EmailVO {

	private Long id;
	private String emailName;

	public EmailVO() {

	}

	public EmailVO(String emailName) {
		super();
		this.emailName = emailName;
	}

	public EmailVO(Long id, String emailName) {
		super();
		this.id = id;
		this.emailName = emailName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

}
