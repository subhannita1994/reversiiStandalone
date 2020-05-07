package main.java.application;

import main.java.multiplayer.*;

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
	
	public void restartGame() throws IOException {
		game = Factory.getInstance().getGame("reversii");
		this.launchXML("LaunchPage.fxml");
	}
	
	public static GameConfiguration getGameConfiguration() {
		return gf;
	}
	
	public int[] getScores() {
		int[] scores = new int[gf.getNumberOfPlayers()];
		for(int i = 0; i <gf.getNumberOfPlayers(); i++) {
			scores[i] = game.getPlayers().get(i).getScore();
		}
		return scores;
	}

	/**
	 * this method is called by LaunchController when player clicks on start button (in single player mode for now)
	 * initializes the game in the given mode with these players.
	 * @throws IOException 
	 */
	public void startGame(Mode mode, HashMap<String,PlayerType> playersInfo) throws IOException {
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
		
		
		//draw the initial board with player names
		this.boardController.drawBoard(this.game.getBoard());
		String[] playerNames = new String[gf.getNumberOfPlayers()];
		for(int i = 0; i<gf.getNumberOfPlayers(); i++) {
			playerNames[i] = this.game.getPlayers().get(i).getPlayerName();
		}
		this.boardController.drawPlayerNames(playerNames);
		
		//if singleplayer mode and current player is AI, call AI.move()
		if(game.getMode().equals(Mode.SINGLE_PLAYER) && game.getCurrentPlayer().getPlayerType().equals(PlayerType.AI))
			game.getCurrentPlayer().move(this.game.getBoard(), this.boardController);
	}

	public void setBoardController(IController boardController) {
		this.boardController = boardController;
		
	}
	
	
	/**
	 * called by boardController.handleClick() when player clicks on a possible circle
	 * @param rowIndex
	 * @param colIndex
	 * @throws IOException
	 */
	public void move(int rowIndex, int colIndex) throws IOException {
		System.out.println(game.getCurrentPlayer().getPlayerName()+" made a move on "+rowIndex+","+colIndex);
		//call reversii's move() -- game logic is implemented here
		game.move(rowIndex,colIndex);
		//draw board and player scores
		this.boardController.drawBoard(this.game.getBoard());
		//check if game is over
		if(this.game.gameOver()) {
			IPlayer winner = game.getWinner();
			try {
				this.boardController.declareWinner(winner.getPlayerName());
			}catch(NullPointerException e) {
				this.boardController.declareWinner("both player");
			}
		}else {
		//if singleplayer mode and current player is AI, call AI.move()
		if(game.getMode().equals(Mode.SINGLE_PLAYER) && game.getCurrentPlayer().getPlayerType().equals(PlayerType.AI))
			game.getCurrentPlayer().move(this.game.getBoard(), this.boardController);
		}
	}
	
	/**
	 * display board on console for debugging
	 */
	public void displayBoard() {
		CellValue[][] board = game.getBoard();
		for(int i = 0; i< gf.getBoardSize()[0]; i++) {
			for(int j=0;j<gf.getBoardSize()[1]; j++) {
				if(board[i][j].equals(CellValue.BLACK))
					System.out.print("B");
				else if(board[i][j].equals(CellValue.WHITE))
					System.out.print("W");
				else if(board[i][j].equals(CellValue.EMPTY))
					System.out.print("E");
				else if(board[i][j].equals(CellValue.POSSIBLE))
					System.out.print("P");
			}
			System.out.println();
		}
	}

	/**
	 * get the index of the current player
	 * @return
	 */
	public int getCurrentPlayer() {
		
		return this.game.getPlayers().indexOf(this.game.getCurrentPlayer());
	}
	
}
