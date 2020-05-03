package application;


import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import multiplayer.*;

public class BoardController extends AbstractController implements IController{

	@FXML GridPane gameBoard;
	private static GameConfiguration GAME_CONF;
	
	public BoardController() {
		this.controllerName="BoardController";
	}
	
	@FXML
	public void initialize() {
		System.out.println("Displaying initial board");
		/**
		 * we cannot use any non-static members of Main class here 
		 * because FXML follows this sequence when it is loaded - 
		 * 1. initialize the controller
		 * 2. inject the FXML objects from the xml document to the controller class
		 * 3. run controller's intialize()
		 * 4. and finally do other stuff such as calling controller's setMain() method
		 * 
		 * Therefore, this.main is null as of now and we have to make use of Main's static gameConfiguration.
		 * Fortunately, gameConfiguration is same throughout 
		 * but game may not be same for a different set of players (in case of multiplayer)
		 */
		GAME_CONF = Main.getGameConfiguration();
		
		for (int i = 0; i < GAME_CONF.getBoardSize()[0]; i++) {
	        for (int j = 0; j < GAME_CONF.getBoardSize()[1]; j++) {

	        	//adding tiles to board
	            Rectangle tile = new Rectangle(GAME_CONF.getTileSize(), GAME_CONF.getTileSize());
	            tile.setFill(Color.BURLYWOOD);
	            tile.setStroke(Color.BLACK);
	            gameBoard.add(tile,j,i);	
	            
	        }
	    }
		
		//adding initial circles to board
		int circleSize = GAME_CONF.getTileSize()/2 -4;
		Circle c = new Circle();
		int[][] tmp = new int[4][2];
		tmp[0][0] = 3;	tmp[0][1] = 3;
		tmp[1][0] = 4;	tmp[1][1] = 4;
		tmp[2][0] = 3;	tmp[2][1] = 4;
		tmp[3][0] = 4;	tmp[3][1] = 3;
		for(int[] i : tmp) {
			if(i[0]==i[1])
				c = new Circle(circleSize, Color.WHITE);
			else
				c = new Circle(circleSize, Color.BLACK);
			gameBoard.add(c, i[0], i[1]);
			GridPane.setHalignment(c, HPos.CENTER);
		}
		
		//adding future circles to board (places where player 1,i.e., black can click on)
		
		
	}
	
	
	@FXML
	public void endGameBtnHandler(ActionEvent e) throws IOException{
		System.out.println("Player wants to quit");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("End Game");
		alert.setContentText("Do you really want to quit?");
		ButtonType yesBtn = new ButtonType("Yes");
		ButtonType noBtn = new ButtonType("No");
		alert.getButtonTypes().setAll(yesBtn,noBtn);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get().equals(yesBtn)) {
			System.out.println("bye, player");
			this.main.quitGame();
		}else if(result.get().equals(noBtn)) {
			System.out.println("...on second thoughts, player wants to continue");
		}
		alert.close();
	}

	
}
