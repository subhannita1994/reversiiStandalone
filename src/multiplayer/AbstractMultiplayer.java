package multiplayer;

import java.util.ArrayList;

import javafx.scene.paint.Color;

abstract class AbstractMultiplayer implements IMultiplayer {
	
	protected Mode mode;
	private String gameName;
	protected ArrayList<IPlayer> players;
	private GameConfiguration gameConfiguration = new GameConfiguration();
	protected IPlayer currentPlayer;
	/**
	 * declare methods that are declared in IMultiplayer here and are common for all games
	 */
	public String getGameName() {
		return this.gameName;
	}
	public GameConfiguration getGameConfiguration() {
		return this.gameConfiguration;
	}
	/**
	 * the order of entry of players matters,i.e., players[0] is the first player and so on
	 * identifiers are set according to player order
	 * the current player is set
	 */
	public void setPlayers(ArrayList<IPlayer> players, GameConfiguration gf) {
		this.players = players;
		int i=0;
		Color[] colors = gf.getIdentifiers();
		for(IPlayer p : players) {
			p.setIdentifier(colors[i]);
			i++;
		}
		this.currentPlayer = this.players.get(0);
	}
	public ArrayList<IPlayer> getPlayers() {
		return this.players;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public IPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}
	@Override
	public IPlayer getWinner() {
		int score = 0;
		IPlayer winner = null;
		for(IPlayer player : players) {
			if(player.getScore() >= score) {
				score = player.getScore();
				winner = player;
			}
		}
		return winner;
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
