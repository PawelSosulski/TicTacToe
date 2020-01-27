package TicTacToe.controllers;

import TicTacToe.Main;
import TicTacToe.model.LocatedImage;
import com.sun.javafx.scene.control.skin.Utils;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;


import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import java.util.List;


public class SymbolPickerController {


    private Button buttonToChangeIcon;
    @FXML
    private GridPane grid;

    private boolean isTransferDone;
    private Main mainApp;


    @FXML
    private void initialize() {

        if (isTransferDone) {
            List<String> paths = fillArrayWithPaths();

            ObservableList<Node> children = grid.getChildren();
            int i = 0;

            try {
                for (Node child : children) {
                    if (child instanceof Button) {
                        Button button = (Button) child;
                        LocatedImage image = new LocatedImage(getClass()
                                .getClassLoader().getResource("view/images/" + paths.get(i++)).toExternalForm());
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);
                        button.setGraphic(imageView);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> fillArrayWithPaths() {
        List<String> paths = new ArrayList<>();
        try {
            paths.add("apple.png");
            paths.add("blue_circle.png");
            paths.add("burger.png");
            paths.add("purple_heart.png");
            paths.add("red_cross.png");
            paths.add("smile.png");
            paths.add("snapchat.png");
            paths.add("yellow_star.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paths;
    }


    @FXML
    private void handlePickIcon(ActionEvent event) {
        try {
            ImageView image;
            Button button = (Button) event.getSource();
            image = (ImageView) button.getGraphic();
            image.setFitHeight(30);
            image.setFitWidth(30);
            buttonToChangeIcon.setGraphic(image);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void transferToPickUpIconStage(Button button) {
        buttonToChangeIcon = button;
        isTransferDone = true;
        initialize();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}