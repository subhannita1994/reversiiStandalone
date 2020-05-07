package main.java.multiplayer;

import java.io.IOException;

import javafx.scene.paint.Color;

public interface IPlayer {
	
	public String getPlayerName();
	public Color getPlayerIdentifier();
	public int getScore();
	public void setScore(int score);
	public PlayerType getPlayerType();
	void setIdentifier(Color identifier);
	public void move(CellValue[][] cellValues, IController boardController) throws IOException;
}
