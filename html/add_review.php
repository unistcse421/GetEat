<?php
	include("tojson.php");

	$d	 	= date('Y-m-d H:i:s', time());
	$r_name = $_POST['name'];
	$score	= $_POST['score'];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$query = "insert into review values(".$d.",".$r_name.",",$score.",1)";
	$q=mysqli_query($conn, $query);
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


