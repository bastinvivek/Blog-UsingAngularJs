/**
 * 
 */
app.factory('FriendService',function($http){
var friendService={}

friendService.suggestedPersons=function(){
return $http.get("http://localhost:8081/BackEndProject/suggestedfriends")
}
friendService.sendFriendRequest=function(toUsername){
	return $http.get("http://localhost:8081/BackEndProject/friendrequest/"+toUsername);
	}
friendService.pendingRequests=function(){
	return $http.get("http://localhost:8081/BackEndProject/pendingrequests")
	}
friendService.updatePendingRequests=function(fromId,status){
	return $http.put("http://localhost:8081/BackEndProject/updatependingrequests/"+fromId+"/"+status)
	}
friendService.listOfFriends=function(){
	return $http.get("http://localhost:8081/BackEndProject/friends")
	}
return friendService;
})
