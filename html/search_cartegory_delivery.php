<?php
	include("tojson.php");
	
	$c_name = $_GET["category"]
	$delivery = $_GET["delivery"];

	$db_host= "localhost";
	$db_user= "cs20121092";
	$db_pw  = "cs20121092";
	$db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);

	if($c_name == '-1' and $delivery == '-1'){
		$q=mysqli_query($conn,"select * from restaurant natural join
								(select Rest_Name, sum(Score)/sum(People) as Score, sum(People) as People
								 from review group by Rest_Name)score");
	}
	elseif($c_name == '-1' and $delivery != '-1'){
		$q=mysqli_query($conn,"select * from restaurant natural join
								(select Rest_Name, sum(Score)/sum(People) as Score, sum(People) as People
								 from review group by Rest_Name)score
								where Delivery_Fee = '' and Delivery_Min = ''");
	
	}
	elseif($c_name != '-1' and $delivery == '-1'){
		$q=mysqli_query($conn,"select * from restaurant natural join
								(select Rest_Name, sum(Score)/sum(People) as Score, sum(People) as People
								 from review group by Rest_Name)score
								where Category = ".$c_name."");
	
	}
	else{
		$q=mysqli_query($conn,"select * from restaurant natural join
								(select Rest_Name, sum(Score)/sum(People) as Score, sum(People) as People
								 from review group by Rest_Name)score
								where Category = ".$c_name." and Delivery_Fee = '' and Delivery_Min = ''");
	
	}
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


