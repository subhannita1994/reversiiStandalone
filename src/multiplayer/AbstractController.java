package multiplayer;

import java.io.IOException;
import java.util.Optional;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public abstract class AbstractController implements IController{
	
	protected Main main;
	protected String controllerName;
	protected Text[] playerNames;
	protected Text[] playerScores;
	
	/**
	 * overridden method to set the main application class instance for this game - helps to use methods such as getHostServices()
	 * purpose is to use a single instance of the main application class and prevent loading the start() method more than once
	 */
	public void setMain(Main main) {
		this.main = main;
		System.out.println(this.controllerName +":Main set");
	}
	
	public void drawBoard(CellValue[][] board){
		
	}
	
	public void drawPlayerNames(String[] playerNames) {
		for(int i = 0; i< playerNames.length; i++) {
			this.playerNames[i].setText(playerNames[i]);
		}
	}
	
	protected void drawPlayerScores() {
		int[] scores = this.main.getScores();
		for(int i = 0;i <scores.length; i++) {
			this.playerScores[i].setText(Integer.toString(scores[i]));
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
			this.main.quitGame();
		}else if(result.get().equals(noBtn)) {
			System.out.println("...on second thoughts, player wants to continue");
		}
		alert.close();
	}
	
	@Override
	public void declareWinner(IPlayer winner) throws IOException {
		System.out.println("GAME OVER");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("End Game");
		alert.setContentText("Do you really want to quit?");
		ButtonType quitBtn = new ButtonType("Quit");
		ButtonType playAgainBtn = new ButtonType("Play Again");
		alert.getButtonTypes().setAll(quitBtn,playAgainBtn);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get().equals(quitBtn)) {
			System.out.println("bye, player");
			this.main.quitGame();
		}else if(result.get().equals(playAgainBtn)) {
			System.out.println("player likes this game too much");
			this.main.restartGame();
		}
		alert.close();
	}
	
	@FXML
	public void restartGame(ActionEvent e)throws IOException{
		System.out.println("Player wants to restart");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Start new Game");
		alert.setContentText("Do you really want to start a new game?");
		ButtonType yesBtn = new ButtonType("Yes");
		ButtonType noBtn = new ButtonType("No");
		alert.getButtonTypes().setAll(yesBtn,noBtn);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get().equals(yesBtn)) {
			System.out.println("...loading new game");
			this.main.restartGame();
		}else if(result.get().equals(noBtn)) {
			System.out.println("...on second thoughts, player wants to continue");
		}
		alert.close();
	}

	
}
