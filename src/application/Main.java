package application;

import multiplayer.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	static IMultiplayer game = null;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LaunchPage.fxml"));
		Parent root = loader.load();
        primaryStage.setTitle("Reversii Game");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        IController controller = loader.getController();
        controller.setMain(this);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		game = new Factory().getGame("reversii");
		launch(args);
	}
	
	
}
