package multiplayer;

import java.util.HashMap;

import javafx.scene.paint.Color;
import multiplayer.IMultiplayer;

public class Factory {
	
	private HashMap<String,IMultiplayer> games = new HashMap<String, IMultiplayer>();
	private static volatile Factory theUniqueFactory;
	
	private Factory() {
		games.put("reversii", new Reversii());
	}
	
	public static synchronized Factory getInstance() {
		if(Factory.theUniqueFactory == null) {
			Factory.theUniqueFactory = new Factory();
		}
		return Factory.theUniqueFactory;
	}
	public IMultiplayer getGame(String key) {
		return games.get(key);
	}
	public IPlayer createPlayer(String playerName, int score, PlayerType playerType) {
		return new Player(playerName, score, playerType);
	}
}
