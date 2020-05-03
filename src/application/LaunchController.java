package application;

import multiplayer.*;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
		ObservableList<String> styleClass = this.playerName.getStyleClass();
		if(this.playerName.getText().trim().length() == 0) {
			System.out.println("player did not enter name");
			if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));
            System.out.println("Launching game");
            ((Main)this.main).launchXML("Board.fxml");
        }
		
	}
	

}
