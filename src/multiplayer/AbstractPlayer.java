package multiplayer;

import javafx.scene.paint.Color;

abstract class AbstractPlayer implements IPlayer{
	
	protected String playerName;
	protected Color identifier;
	protected int score;
	protected PlayerType playerType;
	
	public String getPlayerName() {
		return this.playerName;
	}
	public Color getPlayerIdentifier() {
		return this.identifier;
	}
	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public PlayerType getPlayerType() {
		return this.playerType;
	}
}
