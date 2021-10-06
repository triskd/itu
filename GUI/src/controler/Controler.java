package controler;

import view.BoardView;
import view.ControlsView;
import model.GameBoard;
import model.GameTask;
import model.Server;
import model.Server.ReturnValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.input.KeyEvent;



public class Controler {

	private Scene gameScene;
	private Scene menuScene;
	private Scene statisticScene;
	
	private GameControler gameControler = null;
	private MenuControler menuControler = null;
	
	private Server server;
	
	public Controler(){
		// this.server = new Server("http://triskacv.infinityfreeapp.com/refs/itu/");
		this.server = new Server("https://www.stud.fit.vutbr.cz/~xtrisk05/itu_proj/");
		this.menuControler = new MenuControler();
	}
	
	public Server getServer(){
		return this.server;
	}
	
	public GameControler getGameControler(){
		return this.gameControler;
	}
	
	public MenuControler getMenuControler(){
		return this.menuControler;
	}
	
	public int setGameControler(ReturnValue serverReturn){
		
		if(serverReturn.getRetCode() == 0){
			this.gameControler = new GameControler(serverReturn.getGameTaskValue());
			return 0;
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Could not get task from server");
			alert.setHeaderText(null);
			alert.setContentText("COULD NOT GET TASK FROM SERVER");

			alert.showAndWait();
			return -1;
		}
		
	}
	
	public Scene prepareGameScene(){
		
		if(this.gameControler != null){
			
			
			GridPane mainGrid = new GridPane();
			mainGrid.add(this.gameControler.getBoardView().getGrid(), 0, 0);
			mainGrid.add(this.gameControler.getControlsView().getGrid(), 1, 0);
			mainGrid.setHgap(10);
			mainGrid.setAlignment(Pos.CENTER);
			////////////////////////////////////////
	        
	        StackPane root = new StackPane();
	        root.getChildren().add(mainGrid);
	        
	        Scene scene = new Scene(root, 980, 800);
	        scene.getStylesheets().add("/main.css");
	        
	        //napoveda
	        this.gameControler.getControlsView().getHelpButton().setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	
			    	BoardView.CellView active = Controler.this.gameControler.boardView.getActiveCellView();
			    	
			    	ReturnValue ret = Controler.this.server.game.help(Controler.this.gameControler.getGameBoard().getCells().indexOf(active.getCell()), Controler.this.gameControler.getGameBoard().getGameTask());
			    	if(ret.getRetCode() == 0){
			    		Controler.this.gameControler.getBoardView().getActiveCellView().changeValue(ret.getIntValue());
			    	}
			    	
			    }
			});
	        
	        //kontrola reseni
	        this.gameControler.getControlsView().getCheckButton().setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	
			    	Controler.this.gameControler.getGameBoard().saveState(); //ulozeni stavu hraci desky
			    	ReturnValue retVal = Controler.this.server.game.check(Controler.this.gameControler.getGameBoard().getGameTask());
			    	
			    	if(retVal.getRetCode() == 0){
			    		
			    		if(retVal.getBoolValue() == true){
			    			Alert alert = new Alert(AlertType.INFORMATION);
			    			alert.setTitle("Solution control");
			    			alert.setHeaderText(null);
			    			alert.setContentText("Your solution is CORRECT, congratulations");

			    			alert.showAndWait();
			    		}else{
			    			Alert alert = new Alert(AlertType.WARNING);
			    			alert.setTitle("Solution control");
			    			alert.setHeaderText(null);
			    			alert.setContentText("Your solution is NOT CORRECT, sorry");

			    			alert.showAndWait();
			    		}
			    		
			    	}
			    	
			    }
			});
	        
	        //guma
	        this.gameControler.getControlsView().getRubberButton().setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	
			    	Controler.this.gameControler.getBoardView().getActiveCellView().clear();
			    	
			    }
			});

	        
	        
			
			// scene.setOnKeyPressed(this.gameControler.getBoardView().keyPressHandler);
			scene.addEventFilter(KeyEvent.KEY_PRESSED,this.gameControler.getBoardView().keyPressHandler );
			
			this.gameScene = scene;
	
			return this.gameScene;
		}

		return null;
	}
	
	public Scene prepareMenuScene(){
		
		Label label2= new Label("Sudoku");
		VBox layout2= new VBox(20);
		label2.setStyle("-fx-font-size: 80; -fx-padding: 100 0 80 0;");
		layout2.setAlignment(Pos.TOP_CENTER);
		
		GridPane radios = new GridPane();
		radios.setAlignment(Pos.CENTER);
		radios.setHgap(15); radios.setStyle("-fx-padding: 0 0 30 0");
		radios.add(this.menuControler.getEasyRadio(), 0, 0);
		radios.add(this.menuControler.getMediumRadio(), 1, 0);
		radios.add(this.menuControler.getHardRadio(), 2, 0);
		
		layout2.getChildren().addAll(label2,radios,this.menuControler.getButton6(), this.menuControler.getButton9(), this.menuControler.getButton15());
		
		Scene scene = new Scene(layout2, 980, 800);
        scene.getStylesheets().add("/main.css");
        

		
		this.menuScene = scene;
		return this.menuScene;
	}
	
	
		
	public class GameControler{
		private String inputTool = "value"; // "value" || "note" || "rubber"
		private BoardView boardView;
		private ControlsView controlsView;
		private GameBoard gameBoard;
	
		public GameControler(GameTask gameTask){
			
			this.gameBoard = new GameBoard(gameTask);
			this.boardView = new BoardView(this.gameBoard);
			this.controlsView = new ControlsView(this.gameBoard);
			
		}
		
		
		public BoardView getBoardView(){
			return this.boardView;
		}
		
		public ControlsView getControlsView(){
			return this.controlsView;
		}
		
		public GameBoard getGameBoard(){
			return this.gameBoard;
		}
	
	}
	
	public class MenuControler{
		private Button button6;
		private Button button9;
		private Button button15;
		private RadioButton easyRadio;
		private RadioButton mediumRadio;
		private RadioButton hardRadio;
		private ToggleGroup radioGroup;
		
		MenuControler(){
			this.button6 = new Button("6x6"); this.button6.setStyle("-fx-font-size: 20;-fx-pref-width: 200px; -fx-font-weight: bold;");
			this.button9 = new Button("9x9"); this.button9.setStyle("-fx-font-size: 20;-fx-pref-width: 200px; -fx-font-weight: bold;");
			this.button15 = new Button("15x15"); this.button15.setStyle("-fx-font-size: 20;-fx-pref-width: 200px; -fx-font-weight: bold;");
			
			this.radioGroup = new ToggleGroup();
			
			this.easyRadio = new RadioButton("easy"); this.easyRadio.setToggleGroup(this.radioGroup); this.easyRadio.setSelected(true);
			this.easyRadio.setStyle("-fx-font-size:20;"); this.easyRadio.setUserData(0);
			
			this.mediumRadio = new RadioButton("medium"); this.mediumRadio.setToggleGroup(this.radioGroup);
			this.mediumRadio.setStyle("-fx-font-size:20;");this.mediumRadio.setUserData(1);
		
			this.hardRadio = new RadioButton("hard"); this.hardRadio.setToggleGroup(this.radioGroup);
			this.hardRadio.setStyle("-fx-font-size:20;"); this.hardRadio.setUserData(2); 
		
		}
		
		public Button getButton6(){
			return this.button6;
		}
		
		public Button getButton9(){
			return this.button9;
		}
		
		public Button getButton15(){
			return this.button15;
		}
		
		public ToggleGroup getRadioGroup(){
			return this.radioGroup;
		}
		
		public RadioButton getEasyRadio(){
			return this.easyRadio;
		}
		
		public RadioButton getMediumRadio(){
			return this.mediumRadio;
		}
		
		public RadioButton getHardRadio(){
			return this.hardRadio;
		}
		
	}
	
	class StatisticControler{
		
	}

}
