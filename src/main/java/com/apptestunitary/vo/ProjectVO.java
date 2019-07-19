package com.apptestunitary.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProjectVO {

	private Long id;
	private String name;
	private Timestamp registrationDate;
	private Timestamp dateOfLastEdition;
	private List<PersonVO> peopleVOs = new ArrayList<>();

	public ProjectVO(Long id, String name, Timestamp registrationDate, Timestamp dateOfLastEdition,
			List<PersonVO> peopleVOs) {
		super();
		this.id = id;
		this.name = name;
		this.registrationDate = registrationDate;
		this.dateOfLastEdition = dateOfLastEdition;
		this.peopleVOs = peopleVOs;
	}

	public ProjectVO(String name, List<PersonVO> peopleVOs) {
		super();
		this.name = name;
		this.peopleVOs = peopleVOs;
	}

	public ProjectVO(String name) {
		super();
		this.name = name;
	}

	public ProjectVO() {
	}

	public ProjectVO(Long id, List<PersonVO> people) {
		super();
		this.id = id;
		this.peopleVOs = people;
	}

	public ProjectVO(Long id, String name, List<PersonVO> peopleVOs) {
		super();
		this.id = id;
		this.name = name;
		this.peopleVOs = peopleVOs;
	}

	public ProjectVO(Long id, String name, Timestamp registrationDate, List<PersonVO> peopleVOs) {
		super();
		this.id = id;
		this.name = name;
		this.registrationDate = registrationDate;
		this.peopleVOs = peopleVOs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<PersonVO> getPeopleVOs() {
		return peopleVOs;
	}

	public void setPeopleVOs(List<PersonVO> peopleVOs) {
		this.peopleVOs = peopleVOs;
	}

}
