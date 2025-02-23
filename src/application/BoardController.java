package application;


import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import multiplayer.*;

public class BoardController extends AbstractController implements IController{

	@FXML Text playerName1;
	@FXML Text playerScore1;
	@FXML Text playerName2;
	@FXML Text playerScore2;
	@FXML HBox playerBox1;
	@FXML HBox playerBox2;
	private FadeTransition ft;
	private static GameConfiguration GAME_CONF;

	@FXML private GridPane gameBoard;
	
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
		this.playerNames = new Text[2];
		this.playerScores = new Text[2];
		this.playerBoxes = new HBox[2];
		this.playerNames[0] = playerName1;
		this.playerNames[1] = playerName2;
		this.playerScores[0] = playerScore1;
		this.playerScores[1] = playerScore2;
		this.playerBoxes[0] = playerBox1;
		this.playerBoxes[1] = playerBox2;
		
		ft = new FadeTransition();
		ft.setDuration(new Duration(2000));
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
	}
	
	
	
	public void drawBoard(CellValue[][] board){
		
		this.main.displayBoard();
		
		
		for(int i =0;i<GAME_CONF.getBoardSize()[0]; i++) {
			for(int j=0;j<GAME_CONF.getBoardSize()[1];j++) {
				//removing any previous circles
				StackPane stack = (StackPane) gameBoard.getChildren().get(GAME_CONF.getBoardSize()[0]*i + j + 1);
				for(int k = 0; k<stack.getChildren().size(); k++) {
					if(stack.getChildren().get(k) instanceof Circle)
						stack.getChildren().remove(k);
				}
				if(board[i][j].equals(CellValue.BLACK))
					drawCircle(Color.BLACK, j, i);
				else if(board[i][j].equals(CellValue.WHITE))
					drawCircle(Color.WHITE, j, i);
				else if(board[i][j].equals(CellValue.POSSIBLE)) {
					//drawing possible circles and adding event handlers to them
					drawCircle(Color.BURLYWOOD, j, i);
					StackPane stackPossible = (StackPane) gameBoard.getChildren().get(8*i + j + 1);
					
				      
					stackPossible.setOnMouseReleased(event -> {
						try {
							handleClickHuman(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					stackPossible.setOnMouseEntered(event -> {
						try {
							setCircleStroke(event,3.0);
						}catch (IOException e) {
							e.printStackTrace();
						}
					});
					stackPossible.setOnMouseExited(event -> {
						try {
							setCircleStroke(event,1.0);
						}catch (IOException e) {
							e.printStackTrace();
						}
					});
					
				}
			}
		}
		
		this.drawPlayerScores();
		
		this.highlightPlayerTurn();
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
	 * handle mouse click event from ONLY "possible circles" and human players
	 * @param e
	 * @throws IOException
	 */
	public void handleClickHuman(MouseEvent e) throws IOException{
		StackPane stack = (StackPane)e.getSource();
		System.out.println("Clicked on"+GridPane.getRowIndex(stack)+","+GridPane.getColumnIndex(stack)+" coods:"+e.getX()+","+e.getY()+","+e.getScreenX()+","+e.getScreenY()+"Button: "+e.getButton()+" click count:"+e.getClickCount()+" which buttton down:"+e.isPrimaryButtonDown()+"/"+e.isMiddleButtonDown()+"/"+e.isSecondaryButtonDown()+" pickResult:"+e.getPickResult());
		handleClick(stack);
	}
	/**
	 * handle mouse click from ONLY "possible circles" and human players
	 * @param rowIndex
	 * @param colIndex
	 * @throws IOException 
	 */
	public void handleClickAI(int rowIndex, int colIndex) throws IOException {
		StackPane stack = (StackPane)gameBoard.getChildren().get(GAME_CONF.getBoardSize()[0]*rowIndex+colIndex+1);
		System.out.println("Clicked on"+GridPane.getRowIndex(stack)+","+GridPane.getColumnIndex(stack));
		handleClick(stack);
	}
	
	private void handleClick(StackPane stack) throws IOException {
		for(int k = 0; k<stack.getChildren().size(); k++) {
			if(stack.getChildren().get(k) instanceof Circle)
				if(((Circle)stack.getChildren().get(k)).getFill().equals(Color.BURLYWOOD)) {
					ft.setNode(stack.getChildren().get(k));
					ft.play();
					this.main.move(GridPane.getRowIndex(stack), GridPane.getColumnIndex(stack));
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

	
	


	
	
}

