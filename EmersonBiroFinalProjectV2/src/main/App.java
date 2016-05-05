package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MySQL;
import view.Login;
import view.WindowView;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MySQL sql = new MySQL();
		WindowView window = new WindowView(primaryStage);
		Login login = (Login) window.getLogin();
		
		new Controller(window, login, sql);
	}

}
