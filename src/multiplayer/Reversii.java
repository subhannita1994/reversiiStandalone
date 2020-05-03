package multiplayer;

import javafx.scene.paint.Color;

class Reversii extends AbstractMultiplayer implements IMultiplayer{

	public Mode mode;
	public IMultiplayer main;
	
	
	public Reversii() {
		this.setGameName("Reversii");
		int[] boardSize = new int[2];
		boardSize[0] = 8;	boardSize[1] = 8;
		Color[] identifiers = new Color[2];
		identifiers[0] = Color.BLACK;
		identifiers[1] = Color.WHITE;
		this.setGameConfiguration(boardSize, 2, 50, 2, identifiers);
	}
	
	/**
	 * implement methods that are implemented in IMultiplayer and not in superclass
	 */
	
	/**
	 * implement methods that are specific to Reversii
	 */
	
}
