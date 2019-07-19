package com.apptestunitary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apptestunitary.model.PersonProject;

@Repository
public interface PersonProjectRepository extends JpaRepository<PersonProject, Long> {

	void deleteByIdProject(Long idProject);

	Optional<List<PersonProject>> findByIdProject(Long idProject);

}
