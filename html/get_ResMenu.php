<?php
	include("tojson.php");
	
	$r_id = $_POST['rest_id'];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$query = "select * from menu where Rest_ID = ".$r_id;
	$q=mysqli_query($conn,$query);
	
	// (Rest_Name, Menu_ID, Menu_Name, Category, Price, Frequency)
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


