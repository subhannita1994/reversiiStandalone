package application;

import multiplayer.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	static IMultiplayer game = null;
	static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.launchXML("LaunchPage.fxml");
	}
	
	public static void main(String[] args) {
		game = Factory.getInstance().getGame("reversii");
		launch(args);
	}
	
	public static Stage getStage() {
		return Main.primaryStage;
	}
	
	public void launchXML(String filename) throws IOException{
		System.out.println("Loading "+filename);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
		Parent root = loader.load();
		primaryStage.setTitle(game.getGameName());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		IController controller = loader.getController();
		controller.setMain(this);
		primaryStage.show();
	}
	
	public static void quitGame() {
		Platform.exit();
	}
	
}
