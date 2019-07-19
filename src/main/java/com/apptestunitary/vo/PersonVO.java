package com.apptestunitary.vo;

import java.sql.Timestamp;
import java.util.List;

public class PersonVO {
	private Long id;
	private String namePerson;
	private int age;
	private Timestamp registrationDate;

	public PersonVO() {

	}

	public PersonVO(Long id) {
		super();
		this.id = id;
	}

	public PersonVO(String namePerson, int age, List<EmailVO> emailsVO) {
		super();
		this.namePerson = namePerson;
		this.age = age;
		this.emailsVO = emailsVO;
	}

	public PersonVO(Long id, String namePerson, int age, Timestamp registrationDate, List<EmailVO> emailsVO) {
		super();
		this.id = id;
		this.namePerson = namePerson;
		this.age = age;
		this.registrationDate = registrationDate;
		this.emailsVO = emailsVO;
	}

	public PersonVO(Long id, String namePerson, int age, Timestamp registrationDate, Timestamp dateOfLastEdition,
			List<EmailVO> emailsVO) {
		super();
		this.id = id;
		this.namePerson = namePerson;
		this.age = age;
		this.registrationDate = registrationDate;
		this.dateOfLastEdition = dateOfLastEdition;
		this.emailsVO = emailsVO;
	}

	private Timestamp dateOfLastEdition;
	private List<EmailVO> emailsVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNamePerson() {
		return namePerson;
	}

	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Timestamp getDateOfLastEdition() {
		return dateOfLastEdition;
	}

	public void setDateOfLastEdition(Timestamp dateOfLastEdition) {
		this.dateOfLastEdition = dateOfLastEdition;
	}

	public List<EmailVO> getEmailsVO() {
		return emailsVO;
	}

	public void setEmailsVO(List<EmailVO> emailsVO) {
		this.emailsVO = emailsVO;
	}

}