package multiplayer;

import java.util.HashMap;

import multiplayer.IMultiplayer;

public class Factory {
	
	private HashMap<String,IMultiplayer> games = new HashMap<String, IMultiplayer>();
	
	public Factory() {
		games.put("reversii", new Reversii());
	}
	
	public IMultiplayer getGame(String key) {
		return games.get(key);
	}
}
