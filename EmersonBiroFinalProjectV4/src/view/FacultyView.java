package view;

import java.util.ArrayList;

import handling.GloblVars;
import handling.Observer;
import handling.GloblVars.Events;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FacultyView extends ViewGeneric {
	private BorderPane root;

	public FacultyView(Stage stage, ArrayList<Observer> obsArr) {
		super(stage, new BorderPane(), GloblVars.SV_WIDTH, GloblVars.SV_HEIGHT, obsArr);
		root = (BorderPane) getRoot();
		obsArr = new ArrayList<>();

		setCenter();
		init();
	}

	// this method sets the general info of the faculty to the top
	public void facultyInfoTop(String[] info) {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 0, 10));
		gp.setHgap(100);
		gp.setVgap(5);

		Label nameL = new Label("Name:");
		GridPane.setConstraints(nameL, 0, 0);
		Label nameR = new Label();
		GridPane.setConstraints(nameR, 1, 0);

		Label idL = new Label("ID: ");
		GridPane.setConstraints(idL, 0, 1);
		Label idR = new Label();
		GridPane.setConstraints(idR, 1, 1);

		Label rankL = new Label("Rank: ");
		GridPane.setConstraints(rankL, 0, 2);
		Label rankR = new Label();
		GridPane.setConstraints(rankR, 1, 2);

		idR.setText(info[0]);
		nameR.setText(info[1]);
		rankR.setText(info[2]);

		gp.getChildren().addAll(nameL, nameR, idL, idR, rankL, rankR);

		root.setTop(gp);
	}
	// end of faculty info method

	TextField searcht;

	public void setCenter() {
		VBox center = new VBox(10);
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setAlignment(Pos.CENTER);

		Label searchl = new Label("Enter Student ID to search");
		searcht = new TextField();
		searcht.setMaxWidth(100);
		Button searchb = new Button("Search");
		Button logout = new Button("Logout");
		
		logout.setOnAction(e->{
			NotifyObservers(Events.LOGOUT_BUTTON);
		});

		searchb.setOnAction(e -> {
			NotifyObservers(Events.FV_SEARCH_BUTTON);
		});

		center.getChildren().addAll(searchl, searcht, searchb, logout);
		root.setCenter(center);
	}

	public String getId() {
		return searcht.getText();
	}

}
