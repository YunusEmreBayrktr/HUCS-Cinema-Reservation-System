import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RemoveFilmPane {

    public static Pane getPane(String username){

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15,15,15,15));
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        Label textLabel = new Label("Select the film that you desire to remove and then click OK.");
        Button okBtn = new Button("OK");
        Button backBtn = new Button("BACK");
        Label warningLabel = new Label();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().addAll(castFilms());
        if(choiceBox.getItems().size() != 0){
            choiceBox.setValue(choiceBox.getItems().get(0));
        }

        hBox.getChildren().addAll(backBtn,okBtn);
        vBox.getChildren().addAll(textLabel,choiceBox,hBox,warningLabel);

        okBtn.setOnAction(e -> okAction(choiceBox));
        backBtn.setOnAction(e -> backAction(username));


        return vBox;
    }

    public static String[]  castFilms() {

        ArrayList<String> filmList = new ArrayList<String>(Data.films.keySet());
        String[] arr = new String[filmList.size()];

        for(int i=0; i<filmList.size();i++) {
            arr[i] = filmList.get(i);
        }

        return arr;
    }

    public static void okAction(ChoiceBox<String> choiceBox){

        ArrayList<String> hallsToRemove = new ArrayList<>();

        for(Hall hall : Data.halls.values()){
            if(hall.film.equals(choiceBox.getValue())){
                hallsToRemove.add(hall.name);
            }
        }
        for(String hall : hallsToRemove){
            Data.halls.remove(hall);
        }

        Data.films.remove(choiceBox.getValue());
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(castFilms());
        if(choiceBox.getItems().size() != 0){
            choiceBox.setValue(choiceBox.getItems().get(0));
        }
    }

    public static void backAction(String username){
        SceneController.setScene(AdminWelcomePane.getPane(username));
    }
}
