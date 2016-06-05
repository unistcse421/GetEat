<?php
	include("tojson.php");
	
	$c_name = $_GET["category"]
	$delivery = $_GET["delivery"];
	$desc	= $_GET["desc"];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	$query = "select * from restaurant natural join
				(select Rest_Name, sum(Score)/sum(People) as Score, sum(People) as People
	 			from review group by Rest_Name)score"

	if($desc != '-1')
		$query = $query." order by Score desc";

	if($c_name != '-1'){
		$query = $query." where Category = ".$c_name;
		if($delivery != '-1'){
			$query = $query." and Delivery_Fee = '' and Delivery_Min = ''";
		}
	}
	else{
		if($delivery != '-1'){
			$query = $query." where Delivery_Fee = '' and Delivery_Min = ''";
		}
	}
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


