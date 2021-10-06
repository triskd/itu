package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeListener;

// import javafx.geometry.BaseBounds;
// import javafx.geometry.transform.BaseTransform;
// import javafx.jmx.MXNodeAlgorithm;
// import javafx.jmx.MXNodeAlgorithmContext;
// import javafx.sg.prism.NGNode;






import model.Cell;
import model.GameBoard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


 
public class BoardView {
	private List<CellView> cells;
	private GridPane grid;
	private GameBoard gameBoard;
	
	private int line = 0;
	private int column = 0;
	private CellView activeCell;
	
	public BoardView(GameBoard board){
		this.grid = new GridPane();
		this.cells = new ArrayList<>();
		this.gameBoard = board;

//		this.grid.setHgap(50);
//		this.grid.setVgap(50);
		this.grid.setGridLinesVisible(true);
		
		for(Cell cell : board.getCells()){
			this.cells.add(new CellView(cell));
		}
		
		int borderLine = 1;
		int borderColumn = 1;
		int column = 0;
		int line = 0;
		String style;
		for(CellView cellView : this.cells){
			
			
			this.grid.add(cellView.getButton(), column, line, 1, 1);
			
			
			
			column++;
			
			if(column % Math.sqrt(this.cells.size()) == 0){
				line++;
				column = 0;
			}
			
			//zobrazeni oddeleni "boxu" carami
			if(Math.sqrt(this.cells.size()) == 6){	//typ hry 6
			
				style = cellView.getButton().getStyle();
				if(borderColumn %3 == 0 && borderLine%2 == 0){
					cellView.getButton().setStyle(style + "-fx-border-color: black;-fx-border-width: 0 1 1 0;");
				}
				else if(borderColumn %3 == 0){
					cellView.getButton().setStyle(style + "-fx-border-color: black;-fx-border-width: 0 1 0 0;");
				}else if(borderLine %2 == 0){
					cellView.getButton().setStyle(style + " -fx-border-color: black;-fx-border-width: 0  0 1 0;");
				}
			
			}else if(Math.sqrt(this.cells.size()) == 9){	//typ hry 9
				
				style = cellView.getButton().getStyle();
				if(borderColumn %3 == 0 && borderLine%3 == 0){
					cellView.getButton().setStyle(style + "-fx-border-color: black;-fx-border-width: 0 1 1 0;");
				}
				else if(borderColumn %3 == 0){
					cellView.getButton().setStyle(style + "-fx-border-color: black;-fx-border-width: 0 1 0 0;");
				}else if(borderLine %3 == 0){
					cellView.getButton().setStyle(style + " -fx-border-color: black;-fx-border-width: 0  0 1 0;");
				}
				
			}else if(Math.sqrt(this.cells.size()) == 15){ //typ hry 15
				
				style = cellView.getButton().getStyle();
				if(borderColumn %5 == 0 && borderLine%3 == 0){
					cellView.getButton().setStyle(style + "-fx-border-color: black;-fx-border-width: 0 1 1 0;");
				}
				else if(borderColumn %5 == 0){
					cellView.getButton().setStyle(style + "-fx-border-color: black;-fx-border-width: 0 1 0 0;");
				}else if(borderLine %3 == 0){
					cellView.getButton().setStyle(style + " -fx-border-color: black;-fx-border-width: 0  0 1 0;");
				}
			}
			
			borderLine = line+1;
			borderColumn = column+1;
			
		}
		
		this.setActive(this.cells.get(0));
		
	}
	
	public GameBoard getGameBoard(){
		return this.gameBoard;
	}
	
	public GridPane getGrid(){
		return this.grid;
	}
	
	private void changeActive(String direction){
		int index = 0;
		
		this.activeCell.getButton().getStyleClass().clear();
		this.activeCell.getButton().getStyleClass().addAll("button");
		this.activeCell.clearOnePressed();
		
		if(direction.equals("UP")){
			
			if(this.line == 0){
				this.line = (int) (Math.sqrt(this.cells.size())  -1);
			}else if(this.line <= (Math.sqrt(this.cells.size())  -1)){
				this.line--;
			}
		
		}
		
		if(direction.equals("DOWN")){
			
			if(this.line == (Math.sqrt(this.cells.size()) -1) ){
				this.line = 0;
			}else if(this.line >= 0 ){
				this.line++;
			}
		}
		
		if(direction.equals("RIGHT")){
		
			if(this.column == (Math.sqrt(this.cells.size()) -1) ){
				this.column = 0;
			}else if(this.column >= 0 ){
				this.column++;
			}
			
		}
		
		if(direction.equals("LEFT")){
			
			if(this.column == 0){
				this.column = (int) (Math.sqrt(this.cells.size())  -1);
			}else if(this.column <= (Math.sqrt(this.cells.size())  -1)){
				this.column--;
			}
			
		}
		
		index = (int) (this.line*Math.sqrt(this.cells.size()) + this.column);
		this.activeCell = this.cells.get(index);
		this.activeCell.getButton().getStyleClass().add("active");
//		System.out.println(index);
		
	}
	
	private void setActive(CellView cell){
		
		if(this.activeCell != null){
			this.activeCell.getButton().getStyleClass().clear();
			this.activeCell.getButton().getStyleClass().addAll("button");		
			this.activeCell.clearOnePressed();
		}
		
		int line = 0;
		int column = 0;
		for(CellView cellI : this.cells){

			if(cellI.equals(cell)){
				this.line = line;
				this.column = column;
				break;
			}
			
			column++;
			
			if(column % Math.sqrt(this.cells.size()) == 0){
				column = 0;
				line++;
			}
			
			
		}
		
		this.activeCell = cell;
		this.activeCell.getButton().getStyleClass().add("active");
	}
	
	public CellView getActiveCellView(){
		return this.activeCell;
	}
	
	public EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {

			// System.out.println("hellos" + event.getCode());			
			
			//pohyb v hraci plose
			if (event.getCode() == KeyCode.LEFT) {
				BoardView.this.changeActive("LEFT");
				System.out.println("line: " + BoardView.this.line + ", column: " + BoardView.this.column);
			}
			
			if(event.getCode() == KeyCode.UP){
				BoardView.this.changeActive("UP");
				System.out.println("line: " + BoardView.this.line + ", column: " + BoardView.this.column);
			}
			
			if(event.getCode() == KeyCode.DOWN){
				BoardView.this.changeActive("DOWN");
				System.out.println("line: " + BoardView.this.line + ", column: " + BoardView.this.column);
			}
			
			if(event.getCode() == KeyCode.RIGHT){
				BoardView.this.changeActive("RIGHT");
				System.out.println("line: " + BoardView.this.line + ", column: " + BoardView.this.column);
			}
			
			//zadavani cisel
		
			if(event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.NUMPAD0){
				if(BoardView.this.activeCell.getOnePressed() == true && BoardView.this.activeCell.cell.getMaxValue() >= 10){
					BoardView.this.activeCell.changeValue(10);
				}
			}
			
			if(event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.NUMPAD1){
				if(BoardView.this.activeCell.getOnePressed() == true && BoardView.this.activeCell.cell.getMaxValue() >= 11){
					BoardView.this.activeCell.changeValue(11);
				}else{
					BoardView.this.activeCell.changeValue(1);
				}
			}
			
			if(event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.NUMPAD2){
				if(BoardView.this.activeCell.getOnePressed() == true && BoardView.this.activeCell.cell.getMaxValue() >= 12){
					BoardView.this.activeCell.changeValue(12);
				}else{
					BoardView.this.activeCell.changeValue(2);
				}
			}
			
			if(event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.NUMPAD3){
				if(BoardView.this.activeCell.getOnePressed() == true && BoardView.this.activeCell.cell.getMaxValue() >= 13){
					BoardView.this.activeCell.changeValue(13);
				}else{
					BoardView.this.activeCell.changeValue(3);
				}
			}
			
			if(event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.NUMPAD4){
				if(BoardView.this.activeCell.getOnePressed() == true && BoardView.this.activeCell.cell.getMaxValue() >= 14){
					BoardView.this.activeCell.changeValue(14);
				}else{
					BoardView.this.activeCell.changeValue(4);
				}
			}
			
			if(event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.NUMPAD5){
				if(BoardView.this.activeCell.getOnePressed() == true && BoardView.this.activeCell.cell.getMaxValue() >= 15){
					BoardView.this.activeCell.changeValue(15);
				}else{
					BoardView.this.activeCell.changeValue(5);
				}
			}
			
			if(event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.NUMPAD6){
				BoardView.this.activeCell.changeValue(6);
			}
			
			if(event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.NUMPAD7){
				BoardView.this.activeCell.changeValue(7);
			}
			
			if(event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.NUMPAD8){
				BoardView.this.activeCell.changeValue(8);
			}
			
			if(event.getCode() == KeyCode.DIGIT9 || event.getCode() == KeyCode.NUMPAD9){
				BoardView.this.activeCell.changeValue(9);
			}
			
			//zadavani cisel vetsich nez 9 (verze 15)
			if(event.getCode() == KeyCode.A){
				BoardView.this.activeCell.changeValue(10);
			}
			
			if(event.getCode() == KeyCode.B){
				BoardView.this.activeCell.changeValue(11);
			}
			
			if(event.getCode() == KeyCode.C){
				BoardView.this.activeCell.changeValue(12);
			}
			
			if(event.getCode() == KeyCode.D){
				BoardView.this.activeCell.changeValue(13);
			}
			
			if(event.getCode() == KeyCode.E){
				BoardView.this.activeCell.changeValue(14);
			}
			
			if(event.getCode() == KeyCode.F){
				BoardView.this.activeCell.changeValue(15);
			}
			
			//mazani cisel || poznamek
			
			if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
				BoardView.this.activeCell.clear();
			}
			
			event.consume();
		}
	};

	
	/* Trida zobrazujici jednotlive bunky
	 * 
	 * 
	 * */
	public class CellView{
		private Cell cell;
		private Button button;
		private boolean onePressed = false;
		
		
		CellView(Cell cell){
			this.cell = cell;
			
			if(this.cell.getType().equals("task")){
				this.button = new Button(String.valueOf(this.cell.getValue()));
				this.button.setStyle("-fx-font-weight: bold; -fx-font-size:17;");
			}else if(this.cell.getType().equals("user")){
				this.button = new Button("");
				this.button.setStyle("-fx-font-size:17;");
			}
			
			this.button.setMinSize(50,50);
			this.button.setMaxSize(50,50);
			
			this.button.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	
			    	BoardView.this.setActive(CellView.this);
			    	
			    }
			});
			
		}
		
		
		
		public void changeValue(int value){
			
			if(this.cell.insert("value", value) == 0){
				this.button.setText(String.valueOf(value));
			}
			
			if(value == 1){
				this.onePressed = true;
			}else{
				this.onePressed = false;
			}
			
		}
		
		
		
		public boolean getOnePressed(){
			return this.onePressed;
		}
		
		public void clearOnePressed(){
			this.onePressed = false;
		}
		
		public void clear(){
			
			if(this.cell.clear()){
				this.button.setText("");
			}
			
		}
		
		public Cell getCell(){
			return this.cell;
		}
		
		public Button getButton(){
			return this.button;
		}
		
		
	}
	
}
