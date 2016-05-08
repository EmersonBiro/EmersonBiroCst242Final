package view;

import java.util.ArrayList;

import handling.GloblVars;
import handling.GloblVars.Events;
import handling.Observer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView extends ViewGeneric {

	private TextField usernamet;
	private PasswordField passwordt;

	public LoginView(Stage stage, ArrayList<Observer> obsArr) {
		super(stage, new VBox(10), GloblVars.LV_WIDTH, GloblVars.LV_HEIGHT, obsArr);
		VBox vb = (VBox) getRoot();
		vb.setStyle("-fx-alignment: center center");
		obsArr = new ArrayList<>();

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(0, 10, 10, 10)); // top right bottom left

		HBox title = new HBox(10);
		title.setPadding(new Insets(0, 10, 0, 10));
		Label loginInfo = new Label("Enter username and password");
		loginInfo.setFont(new Font(16));
		title.getChildren().addAll(loginInfo);

		Label usernamel = new Label("Username:");
		usernamet = new TextField();
		usernamet.setPromptText("username");
		GridPane.setConstraints(usernamel, 0, 0);
		GridPane.setConstraints(usernamet, 1, 0);

		Label passwordl = new Label("Password:");
		passwordt = new PasswordField();
		passwordt.setPromptText("password");
		GridPane.setConstraints(passwordl, 0, 1);
		GridPane.setConstraints(passwordt, 1, 1);

		Button loginb = new Button("Login");
		loginb.setMinWidth(100);
		GridPane.setConstraints(loginb, 0, 2);

		Button exitb = new Button("Exit");
		exitb.setMinWidth(100);
		GridPane.setConstraints(exitb, 1, 2);

		gridPane.getChildren().addAll(usernamel, usernamet, passwordl, passwordt, loginb, exitb);
		vb.getChildren().addAll(title, gridPane);

		loginb.setOnAction(e -> {
			NotifyObservers(Events.LV_LOGIN_BUTTON);
		});

		exitb.setOnAction(e -> {
			NotifyObservers(Events.EXIT_BUTTON);
		});

		init();

	}

	public void stop() {
		stage.close();
	}

	public String getUsername() {
		return usernamet.getText();
	}

	public String getPassword() {
		return passwordt.getText();
	}
}
