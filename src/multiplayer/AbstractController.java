package multiplayer;

import javafx.application.Application;

public abstract class AbstractController implements IController{
	
	protected Application main;
	protected String controllerName;
	
	
	/**
	 * overridden method to set the main application class instance for this game - helps to use methods such as getHostServices()
	 * purpose is to use a single instance of the main application class and prevent loading the start() method more than once
	 */
	public void setMain(Application main) {
		this.main = main;
		System.out.println(this.controllerName +":Main set");
	}

}
