package multiplayer;

import javafx.scene.paint.Color;

public class Player extends AbstractPlayer implements IPlayer{
	
	public Player(String playerName, int score, PlayerType playerType) {
		this.playerName = playerName;
		this.score = score;
		this.playerType = playerType;
	}

	@Override
	public void setIdentifier(Color identifier) {
		// TODO Auto-generated method stub
		this.identifier = identifier;
	}

}
