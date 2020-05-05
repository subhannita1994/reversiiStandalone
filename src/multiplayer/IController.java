package multiplayer;


import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;

public interface IController {
	
	public void setMain(Main main);
	public void endGameBtnHandler(ActionEvent e) throws IOException;
	public void drawBoard(CellValue[][] board);
	public void declareWinner(IPlayer winner) throws IOException;
	public void restartGame(ActionEvent e) throws IOException;
	public void drawPlayerNames(String[] playerNames);

}
