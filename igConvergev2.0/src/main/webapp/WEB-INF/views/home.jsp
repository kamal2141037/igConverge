<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Category</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>

</head>
<body>
 <h4>Choose One</h4>
    <form action="updatefeedback" method="post">
         <label><input type="radio" name="mealName" value="breakfast">Breakfast</label> 
        <label><input type="radio" name="mealName" value="lunch">Lunch</label>
        <label><input type="radio" name="mealName" value="dinner">Dinner</label>

        <input type="text" name="mealDate" value="mealDate"/>
    
    <p><input type="submit" value="Choose One"></p>
    </form>
   <!--  <script type="text/javascript">
    $(document).ready(function(){
        $("input[type='button']").click(function(){
        	var mealName = $("input[name='mealName']:checked").val();
        	var data={"mealName":mealName};
        	
                alert("Your Selected" +mealName);
      
            
            $.ajax({
				type:"post",
				url:"saveFeedback",
				contentType:"application/json",
				data:JSON.stringify(data),
				
				success: function()
				{
					alert("Meal Saved");
				},
				error:function(){
					alert("data");
				}
					
			});
        });
        
    });
</script> -->
</body>
</html>