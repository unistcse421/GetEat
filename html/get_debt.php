
<?php
	include("tojson.php");

	$p_name = $_GET['p_name'];
	$u_id	= $_GET['u_id'];
	$lu_id	= $_GET['lu_id'];
/*
	$p_name = "geteat";
	$u_id	= "A00001";
	$lu_id	= "A00001";
*/
	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	if($lu_id == $u_id){
		$query = "select Party_Name, Leader_ID, User_ID, Location, -Price as Price, Name, Phone, Account, Bank
				  from party natural join users 
				  where Party_Name = '$p_name' and not User_ID = '$u_id'";
	}
	else{
		$query = "select Party_Name, Leader_ID, User_ID, Location, Price, Name, Phone, Account, Bank
				  from party natural join users
				  where Party_Name = '$p_name' and not User_ID = '$lu_id' and Leader_ID = User_ID";
	}
	$q=mysqli_query($conn, $query);
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


