package main.java.multiplayer;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import main.java.application.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public abstract class AbstractController implements IController{
	
	protected Main main;
	protected String controllerName;
	protected Text[] playerNames;
	protected Text[] playerScores;
	protected HBox[] playerBoxes;
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
	
	public void handleClickAI(int rowIndex, int colIndex)throws IOException{
		
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
	/**
	 * method to highlight so that the current player box is highlighted
	 */
	protected void highlightPlayerTurn() {
		int curPlayerIndex = this.main.getCurrentPlayer();
		HBox curPlayerBox = this.playerBoxes[curPlayerIndex];
		for(int i =0;i<playerBoxes.length; i++) {
			ObservableList<String> s = playerBoxes[i].getStyleClass();
			s.removeAll(Collections.singleton("turn"));
		}

		ObservableList<String> styleClass = curPlayerBox.getStyleClass();
		if(! styleClass.contains("turn"))
			styleClass.add("turn");
	}
	
	@FXML
	public void endGameBtnHandler(ActionEvent e) throws IOException{
		System.out.println("Player wants to quit");
		
		String result = showAlert(AlertType.CONFIRMATION, "End Game", null, "Do you really want to quit?", "Yes", "No");
		if(result.equals("Yes")) {
			System.out.println("bye, player");
			this.main.quitGame();
		}else if(result.equals("No")) {
			System.out.println("...on second thoughts, player wants to continue");
		}
	
	}
	
	@Override
	public void declareWinner(String winner) throws IOException {
		System.out.println("GAME OVER");
		
		String result = showAlert(AlertType.INFORMATION, "End Game", "Winner: "+winner, "Play another round!", "Quit", "Play Again");
		if(result.equals("Quit")) {
			System.out.println("bye, player");
			this.main.quitGame();
		}else if(result.equals("Play Again")) {
			System.out.println("player likes this game too much");
			this.main.restartGame();
		}
	}
	
	@FXML
	public void restartGame(ActionEvent e)throws IOException{
		System.out.println("Player wants to restart");
		
		String result = showAlert(AlertType.CONFIRMATION, "Start new Game", null, "Do you really want to start a new game?", "Yes", "No");
		if(result.equals("Yes")) {
			System.out.println("...loading new game");
			this.main.restartGame();
		}else if(result.equals("No")) {
			System.out.println("...on second thoughts, player wants to continue");
		}
	}
	
	private String showAlert(AlertType alertType, String title, String headerText, String contentText, String btn1Text, String btn2Text) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		ButtonType btn1 = new ButtonType(btn1Text);
		ButtonType btn2 = new ButtonType(btn2Text);
		alert.getButtonTypes().setAll(btn1,btn2);
		Optional<ButtonType> result = alert.showAndWait();
		alert.close();
		return result.get().getText();
	}

	
	
}
