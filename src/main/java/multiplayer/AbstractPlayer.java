package main.java.multiplayer;

import java.io.IOException;

import javafx.scene.paint.Color;

public abstract class AbstractPlayer implements IPlayer{
	
	protected String playerName;
	protected Color identifier;
	protected int score;
	protected PlayerType playerType;
	
	public AbstractPlayer(String playerName, int score, PlayerType playerType) {
		this.playerName = playerName;
		this.score = score;
		this.playerType = playerType;
	}
	
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
	
	
	public void setIdentifier(Color identifier) {
		
		this.identifier = identifier;
	}
	
	public void move(CellValue[][] cellValues, IController boardController) throws IOException {
		
	}
}
