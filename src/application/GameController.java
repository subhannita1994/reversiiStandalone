package application;
import multiplayer.*;

import java.io.IOException;
import java.util.*;


public class GameController extends AbstractController implements IController{

	public GameController() {
		this.controllerName = "Game Controller";
	}
	
	private int numberOfPlayers;
	
	public void setNumberOfPlayers(int n) {
		numberOfPlayers = n;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	private Player currentPlayer;
	
	private ArrayList<Player> playerList;
	
	
	public void setPlayerList(ArrayList<Player> pl) {
		playerList = pl;
	}
	
	private Player winner = null;
	
	public void setWinner(Player win) {
		winner = win;
	}
	
	public void takeTurns() throws IOException {

		int i = 0;
		//i = playerList.indexOf(this.currentPlayer);
		while (winner == null) {
			i = i % playerList.size();
			currentPlayer = playerList.get(i);
			System.out.println("==============" + currentPlayer.getPlayerName() + "'S TURN==================");
			System.out.println("Initial Number of ");

			//TODO
			//player play his turn
			}
			i++;

		}
	}

