import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.File;

public class AddFilmPane extends Pane {

    public static Pane getPane(String username){

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(15,15,15,15));
        pane.setHgap(10);
        pane.setVgap(5);

        Label textLabel = new Label("Please give name, relative path of the trailer and duration of the film.");
        Label filmNameLabel = new Label("Name:");
        TextField filmNameField = new TextField();
        Label pathLabel = new Label("Trailer (Path):");
        TextField pathField = new TextField();
        Label durationLabel = new Label("Duration (m):");
        TextField durationField = new TextField();
        Button okBtn = new Button("OK");
        Button backBtn = new Button("BACK");
        Label warningLabel = new Label();
        pane.setAlignment(Pos.CENTER);

        GridPane.setConstraints(textLabel, 0, 0, 2, 1);
        GridPane.setConstraints(filmNameLabel, 0, 1);
        GridPane.setConstraints(filmNameField, 1, 1);
        GridPane.setConstraints(pathLabel, 0, 2);
        GridPane.setConstraints(pathField, 1, 2);
        GridPane.setConstraints(durationLabel, 0, 3);
        GridPane.setConstraints(durationField, 1, 3);
        GridPane.setConstraints(okBtn, 1, 4);
        GridPane.setConstraints(backBtn, 0, 4);
        GridPane.setConstraints(warningLabel, 0, 5, 3, 1);
        GridPane.setHalignment(okBtn, HPos.RIGHT);
        GridPane.setHalignment(textLabel, HPos.CENTER);
        GridPane.setHalignment(warningLabel, HPos.CENTER);

        okBtn.setOnAction(e -> okAction(filmNameField,pathField,durationField,warningLabel));
        backBtn.setOnAction(e -> backAction(username));

        pane.getChildren().addAll(textLabel,filmNameLabel,filmNameField,pathLabel,pathField,durationLabel,
                                    durationField,okBtn,backBtn,warningLabel);

        return pane;
    }

    private static void okAction(TextField nameField, TextField pathField, TextField durationField, Label warning ){

        String name = nameField.getText();
        String path = pathField.getText();
        String duration = durationField.getText();

        if(name.length() == 0){
            warning.setText("ERROR: Film name can not be empty!");
            SceneController.playErrorEffect();
        }
        else if(path.length() == 0){
            warning.setText("ERROR: Trailer path can not be empty!");
            SceneController.playErrorEffect();
        }
        else if(duration.length() == 0){
            warning.setText("ERROR: Duration can not be empty!");
            SceneController.playErrorEffect();
        }
        else if(Integer.parseInt(duration) <= 0){
            warning.setText("ERROR: Duration has to be a positive integer!");
            SceneController.playErrorEffect();
        }
        else if(!(new File("assets/trailers/"+path).isFile())){
            warning.setText("ERROR: There is no such a trailer!");
            SceneController.playErrorEffect();
        }
        else if(Data.films.containsKey(name)){
            warning.setText("ERROR: This film already exists!");
            SceneController.playErrorEffect();
        }
        else{
            Data.films.put(name, new Film(name,path,duration));
            warning.setText("SUCCESS: Film added successfully!");
        }
    }

    public static void backAction(String username){
        SceneController.setScene(AdminWelcomePane.getPane(username));
    }
}
