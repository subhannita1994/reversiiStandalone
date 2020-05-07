package main.java.multiplayer;

import java.util.ArrayList;

import javafx.scene.paint.Color;

class Reversii extends AbstractMultiplayer implements IMultiplayer{

	private CellValue[][] board;
	
	public Reversii() {
		//setting up game rule
		this.setGameName("Reversii");
		
		int[] boardSize = new int[2];
		boardSize[0] = 8;	boardSize[1] = 8;
		
		Color[] identifiers = new Color[2];
		identifiers[0] = Color.WHITE;
		identifiers[1] = Color.BLACK;
		
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
	public CellValue[][] getBoard() {
		return board;
	}
	@Override
	public boolean gameOver() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(board[i][j].equals(CellValue.POSSIBLE))
					return false;
			}
		}
		return true;
	}
	@Override
	public IPlayer getWinner() {
		if(players.get(0).getScore() > players.get(1).getScore())
			return players.get(0);
		else if(players.get(1).getScore() > players.get(0).getScore())
			return players.get(1);
		else
			return null;
	}
	
	
	/**
	 * game logic:
	 * for all valid positions around [rowIndex,colIndex]:
	 * 		initialize a stack and fill this stack with the tiles of opponent player in that direction until a tile of this player is encountered
	 * 		if an empty/possible tile is encountered in this process, empty the stack
	 * 		change cellValues of all tiles in the stack and set score of this player and opponent
	 * board is reconstructued by firstly removing all possible circles and then getting all opponent player's tiles
	 * for all tiles belonging to opponent player:
	 * 		
	 * change currentPlayer to opponent player 
	 * called by Main.move()
	 */
	@Override
	public void move(int rowIndex, int colIndex) {
		
		CellValue curPlayerCellValue = this.getCellValue(this.currentPlayer.getPlayerIdentifier(), true);
		IPlayer oppoPlayer = this.getOpponent(this.currentPlayer);
		CellValue oppoPlayerCellValue = this.getCellValue(oppoPlayer.getPlayerIdentifier(), true);
//		System.out.println("Current player:"+curPlayerCellValue.toString()+" Opponent:"+oppoPlayerCellValue);
		board[rowIndex][colIndex]  = curPlayerCellValue;
		
		ArrayList<Coordinate> validPositions = this.getValidPositions(this.currentPlayer.getPlayerIdentifier(), rowIndex, colIndex);
		//iterate for all valid positions around [rowIndex,colIndex]
		for(Coordinate c : validPositions) {
			ArrayList<Coordinate> stack  = new ArrayList<Coordinate>();
			Coordinate pos = c;
			CellValue tile = board[pos.row][pos.col];
			//iterate until a tile of the same player is encountered
			while(!tile.equals(curPlayerCellValue)) {
				//...or until am empty/possible tile is encountered before, in which case terminate
				if(!tile.equals(oppoPlayerCellValue)) {
//					System.out.println("oops, empty/possible tile at"+pos.row+","+pos.col+" -- terminating search");
					stack.clear();
					break;
				}else {		//add this opponent player's tile to stack and update tile
					stack.add(pos);
//					System.out.println("stack adds tile: "+pos.row+","+pos.col+":"+tile.toString());
					pos = nextPos(pos);
					if(pos==null) {	//...unless boundary is hit before, in which case terminate
//						System.out.println("oops, boundary here -- terminating search");
						stack.clear();
						break;
					}
					tile = board[pos.row][pos.col];
				}
			}
			//flip the tiles on stack
			for(Coordinate c1 : stack) {
				board[c1.row][c1.col] = curPlayerCellValue;
//				System.out.println("Flipping tile "+c1.row+","+c1.col+" to "+curPlayerCellValue.toString());
			}
			//update score
			int moveScore = stack.size();
			if(moveScore > 0)
				this.currentPlayer.setScore(this.currentPlayer.getScore()+moveScore+1);
			oppoPlayer.setScore(oppoPlayer.getScore()-moveScore);
//			System.out.println("Updated scores: "+currentPlayer.getScore()+","+oppoPlayer.getScore());
		}
		
		ArrayList<Coordinate> positions = new ArrayList<Coordinate>();
		//remove all possible circles and record opponent player's tiles for the next move
		for(int i =0;i<8;i++)
			for(int j=0;j<8;j++) {
				if(board[i][j].equals(CellValue.POSSIBLE))
					board[i][j] = CellValue.EMPTY;
				else if(board[i][j].equals(oppoPlayerCellValue))
					positions.add(new Coordinate(i,j));
			}
	
		
		//reconstruct board by iterating for all tiles belonging to opponent player
		for(Coordinate c1 : positions) {
//			System.out.println("Opponent tile at "+c1.row+","+c1.col);
			ArrayList<Coordinate> validOppoPositions = this.getValidPositions(oppoPlayer.getPlayerIdentifier(), c1.row, c1.col);
			//iterate for all valid positions around opponent player's tile
			for(Coordinate c : validOppoPositions) {
				Coordinate pos = c;
				CellValue tile = board[pos.row][pos.col];
				//iterate until an empty/possible cell is found
				while(! (tile.equals(CellValue.EMPTY) || tile.equals(CellValue.POSSIBLE)) ) {
					if(tile.equals(oppoPlayerCellValue)) {	//until opponent cell is encountered, in which case terminate
//						System.out.println("oops, opponent tile at "+pos.row+","+pos.col+"-- terminating search");
						pos = null;
						break;
					}
					else {
//						System.out.println("current player tile at "+pos.row+","+pos.col);
						pos = nextPos(pos);
						if(pos==null) {	//until boundary encountered, in which case terminate
//							System.out.println("oops, boundary here -- terminating search");
							break;
						}
						tile = board[pos.row][pos.col];
					}
				}
				if(pos!=null) {	//set possible circle unless a boundary or opponent cell was encountered
					board[pos.row][pos.col] = CellValue.POSSIBLE;
//					System.out.println("Setting tile "+pos.row+","+pos.col+" to Possible");
				}
			}
		}
		
		//swap players
		this.currentPlayer = oppoPlayer;
		
		
		
	}
	
	/**
	 * helper method to return the cellValue of the tile next to pos
	 * @param pos
	 * @return
	 */
	private Coordinate nextPos(Coordinate pos) {
		Coordinate nextPos = null;
		switch(pos.dir) {
		case N:
			nextPos = new Coordinate(pos.row-1, pos.col, pos.dir);
			break;
		case NE:
			nextPos = new Coordinate(pos.row-1, pos.col+1, pos.dir);
			break;
		case E:
			nextPos = new Coordinate(pos.row, pos.col+1, pos.dir);
			break;
		case SE:
			nextPos = new Coordinate(pos.row+1, pos.col+1, pos.dir);
			break;
		case S:
			nextPos = new Coordinate(pos.row+1, pos.col, pos.dir);
			break;
		case SW:
			nextPos = new Coordinate(pos.row+1, pos.col-1, pos.dir);
			break;
		case W:
			nextPos = new Coordinate(pos.row, pos.col-1, pos.dir);
			break;
		case NW:
			nextPos = new Coordinate(pos.row-1, pos.col-1, pos.dir);
			break;
		default:
			break;
		}
		//boundary conditions
		if(nextPos.row < 0 || nextPos.row > 7 || nextPos.col < 0 || nextPos.col > 7)
			nextPos = null;
		return nextPos;
	}
	
	/**
	 * helper method to return opponent player 
	 * @param player
	 * @return
	 */
	private IPlayer getOpponent(IPlayer player) {
		if(this.players.get(0).equals(player))
			return players.get(1);
		else
			return players.get(0);
	}
	/**
	 * helper method to return coordinates on all sides of [rowIndex,colIndex] that are of the opposite color of identifier 
	 * @param identifier
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	private ArrayList<Coordinate> getValidPositions(Color identifier, int rowIndex, int colIndex){
		CellValue oppoCellValue = this.getCellValue(identifier, false);
		ArrayList<Coordinate> pos  = new ArrayList<Coordinate>();
		pos.add(new Coordinate(rowIndex-1,colIndex,Direction.N));
		pos.add(new Coordinate(rowIndex-1,colIndex+1,Direction.NE));
		pos.add(new Coordinate(rowIndex,colIndex+1,Direction.E));
		pos.add(new Coordinate(rowIndex+1,colIndex+1,Direction.SE));
		pos.add(new Coordinate(rowIndex+1,colIndex,Direction.S));
		pos.add(new Coordinate(rowIndex+1,colIndex-1,Direction.SW));
		pos.add(new Coordinate(rowIndex,colIndex-1,Direction.W));
		pos.add(new Coordinate(rowIndex-1,colIndex-1,Direction.NW));
		ArrayList<Coordinate> validPos = new ArrayList<Coordinate>();
		
		for(int i=0;i<8;i++) {
			try {
				if(this.board[pos.get(i).row][pos.get(i).col].equals(oppoCellValue)) {
					validPos.add(pos.get(i));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				//boundary conditions will result in arrayIndexOutOfBounds
			}	
		}
		
		
		return validPos;
	}
	
	/**
	 * helper method to return CellValue.BLACK if identifier is WHITE and self=false, and so on...
	 * @param identifier
	 * @return
	 */
	private CellValue getCellValue(Color identifier, boolean self) {
		if(self) {
			if(identifier.equals(Color.WHITE))
				return CellValue.WHITE;
			else
				return CellValue.BLACK;
		}else {
			if(identifier.equals(Color.WHITE))
				return CellValue.BLACK;
			else
				return CellValue.WHITE;
		}
	}
	
	
}



