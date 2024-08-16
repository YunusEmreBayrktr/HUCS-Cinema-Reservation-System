import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.File;
import java.util.ArrayList;


public class AdminHallPane extends Pane  {

    static GridPane gridPane = new GridPane();
    static Hall chosenHall;
    static Film chosenFilm;
    static String username ;
    static Label info = new Label("");

    static Label warning = new Label("");

    public static Pane getPane(String hall, String username) {

        AdminHallPane.chosenHall = Data.halls.get(hall);
        AdminHallPane.chosenFilm = Data.films.get(chosenHall.film);
        AdminHallPane.username = username;

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15,15,15,15));
        vBox.setFillWidth(true);

        Label text = new Label(chosenFilm.name + " (" + chosenFilm.duration + " minutes) Hall: " + chosenHall.name);
        Button backBtn = new Button("BACK");
        ChoiceBox<String> usersBox = new ChoiceBox<>();

        usersBox.getItems().addAll(castUsers());
        if(usersBox.getItems().size() != 0){
            usersBox.setValue(usersBox.getItems().get(0)
            );
        }


        createButton(usersBox);

        backBtn.setOnAction(e ->{
            AdminHallPane.info.setText("");
            AdminHallPane.warning.setText("");
            gridPane.getChildren().clear();
            SceneController.setScene(AdminFilmPane.getPane(chosenFilm.name,username));
        });

        vBox.getChildren().addAll(text, gridPane,usersBox, AdminHallPane.info,warning,backBtn);

        return vBox;
    }

    private static void createButton(ChoiceBox<String> usersBox){

        for(Seat seat : Data.seats.get(chosenHall.name)){
            Image emptyImage = new Image(new File("assets/icons/empty_seat.png").toURI().toString());
            ImageView emptyImageView = new ImageView(emptyImage);
            emptyImageView.setFitHeight(60);
            emptyImageView.setFitWidth(60);

            Image reservedImage = new Image(new File("assets/icons/reserved_seat.png").toURI().toString());
            ImageView reservedImageView = new ImageView(reservedImage);
            reservedImageView.setFitWidth(60);
            reservedImageView.setFitHeight(60);

            Button button = new Button();
            seat.setButton(button);


            if(!(seat.owner.equals("null"))){
                button.setGraphic(reservedImageView);
                button.setDisable(false);
            }
            else{
                button.setGraphic(emptyImageView);
            }

            button.setOnAction(e -> buttonAction(button,usersBox));
            button.setOnMouseEntered(e ->hoverAction(button));
            button.setOnMouseExited(e ->AdminHallPane.info.setText(""));
            GridPane.setConstraints(button,seat.column,seat.row);
            gridPane.getChildren().add(button);
        }
    }

    private static void buttonAction(Button button, ChoiceBox<String> usersBox){

        Image image = new Image(new File("assets/icons/reserved_seat.png").toURI().toString());
        ImageView reservedImageView = new ImageView(image);
        reservedImageView.setFitHeight(60);
        reservedImageView.setFitWidth(60);

        Image emptyImage = new Image(new File("assets/icons/empty_seat.png").toURI().toString());
        ImageView emptyImageView = new ImageView(emptyImage);
        emptyImageView.setFitHeight(60);
        emptyImageView.setFitWidth(60);

        for(Seat seat : Data.seats.get(AdminHallPane.chosenHall.name)){
            if (seat.button == button && seat.owner.equals("null")){
                seat.button.setGraphic(reservedImageView);
                seat.owner = usersBox.getValue();

                if(Data.users.get(usersBox.getValue()).clubMember.equals("false")){
                    seat.price = Integer.parseInt(AdminHallPane.chosenHall.price);
                }
                else {
                    seat.price = (int) Math.round(Integer.parseInt(AdminHallPane.chosenHall.price) *0.9);
                }
                AdminHallPane.warning.setText("Seat at "+ (seat.row+1) + "-" + (seat.column+1) + " is bought for "
                                            + seat.owner + " for " + seat.price+ " TL successfully");
            }
            else if (seat.button == button) {
                AdminHallPane.warning.setText("Seat at "+ (seat.row+1) + "-" + (seat.column+1) + " is refunded to " + seat.owner +" successfully!");
                seat.button.setGraphic(emptyImageView);
                seat.owner = "null";
                seat.price = 0;

            }
        }


    }

    private static void hoverAction(Button button){

        for(Seat seat : Data.seats.get(chosenHall.name)){
            if(seat.button == button){
                if (seat.owner.equals("null")){
                    AdminHallPane.info.setText("Not bought yet!");
                }
                else {
                    AdminHallPane.info.setText("Bought by " + seat.owner + " for " + seat.price + "TL!");
                }
            }
        }
    }

    private static String[]  castUsers() {

        ArrayList<String> users = new ArrayList<>(Data.users.keySet());

        String[] arr = new String[users.size()];

        for(int i=0; i<users.size(); i++){
            arr[i] = users.get(i);
        }

        return arr;
    }
}




