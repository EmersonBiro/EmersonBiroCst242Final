package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowView {

	private BorderPane root;
	private Scene scene;
	private Stage login;
	private Stage mainStage;

	public WindowView(Stage stage) {
		// save the main stage to a global variable
		mainStage = stage;

		// set login to a new stage, because login is a stage
		login = new Login();
		// show the login stage, which is just the login screen
		login.show();
	}

	public void displayMain() {
		login.close();
		mainStage.setTitle("Sain Report");
		root = new BorderPane();
		scene = new Scene(root, 1000, 500);

		mainStage.setScene(scene);
		mainStage.show();
	}

	public void studentView(String[] info, ObservableList<String> list) {
		displayMain();

		studentInfoTop(info);

		requiredCoursesTaken(list);

		for (int i = 0; i < list.size(); i++) {
			list.get(i);
		}

		setCenter();
	}

	public void setCenter() {
		GridPane center = new GridPane();
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setHgap(10);
		center.setVgap(10);
		
		Font font = new Font(24);
		
		Label reqCoursesTaken = new Label("Required Courses Taken");
		reqCoursesTaken.setAlignment(Pos.CENTER);
		reqCoursesTaken.setFont(font);
		GridPane.setConstraints(reqCoursesTaken, 0, 0);
		GridPane.setConstraints(rCTaken, 0, 1);
		
		center.getChildren().addAll(reqCoursesTaken, rCTaken);
		
		root.setCenter(center);
	}

	// this method creates the view for the basic info of the student
	public void studentInfoTop(String[] info) {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setHgap(100);
		gp.setVgap(10);

		Label nameL = new Label("Name:");
		GridPane.setConstraints(nameL, 0, 0);
		Label nameR = new Label();
		GridPane.setConstraints(nameR, 1, 0);

		Label idL = new Label("ID: ");
		GridPane.setConstraints(idL, 0, 1);
		Label idR = new Label();
		GridPane.setConstraints(idR, 1, 1);

		Label majorL = new Label("Major: ");
		GridPane.setConstraints(majorL, 0, 2);
		Label majorR = new Label();
		GridPane.setConstraints(majorR, 1, 2);

		Label gpaL = new Label("GPA: ");
		GridPane.setConstraints(gpaL, 0, 3);
		Label gpaR = new Label();
		GridPane.setConstraints(gpaR, 1, 3);

		Label campusL = new Label("Campus: ");
		GridPane.setConstraints(campusL, 0, 4);
		Label campusR = new Label();
		GridPane.setConstraints(campusR, 1, 4);

		idR.setText(info[0]);
		nameR.setText(info[1]);
		gpaR.setText(info[2]);
		majorR.setText(info[3]);
		campusR.setText(info[4]);

		gp.getChildren().addAll(nameL, nameR, idL, idR, majorL, majorR, gpaL, gpaR, campusL, campusR);

		root.setTop(gp);
	}

	private ListView<String> rCTaken;

	// this method creates the view
	public void requiredCoursesTaken(ObservableList<String> list) {

		rCTaken = new ListView<String>();
		rCTaken.setItems(list);

	}

	public void errorWindow() {
		// this is just the error window you get if you failed to login
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Username or Password");
		alert.setHeaderText("The Username or Password you entered is incorrect");
		alert.setContentText("Try again or exit!");

		alert.showAndWait();
	}

	public Stage getLogin() {
		return login;
	}

}
