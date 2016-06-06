<?php
	include("tojson.php");

	$u_id = $_POST['u_id'];
	//$u_id = $_GET['u_id'];
	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$q=mysqli_query($conn,"select * from party natural join
							(select User_ID as Leader_ID, Name from users)u where User_ID = '$u_id'");
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


