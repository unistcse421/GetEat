<?php
	$db_host= "localhost";
        $db_user= "cs20121092";
        $db_pw  = "cs20121092";
        $db_name= "GetEat";
	$conn = mysqli_connect($db_host,$db_user,$db_pw,$db_name);
	
	$q=mysqli_query($conn,"select * from restaurant natural join
							(select Rest_Name, sum(Score)/sum(People) as Score, sum(People) as People 
							from review group by Rest_Name)score");
	
	//retrieve restaurant name, cuisine from table rest_info, and number from rest_line
	/*
	while($row = mysqli_fetch_assoc($q)){
		$output_info[]=$row;
	}
	*/
	$fields = mysqli_num_fields($q);
	echo "<table>";
	while($row = mysqli_fetch_row($q)){
		echo"<tr>";
		for($i = 0; $i<=$fields; $i++){
			echo "<td>".$row[$i]."</td>";
		}
		echo "</tr>";
	};

	echo "</table>";
	//print(json_encode($output_info));
	mysqli_close($localhost);
?>


