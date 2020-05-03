package application;


import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import multiplayer.*;

public class BoardController extends AbstractController implements IController{

	@FXML GridPane gameBoard;
	
	public BoardController() {
		this.controllerName="BoardController";
	}
	
	@FXML
	public void initialize() {
		System.out.println("Displaying initial board");
		int[] boardSize = Main.game.getGameConfiguration().getBoardSize();
		for (int i = 0; i < boardSize[0]; i++) {
	        for (int j = 0; j < boardSize[1]; j++) {

	            Rectangle tile = new Rectangle(50, 50);
	            tile.setFill(Color.BURLYWOOD);
	            tile.setStroke(Color.BLACK);

	            GridPane.setRowIndex(tile, i);
	            GridPane.setColumnIndex(tile, j); 
	            gameBoard.getChildren().add(tile);
	            
//	            tile.setOnMouseClicked(event -> drawMove(text));
	        }
	    }
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
			Main.quitGame();
		}else if(result.get().equals(noBtn)) {
			System.out.println("...on second thoughts, player wants to continue");
		}
		alert.close();
	}

	
}
