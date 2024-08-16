import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class LoginPane extends Pane{


	public static Pane getPane() {
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		pane.setHgap(10);
		pane.setVgap(5);


		Label text1 = new Label("Welcome to the HUCS Cinema Reservation System!");
		Label text2 = new Label("Please enter your credentials below and click LOGIN");
		Label text3 = new Label("You can create a new account by clicking SIGN UP button");
		Label usernameLabel = new Label("Username:");
		TextField usernameField = new TextField();
		Label passwordLabel = new Label("Password:");
		PasswordField passwordField = new PasswordField();
		Button loginBtn = new Button("LOG IN");
		Button signupBtn = new Button("SIGN UP");
		Label warning = new Label();


		pane.setAlignment(Pos.CENTER);
		text1.setMaxWidth(Double.MAX_VALUE);
		text1.setAlignment(Pos.CENTER);
		text2.setMaxWidth(Double.MAX_VALUE);
		text2.setAlignment(Pos.CENTER);
		text3.setMaxWidth(Double.MAX_VALUE);
		text3.setAlignment(Pos.CENTER);
		signupBtn.setAlignment(Pos.TOP_RIGHT);
		warning.setMaxWidth(Double.MAX_VALUE);
		warning.setAlignment(Pos.CENTER);


		GridPane.setConstraints(text1, 0, 0, 2, 1);
		GridPane.setConstraints(text2, 0, 1, 2, 1);
		GridPane.setConstraints(text3, 0, 2, 2, 1);
		GridPane.setConstraints(usernameLabel, 0, 3);
		GridPane.setConstraints(usernameField, 1, 3);
		GridPane.setConstraints(passwordLabel, 0, 4);
		GridPane.setConstraints(passwordField, 1, 4);
		GridPane.setConstraints(loginBtn, 1, 5);
		GridPane.setConstraints(signupBtn, 0, 5);
		GridPane.setConstraints(warning,0,6,2,1);
		GridPane.setHalignment(loginBtn, HPos.RIGHT);


		signupBtn.setOnAction(e -> SceneController.setScene(SignupPane.getPane()));
		loginBtn.setOnAction(e -> loginAction(usernameField, passwordField, warning));
		  

		pane.getChildren().addAll(text1,text2,text3,loginBtn,signupBtn,usernameLabel,usernameField,
				  					passwordLabel,passwordField,warning);
		
		return pane;
	}

	private static void loginAction(TextField username, PasswordField password, Label warning){
		String name = username.getText();
		String hashedPassword = Data.hashPassword(String.valueOf(password.getText()));

		if(Data.users.containsKey(name)) {
			if(Data.users.get(name).password.equals(hashedPassword) && Data.users.get(name).admin.equals("true")) {
				SceneController.setScene(AdminWelcomePane.getPane(name));
			}
			else if(Data.users.get(name).password.equals(hashedPassword)){
				SceneController.setScene(WelcomePane.getPane(name));
			}
			else{
				SceneController.playErrorEffect();
				warning.setText("ERROR: There is no such a credential!");
				password.clear();
			}
		}
		else{
			SceneController.playErrorEffect();
			warning.setText("ERROR: There is no such a credential!");
			password.clear();
		}
	}
}