package com.apptestunitary.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository personRepository;

	private EmailService emailService;

	@Autowired
	public PersonService(PersonRepository personRepository, EmailService emailService) {
		super();
		this.personRepository = personRepository;
		this.emailService = emailService;
	}

	@Transactional
	public Person save(Person person) {
		boolean consult = false;
		if (person.getId() != null && person.getId() > 0) {
			consult = true;
		}

		List<Email> emailsSaved = new ArrayList<>();

		Person personSaved = personRepository.save(person);
		List<Email> emails = buildEmailsWithPerson(personSaved, person.getEmails());
		if (emails != null && emails.size() > 0) {
			emailsSaved = emailService.saveList(emails);
			personSaved.getEmails().clear();
			if (consult) {
				emailsSaved = emailService.findEmailsByIdPerson(personSaved.getId());
				personSaved.getEmails().addAll(emailsSaved);
			} else {
				personSaved.getEmails().addAll(emailsSaved);
			}
		}
		return personSaved;
	}

	private List<Email> buildEmailsWithPerson(Person personSaved, List<Email> emails) {
		List<Email> emailsReady = new ArrayList<Email>();
		for (Email email : emails) {
			if (email.getId() != null && email.getId() > 0) {
				emailsReady.add(new Email(email.getId(), email.getEmailName(), personSaved));
			} else {
				emailsReady.add(new Email(email.getEmailName(), personSaved));
			}
		}
		return emailsReady;
	}

	public Optional<Person> findPerson(Long id) {
		return personRepository.findById(id);
	}

	public Optional<List<Person>> findSearchingNamePerson(String namePerson) {
		return personRepository.findSearchingNamePerson(namePerson.toLowerCase().trim());
	}

	public void deletePerson(Long id) {
		personRepository.deleteById(id);
	}

	public Optional<List<Person>> findPersonByNameAndDateOfLastEdition(String namePerson, Timestamp firstPeriod,
			Timestamp secondPeriod) {

		if (firstPeriod == null || secondPeriod == null) {
			return findSearchingNamePerson(namePerson);
		}

		return personRepository.findPersonByNameAndDateOfLastEdition(namePerson.toLowerCase().trim(), firstPeriod,
				secondPeriod);
	}

	public Optional<List<Person>> findPersonByNameAndRegistrationDate(String namePerson, Timestamp firstPeriod,
			Timestamp secondPeriod) {
		if (firstPeriod == null || secondPeriod == null) {
			return findSearchingNamePerson(namePerson);
		}

		return personRepository.findPersonByNameAndRegistrationDate(namePerson.toLowerCase().trim(), firstPeriod,
				secondPeriod);
	}

}