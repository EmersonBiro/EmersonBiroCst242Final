package driver;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MySql;
import view.LoginView;

public class App extends Application{

	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Controller(new LoginView(primaryStage, null), new MySql());
	}

}
