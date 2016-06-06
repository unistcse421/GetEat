
<?php

	include("tojson.php");
	// get party name, id of user who ordered and menu id
	/*
	$now	= time();
	$date	= date('Y-m-d H:i:s', $now);
	$p_name = $_POST['p_name'];
	$lu_id	= $_POST['lu_id'];
	$r_name	= $_POST['r_name'];
	$m_id	= $_POST['m_id'];		// array
	$q		= $_POST['quantity'];	// array
	$p		= $_POST['price'];		// = price/person
	*/
	$now	= time();
	$date	= date('Y-m-d H:i:s', $now);
	$p_name = $_POST['p_name'];
	$lu_id	= $_POST['lu_id'];
	$r_name	= $_POST['r_name'];
	$p		= $_POST['price'];		// = price/person

	$input	= $_SERVER['QUERY_STRING'];		
	$vars	= array();
	foreach(explode('&',$input) as $pair){
		list($key,$value) = explode('=',$pair);
		$key = urldecode($key);
		$value = urldecode($value);
		$vars[$key][] = $value;
	}

	$m_id	= $vars['m_id'];
	$q		= $vars['quantity'];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);

	// update party table
	$query1 = "update party set Price = '$p' where Party_Name = '$p_name' and Leader_ID = '$lu_id'";
	mysqli_query($conn, $query1);

	// insert values in ordered table
	// when we get the multiple menus it should be executed recursively
	for($i = 0; $i < count($m_id); $i++){
		$o_id = (string)$now.$m_id[$i];
		$query2 = "insert into ordered values('$o_id','$date','$p_name','$lu_id','$r_name','$m_id[$i]','$q[$i]')";	
		mysqli_query($conn, $query2);
	
	}
	
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	/*
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	
	print(json_encode($output_info));
	*/
	mysqli_close($localhost);
?>


