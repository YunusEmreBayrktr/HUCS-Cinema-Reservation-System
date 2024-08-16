import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AdminWelcomePane extends Pane{

    public static Pane getPane(String username) {

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(15,15,15,15));
        pane.setHgap(10);
        pane.setVgap(5);
        pane.setAlignment(Pos.CENTER);

        Label text1 = new Label("Welcome " + username + " (Admin)!");
        if(Data.users.get(username).clubMember.equals("true")){
            text1 = new Label("Welcome " + username + " (Admin - Club Member)!");
        }
        Label text2 = new Label("You can either select film below or do edits.");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        Button okBtn = new Button("OK");
        Button logoutBtn = new Button("LOG OUT");
        Button addFilmBtn = new Button("Add Film");
        Button removeFilmBtn = new Button("Remove Film");
        Button editUsersBtn = new Button("Edit Users");

        choiceBox.getItems().addAll(castFilms());
        if(choiceBox.getItems().size() != 0){
            choiceBox.setValue(choiceBox.getItems().get(0));
        }

        GridPane.setConstraints(text1, 0, 0, 3, 1);
        GridPane.setConstraints(text2, 0, 1, 3, 1);
        GridPane.setConstraints(choiceBox, 0, 2, 3, 1);
        GridPane.setConstraints(okBtn, 3, 2, 1, 1);
        GridPane.setConstraints(addFilmBtn,0,3,1,1);
        GridPane.setConstraints(removeFilmBtn,1,3,1,1);
        GridPane.setConstraints(editUsersBtn,2,3,1,1);
        GridPane.setConstraints(logoutBtn, 2, 4, 1, 1);
        GridPane.setHalignment(logoutBtn, HPos.RIGHT);
        GridPane.setHalignment(text1, HPos.CENTER);
        GridPane.setHalignment(text2, HPos.CENTER);

        logoutBtn.setOnAction(e -> SceneController.setScene(LoginPane.getPane()));
        addFilmBtn.setOnAction(e -> SceneController.setScene(AddFilmPane.getPane(username)));
        removeFilmBtn.setOnAction(e -> SceneController.setScene(RemoveFilmPane.getPane(username)));
        editUsersBtn.setOnAction(e -> SceneController.setScene(EditUserPane.getPane(username)));
        okBtn.setOnAction(e -> {
            if (choiceBox.getValue() != null){
                SceneController.setScene(AdminFilmPane.getPane(choiceBox.getValue(),username));}
        });


        pane.getChildren().addAll(text1,text2,choiceBox,okBtn,addFilmBtn,removeFilmBtn,editUsersBtn,logoutBtn);

        return pane;

    }

    public static String[]  castFilms() {

        ArrayList<String> filmList = new ArrayList<String>(Data.films.keySet());
        String[] arr = new String[filmList.size()];

        for(int i=0; i<filmList.size();i++) {
            arr[i] = filmList.get(i);
        }

        return arr;
    }
}





