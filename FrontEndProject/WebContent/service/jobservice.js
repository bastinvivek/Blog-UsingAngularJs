/**
 * 
 */
app.factory('JobService',function($http){
var jobService={}

jobService.saveJob=function(job){
return $http.post("http://localhost:8081/BackEndProject/savejob",job)
}
jobService.getAllJobs=function(){
	return $http.get("http://localhost:8081/BackEndProject/getalljobs")
	}
jobService.getJob=function(id){
	return $http.get("http://localhost:8081/BackEndProject/getjob/"+id)
	}
return jobService;
})
