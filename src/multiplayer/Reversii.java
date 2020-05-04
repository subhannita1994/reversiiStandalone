package multiplayer;

import javafx.scene.paint.Color;

class Reversii extends AbstractMultiplayer implements IMultiplayer{

	private CellValue[][] board;
	
	public Reversii() {
		//setting up game rule
		this.setGameName("Reversii");
		
		int[] boardSize = new int[2];
		boardSize[0] = 8;	boardSize[1] = 8;
		
		Color[] identifiers = new Color[2];
		identifiers[0] = Color.BLACK;
		identifiers[1] = Color.WHITE;
		
		this.setGameConfiguration(boardSize, 2, 50, 2, identifiers);
		
		this.board = new CellValue[8][8];
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				board[i][j] = CellValue.EMPTY;
		board[3][3] = board[4][4] = CellValue.WHITE;
		board[3][4] = board[4][3] = CellValue.BLACK;
		board[2][4] = board[3][5] = board[4][2] = board[5][3]  = CellValue.POSSIBLE;
		
	}
	
	/**
	 * implement methods that are implemented in IMultiplayer and not in abstract superclass
	 */
	
	/**
	 * implement methods that are specific to Reversii
	 */
	@Override
	public void setCell(CellValue cellValue, int rowIndex, int colIndex) {
		board[rowIndex][colIndex] = cellValue;
	}

	@Override
	public CellValue[][] getBoard() {
		return board;
	}
	
	
}
