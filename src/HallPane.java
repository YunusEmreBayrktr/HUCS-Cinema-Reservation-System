import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.File;


public class HallPane extends Pane  {

    static GridPane gridPane = new GridPane();
    static Hall chosenHall;
    static Film chosenFilm;
    static String username ;
    static Label warning = new Label("");

    public static Pane getPane(String hall, String username) {


        HallPane.chosenHall = Data.halls.get(hall);
        HallPane.chosenFilm = Data.films.get(chosenHall.film);
        HallPane.username = username;

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15,15,15,15));
        vBox.setFillWidth(true);

        Label text = new Label(chosenFilm.name + " (" + chosenFilm.duration + " minutes) Hall: " + chosenHall.name);
        Button backBtn = new Button("BACK");



        createButton();

        backBtn.setOnAction(e ->{
            HallPane.warning.setText("");
            gridPane.getChildren().clear();
            SceneController.setScene(FilmPane.getPane(chosenFilm.name,username));
        });

        vBox.getChildren().addAll(text, gridPane, HallPane.warning,backBtn);

        return vBox;
    }

    private static void createButton(){

        for(Seat seat : Data.seats.get(chosenHall.name)){
            Image emptyImage = new Image(new File("assets/icons/empty_seat.png").toURI().toString());
            ImageView emptyImageView = new ImageView(emptyImage);
            emptyImageView.setFitHeight(64);
            emptyImageView.setFitWidth(64);

            Image reservedImage = new Image(new File("assets/icons/reserved_seat.png").toURI().toString());
            ImageView reservedImageView = new ImageView(reservedImage);
            reservedImageView.setFitWidth(64);
            reservedImageView.setFitHeight(64);

            Button button = new Button();
            seat.setButton(button);

            if (seat.owner.equals("null")){
                button.setGraphic(emptyImageView);
            }
            else if (seat.owner.equals(username)){
                button.setGraphic(reservedImageView);
            }
            else {
                button.setGraphic(reservedImageView);
                button.setDisable(true);
            }



            button.setOnAction(e -> buttonAction(button));
            GridPane.setConstraints(button,seat.column,seat.row);
            gridPane.getChildren().add(button);
        }
    }

    private static void buttonAction(Button button){

        Image image = new Image(new File("assets/icons/reserved_seat.png").toURI().toString());
        ImageView reservedImageView = new ImageView(image);
        reservedImageView.setFitHeight(64);
        reservedImageView.setFitWidth(64);

        Image emptyImage = new Image(new File("assets/icons/empty_seat.png").toURI().toString());
        ImageView emptyImageView = new ImageView(emptyImage);
        emptyImageView.setFitHeight(64);
        emptyImageView.setFitWidth(64);

        for(Seat seat : Data.seats.get(HallPane.chosenHall.name)){
            if (seat.button == button && seat.owner.equals("null")){
                seat.button.setGraphic(reservedImageView);
                seat.owner = HallPane.username;

                if(Data.users.get(HallPane.username).clubMember.equals("false")){
                    seat.price = Integer.parseInt(HallPane.chosenHall.price);
                }
                else {
                    seat.price = (int) Math.round(Integer.parseInt(HallPane.chosenHall.price) *0.9);
                }
                HallPane.warning.setText("Seat at "+ (seat.row+1) + "-" + (seat.column+1) + " is bought for "+ seat.price+ " TL successfully");
            }
            else if (seat.button == button) {
                seat.button.setGraphic(emptyImageView);
                seat.owner = "null";
                seat.price = 0;
                HallPane.warning.setText("Seat at "+ (seat.row+1) + "-" + (seat.column+1) + " is refunded successfully!");
            }
        }
    }
}


