<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	  <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	
</head>
<body  bgcolor="#136a8a">
<form action="saveitem" method="Post">
Menu Name <input type="text" name="menuName"><br><br>
Item Name	<input type="text" name="itemName"><br>	
Item Price: <input type="text" name="itemPrice"><br>
		<input type="submit"/>
	</form>

<a href="GetItems.jsp">View the Items</a>
	<script type="text/javascript">
	$(document).ready(function(){
		$("form input:text").mouseover(function(){
			$(this).focus().css('background','pink');
		});
		$("form input:text").mouseleave(function(){
			$(this).css('background','white');
		});
	});
	</script>
</body>
</html>
