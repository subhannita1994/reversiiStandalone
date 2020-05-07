package main.java.multiplayer;

public class Coordinate{
	int row;
	int col;
	Direction dir;
	public Coordinate(int row, int col, Direction dir) {
		this.row = row;	this.col  = col;	this.dir  = dir;
	}
	public Coordinate(int row, int col) {
		this.row =  row;	this.col = col;
	}
	public int getRow() {
		return this.row;
	}
	public int getCol() {
		return this.col;
	}
	public Direction getDir() {
		return this.dir;
	}
	@Override
	public boolean equals(Object obj) {
		if(((Coordinate)obj).getCol() == this.col && ((Coordinate)obj).getRow() == this.row )
			return true;
		else
			return false;
	}
	
}