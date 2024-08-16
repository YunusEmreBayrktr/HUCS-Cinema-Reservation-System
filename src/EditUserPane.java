import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EditUserPane extends Pane {

    public static Pane getPane(String username) {

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(20,20,20,20));
        vBox.setAlignment(Pos.CENTER);

        Button backBtn = new Button("BACK");
        Button clubMemberBtn = new Button("Promote/Demote Club Member");
        Button adminBtn = new Button("Promote/Demote Admin");

        TableColumn<User, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(200);

        TableColumn<User, String> clubMemberColumn = new TableColumn<>("Club Member");
        clubMemberColumn.setCellValueFactory(new PropertyValueFactory<>("clubMember"));
        clubMemberColumn.setMinWidth(70);

        TableColumn<User, String> adminColumn = new TableColumn<>("Admin");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));
        adminColumn.setMinWidth(70);

        TableView<User> userTableView = new TableView<>();
        userTableView.setItems(getUsers(username));
        userTableView.getColumns().addAll(nameColumn,clubMemberColumn,adminColumn);
        userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        clubMemberBtn.setOnAction(e -> clubMemberAction(userTableView));
        adminBtn.setOnAction(e -> adminAction(userTableView));
        backBtn.setOnAction(e -> SceneController.setScene(AdminWelcomePane.getPane(username)));

        hBox.getChildren().addAll(backBtn,clubMemberBtn,adminBtn);
        vBox.getChildren().addAll(userTableView,hBox);

        return vBox;
    }


    private static ObservableList<User> getUsers(String username){
        ObservableList<User> userList = FXCollections.observableArrayList();
        for(User user : Data.users.values()){
            if(!(user.name.equals(username))) {
                userList.add(user);
            }
        }
        return userList;
    }


    private static void clubMemberAction(TableView<User> userTableView){

        if(userTableView.getSelectionModel().getSelectedItem().clubMember.equals("true")){
            userTableView.getSelectionModel().getSelectedItem().clubMember = "false";
            userTableView.refresh();
        }
        else if(userTableView.getSelectionModel().getSelectedItem().clubMember.equals("false")){
            userTableView.getSelectionModel().getSelectedItem().clubMember = "true";
            userTableView.refresh();
        }
    }


    private static void adminAction(TableView<User> userTableView){

        if(userTableView.getSelectionModel().getSelectedItem().admin.equals("true")){
            userTableView.getSelectionModel().getSelectedItem().admin = "false";
            userTableView.refresh();
        }
        else if(userTableView.getSelectionModel().getSelectedItem().admin.equals("false")){
            userTableView.getSelectionModel().getSelectedItem().admin = "true";
            userTableView.refresh();
        }
    }
}

