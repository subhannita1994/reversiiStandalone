package multiplayer;

abstract class AbstractMultiplayer implements IMultiplayer {
	
	public String playerName;
	public Mode mode;
	private String gameName;
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
	
	/**
	 * include protected helper methods here with stuff that is common for all multiplayer games
	 */
	protected void setGameName(String gameName) {
		this.gameName = gameName;
	}
	protected void setGameConfiguration(int[] boardSize, int numberOfPlayers, int tileSize) {
		this.gameConfiguration.boardSize = boardSize;
		this.gameConfiguration.numberOfPlayers = numberOfPlayers;
		this.gameConfiguration.tileSize = tileSize;
	}

}
