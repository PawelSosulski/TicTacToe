package TicTacToe.controllers;

import TicTacToe.Main;
import TicTacToe.model.LocatedImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainSceneController {
    private Image image = new Image(getClass().getResourceAsStream("/view/main/main_image.png"));
    private ImageView imageX = new ImageView(
            new LocatedImage(getClass().getClassLoader().getResource("view/images/red_cross.png").toExternalForm()));
    private ImageView imageY = new ImageView(
            new LocatedImage(getClass().getClassLoader().getResource("view/images/blue_circle.png").toExternalForm()));
    private boolean players;
    private Stage primaryStage;
    private BorderPane root;

    @FXML
    private Label firstPlayerLabel;
    @FXML
    private Label secondPlayerLabel;
    @FXML
    private Button buttonFirstImage;
    @FXML
    private Button buttonSecondImage;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField secondNameField;
    @FXML
    private RadioButton radioPlayer;
    @FXML
    private RadioButton radioComp;

    final ToggleGroup group = new ToggleGroup();
    private MainStageController mainStageController;
    private Main mainApp;


    @FXML
    private void initialize() {

        imageView.setImage(image);
        imageX.setFitHeight(30);
        imageX.setFitWidth(30);
        imageY.setFitHeight(30);
        imageY.setFitWidth(30);

        buttonFirstImage.setGraphic(imageX);
        buttonSecondImage.setGraphic(imageY);
        radioPlayer.setSelected(true);
        radioPlayer.setToggleGroup(group);
        radioComp.setToggleGroup(group);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (radioPlayer.isSelected()) {
                    setRadioPlayer();
                } else if (radioComp.isSelected()) {
                    setCompPlayer();
                } else {
                    System.out.println("Something goes wrong");
                }
            }
        });
        firstNameField.requestFocus();

    }

    private void setRadioPlayer() {
        firstPlayerLabel.setText("Player one name");
        secondPlayerLabel.setText("Player two name");
        firstNameField.setText("");
        firstNameField.setEditable(true);
        secondNameField.setText("");
        secondNameField.setEditable(true);
        firstNameField.setPromptText("name");
        secondNameField.setStyle("-fx-text-inner-color: black;-fx-background-color: white");

        secondNameField.setPromptText("name");
    }

    private void setCompPlayer() {
        firstPlayerLabel.setText("Player name");
        secondPlayerLabel.setText("Computer");
        firstNameField.setEditable(true);
        firstNameField.setPromptText("name");
        secondNameField.setEditable(false);
        secondNameField.setPromptText("");
        secondNameField.setText("Computer");
        secondNameField.setStyle("-fx-background-color: #d3d3d3;-fx-text-inner-color: #d3d3d3;");
    }

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tttBoard.fxml"));
        AnchorPane newPane = loader.load();

        if (isValid()) {
            if (radioPlayer.isSelected()) {
                players = true;
            } else {
                players = false;
            }
            root.setCenter(newPane);
            tttBoardController tttBoardController = loader.getController();
            tttBoardController.setPrimaryStage(primaryStage);
            tttBoardController.setMainStageController(mainStageController);
            tttBoardController.setMainApp(mainApp);
            tttBoardController.transferToBoardGame(players, firstNameField.getText(), secondNameField.getText(),
                    ((ImageView) buttonFirstImage.getGraphic()).getImage(),
                    ((ImageView) buttonSecondImage.getGraphic()).getImage(),
                    mainApp.getSoundControl());
            primaryStage.show();
        }
    }


    private boolean isValid() {
        String errorMessage = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Invalid " + firstPlayerLabel.getText() + ".\n";
        }
        if (secondNameField.getText() == null || secondNameField.getText().length() == 0) {
            errorMessage += "Invalid " + secondPlayerLabel.getText() + ".\n";
        }

        if (getUrlOfImage(buttonFirstImage).equals(getUrlOfImage(buttonSecondImage))) {
            errorMessage += "Invalid icons - cannot be the same.\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct the wrong fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    private String getUrlOfImage(Button button) {
        return ((LocatedImage) ((ImageView) button.getGraphic()).getImage()).getUrl();
    }


    @FXML
    private void handleIconPicker(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/SymbolPicker.fxml"));
        AnchorPane pane = loader.load();
        SymbolPickerController controller = loader.getController();
        Button button = (Button) event.getSource();
        controller.setMainApp(mainApp);
        controller.transferToPickUpIconStage(button);
        Stage statisticStage = new Stage();
        statisticStage.initModality(Modality.WINDOW_MODAL);
        statisticStage.initOwner(primaryStage);
        Scene scene = new Scene(pane);
        statisticStage.setScene(scene);
        statisticStage.showAndWait();
    }


    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void setMainStageController(MainStageController controller) {
        this.mainStageController = controller;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }
}
