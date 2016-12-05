package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/application/DSA_GUI.fxml"));
			Scene scene = new Scene(root,723,589);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			Image icon = new Image(getClass().getResourceAsStream("/Icon/hard-drive.jpg"));
			primaryStage.getIcons().add(icon);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Disk Scheduling Simulator");
			primaryStage.setMaxWidth(589.0);
			primaryStage.setMaxWidth(723.0);
			primaryStage.show();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
