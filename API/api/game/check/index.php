<?php 

	require_once("../games.php");

	if(isset($_POST['data'])){
		
		$param = $_POST['data'];
		$req = json_decode($param,true);

		$req['userTask'] =  json_decode( $req['userTask'] , true);
		$req['userSolution'] = json_decode( $req['userSolution'] , true);;

		if( isset($req['type']) && isset($req['difficulty']) && isset($req['taskId']) && isset($req['userSolution'])  && isset($req['userTask']) ){


				if(isset($gameTask[$req['type']])){

					if( isset($gameTask[$req['type']][$req['difficulty']]) ){

						if( isset( $gameTask[$req['type']][$req['difficulty']][$req['taskId']]['reseni'] ) ){

							$ret['status'] = 'ok';
							
							if( $gameTask[$req['type']][$req['difficulty']][$req['taskId']]['reseni'] === $req['userSolution'] ){
								$ret['correct'] = 'ok';
							}else{
								$ret['correct'] = 'ko';
								
							}

							echo json_encode($ret);
							return;

						}else{

							$ret['status'] = 'ko';
							$ret['response'] = "unkonown taskId";


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
			$ret['response'] = "value of call or type or difficulty or taskId or userSolution not set";
			$ret['request'] = $_POST['data'];

			echo json_encode($ret);
			return;
		}


	}

	$ret['status'] = 'ko';
	$ret['response'] = "wrong POST varible, please use POST varibale data ";

	echo json_encode($ret);
	return;

 ?>