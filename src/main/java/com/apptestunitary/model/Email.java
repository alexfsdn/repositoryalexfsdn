package com.apptestunitary.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

@Entity
public class Email implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "email_name")
	@NotNull
	private String emailName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
	@ForeignKey(name = "person_fk")
	private Person person;

	public Email(Long id, @NotNull String emailName, Person person) {
		super();
		this.id = id;
		this.emailName = emailName;
		this.person = person;
	}

	public Email(@NotNull String emailName, Person person) {
		super();
		this.emailName = emailName;
		this.person = person;
	}

	public Email(Long id, @NotNull String emailName) {
		super();
		this.id = id;
		this.emailName = emailName;
	}

	public Email(@NotNull String emailName) {
		super();
		this.emailName = emailName;
	}

	public Email() {
	}

	public Long getId() {
		return id;
	}

	public String getEmailName() {
		return emailName;
	}

	public Person getPerson() {
		return person;
	}
}