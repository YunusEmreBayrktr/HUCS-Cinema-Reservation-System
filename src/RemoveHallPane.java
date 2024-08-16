import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class RemoveHallPane extends Pane{

    public static Pane getPane(String film, String username) {

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(15,15,15,15));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);

        Label textLabel = new Label("Select the hall that you desire to remove ");
        Button okBtn = new Button("OK");
        Button backBtn = new Button("BACK");
        ChoiceBox<String> hallChoice = new ChoiceBox<>();

        hallChoice.getItems().setAll(castHalls(film));
        if (hallChoice.getItems().size() != 0) {
            hallChoice.setValue(hallChoice.getItems().get(0));
        }

        GridPane.setConstraints(textLabel,0,0,2,1);
        GridPane.setConstraints(hallChoice,0,1,2,1);
        GridPane.setConstraints(okBtn,1,2);
        GridPane.setConstraints(backBtn,0,2);
        GridPane.setHalignment(textLabel,HPos.CENTER);
        GridPane.setHalignment(hallChoice,HPos.CENTER);
        GridPane.setHalignment(okBtn,HPos.RIGHT);

        okBtn.setOnAction(e -> okAction(hallChoice,film));
        backBtn.setOnAction(e -> backAction(film,username));

        pane.getChildren().addAll(textLabel,hallChoice,okBtn,backBtn);


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

    private static void okAction(ChoiceBox<String> choiceBox, String film){

        Data.seats.remove(choiceBox.getValue());
        Data.halls.remove(choiceBox.getValue());
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(castHalls(film));
        if(choiceBox.getItems().size() != 0){
            choiceBox.setValue(choiceBox.getItems().get(0));
        }
    }

    private static void backAction(String film, String username){
        SceneController.setScene(AdminFilmPane.getPane(film, username));
    }
}
