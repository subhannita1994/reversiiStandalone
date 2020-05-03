package multiplayer;

class Reversii extends AbstractMultiplayer implements IMultiplayer{

	public Mode mode;
	public IMultiplayer main;
	
	
	public Reversii() {
		this.setGameName("Reversii");
		int[] boardSize = new int[2];
		boardSize[0] = 8;	boardSize[1] = 8;
		this.setGameConfiguration(boardSize,2,50);
	}
	
	/**
	 * implement methods that are implemented in IMultiplayer and not in superclass
	 */
	
	/**
	 * implement methods that are specific to Reversii
	 */
	
}
