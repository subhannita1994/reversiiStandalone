package main.java.application;

import main.java.multiplayer.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LaunchController extends AbstractController implements IController{
	
	@FXML Button startGameBtn;
	@FXML AnchorPane multiPlayerOptionsPane;
	@FXML Text inviteLink;
	@FXML ToggleButton findOpponentBtn;
	@FXML TextField playerName;
	@FXML RadioButton singlePlayerRadioBtn;
	
	public LaunchController() {
		this.controllerName = "LaunchController";
		
	}
	
	
	/**
	 * action handler for "instructions" button
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void openInstructions(ActionEvent e) throws IOException{
		System.out.println("opening instructions page...");
		this.main.getHostServices().showDocument("http://www.flyordie.com/games/help/reversi/en/games_rules_reversi.html");
	}


	@FXML
	public void singlePlayerRadioBtnHandler(ActionEvent e)throws IOException{
		this.multiPlayerOptionsPane.setVisible(false);
		this.startGameBtn.setDisable(false);
		System.out.println("player selected singlePlayer mode: can start game now");
	}

	@FXML
	public void multiPlayerRadioBtnHandler(ActionEvent e) throws IOException{
		this.startGameBtn.setDisable(true);
		this.multiPlayerOptionsPane.setVisible(true);
		this.inviteLink.setText(this.generateInviteLink());
		System.out.println("player selected multiPlayer mode");
	}

	/**
	 * creates invite link
	 * @return
	 */
	private String generateInviteLink() {
		// TODO Auto-generated method stub
		return "link";
	}
	
	@FXML
	public void findOpponentBtnHandler(ActionEvent e) throws IOException{
		if(this.findOpponentBtn.isSelected()) {
			this.startGameBtn.setDisable(false);
			System.out.println("player selected find opponent: can start game now");
		}
		else {
			this.startGameBtn.setDisable(true);
			System.out.println("player de-selected find opponent");
		}
	}
	
	@FXML
	public void startGameBtnHandler(ActionEvent e) throws IOException{
		String playerName = this.playerName.getText();
		ObservableList<String> styleClass = this.playerName.getStyleClass();
		if(playerName.trim().length() == 0) {
			System.out.println("player did not enter name");
			if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));
            System.out.println("Launching game");
            Mode mode = null;
        	HashMap<String, PlayerType> players = new HashMap<String, PlayerType>();
            if(this.singlePlayerRadioBtn.isSelected()) {
            	System.out.println("....in single player mode: players are AI and "+playerName);
            	players.put(playerName, PlayerType.HUMAN);
            	players.put("Bill", PlayerType.AI);
            	mode = Mode.SINGLE_PLAYER;
            }
            /**
             * TODO:
             * else if multiplayer......
             */
            IController boardController = this.main.launchXML("Board.fxml");
            this.main.setBoardController(boardController);
            this.main.startGame(mode, players);
            
        }
		
	}

	
	
	

}
