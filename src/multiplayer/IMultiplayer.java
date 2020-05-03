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
}
