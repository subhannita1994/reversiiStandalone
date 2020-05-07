package main.java.multiplayer;


import java.io.IOException;

import main.java.application.Main;
import javafx.event.ActionEvent;

public interface IController {
	
	public void setMain(Main main);
	public void endGameBtnHandler(ActionEvent e) throws IOException;
	public void drawBoard(CellValue[][] board);
	public void declareWinner(String winner) throws IOException;
	public void restartGame(ActionEvent e) throws IOException;
	public void drawPlayerNames(String[] playerNames);
	public void handleClickAI(int rowIndex, int colIndex)throws IOException;
}
