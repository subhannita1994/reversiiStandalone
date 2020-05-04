package multiplayer;

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
	public void setCell(CellValue cellValue, int rowIndex, int colIndex);
	public CellValue[][] getBoard();
}
