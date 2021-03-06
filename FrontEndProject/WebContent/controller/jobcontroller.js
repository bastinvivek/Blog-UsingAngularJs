/**
 * 
 */
app.controller('JobController',function($scope,$location,JobService)
		{
	$scope.jobs=JobService.getAllJobs().then(function(response){
		$scope.jobs=response.data;
	},function(response){
		$scope.message=response.data.message
		$('#login-modal').modal();
	})
	$scope.saveJob=function(){
		JobService.saveJob($scope.job).then(function(response){
			$location.path('/getalljobs')
		},function(response){
			$scope.message=response.data.message
			console.log=response.status
			if(response.status==401)
				$('#login-modal').modal();
			if(response.status==500)
				$location.path('/savejob')
		})
	
		$scope.job=JobService.getJob(id).then(function(response){
		$scope.job=response.data;
	},function(response){
		$scope.message=response.data.message
		$('#login-modal').modal();
	})
	}
		})