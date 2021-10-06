package view;

import java.io.IOException;

import controler.Controler;

import view.BoardView.CellView;

import model.GameBoard;
import model.Server;
import model.Server.ReturnValue;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
// import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class Main extends Application {
    
	private Scene menuScene, gameScene, statisticScene;
	private Stage stage;
	private Controler controler;
	private int difficulty = 0;
	private int gameReady = -1;
	
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    	this.stage = primaryStage;
        primaryStage.setTitle("Sudoku");

	
    	this.controler = new Controler();
    	// controler.setGameControler(controler.getServer().game.getTask(9, 0));
    	this.gameScene = controler.prepareGameScene();
    	this.menuScene = controler.prepareMenuScene();
    	
    	
    	controler.getMenuControler().getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() { 
            @Override
    		public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n){ 

            	RadioButton rb = (RadioButton)controler.getMenuControler().getRadioGroup().getSelectedToggle(); 
            	
                if (rb != null) { 
                   difficulty = (int) rb.getUserData();
                } 
            }

			
        }); 
    	
    	//vyber hry 6x6
    	controler.getMenuControler().getButton6().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override 
    	    public void handle(ActionEvent e) {
    	    	
    	    	gameReady = controler.setGameControler(controler.getServer().game.getTask(6,difficulty));
    	    	gameScene = controler.prepareGameScene();
    	    	
    	    	if(gameReady == 0){
    	    		stage.setScene(gameScene);
    	    	}else{
    	    		stage.setScene(menuScene);
    	    	}
    	    	
    	    	
    	    	controler.getGameControler().getControlsView().getMenuButton().setOnAction(new EventHandler<ActionEvent>() {
    	    	    @Override 
    	    	    public void handle(ActionEvent e) {
    	    	    	
    	    	
    	    	    	Main.this.stage.setScene(menuScene);
    	    	    	
    	    	    }
    	    	});
    	    	
    	    }
    	});
    	
    	//vyber hry 9x9
    	controler.getMenuControler().getButton9().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override 
    	    public void handle(ActionEvent e) {
    	    	
    	    	gameReady = controler.setGameControler(controler.getServer().game.getTask(9,difficulty));
    	    	gameScene = controler.prepareGameScene();
    	    	
    	    	if(gameReady == 0){
    	    		stage.setScene(gameScene);
    	    	}else{
    	    		stage.setScene(menuScene);
    	    	}
    	    	
    	    	controler.getGameControler().getControlsView().getMenuButton().setOnAction(new EventHandler<ActionEvent>() {
    	    	    @Override 
    	    	    public void handle(ActionEvent e) {
    	    	    	
    	    	    	Main.this.stage.setScene(menuScene);
    	    	    	
    	    	    }
    	    	});
    	    	
    	    }
    	});
    	
    	//vyber hry 15x15
    	controler.getMenuControler().getButton15().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override 
    	    public void handle(ActionEvent e) {
    	    	
    	    	gameReady = controler.setGameControler(controler.getServer().game.getTask(15,difficulty));
    	    	gameScene = controler.prepareGameScene();
    	    	
    	    	if(gameReady == 0){
    	    		stage.setScene(gameScene);
    	    	}else{
    	    		stage.setScene(menuScene);
    	    	}
    	    	
    	    	controler.getGameControler().getControlsView().getMenuButton().setOnAction(new EventHandler<ActionEvent>() {
    	    	    @Override 
    	    	    public void handle(ActionEvent e) {
    	    	    	
    	    	    	Main.this.stage.setScene(menuScene);
    	    	    	
    	    	    }
    	    	});
    	    	
    	    }
    	});
    	
    	stage.setScene(menuScene);
    	stage.show();
    	
        
    }
}
