package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Stage {

	public Login() {
		this.setTitle("SAIN LOGIN");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 175);

		root.setTop(top());

		this.setResizable(false);
		this.setScene(scene);
	}

	private TextField usernamet;
	private PasswordField passwordt;
	private Button loginb;

	public VBox top() {
		VBox top = new VBox(10);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(0, 10, 10, 10)); // top right bottom left

		HBox title = new HBox(10);
		title.setPadding(new Insets(10, 10, 10, 10));
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

		loginb = new Button("Login");
		loginb.setMinWidth(100);
		GridPane.setConstraints(loginb, 0, 2);

		Button exitb = new Button("Exit");
		exitb.setMinWidth(100);
		GridPane.setConstraints(exitb, 1, 2);

		gridPane.getChildren().addAll(usernamel, usernamet, passwordl, passwordt, loginb, exitb);
		top.getChildren().addAll(title, gridPane);

		exitb.setOnAction(e -> {
			System.exit(0);
		});

		return top;
	}

	public void loginButtonPressed(EventHandler<ActionEvent> ev) {
		loginb.setOnAction(ev);
	}

	public String getUsernamet() {
		return usernamet.getText();
	}

	public String getPasswordt() {
		return passwordt.getText();
	}

}
