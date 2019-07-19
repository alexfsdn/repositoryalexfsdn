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
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	@Column(name = "registration_date")
	@NotNull
	private Timestamp registrationDate;

	@Column(name = "date_of_last_edition")
	@NotNull
	private Timestamp dateOfLastEdition;

	@ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
	private List<Person> people = new ArrayList<>();

	public Project(Long id, @NotNull String name, @NotNull Timestamp registrationDate, List<Person> people) {
		super();
		this.id = id;
		this.name = name;
		this.registrationDate = registrationDate;
		this.dateOfLastEdition = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.people = people;
	}

	public Project(@NotNull String name) {
		super();
		this.name = name;
		this.registrationDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.dateOfLastEdition = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public Project(@NotNull String name, List<Person> people) {
		super();
		this.name = name;
		this.people = people;
		this.registrationDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.dateOfLastEdition = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public Project() {
	}

	public Project(Long id, @NotNull String name, List<Person> people) {
		super();
		this.id = id;
		this.name = name;
		this.people = people;
		this.registrationDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.dateOfLastEdition = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public Timestamp getDateOfLastEdition() {
		return dateOfLastEdition;
	}

	public List<Person> getPeople() {
		return people;
	}
}