package gui;

import db.DatabaseHandle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

public class SettingController
{

    @FXML
    private TextField ImagePathField;

    @FXML
    private TextField handleUriField;

    @FXML
    private TextField instaPostImageNumberField;

    @FXML
    private Button submitSettingButton;

    @FXML
    private Button deleteDBButton;

    @FXML
    private Button backButton;

    @FXML
    private StackPane contentViewForChange;


    @FXML
    protected void handleBackButtonAction(ActionEvent actionEvent) throws Exception
    {
        StackPane stackPane = FXMLLoader.load(getClass().getResource("menu.fxml"));
        contentViewForChange.getChildren().setAll(stackPane);
    }

    @FXML
    protected void handleDeleteDBButtonAction(ActionEvent actionEvent)
    {
        Window owner = deleteDBButton.getScene().getWindow();

        DatabaseHandle databaseHandle = new DatabaseHandle();
        databaseHandle.deleteInstaRows();

        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "database deleted successfully", "click ok to continue");
    }

    @FXML
    protected void handleSubmitSettingButtonAction(ActionEvent actionEvent)
    {

        Window owner = submitSettingButton.getScene().getWindow();
        if(ImagePathField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your image path");
            return;
        }
        if(handleUriField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your uri");
            return;
        }
        if(instaPostImageNumberField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a insta post image number");
            return;
        }


        DatabaseHandle databaseHandle = new DatabaseHandle();
        databaseHandle.insertSettingData(ImagePathField.getText(), handleUriField.getText() , instaPostImageNumberField.getText());

        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "setting configed successfully", databaseHandle.querySettingData());

    }


}





