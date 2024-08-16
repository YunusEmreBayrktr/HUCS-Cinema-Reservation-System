import java.io.File;
import java.util.ArrayList;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class FilmPane extends Pane{
	

	public static Pane getPane(String film, String username)  {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(15,15,15,15));
		VBox verticalButtons = new VBox();
		HBox horizontalButtons = new HBox();

		Label text = new Label(Data.films.get(film).name + " (" + Data.films.get(film).duration + " minutes)");
		Button playBtn = new Button("  ▶  ");
		Button forwardBtn = new Button(" >>");
		Button backwardBtn = new Button("<< ");
		Button restartBtn = new Button("|<<");
		Button okBtn = new Button("OK");
		Button backBtn = new Button("BACK");
		Slider volumeSlider = new Slider(0.0,100.0,50.0);
		Media media = new Media(new File("assets/trailers/"+Data.films.get(film).path).toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		MediaView viewer = new MediaView(player);
		ChoiceBox<String> hallChoice = new ChoiceBox<>();

		hallChoice.getItems().setAll(castHalls(film));
		if(hallChoice.getItems().size() != 0) {
			hallChoice.setValue(hallChoice.getItems().get(0));
		}

		verticalButtons.setSpacing(15);
		verticalButtons.setAlignment(Pos.CENTER);
		horizontalButtons.setSpacing(15);
		horizontalButtons.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(text,Pos.CENTER);
		BorderPane.setAlignment(viewer,Pos.CENTER);
		BorderPane.setMargin(viewer, new Insets(15,15,15,15));
		volumeSlider.setOrientation(Orientation.VERTICAL);
		viewer.setFitWidth(1100);
		viewer.setFitHeight(600);


		playBtn.setOnAction(e -> playAction(player,playBtn));
		forwardBtn.setOnAction(e -> forwardAction(player));
		backwardBtn.setOnAction(e -> backwardAction(player));
		restartBtn.setOnAction(e -> restartAction(player));
		backBtn.setOnAction(e -> backAction(player, username));
		okBtn.setOnAction(e -> okAction(player, hallChoice, username));
		volumeSlider.setValue(player.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if(volumeSlider.isValueChanging()){
					player.setVolume(volumeSlider.getValue()/100.0);
				}
			}
		});

	verticalButtons.getChildren().addAll(playBtn,backwardBtn,forwardBtn,restartBtn,volumeSlider);
	horizontalButtons.getChildren().addAll(backBtn,hallChoice,okBtn);

	pane.setTop(text);
	pane.setCenter(viewer);
	pane.setRight(verticalButtons);
	pane.setBottom(horizontalButtons);

		
	return pane;
	}


	private static String[]  castHalls(String film) {

		ArrayList<String> halls = new ArrayList<>();

		for(Hall hall : Data.halls.values()){
			if(hall.film.equals(film)){
				halls.add(hall.name);
			}
		}
		String[] arr = new String[halls.size()];

		for(int i=0; i<halls.size(); i++){
			arr[i] = halls.get(i);
		}

		return arr;
	}


	private static void playAction(MediaPlayer player, Button playBtn){

		player.play();
		playBtn.setText("  ||  ");
		if (player.getStatus().equals(MediaPlayer.Status.PAUSED)){
			player.play();
			playBtn.setText("  ||  ");

		}
		else if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
			player.pause();
			playBtn.setText("  ▶  ");
		}
	}

	private static void forwardAction(MediaPlayer player){

		if (player.getCurrentTime().toSeconds()<player.getTotalDuration().toSeconds()-5){
			player.seek(Duration.seconds(player.getCurrentTime().toSeconds()+5));
		}
	}

	private static void backwardAction(MediaPlayer player){

		if(player.getCurrentTime().toSeconds()>5){
			player.seek(Duration.seconds(player.getCurrentTime().toSeconds()-5));
		}
	}

	private static void restartAction(MediaPlayer player){
		player.seek(Duration.ZERO);
	}

	private static void backAction(MediaPlayer player, String username){
		player.stop();
		SceneController.setScene(WelcomePane.getPane(username));
	}

	private static void okAction(MediaPlayer player,ChoiceBox<String> hallChoice, String username){
		player.stop();
		String hall = hallChoice.getValue();
		if (hall != null) {
			SceneController.setScene(HallPane.getPane(hall,username));
		}
	}
}
