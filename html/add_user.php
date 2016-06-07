<?php
	include("tojson.php");
	$u_id =	$_GET['id'];
	$u_name = $_GET['name'];
	$phone = $_GET['phone'];
	$account = $_GET['account'];
	$bank = $_GET['bank'];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	//$query = "insert into users values(".$u_id.",".$u_name.",",$phone.",".$account.",".$bank.")";
	$q=mysqli_query($conn,"insert into users values('$u_id','$u_name','$phone','$account','$bank')");

	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


