// script.js

// create the module and name it igConverge

var igConverge = angular.module('igConverge', [ 'ngRoute' ]);
console.log("this is controller" + pageContext);
// create the controller and inject Angular's $scope

/*igConverge.controller('mainController',['$scope', function($scope) {
	console.log("this is main ciontroolelr");
	// create a message to display in our view
	// $scope.user = 'true';
}]);*/

igConverge.controller("EventsController", function($scope) {
	// var url = "http://172.18.84.49:8080/igConverge/user/fetchevents";
	alert("this si event");
	$scope.event = [ {
		"id" : "558cf7bd11b331cc6b1972af",
		"name" : "wwf hitech",
		"startTime" : "Aug 22, 2015 12:00:00 AM",
		"endTime" : "Aug 24, 2015 12:00:00 AM",
		"venue" : "Cafe",
		"description" : "hav efun",
		"notification" : true,
		"category" : "wwf",
		"eventImage" : []
	}, {
		"id" : "558cff29392050471ffa64f4",
		"name" : "carrom",
		"startTime" : "Aug 25, 2015 12:00:00 AM",
		"endTime" : "Aug 26, 2015 12:00:00 AM",
		"venue" : "cafe",
		"description" : "carrom tournament",
		"notification" : true,
		"category" : "match",
		"eventImage" : []
	} ];
	$scope.array = [];
	angular.forEach($scope.event, function(event) {
		$scope.array.push(event);
	});
	/*
	 * $scope.newArray = compressArray($scope.array);
	 * console.log($scope.newArray);
	 */

	/*
	 * Function to find duplicates in Event list
	 */
	for (var i = 0; i < $scope.event.length; i++) {
		var arrlen = $scope.event.length;
		for (var j = 0; j < arrlen; j++) {
			if ($scope.array[i] == $scope.event[j]) {
				$scope.event = $scope.event.slice(0, j).concat(
						$scope.event.slice(j + 1, arrlen));
			}
		}
	}

	// alert("message");
	console.log($scope.event);

});
/*
 * $scope.init = function(parameter) { }
 */

// configure our routes
igConverge.config(function($routeProvider, $locationProvider) {
	$routeProvider

	// route for the home page
	.when('/', {
		templateUrl : pageContext + '/pages/dashboard.html',
		controller : 'mainController'
	})

	// route for the event page
	.when('/viewevent', {
		templateUrl : pageContext + '/pages/events.html',
		controller : 'EventsController'
	})

	// route for the holiday page
	.when('/viewholiday', {
		templateUrl : pageContext + 'pages/holiday.html',
		controller : 'holidaycontroller'
	})
	// route for the manage events page
	.when('/manageevent', {
		templateUrl : 'pages/managevents.html',
		controller : 'holidaycontroller'
	})
	// route for the manage holiday page
	.when('/manageholiday', {
		templateUrl : 'pages/holiday.html',
		controller : 'holidaycontroller'
	})
	// route for the managemenu page
	.when('/managemenu', {
		templateUrl : 'pages/managemenu.html',
		controller : 'holidaycontroller'
	});

	// route for the manage event page

});

// create the controller and inject Angular's $scope
igConverge.controller('mainController', function($scope) {
	// create a message to display in our view
	$scope.user = 'false';

	console.log("this is  main controller");

});

igConverge.controller('EventsController', function($scope) {

});

igConverge.controller('holidaycontroller', function($scope) {

});

igConverge.filter('myDateFormat', function myDateFormat($filter) {
	return function(text) {
		var tempdate = new Date(text.replace(/-/g, "/")).getTime();
		return $filter('date')(tempdate, "MMM d, y");
	};
});

igConverge.filter('dateFormat', function myDateFormat($filter) {
	return function(text) {
		var tempdate = new Date(text.replace(/-/g, "/")).getTime();
		return $filter('date')(tempdate, "h:mm");
	};
});

igConverge.filter('dateFormat1', function myDateFormat($filter) {
	return function(text) {
		var tempdate = new Date(text.replace(/-/g, "/")).getTime();
		return $filter('date')(tempdate, "h:mm a");
	};
});
