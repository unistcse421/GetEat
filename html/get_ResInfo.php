<?php
	include("tojson.php");
	
	$db_host= "localhost";
        $db_user= "cs20121092";
        $db_pw  = "cs20121092";
        $db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$q=mysqli_query($conn,"select * from restaurant natural join
							(select Rest_Name, round(sum(Score)/sum(People),1) as Score, sum(People) as People 
							from review group by Rest_Name)score");
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	print(json_encode($output_info));
	mysqli_close($localhost);
?>


