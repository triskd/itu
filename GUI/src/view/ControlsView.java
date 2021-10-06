package view;

import java.util.ArrayList;
import java.util.List;

import view.BoardView.CellView;

import model.GameBoard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ControlsView {
	private Button checkButton;
	private Button saveButton;
	private Button penButton;
	private Button pencilButton;
	private Button helpButton;
	private Button rubberButton;
	private Button menuButton;
	private GridPane grid;
	private String tool;
	private List<Button> numbers;

	public ControlsView(GameBoard gameBoard){
		this.grid = new GridPane();
		this.numbers = new ArrayList<>();
		
		
		this.saveButton = new Button("save");
		this.penButton = new Button("pen");
		this.pencilButton = new Button("pencil");
		this.checkButton = new Button("check"); this.checkButton.setStyle("-fx-pref-width: 200px; -fx-font-weight: bold; -fx-font-size:20;");
		this.helpButton = new Button("help");  this.helpButton.setStyle("-fx-pref-width: 200px; -fx-font-weight: bold; -fx-font-size:20;");
		this.rubberButton = new Button("rubber");  this.rubberButton.setStyle("-fx-pref-width: 200px; -fx-font-weight: bold; -fx-font-size:20;"); 
		this.menuButton = new Button("back to menu");  this.menuButton.setStyle("-fx-pref-width: 200px; -fx-font-weight: bold; -fx-font-size:20;");
		
		int buttons = 0;
		for(int i = 0; i < Math.sqrt(gameBoard.getCells().size()); i++){
			this.numbers.add(new Button(String.valueOf(i)));
			buttons++;
		}
		
		//
		//this.grid.add(this.penButton, 0, 0);
		//this.grid.add(this.pencilButton, 1, 0);7
		this.grid.add(this.rubberButton, 0, 0);
		this.grid.add(this.helpButton, 0, 1);
		
		this.grid.add(this.checkButton, 0, 2);
		this.grid.add(this.menuButton, 0, 5);
	
		this.grid.setVgap(15);
		
	}
	
	
	
	public GridPane getGrid(){
		return this.grid;
	}
	
	public Button getHelpButton(){
		return this.helpButton;
	}
	
	public Button getCheckButton(){
		return this.checkButton;
	}
	
	public Button getMenuButton(){
		return this.menuButton;
	}
	
	public Button getRubberButton(){
		return this.rubberButton;
	}
}
