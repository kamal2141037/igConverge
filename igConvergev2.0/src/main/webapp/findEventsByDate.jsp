<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Events By Dates.</title>
</head>
<body>
<h3>Enter Start Date and End Date</h3><br>
<form action="user/findeventsbydate">
Event Start Date:
<input type="text" name="eventStartTime"><br>
Event End Date:
<input type="text" name="eventEndTime"><br>
<input type="submit" value="Find Events">
</form>
</body>
</html>