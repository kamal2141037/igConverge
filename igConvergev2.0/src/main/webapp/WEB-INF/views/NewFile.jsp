<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save Feedback</title>
</head>
<body>

<body>
<form method="POST" enctype="multipart/form-data"
action="updatefeedback">
<input type="text" name="id">
<input type="text" name="employee.id">
<input type="text" name="employee.name">
<input type="text" name="ratings.score">
comment<input type="text" name="ratings.comment">
File to upload: <input type="file" name="mealImage"><br /> <br /> <input type="submit"
value="Upload"> Press here to upload the file!
</form>



</body>
</html>