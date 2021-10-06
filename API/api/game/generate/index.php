<?php 
	
	require_once("../games.php");

	if(isset($_POST['data']) ){

		$param = $_POST['data'];
		$req = json_decode($param,true);


		if(isset($req['type']) && isset($req['difficulty']) ){

			//kontrola rozsahu hraci plochy zadani
			if($req['type'] != 6 && $req['type'] != 9 && $req['type'] != 15 ){

				$ret['status'] = 'ko';
				$ret['response'] = 'wrong game type, valid 6/9/15';
				echo json_encode($ret);
				return;

			}

			//kontorla rozsahul obtiznosti hry
			if($req['difficulty'] < 0 || $req['difficulty']  > 2){
				$ret['status'] = 'ko';
				$ret['response'] = 'wrong game difficulty, valid 0/1/2';
				echo json_encode($ret);
				return;
			}

			
			$number = rand(0, count($gameTask[$req['type']][$req['difficulty']]) -1);

			$ret['status'] = 'ok';
			$ret['response'] = 'new task';
			$ret['task'] = $gameTask[$req['type']][$req['difficulty']][$number]['zadani'];
			$ret['taskId'] = $number;
			echo json_encode($ret);
			return;
			
			
			

		}else{

			$ret['status'] = 'ko';
			$ret['response'] = 'type or difficulty of game not set';
			echo json_encode($ret);
			return;
		}

	}

	$ret['status'] = 'ko';
	$ret['response'] = "wrong POST varible, please use POST varibale data ";

	echo json_encode($ret);
	return;

 ?>