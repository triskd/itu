package model;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	private int value;
	private List<Integer> notes;
	private int maxValue;
	private String type; // "user" || "task"
	
	public Cell(int maxValue, int value){
		this.maxValue = maxValue;
		this.value = value;
		this.type = "task";
		this.value = value;
		this.notes = null;
	}
	
	public Cell(int maxValue){
		this.maxValue  = maxValue;
		this.value = 0;
		this.type = "user";
		this.notes = new ArrayList<>();
	}
	
	/* Vlozi do bunky novou hodnotu
	 * 
	 * 
	 * */
	public int insert(String type, int value){
		
		if(this.type.equals("task")){
			return -1;
		}
		
		if(type.equals("value")){
			
			if(value < 1 || value > this.maxValue){
				return -1;
			}
			
			this.value = value;
			this.notes.clear();
			return 0;
		}
		
		if(type.equals("note")){
			
			if(value < 1 || value > this.maxValue){
				return -1;
			}
			
			if(this.notes.contains(new Integer(value))){
				return -1;
			}
			
			if(this.notes.size() >= this.maxValue){
				return -1;
			}
			
			this.notes.add(new Integer(value));
			
			return 0;
		}
		
		return -1;
	}
	
	public boolean clear(){
		
		if(this.type.equals("user")){
			this.value = 0;
			this.notes.clear();	
			return true;
		}
		return false;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public int getMaxValue(){
		return this.maxValue;
	}
	
	public int[] getNotes(){
		int[] arr = new int[this.notes.size()];
		return arr;
	}
	
	public String getType(){
		return this.type;
	}
	
	public static void main(String[] args){
		
		Cell cell = new Cell(9);
		
		System.out.println(cell.insert("note", 0));
		System.out.println(cell.insert("note", 1));
//		System.out.println(cell.insert("note", 2));
//		System.out.println(cell.insert("note", 3));
//		System.out.println(cell.insert("note", 4));
//		System.out.println(cell.insert("note", 5));
//		System.out.println(cell.insert("note", 6));
//		System.out.println(cell.insert("note", 7));
//		System.out.println(cell.insert("note", 8));
//		System.out.println(cell.insert("note", 9));
		System.out.println(cell.insert("note", 15));
	}
	
}
