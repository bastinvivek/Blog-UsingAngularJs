package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.Persons;

public interface FriendDao {
List<Persons> listOfSuggestedPersons(String username);
void friendRequest(String fromUsername,String toUsername);
List<Friend> pendingRequests(String loggedInUsername);
void updatePendingRequest(String fromId,String toId,char status);
List<Friend> friends(String username);
}
