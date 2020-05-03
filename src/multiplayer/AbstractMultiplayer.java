package multiplayer;

import java.util.ArrayList;

import javafx.scene.paint.Color;

abstract class AbstractMultiplayer implements IMultiplayer {
	
	protected Mode mode;
	private String gameName;
	protected ArrayList<IPlayer> players;
	private GameConfiguration gameConfiguration = new GameConfiguration();
	/**
	 * declare methods that are declared in IMultiplayer here and are common for all games
	 */
	public String getGameName() {
		return this.gameName;
	}
	public GameConfiguration getGameConfiguration() {
		return this.gameConfiguration;
	}
	public void setPlayers(ArrayList<IPlayer> players) {
		this.players = players;
	}
	public ArrayList<IPlayer> getPlayers() {
		return this.players;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	/**
	 * include protected helper methods here with stuff that is common for all multiplayer games
	 */
	protected void setGameName(String gameName) {
		this.gameName = gameName;
	}
	protected void setGameConfiguration(int[] boardSize, int numberOfPlayers, int tileSize, int startingScore, Color[] identifiers) {
		this.gameConfiguration.boardSize = boardSize;
		this.gameConfiguration.numberOfPlayers = numberOfPlayers;
		this.gameConfiguration.tileSize = tileSize;
		this.gameConfiguration.startingScore = startingScore;
		this.gameConfiguration.identifiers = identifiers;
	}

}
