<?php
	include("tojson.php");
	
	$r_id = $_POST['rest_id'];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$query = "select Party_Name, Name, Rest_Name, Category, Menu_Name, Quantity, Price from menu natural join
		(select Party_Name, Name, Menu_ID, Quantity from users,
	 	(select * from ordered natural join
	  	(select max(Date) as Date, ordered.Leader_ID from ordered, party 
		where ordered.Leader_ID = party.Leader_ID and User_ID = '$r_id')m)o
	 	where o.Leader_ID = users.User_ID)u";
	$q=mysqli_query($conn,$query);
	
	// (Rest_Name, Menu_ID, Menu_Name, Category, Price, Frequency)
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


