import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class SignupPane extends Pane{
	
	public static Pane getPane() {
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		pane.setHgap(10);
		pane.setVgap(5);


		Label label1 = new Label("Welcome to the HUCS Cinema Reservation System!");
		Label label2 = new Label("Fill the form below to create a new account.");
		Label label3 = new Label("You can go to Log In page by clicking LOG IN button.");
		Label usernameLabel = new Label("Username:");
		TextField usernameField = new TextField();
		Label passwordLabel1 = new Label("Password:");
		PasswordField passwordField1 = new PasswordField();
		Label passwordLabel2 = new Label("Password:");
		PasswordField passwordField2 = new PasswordField();
		Button loginBtn = new Button("LOG IN");
		Button signupBtn = new Button("SIGN UP");
		Label warningLabel = new Label();
		pane.setAlignment(Pos.CENTER);

		GridPane.setConstraints(label1, 0, 0, 2, 1);
		GridPane.setConstraints(label2, 0, 1, 2, 1);
		GridPane.setConstraints(label3, 0, 2, 2, 1);
		GridPane.setConstraints(usernameLabel, 0, 3);
		GridPane.setConstraints(usernameField, 1, 3);
		GridPane.setConstraints(passwordLabel1, 0, 4);
		GridPane.setConstraints(passwordField1, 1, 4);
		GridPane.setConstraints(passwordLabel2, 0, 5);
		GridPane.setConstraints(passwordField2, 1, 5);
		GridPane.setConstraints(loginBtn, 0, 6);
		GridPane.setConstraints(signupBtn, 1, 6);
		GridPane.setConstraints(warningLabel,0,7,2,1);
		GridPane.setHalignment(signupBtn, HPos.RIGHT);
		GridPane.setHalignment(label1, HPos.CENTER);
		GridPane.setHalignment(label2, HPos.CENTER);
		GridPane.setHalignment(label3, HPos.CENTER);
		GridPane.setHalignment(warningLabel, HPos.CENTER);


		loginBtn.setOnAction(e -> SceneController.setScene(LoginPane.getPane()));
		signupBtn.setOnAction(e -> signupAction(usernameField,passwordField1,passwordField2,warningLabel));
		  
		 
		pane.getChildren().addAll(label1,label2,label3,loginBtn,signupBtn,usernameLabel,usernameField,
				  					passwordLabel1,passwordField1,passwordLabel2,passwordField2,warningLabel);
		
		return pane;
	}

	private static void signupAction(TextField usernameField, PasswordField passwordField1, PasswordField passwordField2, Label warning){

		String name = usernameField.getText();
		String password1 = String.valueOf(passwordField1.getText());
		String password2 = String.valueOf(passwordField2.getText());
		String hashedPassword = Data.hashPassword(password1);

		if(name.length() == 0){
			warning.setText("ERROR: Username cannot be empty!");
			SceneController.playErrorEffect();
		}
		else if (password1.length() == 0 || password2.length() == 0) {
			SceneController.playErrorEffect();
			warning.setText("ERROR: Password cannot be empty!");
		}
		else if (Data.users.containsKey(name)) {
			SceneController.playErrorEffect();
			warning.setText("ERROR: This usernameField already exists!");
		}
		else if (!password1.equals(password2)) {
			SceneController.playErrorEffect();
			warning.setText("ERROR: Passwords do not match!");
			passwordField1.clear();
			passwordField2.clear();
		}
		else {
			String[] user = {"user",name,hashedPassword,"false","false"} ;
			Data.users.put(name,new User(name,hashedPassword,"false","false"));
			warning.setText("SUCCESS: You have successfully registered with your new credentials!");
		}
	}
}
