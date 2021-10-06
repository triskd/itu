package model;

public class GameTask {

	private int[] task;
	private int[] userSolution;
	private int type;
	private int difficulty;
	private int id;
	private int helps;
	private int gameBoardSize;
	
	GameTask(int id, int type, int difficulty, int[] task){
		this.id = id;
		this.type = type;
		this.difficulty = difficulty;
		this.task = task.clone();
		this.helps = 0;
		this.gameBoardSize = type*type-1;
		this.userSolution = task.clone();
	}
	
	GameTask(GameTask game){
		this.id = game.getId();
		this.type = game.getType();
		this.difficulty = game.getDifficulty();
		this.helps = game.getHelps();
		this.task = game.getTask();
		this.gameBoardSize = type*type -1;
		this.userSolution = task.clone();
	}
	
	public boolean addToUserSolution(int cell, int value){
		
		if(value < 1 || value > this.type){
			return false;
		}
		
		if(cell < 0 || cell > this.gameBoardSize){
			return false;
		}
		
		if(this.task[cell] != 0){
			return false;
		}
		
		this.userSolution[cell] = value;
		
		return true;
	}
	
	public int getType(){
		return this.type;
	}
	
	public int getDifficulty(){
		return this.difficulty;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getHelps(){
		return this.helps;
	}
	
	public void incrementHelps(){
		this.helps++;
	}
	
	public int getGameBoardSize(){
		return this.gameBoardSize;
	}
	
	public int[] getTask(){
		
		return this.task.clone();
		
	}

    public void clearUserSolution(){
        this.userSolution = this.task.clone();
    }    
	
	public int[] getUserSolution(){
		return this.userSolution;
	}
	
	@Override
	public String toString(){
		String ret = "taskId: " + this.id + ", task type: " + this.type + ", difficulty: " + this.difficulty + ", helps: " + this.helps + ", task: [";
		
		//vypsani tasku
		for(int i = 0; i < this.task.length; i++){
			
			ret += String.valueOf(this.task[i]);
			
			if(i < (this.task.length -1)){
				 ret += ", ";
			
			}else{
				ret += "]";
			}
			
		}
		
		ret += ", userSolution: [";
		
		//vypsani uzivalskeho reseni
		for(int i = 0; i < this.task.length; i++){
			
			ret += String.valueOf(this.userSolution[i]);
			
			if(i < (this.task.length -1)){
				 ret += ", ";
			
			}else{
				ret += "]";
			}
			
		}
		
		return ret;
	
	}
	
}
