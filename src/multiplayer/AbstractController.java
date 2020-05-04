package multiplayer;

import application.Main;

public abstract class AbstractController implements IController{
	
	protected Main main;
	protected String controllerName;
	
	
	/**
	 * overridden method to set the main application class instance for this game - helps to use methods such as getHostServices()
	 * purpose is to use a single instance of the main application class and prevent loading the start() method more than once
	 */
	public void setMain(Main main) {
		this.main = main;
		System.out.println(this.controllerName +":Main set");
	}
	
	public void drawBoard(CellValue[][] board){
		
	}

	
}
