package driver;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.MySql;
import view.LoginView;

/**
 * 
 * 
 * This project is a recreation of the SAIN report in java
 * 
 * This is the App class, it extends Application to be used with javafx
 * 
 * @author Emerson Biro
 * 
 */

public class App extends Application {
	/**
	 * This is the main method, this is where the program starts All it does is
	 * launch the javafx
	 * 
	 * @param args
	 *            what the program starts with
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This is where the controller is created which manages the entire program
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setOnCloseRequest(e->{
			Platform.exit();
			System.exit(0);
		});
		new Controller(new LoginView(primaryStage, null), new MySql());
	}

}
