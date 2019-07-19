package com.apptestunitary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptestunitary.model.Email;
import com.apptestunitary.repository.EmailRepository;

@Service
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;

	public List<Email> saveList(List<Email> emails) {
		return this.emailRepository.saveAll(emails);

	}

	public List<Email> findEmailsByIdPerson(Long id) {
		return this.emailRepository.findByPerson(id);
	}

}
