package multiplayer;


import application.AI;
import multiplayer.IMultiplayer;

public class Factory {
	
	private static volatile Factory theUniqueFactory;
	
	private Factory() {
		
	}
	
	public static synchronized Factory getInstance() {
		if(Factory.theUniqueFactory == null) {
			Factory.theUniqueFactory = new Factory();
		}
		return Factory.theUniqueFactory;
	}
	public IMultiplayer getGame(String key) {
		if(key.equals("reversii"))
			return new Reversii();
		return null;
	}
	public IPlayer createPlayer(String playerName, int score, PlayerType playerType) {
		if(playerType.equals(PlayerType.HUMAN))
			return new Player(playerName, score, playerType);
		else if(playerType.equals(PlayerType.AI))
			return new AI("Bill",score, playerType);
		return null;
	}
}

