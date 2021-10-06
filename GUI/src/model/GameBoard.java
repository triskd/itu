package model;

import java.util.ArrayList;
import java.util.List;

import model.Server.ReturnValue;

public class GameBoard {
	private GameTask gameTask;
	private List<Cell> board;
//	private Cell activeCell;
	
	public GameBoard(GameTask gameTask){
		this.gameTask = new GameTask(gameTask);
		this.board = new ArrayList<>();
		this.createBoard();
	}
	
	private void createBoard(){
		
		for(int i : this.gameTask.getTask()){
			Cell cell;
			if(i != 0){
				cell = new Cell(this.gameTask.getType(), i);
				this.board.add(cell);
			}else{
				cell = new Cell(this.gameTask.getType());
				this.board.add(cell);
			}
		}
	
	}
	
	public List<Cell> getCells(){
		return this.board;
	}
	
	public void saveState(){
        
        this.gameTask.clearUserSolution();		
    
		int i = 0;
		for(Cell cell : this.board){
			this.gameTask.addToUserSolution(i, cell.getValue());
			i++;
		}
		
//		System.out.println(java.util.Arrays.toString(this.gameTask.getUserSolution()));
	}
	
	public void print(){
		int i = 0;
		
		for(Cell cell : this.board){
			
			if(i % 9 == 0){
				System.out.println("");
			}
			
			System.out.print(cell.getValue() + " ");
			i++;	
		
		}
		
		System.out.println("");
	}
	
	public GameTask getGameTask(){
		return this.gameTask;
	}
	
	public static void main(String[] args){
		
		Server server = new Server("https://www.stud.fit.vutbr.cz/~xtrisk05/itu_proj/");
		ReturnValue ret = server.game.getTask(9, 0);
		GameBoard board = new GameBoard(ret.getGameTaskValue());
		
		
		
		int i = 0;
		for(Cell cell : board.board){
			
			if(i % 9 == 0){
				System.out.println("");
			}
			System.out.print(cell.getValue() + " ");
			i++;
			
			
		}
		
		System.out.println("");
		
		i = 0;
		for(Cell cell : board.board){
		
			cell.insert("value", 2);
			if(i % 9 == 0){
				System.out.println("");
			}
			System.out.print(cell.getValue() + " ");
			i++;
			
			
		}
		
		board.saveState();
		
	}
	
}
