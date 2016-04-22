(function() {
	'use strict';

	angular.module('angularWebApp').config(routeConfig);

	function routeConfig($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'app/main/main.html',
			controller : 'MainController',
			controllerAs : 'main'
		}).when('/register', {
			templateUrl : 'app/main/register.html',
			controller : 'RegisterController',
			controllerAs : 'reg'
		}).when('/sent', {
			templateUrl : 'app/main/email.html',
			controller : 'VerifyController',
			controllerAs : 'verify'
		}).when('/about', {
			templateUrl : 'app/main/about.html',
			controller : 'AboutController',
			controllerAs : 'about'
		}).when('/contact', {
			templateUrl : 'app/main/contact.html',
			controller : 'ContactController',
			controllerAs : 'cont'
		}).when('/home', {
			templateUrl : 'app/main/homeLoggedIn.html',
			controller : 'HomeLoggedInController',
			controllerAs : 'home'
		}).when('/activate/email/:email/hash/:hash', {
			templateUrl : 'app/main/activate.html',
			controller : 'ActivateController',
			controllerAs : 'activate'
		}).when('/advancedSearch', {
			templateUrl : 'app/main/advancedSearch.html',
			controller: 'AdvancedSearchController',
			controllerAs: 'asc'
        }).when('/uploadFile', {
          templateUrl : 'app/main/uploadFile.html',
          controller : 'uploadFileController',
          controllerAs : 'upload'
        }).when('/manageFile', {
          templateUrl : 'app/main/fileManagement.html',
          controller : 'fileManagementController',
          controllerAs : 'manage'
		}).when('/addDeleteCourse', {
			templateUrl : 'app/main/addDeleteCourse.html',
			controller: 'AddDeleteCourseController',
			controllerAs: 'adc'
		}).when('/following', {
			templateUrl : 'app/main/following.html',
			controller: 'FollowingController',
			controllerAs: 'fc'
		}).when('/course/:id', {
			templateUrl : 'app/main/course.html',
			controller: 'CourseController',
			controllerAs: 'cc'
		}).when('/notifications', {
			templateUrl : 'app/main/notifications.html',
			controller: 'NotificationsController',
			controllerAs: 'nc'
		}).otherwise({
			redirectTo : '/'
		});

		// $locationProvider.html5Mode(true);
	}

})();
