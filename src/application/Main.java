package application;

import multiplayer.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private IMultiplayer game;
	private static GameConfiguration gf;
	private Stage primaryStage;
	private IController boardController;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		//initializing game
		game = Factory.getInstance().getGame("reversii");
		gf = game.getGameConfiguration();
		
		//starting stage
		this.primaryStage = primaryStage;
		this.launchXML("LaunchPage.fxml");
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
	
	
	public IController launchXML(String filename) throws IOException{
		
		System.out.println("Loading "+filename);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
		Parent root = loader.load();
		IController controller = loader.getController();
		controller.setMain(this);
		primaryStage.setTitle(game.getGameName());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		
		primaryStage.show();
		return controller;
	}
	
	public void quitGame() {
		Platform.exit();
	}
	
	
	public static GameConfiguration getGameConfiguration() {
		return gf;
	}

	/**
	 * this method is called by LaunchController when player clicks on start button (in single player mode for now)
	 * initializes the game in the given mode with these players.
	 * @throws IOException 
	 */
	public void startGame(Mode mode, HashMap<String,PlayerType> playersInfo) {
		// if #players returned by launchController != #players allowed by reversii, then quit
		if(gf.getNumberOfPlayers() != playersInfo.keySet().size()) {
			System.out.println("ERROR: incorrect number of players");
			this.quitGame();
			return;
		}
		
		//create players
		this.game.setMode(mode);
		ArrayList<IPlayer> players = new ArrayList<IPlayer>();
		for(String playerName : playersInfo.keySet()) {
			IPlayer p = Factory.getInstance().createPlayer(playerName, gf.getStartingScore(), playersInfo.get(playerName));
			players.add(p);
			System.out.println("Player created-- Name:"+p.getPlayerName()+" score:"+p.getScore()+" type:"+p.getPlayerType());
			
		}
		
		//decide order of players
		Collections.shuffle(players);
		game.setPlayers(players,gf);
		System.out.println("....players added to game in the order :");
		for(IPlayer p : game.getPlayers()) {
			System.out.println("..."+p.getPlayerName()+":"+p.getPlayerIdentifier());
		}
		
		//draw the initial board
		this.boardController.drawBoard(this.game.getBoard());
		
	}

	public void setBoardController(IController boardController) {
		this.boardController = boardController;
		
	}
	
}
