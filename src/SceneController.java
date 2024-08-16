import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class SceneController {

	private static Stage stage;

	public SceneController(Stage stage) {
		SceneController.stage = stage;
	}

	public static void setScene(Pane pane) {
		
		Scene scene = new Scene(pane);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();
	}

	public static void playErrorEffect(){

		Media media = new Media(new File("assets/effects/error.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
}
