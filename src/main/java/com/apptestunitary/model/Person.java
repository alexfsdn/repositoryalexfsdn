package com.apptestunitary.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@NotEmpty
	@Column(name = "name_person")
	private String namePerson;

	@Min(value = 1)
	@NotNull
	private int age;

	@Column(name = "registration_date")
	@NotNull
	private Timestamp registrationDate;

	@Column(name = "date_of_last_edition")
	@NotNull
	private Timestamp dateOfLastEdition;

	@OneToMany(mappedBy = "person", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Email> emails = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "person_project", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = {
			@JoinColumn(name = "project_id") })
	private List<Project> projects = new ArrayList<>();

	public Person(Long id, @NotNull String namePerson, @Min(1) @NotNull int age, @NotNull Timestamp registrationDate,
			List<Email> emails) {
		super();
		this.id = id;
		this.namePerson = namePerson;
		this.age = age;
		this.registrationDate = registrationDate;
		this.dateOfLastEdition = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.emails = emails;
	}

	public Person(@NotNull String namePerson, @Min(1) @NotNull int age, List<Email> emails) {
		super();
		this.namePerson = namePerson;
		this.age = age;
		this.emails = emails;
		this.registrationDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.dateOfLastEdition = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public Person() {

	}

	public Person(Long id) {
		super();
		this.id = id;
	}

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

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}