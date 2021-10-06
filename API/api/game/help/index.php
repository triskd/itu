<?php 
	
	require_once("../games.php");

	if(isset($_POST['data'])){

		$param = $_POST['data'];
		$req = json_decode($param,true);

	
		if(isset($req['cell']) && isset($req['taskId']) && isset($req['type']) && isset($req['difficulty']) ) {


			if(isset($gameTask[$req['type']])){

				if( isset($gameTask[$req['type']][$req['difficulty']]) ){

					if( isset( $gameTask[$req['type']][$req['difficulty']][$req['taskId']]['reseni'][$req['cell']] ) ){

						$ret['status'] = 'ok';
						$ret['response'] = "help, value of cell";
						$ret['cellValue'] = $gameTask[$req['type']][$req['difficulty']][$req['taskId']]['reseni'][$req['cell']];

						echo json_encode($ret);
						return;

					}else{

						$ret['status'] = 'ko';
						$ret['response'] = "cell out of range";


						echo json_encode($ret);
						return;
						
					}
			

				}else{
	
					$ret['status'] = 'ko';
					$ret['response'] = "unkonown type of difficulty";


					echo json_encode($ret);
					return;
	

				}


			}else{

				$ret['status'] = 'ko';
				$ret['response'] = "unkonown type of game";

				echo json_encode($ret);
				return;
			}
			


		}else{

			$ret['status'] = 'ko';
			$ret['response'] = 'value of cell or taskId or type or difficulty not set';

			echo json_encode($ret);
			return;
		}

	}

	$ret['status'] = 'ko';
	$ret['response'] = "wrong POST varible, please use POST varibale data ";

	echo json_encode($ret);
	return;

 ?>