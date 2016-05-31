var app = angular.module('igConverge', []);
app.controller('feedbackController', function($scope,$http) {
	
	var score=0;
	//---------------------------------------------------------
	$scope.addFeedback=function() {
		var feedbackComment = document.getElementById("feedbackComment").value;

		var ratingScore = document.getElementsByName('group-2');

		alert(feedbackComment);
		for (var i = 0, length = ratingScore.length; i < length; i++) {
			if (ratingScore[i].checked) {
				/*alert(ratingScore[i].value + feedbackComment);*/
				 score = ratingScore[i].value;
				 score=parseInt(score);
				break;
			}// if
		}// for

		var mealId = "558d53233920d45825eef69f";
		var empId = "2150190";
		var empName = "Mayank";
		
		var params= 
		{
			"id":mealId,
			"employee.id":empId,
			"employee.name":empName,
			"ratings.comment":feedbackComment,
			"ratings.score":score			
		};
		
		alert(params);
		
		var req = {
				method : 'POST',
				url : 'http://172.18.84.49:8080/igConverge/user/savefeedbackviaportal',
				headers : {
				'Content-Type' : "application/json ;charset=utf-8"
				},
				params : params
				};
		console.log(params);
		
		$http(req).success(function(data) {
			console.log(data);

			});
		

	};
	
});// feedback method end.
