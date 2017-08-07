package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.FriendDao;
import com.niit.model.Error;
import com.niit.model.Friend;
import com.niit.model.Persons;

@RestController
public class FriendController {
	@Autowired
	private FriendDao friendDao;
	@RequestMapping(value="/suggestedfriends",method=RequestMethod.GET)
	public ResponseEntity<?> suggestedFriends(HttpSession session){
		Persons persons=(Persons)session.getAttribute("person");
		System.out.println("entering into suggested friends");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		try{
		System.out.println(persons.getUsername());
		List<Persons> suggestedPersons=friendDao.listOfSuggestedPersons(persons.getUsername());
		return new ResponseEntity<List<Persons>>(suggestedPersons,HttpStatus.OK);
	}
		catch(Exception e){
			System.out.println(e.getMessage());
			Error error=new Error(1,"error");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);	
		}}
	@RequestMapping(value="/friendrequest/{toUsername}")
	public ResponseEntity<?> friendRequest(@PathVariable String toUsername,HttpSession session)
	{
		Persons persons=(Persons)session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
	String fromUsername=persons.getUsername();
	friendDao.friendRequest(fromUsername, toUsername);
	return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
	public ResponseEntity<?> pendingRequests(HttpSession session)
	{
		Persons persons=(Persons)session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Friend> requestFriends=friendDao.pendingRequests(persons.getUsername());
		return new ResponseEntity<List<Friend>>(requestFriends,HttpStatus.OK);
	}
	@RequestMapping(value="/updatependingrequests/{fromId}/{status}",method=RequestMethod.PUT)
	public ResponseEntity<?> updatePendingReqests(@PathVariable String fromId,@PathVariable char status,HttpSession session)
	{
		Persons persons=(Persons)session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		friendDao.updatePendingRequest(fromId,persons.getUsername(), status);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/friends",method=RequestMethod.GET)
	public ResponseEntity<?> friends(HttpSession session)
	{
		Persons persons=(Persons)session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Friend> friends=friendDao.friends(persons.getUsername());
		return new ResponseEntity<List<Friend>>(friends,HttpStatus.OK);
	}
}