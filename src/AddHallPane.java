import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class AddHallPane extends Pane {

    public static Pane getPane(String film, String username) {

        Film chosenFilm = Data.films.get(film);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(15,15,15,15));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);

        Label textLabel = new Label(chosenFilm.name + " (" + chosenFilm.duration + "minutes)");
        Label rowLabel = new Label("Row:");
        ChoiceBox<String> rowChoiceBox = new ChoiceBox<>();
        Label columnLabel = new Label("Column:");
        ChoiceBox<String> columnChoiceBox = new ChoiceBox<>();
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        Label priceLabel = new Label("Price");
        TextField priceField = new TextField();
        Button okBtn = new Button("OK");
        Button backBtn = new Button("BACK");
        Label warning = new Label("");

        String[] seatChoices = {"3","4","5","6","7","8","9","10"};
        rowChoiceBox.getItems().addAll(seatChoices);
        rowChoiceBox.setValue("3");
        columnChoiceBox.getItems().addAll(seatChoices);
        columnChoiceBox.setValue("3");

        GridPane.setConstraints(textLabel,0,0,2,1);
        GridPane.setConstraints(rowLabel,0,1);
        GridPane.setConstraints(rowChoiceBox,1,1);
        GridPane.setConstraints(columnLabel,0,2);
        GridPane.setConstraints(columnChoiceBox,1,2);
        GridPane.setConstraints(nameLabel,0,3);
        GridPane.setConstraints(nameField,1,3);
        GridPane.setConstraints(priceLabel,0,4);
        GridPane.setConstraints(priceField,1,4);
        GridPane.setConstraints(backBtn,0,5);
        GridPane.setConstraints(okBtn,1,5);
        GridPane.setConstraints(warning,0,6,2,1);
        GridPane.setHalignment(textLabel,HPos.CENTER);
        GridPane.setHalignment(rowChoiceBox,HPos.CENTER);
        GridPane.setHalignment(columnChoiceBox,HPos.CENTER);
        GridPane.setHalignment(okBtn,HPos.RIGHT);

        okBtn.setOnAction(e -> okAction(rowChoiceBox,columnChoiceBox,nameField,priceField,film,warning));
        backBtn.setOnAction(e -> backAction(film,username));

        pane.getChildren().addAll(textLabel,rowLabel,rowChoiceBox,columnLabel,columnChoiceBox,nameLabel,nameField,priceLabel,
                                    priceField,backBtn,okBtn,warning);

        return pane;
    }

    private static void okAction(ChoiceBox<String> row, ChoiceBox<String> column, TextField name, TextField price, String film, Label warning ){

        if(name.getText().length() == 0){
            warning.setText("ERROR: Hall name can not be empty!");
            SceneController.playErrorEffect();
        }
        else if(price.getText().length() == 0){
            warning.setText("ERROR: Price can not be empty!");
            SceneController.playErrorEffect();
        }
        else if(Integer.parseInt(price.getText()) <= 0){
            warning.setText("ERROR: Price has to be a positive integer!");
            SceneController.playErrorEffect();
        }
        else if(Data.halls.containsKey(name.getText())){
            warning.setText("ERROR: This hall already exists!");
            SceneController.playErrorEffect();
        }
        else {
            Data.halls.put(name.getText(), new Hall(name.getText(),film,price.getText(),row.getValue(),column.getValue()));
            ArrayList<Seat> seats = new ArrayList<>();

            int rowInt = Integer.parseInt(row.getValue());
            int columnInt = Integer.parseInt(column.getValue());

            for(int i=0; i<rowInt; i++){
                for(int j=0; j<columnInt; j++){
                    seats.add(new Seat(film,name.getText(),i,j,"null",0));
                }
            }
            Data.seats.put(name.getText(),seats);
            warning.setText("SUCCESS: Hall created successfully");
        }
    }

    private static void backAction(String film, String username){
        SceneController.setScene(AdminFilmPane.getPane(film,username));
    }


}
