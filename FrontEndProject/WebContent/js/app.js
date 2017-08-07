/**
 * 
 */
var app = angular.module("blog", [ 'ngRoute', 'ngCookies' ])
app.config(function($routeProvider) {
	console.log('hai')
	$routeProvider
	.when('/home', {
		templateUrl : 'views/home.html',
		controller : 'HomeController'
	})
	.when('/', {
		templateUrl : 'views/home.html',
		controller : 'HomeController'
	})
	.when('/home', {
		templateUrl : 'views/home.html',
		controller : 'HomeController'
	})
	.when('/register', {
		templateUrl : 'views/register.html',
		controller : 'PersonController',
		activetab : 'register'
	})
	.when('/login', {
		templateUrl : 'views/login.html',
		controller : 'PersonController',
		controllerAs : 'pCtrl'
	})
	.when('/savejob', {
		templateUrl : 'views/savejob.html',
		controller : 'JobController'
	})
	.when('/getalljobs', {
		templateUrl : 'views/alljobs.html',
		controller : 'JobController'
	})
	.when('/getjob/:id', {
		templateUrl : 'views/jobs.html',
		controller : 'JobController'
	})
	.when('/saveblog', {
		templateUrl : 'views/saveblog.html',
		controller : 'BlogController'
	})
	.when('/getallblogs', {
		templateUrl : 'views/blogdetailslist.html',
		controller : 'BlogController'
	})
	.when('/getBlogForApproval/:id', {
		templateUrl : 'views/approvalform.html',
		controller : 'BlogDetailController'
	})
	.when('/getBlogDetails/:id', {
		templateUrl : 'views/newblogdetails.html',
		controller : 'BlogDetailController'
	})
	.when('/chat', {
		templateUrl : 'views/chat.html',
		controller : 'ChatCtrl'
	})
	.when('/profilepicture', {
		templateUrl : 'views/profilepicture.html'
	})
	.when('/edituserprofile', {
		templateUrl : 'views/edituserprofile.html',
		controller : 'PersonController'
	})
	.when('/suggestedpersons', {
		templateUrl : 'views/suggestedpersons.html',
		controller : 'FriendController'
	})
	.when('/pendingrequests', {
		templateUrl : 'views/pendingrequests.html',
		controller : 'FriendController'
	})
	.when('/listoffriends', {
		templateUrl : 'views/listoffriends.html',
		controller : 'FriendController'
	})
	.otherwise({
		templateUrl : 'views/pagenotfound.html'
	})
})
app.run(function($rootScope, $location, PersonService, $cookieStore) {

	if ($rootScope.currentPerson == undefined)
		$rootScope.currentPerson = $cookieStore.get("currentPerson")

	$rootScope.logout = function() {
		PersonService.logout().then(function(response) {
			$rootScope.message = "loggedout successfully.."
			delete $rootScope.currentPerson;
			$cookieStore.remove("currentPerson");
			$location.path('/home')
		}, function(response) {
			console.log(response.status)
			$rootScope.message = response.data.message
			$('#login-modal').modal();
		})
	}
	
})