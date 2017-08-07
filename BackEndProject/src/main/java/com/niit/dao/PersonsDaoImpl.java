package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Persons;

@Repository
public class PersonsDaoImpl implements PersonsDao{
	@Autowired
private SessionFactory sessionFactory;
	
	public void registration(Persons persons) {
		Session session=sessionFactory.openSession();
		session.save(persons);
		session.flush();
		session.close();
	}

	public List<Persons> getAllPersons() {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Persons");
		List<Persons> persons=query.list();
		session.close();
		return persons;
	}
	public Persons login(Persons persons) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Persons where username=? and password=? and enabled=?");
		query.setString(0,persons.getUsername());
		query.setString(1,persons.getPassword());
		query.setBoolean(2,true);
		Persons validPersons=(Persons) query.uniqueResult();
		session.close();
		return validPersons;
	}
	public Persons updatePerson(Persons validPerson) {
	    Session session=sessionFactory.openSession();
	    session.update(validPerson);
	    session.flush();
	    session.close();
		return validPerson;
	}

	public Persons getPersonByUsername(String username) {
		Session session=sessionFactory.openSession();
		Persons persons=(Persons) session.get(Persons.class,username);
		session.close();
		return persons;
	}
}