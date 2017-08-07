package com.niit.dao;

import java.util.List;

import com.niit.model.Persons;

public interface PersonsDao {
	void registration(Persons persons);
	List<Persons> getAllPersons();
	Persons login(Persons persons);
	Persons updatePerson(Persons validPerson);
	Persons getPersonByUsername(String username);
}
