package application;


import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import multiplayer.*;

public class BoardController extends AbstractController implements IController{

	@FXML GridPane gameBoard;
	private static GameConfiguration GAME_CONF;
	
	public BoardController() {
		this.controllerName="BoardController";
	}
	
	@FXML
	public void initialize() {
		System.out.println("Displaying game board");
		/**
		 * we cannot use any non-static members of Main class here 
		 * because FXML follows this sequence when it is loaded - 
		 * 1. initialize the controller
		 * 2. inject the FXML objects from the xml document to the controller class
		 * 3. run controller's intialize()
		 * 4. and finally do other stuff such as calling controller's setMain() method
		 * 
		 * Therefore, this.main is null as of now and we have to make use of Main's static gameConfiguration.
		 * Fortunately, gameConfiguration is same throughout 
		 * but game may not be same for a different set of players (in case of multiplayer)
		 */
		GAME_CONF = Main.getGameConfiguration();
		
		for (int i = 0; i < GAME_CONF.getBoardSize()[0]; i++) {
	        for (int j = 0; j < GAME_CONF.getBoardSize()[1]; j++) {

	        	//adding tiles to board
	            Rectangle tile = new Rectangle(GAME_CONF.getTileSize(), GAME_CONF.getTileSize());
	            tile.setFill(Color.BURLYWOOD);
	            tile.setStroke(Color.BLACK);
	            StackPane stack = new StackPane();
	            stack.getChildren().add(tile);
	            gameBoard.add(stack,j,i);
	        }
	    }
	}
	
	public void drawBoard(CellValue[][] board){
		for(int i =0;i<GAME_CONF.getBoardSize()[0]; i++) {
			for(int j=0;j<GAME_CONF.getBoardSize()[1];j++) {
				
				if(board[i][j].equals(CellValue.BLACK))
					drawCircle(Color.BLACK, j, i);
				else if(board[i][j].equals(CellValue.WHITE))
					drawCircle(Color.WHITE, j, i);
				else if(board[i][j].equals(CellValue.POSSIBLE)) {
					//drawing possible circles and adding event handlers to them
					drawCircle(Color.BURLYWOOD, j, i);
					StackPane stack = (StackPane) gameBoard.getChildren().get(8*i + j + 1);
					stack.setOnMouseReleased(event -> {
						try {
							handleClick(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					stack.setOnMouseEntered(event -> {
						try {
							setCircleStroke(event,3.0);
						}catch (IOException e) {
							e.printStackTrace();
						}
					});
					stack.setOnMouseExited(event -> {
						try {
							setCircleStroke(event,1.0);
						}catch (IOException e) {
							e.printStackTrace();
						}
					});
					
				}else if(board[i][j].equals(CellValue.EMPTY)) {
					//removing any previous possible  circles
					StackPane stack = (StackPane) gameBoard.getChildren().get(8*i + j + 1);
					for(Node n : stack.getChildren())
						if(n instanceof Circle)
							stack.getChildren().remove(n);
				}
			}
		}
	}
	
	/**
	 * draw circle of color on board
	 */
	private void drawCircle(Color color, int colIndex, int rowIndex) {
		int circleSize = GAME_CONF.getTileSize()/2 -4;
		Circle c = new Circle(circleSize, color);
		if(color.equals(Color.BURLYWOOD))	//for "possible circles"
			c.setStroke(Color.DARKGRAY);
		((StackPane)gameBoard.getChildren().get(rowIndex * GAME_CONF.getBoardSize()[0] + colIndex + 1)).getChildren().add(c);
		
	}
	
	/**
	 * handle mouse click event from ONLY "possible circles"
	 * @param e
	 * @throws IOException
	 */
	private void handleClick(MouseEvent e)throws IOException{
		StackPane stack = (StackPane)e.getSource();
		for(Node n : stack.getChildren()) {
			if(n instanceof Circle) {
				if(((Circle)n).getFill().equals(Color.BURLYWOOD)) {
					System.out.println("Player made a move on "+GridPane.getRowIndex(stack)+","+GridPane.getColumnIndex(stack));
				}
			}
		}
	}

	/**
	 * animated effects on "possible circle"s
	 * @param stroke
	 * @throws IOException
	 */
	private void setCircleStroke(MouseEvent e, double value)throws IOException{
		StackPane stack = (StackPane)e.getSource();
		for(Node n : stack.getChildren()) {
			if(n instanceof Circle) {
				if(((Circle)n).getFill().equals(Color.BURLYWOOD))
					((Circle)n).setStrokeWidth(value);
			}
		}
	}

	@FXML
	public void endGameBtnHandler(ActionEvent e) throws IOException{
		System.out.println("Player wants to quit");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("End Game");
		alert.setContentText("Do you really want to quit?");
		ButtonType yesBtn = new ButtonType("Yes");
		ButtonType noBtn = new ButtonType("No");
		alert.getButtonTypes().setAll(yesBtn,noBtn);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get().equals(yesBtn)) {
			System.out.println("bye, player");
			this.main.quitGame();
		}else if(result.get().equals(noBtn)) {
			System.out.println("...on second thoughts, player wants to continue");
		}
		alert.close();
	}

	
}
