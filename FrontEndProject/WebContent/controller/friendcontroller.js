/**
 * 
 */
app.controller('FriendController',function($scope,FriendService,$location){
	function listOfSuggestedPersons(){
		$scope.suggestedPersons=FriendService.suggestedPersons().then(function(response){
			alert('entering into suggested')
			console.log(response.data)
			$scope.suggestedPersons=response.data;
		},function(response){
			console.log(response.data)
			console.log(response.status)
		})
	}
	function pendingRequests(){
		$scope.listOfPendingRequests=FriendService.pendingRequests().then(function(response){
			$scope.listOfPendingRequests=response.data;
		},function(response){
			console.log(response.data)
		})
	}
	function listOfFriends(){
		$scope.friendsList=FriendService.listOfFriends().then(function(response){
			$scope.friendsList=response.data;
		},function(response){
			console.log(response.data)
		})
	}
	/**
	 * to user class
	 */
	$scope.friendrequest=function(toUsername){
		FriendService.sendFriendRequest(toUsername).then(function(response){
			listOfSuggestedPersons();
			$location.path('/suggestedpersons')
		},function(response){
			consloe.log(response.data)
		})
	}
	$scope.updatePendingRequests=function(fromId,status){
		FriendService.updatePendingRequests(fromId,status).then(function(response){
			pendingRequests();
			$loaction.path('/pendingrequests')
		},function(response){
			console.log(response.data)
			$loaction.path('/pendingrequests')
		})
	}
	listOfFriends();
	pendingRequests();
	listOfSuggestedPersons();
})