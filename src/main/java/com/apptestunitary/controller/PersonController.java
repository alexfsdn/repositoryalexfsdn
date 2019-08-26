package com.apptestunitary.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apptestunitary.model.Person;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.util.PersonUtil;
import com.apptestunitary.util.PersonVOUtil;
import com.apptestunitary.util.ValidationPersonVOUtil;
import com.apptestunitary.util.ValidationUtil;
import com.apptestunitary.vo.PersonVO;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<PersonVO> save(@RequestBody PersonVO personVO) {

		if (!ValidationPersonVOUtil.personIsValid((personVO))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Person person = PersonUtil.buildPerson(personVO);
		Person personSaved = personService.save(person);
		PersonVO personVOSaved = PersonVOUtil.buildPersonVO(personSaved);

		return new ResponseEntity<PersonVO>(personVOSaved, HttpStatus.OK);
	}

	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO personVO) {
		if (!ValidationPersonVOUtil.personIsValidToUpdate((personVO))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Person personToUpdate = PersonUtil.buildPerson(personVO);
		Person personUpdated = personService.save(personToUpdate);
		PersonVO personVOUpdated = PersonVOUtil.buildPersonVO(personUpdated);

		return new ResponseEntity<PersonVO>(personVOUpdated, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<PersonVO> findById(@PathVariable("id") Long id) {
		if (ValidationUtil.isNegative(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<Person> personFinded = personService.findPerson(id);

		if (!personFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		PersonVO personVO = PersonVOUtil.buildPersonVO(personFinded.get());

		return new ResponseEntity<PersonVO>(personVO, HttpStatus.OK);
	}

	@GetMapping(value = "/namePerson/{namePerson}", produces = "application/json")
	public ResponseEntity<List<PersonVO>> findByNamePerson(@PathVariable("namePerson") String namePerson) {
		if (StringUtils.isEmpty(namePerson)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<List<Person>> peopleFinded = personService.findSearchingNamePerson(namePerson);

		if (!peopleFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<PersonVO> peopleVO = PersonVOUtil.buildPeopleVO(peopleFinded.get());

		return new ResponseEntity<List<PersonVO>>(peopleVO, HttpStatus.OK);
	}

	@GetMapping(value = "/namePersonAndDateOfLastEdition/", produces = "application/json")
	public ResponseEntity<List<PersonVO>> findNamePersonAndDateOfLastEdition(
			@RequestParam("namePerson") String namePerson, @RequestParam("firstPeriod") long firstPeriod,
			@RequestParam("secondPeriod") long secondPeriod) {

		Timestamp first = null;
		Timestamp second = null;
		if (firstPeriod < 0 || secondPeriod < 0) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		first = new Timestamp(firstPeriod);
		second = new Timestamp(secondPeriod);

		Optional<List<Person>> peopleFinded = personService.findPersonByNameAndDateOfLastEdition(namePerson, first,
				second);

		if (!peopleFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<PersonVO> peopleVO = PersonVOUtil.buildPeopleVO(peopleFinded.get());

		return new ResponseEntity<List<PersonVO>>(peopleVO, HttpStatus.OK);
	}

	@GetMapping(value = "/namePersonAndRegistrationDate/", produces = "application/json")
	public ResponseEntity<List<PersonVO>> findNamePersonAndRegistrationDate(
			@RequestParam("namePerson") String namePerson, @RequestParam("firstPeriod") long firstPeriod,
			@RequestParam("secondPeriod") long secondPeriod) {

		Timestamp first = null;
		Timestamp second = null;
		if (firstPeriod < 0 || secondPeriod < 0) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		first = new Timestamp(firstPeriod);
		second = new Timestamp(secondPeriod);

		Optional<List<Person>> peopleFinded = personService.findPersonByNameAndRegistrationDate(namePerson, first,
				second);

		if (!peopleFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<PersonVO> peopleVO = PersonVOUtil.buildPeopleVO(peopleFinded.get());

		return new ResponseEntity<List<PersonVO>>(peopleVO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long id) throws Exception {
		if (ValidationUtil.isNegative(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<Person> personFinded = personService.findPerson(id);

		if (!personFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		personService.deletePerson(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}