<%@page import="com.infogain.igconverge.model.Event"%>
<%@page import="java.util.List"%>
<%@page import="com.infogain.igconverge.controller.AdminController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
 <h4>Choose Event to update</h4>
 
 
     <form action="updateEvent" method="post">
         <label><input type="radio" name="eventName" value="wwf">wwf</label> 
        <label><input type="radio" name="eventName" value="Holi Celebrations">Holi Celebrations</label>

        <input type="text" name="eventDate" value="eventDate"/>
    
    <p><input type="submit" value="Choose One"></p>
    </form>
</body>
</html>