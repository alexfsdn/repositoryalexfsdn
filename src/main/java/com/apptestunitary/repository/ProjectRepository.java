package com.apptestunitary.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apptestunitary.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Modifying
	@Query("select p from Project p where lower(p.name) like %:name%")
	Optional<List<Project>> findProjectByName(@Param("name") String name);
	
	@Modifying
	@Query("select p from Project p where lower(p.name) like %:name% "
			+ " and p.dateOfLastEdition between :firstPeriod and :secondPeriod ")
	Optional<List<Project>> findProjectByNameAndDateOfLastEdition(@Param("name") String name,
			@Param("firstPeriod") Timestamp firstPeriod, @Param("secondPeriod") Timestamp secondPeriod);

	@Modifying
	@Query("select p from Project p where lower(p.name) like %:name% "
			+ " and p.registrationDate between :firstPeriod and :secondPeriod ")
	Optional<List<Project>> findProjectByNameAndRegistrationDate(@Param("name") String name,
			@Param("firstPeriod") Timestamp firstPeriod, @Param("secondPeriod") Timestamp secondPeriod);



}
