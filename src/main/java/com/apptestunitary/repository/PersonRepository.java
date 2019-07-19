package com.apptestunitary.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apptestunitary.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	Optional<Person> findById(Long id);

	@Modifying
	@Query("select p from Person p where lower(p.namePerson) like %:namePerson%")
	Optional<List<Person>> findSearchingNamePerson(@Param("namePerson") String namePerson);

	@Modifying
	@Query("select p from Person p where lower(p.namePerson) like %:namePerson% "
			+ " and p.dateOfLastEdition between :firstPeriod and :secondPeriod ")
	Optional<List<Person>> findPersonByNameAndDateOfLastEdition(@Param("namePerson") String namePerson,
			@Param("firstPeriod") Timestamp firstPeriod, @Param("secondPeriod") Timestamp secondPeriod);

	@Modifying
	@Query("select p from Person p where lower(p.namePerson) like %:namePerson% "
			+ " and p.registrationDate between :firstPeriod and :secondPeriod ")
	Optional<List<Person>> findPersonByNameAndRegistrationDate(@Param("namePerson") String namePerson,
			@Param("firstPeriod") Timestamp firstPeriod, @Param("secondPeriod") Timestamp secondPeriod);

}
