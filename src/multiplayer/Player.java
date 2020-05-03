package multiplayer;

import javafx.scene.paint.Color;

class Player extends AbstractPlayer implements IPlayer{
	
	public Player(String playerName, Color identifier, int score, PlayerType playerType) {
		this.playerName = playerName;
		this.identifier = identifier;
		this.score = score;
		this.playerType = playerType;
	}

}
