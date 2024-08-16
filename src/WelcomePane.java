import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class WelcomePane extends Pane{
	
	public static Pane getPane(String username) {

		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		pane.setHgap(10);
		pane.setVgap(5);
		pane.setAlignment(Pos.CENTER);

		Label text1 = new Label("Welcome " + username + "!");
		if (Data.users.get(username).clubMember.equals("true")){
			text1.setText("Welcome " + username + "! (Club Member)");
		}
		Label text2 = new Label("Select a film and then click OK to continue.");
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		Button okBtn = new Button("OK");
		Button logoutBtn = new Button("LOG OUT");

		choiceBox.getItems().addAll(castFilms());
		if(choiceBox.getItems().size() != 0){
			choiceBox.setValue(choiceBox.getItems().get(0));
		}



		GridPane.setConstraints(text1, 0, 0, 2, 1);
		GridPane.setConstraints(text2, 0, 1, 2, 1);
		GridPane.setConstraints(choiceBox, 0, 2, 1, 1);
		GridPane.setConstraints(okBtn, 1, 2, 1, 1);
		GridPane.setConstraints(logoutBtn, 1, 3, 1, 1);
		GridPane.setHalignment(logoutBtn, HPos.LEFT);
		GridPane.setHalignment(text1, HPos.CENTER);
		GridPane.setHalignment(text2, HPos.CENTER);

		logoutBtn.setOnAction(e -> SceneController.setScene(LoginPane.getPane()));
		okBtn.setOnAction(e -> {
			if(choiceBox.getValue() != null){
				SceneController.setScene(FilmPane.getPane(choiceBox.getValue(),username));}
		});

		pane.getChildren().addAll(text1,text2,choiceBox,okBtn,logoutBtn);

		return pane;
	}
	
	public static String[]  castFilms() {
		
		ArrayList<String> filmList = new ArrayList<String>(Data.films.keySet());
		String[] arr = new String[filmList.size()];
		
		for(int i=0; i<filmList.size();i++) {
			arr[i] = filmList.get(i);
		}
		
		return arr;	
	}
}




