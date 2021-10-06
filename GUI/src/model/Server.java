package model;

import java.text.ParseException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Server {
	
	private String serverUrl;
	public Game game;
	public HTTP req;
	
	public Server(String server){
		
		this.serverUrl = server;
		this.game = new Game();
		this.req = new HTTP();
		
	}
	
	public String getUrl(){
		return this.serverUrl;
	}
	
	public static void main(String args[]) throws Exception{
		
		Server server = new Server("https://www.stud.fit.vutbr.cz/~xtrisk05/itu_proj/");
		
		ReturnValue task = server.game.getTask(9, 0);
		//GameTask game = task.getGameTaskValue();
//		ReturnValue help = server.game.help(79, task.getGameTaskValue());
//		ReturnValue check = server.game.check(task.getGameTaskValue());
		
		
		//System.out.println(task);
		//System.out.println(help);
//		System.out.println(check);
//		System.out.println(task.getGameTaskValue().addToUserSolution(0, 2));
		System.out.println(task);
	
	}
		
	/*	Datovy typ pro navratovou hodnotu 
	 * 	@param int retCode - navratovy kod funkce
	 * 	@param int/String/boolean value - hodnota vracena funkci
	 */
	public class ReturnValue{
			
		private int retCode;
		private boolean boolValue;
		private int intValue;
		private String strValue;
		private String set;
		private int[] intArr;
		private GameTask gameTaskValue;
		
		public ReturnValue(int retCode, boolean value){
			this.retCode = retCode;
			this.boolValue = value;
			this.set = "bool";
		}
		
		public ReturnValue(int retCode, int value){
			this.retCode = retCode;
			this.intValue = value;
			this.set = "int";
		}
		
		public ReturnValue(int retCode, String value){
			this.retCode = retCode;
			this.strValue = value;
			this.set = "string";
		}
		
		public ReturnValue(int retCode, int[] arr){
			this.retCode = retCode;
			this.intArr = arr;
			this.set = "intArr";
		}
		
		public ReturnValue(int retCode, GameTask game){
			this.retCode = retCode;
			this.gameTaskValue = new GameTask(game);
			this.set = "gameTask";
		}
		
		public int getRetCode(){
			return this.retCode;
		}
		
		public boolean getBoolValue(){
			return this.boolValue;
		}
		
		public String getStrValue(){
			return this.strValue;
		}
		
		public int getIntValue(){
			return this.intValue;
		}
		
		public int[] getIntArrVaue(){
			int[] ret = this.intArr.clone();
			return ret;
		}
		
		public GameTask getGameTaskValue(){
			return this.gameTaskValue;
		}
		
		public String getType(){
			return this.set;
		}
		
		@Override
		public String toString(){
			
			if(this.set.equals("bool")){
				return "retCode: " + String.valueOf(this.retCode) + ", hodonota: " + Boolean.toString(this.boolValue); 
			}
			
			if(this.set.equals("int")){
				return "retCode: " + String.valueOf(this.retCode) + ", hodonota: " + String.valueOf(this.intValue);
			}
			
			if(this.set.equals("string")){
				return "retCode: " + String.valueOf(this.retCode) + ", hodonota: " + this.strValue;
			}
			
			if(this.set.equals("intArr")){
				String ret = "retCode: " + String.valueOf(this.retCode) + ", hodnota: [";
				
				for(int i = 0; i < this.intArr.length; i++){
					
					ret += String.valueOf(this.intArr[i]);
					
					if(i < (this.intArr.length -1)){
						 ret += ", ";
					
					}else{
						ret += "]";
					}
					
				}
				
				ret += " , delka zadani: " + this.intArr.length;
				return ret;
			}
			
			if(this.set.equals("gameTask")){
				return "retCode: " + this.retCode + ", GameTask: {" + this.gameTaskValue.toString() + " }";
			}
			
			return "";
			
		}
		
	}
	
	/* Trida pro komunikaci se servrem, dotazy tykajici se hry
	 * 
	 *  
	 */
	public class Game{
		
		private String path;
		
		
		public Game(){
			
			this.path = Server.this.serverUrl + "/api/game/";
			
		}
		
		public String getUrl(){
			return this.path;
		}
		
		
		/*	Ziska ze serveru nove zadani - int[]
		 * 
		 * */
		public ReturnValue getTask(int type, int difficulty)  {
			String response = "";
			int retCode = 0;
			
			
			
			if(difficulty < 0 || difficulty > 2){
				return new ReturnValue(-1, "wrong difficulty, values 0-2 are valid");
			}
			
			if(type != 6 && type != 9 && type != 15 ){
				return new ReturnValue(-1, "wrong type, values 6, 9, 15 are valid");
			}
		
			// naplneni JSON obejktu pro dotaz na server
			JSONObject reqJson = new JSONObject();
			reqJson.put("type", type);
			reqJson.put("difficulty", difficulty);

			
			response = Server.this.req.POST(this.path+"generate/index.php", reqJson.toString());
			
			if( response.contains("REQERR") ){
				retCode = -1;
			}
						

			//zpracovani odpovedi JSON ze serveru
			try{
				Object obj = new JSONParser().parse(response);
				JSONObject jo = (JSONObject) obj;
			
				String responseStatus = (String) jo.get("status");
				String responseResponse = (String) jo.get("response");
				Long responseTaskId = (Long) jo.get("taskId");
				
				if( responseStatus.equals("ko") ){
					return new ReturnValue(-1, responseResponse);					
				}
				
				//ziskani JSONArray 
				JSONArray taskArr = (JSONArray) jo.get("task");
				int [] task = null;
				Long number;
				
				//kontrola zda v JSON existuje task
				if(taskArr != null){
					
					task = new int[taskArr.size()];
					
					for(int i = 0; i < taskArr.size(); i++){
						number = (Long) taskArr.get(i);
						task[i] = number.intValue();
					}
					
					
					return new ReturnValue(0, new GameTask(responseTaskId.intValue(), type, difficulty, task.clone() ));
				}
				
				return new ReturnValue(-1, "Could get task from json");
				
				
			}catch(org.json.simple.parser.ParseException e){
//				return new ReturnValue(-1, response);
				return new ReturnValue(-1, "not JSON received, " + response);
			}
			
			
			
		}
		

		/* Zkontroluje uzivatelem vyplenene zadani
		 * 
		 * 
		 * 
		 * **/
		public ReturnValue check(GameTask userGame){
			
			String response = "";
					
			//sestaveni JSON objektu pro dataz
			JSONObject reqJson = new JSONObject();
			reqJson.put("userSolution", java.util.Arrays.toString(userGame.getUserSolution()));
			reqJson.put("taskId", userGame.getId());
			reqJson.put("type", userGame.getType());
			reqJson.put("difficulty", userGame.getDifficulty());
			reqJson.put("userTask", java.util.Arrays.toString(userGame.getTask()));
		
			
			//odeslani pozadavku na server
			response = Server.this.req.POST(this.path+ "check/index.php", reqJson.toJSONString());
			
			
			if( response.contains("REQERR") ){
				return new ReturnValue(-1, response);
			}
			
			//zpracovani JSON objektu z odpovedi
			try{
				JSONObject responseJson = (JSONObject) new JSONParser().parse(response);
				String responseStatus = (String) responseJson.get("status");
				String responseCorrect = (String) responseJson.get("correct");
				String responseResponse = (String) responseJson.get("response");
				String responseRequest = (String) responseJson.get("request");
				
				if(responseStatus.equals("ko")){
					return new ReturnValue(-1, responseResponse);
				}
				
				if(responseCorrect.equals("ok")){
					return new ReturnValue(0, true);
				}
				
				return new ReturnValue(0, false);
			
			}catch(org.json.simple.parser.ParseException e){
				return new ReturnValue(-1, "not JSON received: " + response);
			}
			
		}
	
		/* Ziska napovedu, hodna bunky cell
		 * 
		 * */
		public ReturnValue help(int cell, GameTask userGame){
			
			String response = "";
			int retCode = 0;
			
			
			if(cell < 0 || cell > userGame.getGameBoardSize()){
				System.out.println(userGame.getGameBoardSize());
				return new ReturnValue(-1, "cell number from wrong interval");
			}
			
			//sestaveni JSON objectu na dotaz
			JSONObject reqJson = new JSONObject();
			reqJson.put("cell", cell);
			reqJson.put("taskId", userGame.getId());
			reqJson.put("type", userGame.getType());
			reqJson.put("difficulty", userGame.getDifficulty());
			
			
			//odeslani pozadavku na server
			response = Server.this.req.POST(this.path + "help/index.php", reqJson.toString());
			
			if( response.contains("REQERR") ){
				return new ReturnValue(-1, 0);
			}
			
			//zpracovani JSON odpovedi ze serveru
			try{
				
				Object obj = new JSONParser().parse(response);
				JSONObject ojResponse = (JSONObject) obj;
				
				String responseStatus = (String) ojResponse.get("status");
				String responseResponse = (String) ojResponse.get("response");
				Long responseCellValue = (Long) ojResponse.get("cellValue");
				
				if(responseStatus.equals("ko")){
					return new ReturnValue(-1, responseResponse);
				}
				
				return new ReturnValue(0, responseCellValue.intValue());
				
				
				
			}catch(org.json.simple.parser.ParseException e){
				return new ReturnValue(-1, "not JSON received: " + response);
			}
//			return new ReturnValue(-1,  response);
			
		}
		
		
		
	}
	
	/* Trida pro komunikaci se servrem, dotazy tykajici se uzivatel
	 * 
	 */
	class User{
		
		private String path;
		private boolean loggedIn;
		private String userId;
		
		public User(){
			this.path = Server.this.serverUrl + "/api/user/";
			this.loggedIn = false;
			this.userId = "";
		}
		
		public ReturnValue create(String login, String password, String mail){
			
		
			return new ReturnValue(0, "user created");
		}
		
		public ReturnValue auth(String login, String password){
			
		
			return new ReturnValue(0, "user logged in");
		}
		
	
	}
	
	/* Trida pro komunikaci se serverem, dotazy tykajici statistik
	 * 
	 * */
	class Statistic{
		
		private String path;
		
		Statistic(){
			this.path = Server.this.serverUrl + "/api/statistic/";
		}
		
		public ReturnValue addStatistic(String date, int time, int helps){
			
			
			return new ReturnValue(0, "statistic added");
		}
		
		public ReturnValue getAll(){
			
			return new ReturnValue(0, "all statistics returned");
		}
		
		public ReturnValue getUsers(){
			
			return new ReturnValue(0, "users statistic");
		}
		
		
	}
	
	

}
