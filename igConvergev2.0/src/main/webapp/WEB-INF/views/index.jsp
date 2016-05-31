<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="igConverge">
<head>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">



<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>


<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
</script> -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>

<link href="<c:url value="/resources/css/holidayStyle.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/rating.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/dashboardStyle1.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/dashboardStyle.css" />"
	rel="stylesheet">


<script type="text/javascript">
	var pageContext = "${pageContext.request.contextPath}";
</script>
<script src="<c:url value="/resources/js/script.js" />"></script>
<script src="<c:url value="/resources/js/SavingFeedback.js" />"></script>


</head>
<body ng-app="igConverge">

	<div id="main" ng-controller="mainController">
		<div ng-include src="'${pageContext.request.contextPath}/pages/header.html'"></div>
		<!-- <div ng-view></div> -->
		<!-- angular templating -->
		<!-- this is where content will be injected -->
		<div ng-include src="'${pageContext.request.contextPath}/pages/footer.html'"></div>
	</div>
</body>
</html>
