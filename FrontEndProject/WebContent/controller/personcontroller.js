/**
 * 
 */
app.controller('HomeController', function($scope) {
})

app.controller('PersonController', function(PersonService, $scope, $location,$rootScope,$cookieStore) {
	$scope.imgpath='';
	$scope.imgpath='http://localhost:8081/BackEndProject/getimage/';
	
	$scope.register = function() {
		PersonService.register($scope.person).then(function(response) {
			$scope.message = "register sucessfully"
			console.log(response.status)
			console.log(response.data)
			$('#login-modal').modal();
		}, function(response) {
			$scope.Error = response.data;
			$('#register-modal').modal();
		})
	}
	$scope.login = function() {
		PersonService.login($scope.person).then(function(response) {
			$rootScope.currentPerson = response.data
			$cookieStore.put("currentPerson",response.data)
			$location.path('/home')
		}, function(response) {
			$scope.error = response.data
			$('#login-modal').modal();
		})
	}
	$scope.personobj=PersonService.getPersonByUsername().then(function(response){
        $scope.personobj=response.data
		console.log(response.data)
		},function(response){
			console.log(response.data)
			
		})
	$scope.update=function(){
		PersonService.updatePersonProfile($scope.personobj).then(function(response){
			$scope.personobj={}
			console.log(response.data)
			$location.path('/home')
		},function(response){
			console.log(response.data)
			$location.path('/edituserprofile')
		})
		}
	
})