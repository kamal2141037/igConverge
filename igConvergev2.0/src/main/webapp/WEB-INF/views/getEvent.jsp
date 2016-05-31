<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Event</title>
<!--Bootstrap API from CDN-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv.js"></script>
		<![endif]-->






<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js">
</script>
<script type="text/javascript">
	var app = angular.module("myeventsApp", []);
	app.controller("EventsController", function($scope, $http) 
	{
		var url = "/igConverge/user/fetchevents";
		
		$http.get(url).success(function(data) 
	{
			
			$scope.e = data;
			alert("message");
		});

	});
</script>

<style>
body {
	font-family: verdana;
}

.app_header {
	background-image: url("igconverge_header.jpg");
	height: 200px;
}

.image1 {
	background-image: url("igconverge_header.jpg");
	height: 200px;
	width: 200px;
}

.fonts {
	font-size: 13px;
}

.navigation {
	margin: auto;
	margin-top: 20px;
}

#home {
	position: relative;
	top: 40px;
	left: 487px;
}

#btn {
	position: relative;
	top: 40px;
	left: 500px;
}

h3 {
	padding: 0px;
	margin: 0px;
}

.app_container {
	
}

.content {
	margin: 10%;
	margin-top: -5%;
	margin-bottom: 0;
	background-color: white;
	box-shadow: 0px 0px 4px #ccc;
	padding: 5px;
}

h3 {
	padding: 15px;
}

.first {
	margin-left: 15px;
	margin-right: 15px;
	margin-top: 10px;
}

h5 {
	color: green;
}

li {
	margin-top: 5px;
	background-color: gray;
	border: 0px;
	border-radius: 8px;
}

.list-group-item {
	background-color: lightGray;
	margin-top: 10px;
}

ul {
	margin: 15px;
}
</style>

</head>
<body ng-app="myeventsApp">
	<div ng-controller="EventsController" class="container">

		<header>
		<div class="row app_header">
			<div class="row navigation">
				<a href="#"><span id="home" class="glyphicon glyphicon-home"></span></a>
				<button type="button" id="btn" class="btn btn-default">Login</button>
				<div class="col-md-3 col-md-offset-1">
					<h3>igConverge</h3>
				</div>
			</div>
		</div>
		</header>



		<div class="row app_container">
			<div class="row content">
				<h3>
					INFOGAIN <strong>EVENTS</strong><small> for next seven days</small>
				</h3>
				<ul class="list-group">
					<h4>
						<font color="lightGreen">&nbsp;&nbsp;&nbsp;11 June 2015</font>
					</h4>
					<li class="list-group-item" ng-repeat="i in event">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">{{i.eventStartTime}}</span>
							</div>
							<div class="col-md-10">
								<div class="row">
									<strong>DOCTOR VISIT - AYURVEDA</strong>
								</div>
								<div class="row">Doctor Room,,Infogain</div>
							</div>
							</div>
					</li>
					<li class="list-group-item">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">10:00 - 11:00 AM</span>
							</div>
							<div class="col-md-10">


								<div class="row">
									<strong>DOCTOR VISIT-AYURVEDA</strong><br> Doctor
									Room,Infogain
								</div>
								<div class="row">
									<div class="col-md-4">
										<div class="image1" style="margin-left: -16px;"></div>
									</div>
									<div class="col-md-8">
										<br> <br> Lorem Ispum is simply dummy text of the
										printing and typesetting industry Lorem Ispum has been the
										industry's standard dummy text ever since the 1500's, when an
										unknown printer took a galley of type and scrambled it to make
										a type specimen book.

									</div>

								</div>
							</div>
						</div>
					</li>

					<li class="list-group-item">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">10:00 - 11:00 AM</span>
							</div>
							<div class="col-md-10">
								<div class="row">
									<strong>DOCTOR VISIT - AYURVEDA</strong>
								</div>
								<div class="row">Doctor Room,Infogain</div>
							</div>
					</li>
				</ul>

				<br>

				<ul class="list-group">
					<h4>
						<font color="lightGreen">&nbsp;&nbsp;&nbsp;12 June 2015</font>
					</h4>
					<li class="list-group-item">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">10:00 - 11:00 AM</span>
							</div>
							<div class="col-md-10">
								<div class="row">
									<strong>DOCTOR VISIT - AYURVEDA</strong>
								</div>
								<div class="row">Doctor Room,Infogain</div>
							</div>

						</div>
					</li>

					<li class="list-group-item">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">10:00 - 11:00 AM</span>
							</div>
							<div class="col-md-10">
								<div class="row">
									<strong>DOCTOR VISIT - AYURVEDA</strong>
								</div>
								<div class="row">Doctor Room,Infogain</div>
								<br>
								<div class="row">Lorem Ispum is simply dummy text of the
									printing and typesetting industry</div>
								<div class="row">Lorem Ispum has been the industry's
									standard dummy text ever since the</div>
								<div class="row">1500's, when an unknown printer took a
									galley of type and scrambled it to</div>
								<div class="row">make a type specimen book.</div>

							</div>

						</div>
					</li>


					<li class="list-group-item">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">10:00 - 11:00 AM</span>
							</div>
							<div class="col-md-10">
								<div class="row">
									<strong>DOCTOR VISIT - AYURVEDA</strong>
								</div>
								<div class="row">Doctor Room,Infogain</div>
							</div>
						</div>
					</li>
					<li class="list-group-item">
						<div class="row">
							<div class="col-md-2">
								<span class="fonts">10:00 - 11:00 AM</span>
							</div>
							<div class="col-md-10">
								<div class="row">
									<strong>DOCTOR VISIT - AYURVEDA</strong>
								</div>
								<div class="row">Doctor Room,Infogain</div>
							</div>
						</div>
					</li>
				</ul>

			</div>
			<h1>Events</h1>
			<hr />
			<ul>
				<li ng-repeat="i in e"><span>{{i.eventName}}</span> <span>{{i.eventStartTime}}</span>
					<span>{{i.eventEndTime}}</span></li>
			</ul>


		</div>
		<footer>
		<div class="row">
			<hr>
			Copyright 2015. All rights reserved by Infogain.com
		</div>
		</footer>
	</div>







</body>
</html>