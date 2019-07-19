package com.apptestunitary.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable
@Entity
@Table(name = "person_project")
public class PersonProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "person_id")
	private Long idPerson;

	@Column(name = "project_id")
	private Long idProject;

	public PersonProject(Long idPerson, Long idProject) {
		super();
		this.idPerson = idPerson;
		this.idProject = idProject;
	}

	public PersonProject() {
	}

	public Long getIdPerson() {
		return idPerson;
	}

	public Long getIdProject() {
		return idProject;
	}

}