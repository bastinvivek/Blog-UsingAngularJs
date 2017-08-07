package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.PersonsDao;
import com.niit.model.Persons;
import com.niit.model.Error;

@RestController
public class PersonController {
	@Autowired
	private PersonsDao personsDao;
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody Persons person)
	{

		try
		{
			person.setEnabled(true);
			List<Persons> persons=personsDao.getAllPersons();
			System.out.println("entering registration");
			for(Persons p:persons){
				if(p.getUsername().equals(person.getUsername()))
				{
					Error error=new Error(2,"username exist");
					return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			System.out.println(person.getUsername());
			personsDao.registration(person);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}catch(Exception e)
		{
			Error error=new Error(1,"cannot register details");
			System.out.println(e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Persons persons,HttpSession session)
	{
		Persons validPerson=personsDao.login(persons);
		if(validPerson==null)
		{
			Error error=new Error(3,"invalid username");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else
		{
			validPerson.setOnline(true);
			validPerson=personsDao.updatePerson(validPerson);
			session.setAttribute("person",validPerson);
			return new ResponseEntity<Persons>(validPerson,HttpStatus.OK);
		}
		
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{
		Persons persons=(Persons) session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(4,"invalid username");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		//persons=personsDao.getPersonByUsername(persons.getUsername());
		persons.setOnline(false);
		personsDao.updatePerson(persons);
		session.removeAttribute("person");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/updateperson",method=RequestMethod.PUT)
	public ResponseEntity<?> updatePerson(@RequestBody Persons person,HttpSession session)
	{
		Persons persons=(Persons) session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(4,"invalid username");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		personsDao.updatePerson(person);
		session.setAttribute("person", person);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}

	@RequestMapping(value="/getpersondetails",method=RequestMethod.GET)
	public ResponseEntity<?> getPersonDetails(HttpSession session)
	{
		Persons persons=(Persons) session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(4,"invalid username");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		persons=personsDao.getPersonByUsername(persons.getUsername());
		return new ResponseEntity<Persons>(persons,HttpStatus.OK);
		}
}
