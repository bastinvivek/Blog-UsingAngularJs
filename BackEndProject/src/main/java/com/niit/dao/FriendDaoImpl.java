package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.Persons;

@Repository
public class FriendDaoImpl implements FriendDao{
	@Autowired
	private SessionFactory sessionFactory;

	public List<Persons> listOfSuggestedPersons(String username) {
		Session session=sessionFactory.openSession();
		/*SQLQuery sqlQuery=session.createSQLQuery("select * from Persons where username in"
		+"(select username from Persons where username!=?"
		+"minus"
		+"(select fromId from Friend_Table where toId=?"
		+"union"
		+"select toId from Friend_Table where fromId=? ))");
		sqlQuery.setString(0,username);
		sqlQuery.setString(1,username);
		sqlQuery.setString(2,username);
		sqlQuery.addEntity(Persons.class);
		List<Persons> suggestedPersonsList=sqlQuery.list();*/
		List<Persons> persons = session.createQuery("FROM Persons").list();
		session.close();
		return persons;
	}
	public void friendRequest(String fromUsername, String toUsername) {
	Session session=sessionFactory.openSession();
	Friend friend=new Friend();
	friend.setFromId(fromUsername);
	friend.setToId(toUsername);
	friend.setStatus('p');
	session.save(friend);
	session.flush();
	session.close();
	}
	public List<Friend> pendingRequests(String loggedInUserName) {
	Session session=sessionFactory.openSession();
	Query query=session.createQuery("from Friend where toId=? and status=?");
	query.setString(0, loggedInUserName);
	query.setCharacter(1,'p');
	List<Friend> penddingRequests=query.list();
	session.close();
	return penddingRequests;
	}
	public void updatePendingRequest(String fromId, String toId, char status) {
	Session session=sessionFactory.openSession();
	Query query=session.createQuery("from Friend where fromId=? and toId=?");
	query.setString(0, fromId);
	query.setString(1, toId);
	Friend friend=(Friend) query.uniqueResult();
	friend.setStatus(status);
	session.update(friend);
	session.flush();
	session.close();
	}
	public List<Friend> friends(String username) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Friend where (fromId=? or toId=?) and status=?");
		query.setString(0, username);
		query.setString(1, username);
		query.setCharacter(2, 'A');
		List<Friend> friends=query.list();
		session.close();
		return friends;
	}
}