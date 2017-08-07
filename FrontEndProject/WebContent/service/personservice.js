/**
 * 
 */
app.factory('PersonService',function($http){
var personService={}

personService.register=function(person){
return $http.post("http://localhost:8081/BackEndProject/register",person)
}
personService.login=function(person){
	return $http.post("http://localhost:8081/BackEndProject/login",person)
	}
personService.logout=function(){
	return $http.get("http://localhost:8081/BackEndProject/logout")
	}
personService.getPersonByUsername=function(){
	return $http.get("http://localhost:8081/BackEndProject/getpersondetails")
}

personService.updatePersonProfile=function(person){
	return $http.put("http://localhost:8081/BackEndProject/updateperson",person)
}
return personService;
})
