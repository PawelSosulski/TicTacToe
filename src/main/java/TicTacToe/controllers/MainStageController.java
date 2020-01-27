package TicTacToe.controllers;

import TicTacToe.Main;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStageController {
    private BorderPane root;
    private Main mainApp;


    @FXML
    private Button newGameButton;

    @FXML
    private Button optionsButton;




    @FXML
    private void initialize() {
        newGameButton.setDisable(true);
    }


    public void unDisableNewGameButton() {
        newGameButton.setDisable(false);
    }


    @FXML
    private void handleStartButton() throws IOException {
        mainApp.loadMainScene(this);
        newGameButton.setDisable(true);
    }



    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    @FXML
    public void handleOptionsButton(javafx.event.ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Options.fxml"));
        AnchorPane pane = loader.load();
        // StatisticSceneController controller = loader.getController();
        //controller.transfer(scoreTable, firstNameLabel.getText(), secondNameLabel.getText());
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.WINDOW_MODAL);
        optionsStage.initOwner(primaryStage);
        Scene scene = new Scene(pane);
        OptionsController controller = loader.getController();
        controller.setMainApp(mainApp);
        optionsStage.setScene(scene);
        optionsStage.showAndWait();

    }
}
