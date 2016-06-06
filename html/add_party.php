<?php
	include("tojson.php");
	
	$p_name = $_POST['name'];
	$lu_id = $_POST['leader_id'];

	$u_id =	$_POST['id'];
	$loc = $_POST['loc'];
	$price = $_POST['price'];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$query = "insert into party values(".$p_name.",".$lu_id.",",$u_id.",".$loc.",".$price.")";
	$q=mysqli_query($conn, $query);
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


