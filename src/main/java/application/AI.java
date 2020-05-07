package main.java.application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;
import main.java.multiplayer.*;
/**
 * strategy : assign fitness value to each cell
 * make a move on the cell with best fitness
 * if multiple cells with equal best fitness found: make a move on the with maximum score
 * @author subhannitasarcar
 *
 */
public class AI extends AbstractPlayer implements IPlayer{

	private CellValue oppoCellValue;
	private CellValue selfCellValue;
	private int[][] priority = {{100,30,90,80,80,90,30,100},{30,20,40,50,50,40,20,30},{90,40,70,60,60,70,40,90},{80,50,60,10,10,60,50,80},{80,50,60,10,10,60,50,80},{90,40,70,60,60,70,40,90},{30,20,40,50,50,40,20,30},{100,30,90,80,80,90,30,100}};
	public AI(String playerName, int score, PlayerType playerType) {
		super(playerName, score, playerType);
		
		
		
		
		System.out.println("Reversii AI is here - beware!");
	}
	
	public void move(CellValue[][] board, IController boardController) throws IOException {
	
		
		if(this.getPlayerIdentifier().equals(Color.WHITE)) {
			selfCellValue = CellValue.WHITE;
			oppoCellValue = CellValue.BLACK;
		}
		else {
			selfCellValue = CellValue.BLACK;
			oppoCellValue = CellValue.WHITE;
		}
		
		HashMap<Coordinate, Integer> possibleMoves = new HashMap<Coordinate, Integer>();
		for(int i=0;i<board.length; i++) {
			for(int j=0;j<board[0].length; j++) {
				if(board[i][j].equals(CellValue.POSSIBLE)) {
					Coordinate c = new Coordinate(i,j);
					possibleMoves.put(c, priority[i][j]);
					System.out.println("AI: Possible move found : "+c.getRow()+","+c.getCol()+"--"+possibleMoves.get(c));
				}
			}
		}

		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
		for(int p=100; p>=10; p-=10) {
			for(Coordinate c : possibleMoves.keySet()) {
				if(possibleMoves.get(c) == p) {
					moves.add(c);
				}
			}
			if(moves.size()>0) {
				System.out.println("AI: Priority of best move : "+possibleMoves.get(moves.get(0)));
				break;
			}
		}
		
		int score = 1;
		Coordinate bestMove = null;
		for(Coordinate c : moves) {
			System.out.println("AI: possible best move:"+c.getRow()+","+c.getCol());
			int count1 = 0;
			ArrayList<Coordinate> validPositions = this.getValidPositions(c.getRow(), c.getCol(), board);
			for(Coordinate validPosition : validPositions) {
				Coordinate pos = validPosition;
				int count = 0;
				while(board[pos.getRow()][pos.getCol()].equals(oppoCellValue)) {
					count++;
					pos = nextPos(pos, c);
					if(pos==null) {	//boundary
						count = 0;	break;
					}else if(board[pos.getRow()][pos.getCol()].equals(CellValue.EMPTY) || board[pos.getRow()][pos.getCol()].equals(CellValue.POSSIBLE)) {	//empty or possible
						count = 0; break;
					}else if (board[pos.getRow()][pos.getCol()].equals(this.selfCellValue)) {	//another self tile found
						break;
					}
				}
				count1 += count;
			}
			System.out.println("AI:......"+count1);
			if(count1 >= score) {
				score = count1;
				bestMove = c;
			}
		}
		if(bestMove!=null) {
			System.out.println("best move:"+bestMove.getRow()+","+bestMove.getCol());
			boardController.handleClickAI(bestMove.getRow(), bestMove.getCol());
		}
	}
	
	private Coordinate nextPos(Coordinate pos1, Coordinate pos2) {
		
		int row, col;
		
		if(pos2.getRow() != pos1.getRow()) {
			if(pos2.getCol() < pos1.getCol()) 
				col = pos1.getCol()+1;
			else if(pos2.getCol() > pos1.getCol()) 
				col = pos1.getCol() -1;
			else 
				col = pos1.getCol();
			
			if(pos2.getRow() < pos1.getRow())
				row = pos1.getRow() +1;
			else
				row = pos1.getRow() -1;
			
		}else {
			row = pos1.getRow();
			if(pos2.getCol() > pos1.getCol())
				col = pos1.getCol() -1;
			else if(pos2.getCol() < pos1.getCol())
				col = pos1.getCol() +1;
			else
				col = pos1.getCol();
		}
		
		if(row<0 || row>7 || col<0 || col>7)
			return null;
		
		return new Coordinate(row,col);
		
	}
	
	/**
	 * helper method to return coordinates on all sides of [rowIndex,colIndex] that are of the opponent 
	 * @param identifier
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	private ArrayList<Coordinate> getValidPositions(int rowIndex, int colIndex, CellValue[][] board){
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
				if(board[pos.get(i).getRow()][pos.get(i).getCol()].equals(oppoCellValue)) {
					validPos.add(pos.get(i));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				//boundary conditions will result in arrayIndexOutOfBounds
			}	
		}
		
		
		return validPos;
	}
	

	
}
