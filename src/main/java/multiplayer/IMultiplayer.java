package main.java.multiplayer;

import java.util.ArrayList;

public interface IMultiplayer {
	
	/**
	 * include public methods that are common to all multiplayer games here 
	 */
	public String getGameName();
	public GameConfiguration getGameConfiguration();
	public void setPlayers(ArrayList<IPlayer> players, GameConfiguration gf);
	public ArrayList<IPlayer> getPlayers();
	public void setMode(Mode mode);
	public CellValue[][] getBoard();
	public IPlayer getCurrentPlayer();
	public void move(int rowIndex, int colIndex);
	public boolean gameOver();
	public IPlayer getWinner();
	public Object getMode();
}
