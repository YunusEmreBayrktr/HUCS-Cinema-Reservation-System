import java.io.File;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
      launch(args);
  }
  
  

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage)  {

      new Data();
      new SceneController(primaryStage);

	  primaryStage.setTitle(Data.title);
      primaryStage.getIcons().add(new Image(new File("assets/icons/logo.png").toURI().toString()));
      primaryStage.setOnCloseRequest(e -> Data.saveBakupData());
	  SceneController.setScene(LoginPane.getPane());
      primaryStage.sizeToScene();

	  primaryStage.show();

  }
} 

