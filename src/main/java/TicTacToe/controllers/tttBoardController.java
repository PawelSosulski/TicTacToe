package TicTacToe.controllers;

import TicTacToe.Main;
import TicTacToe.model.TicTacToe;
import TicTacToe.model.*;
import TicTacToe.model.audio.Sound;
import TicTacToe.model.audio.SoundControl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class tttBoardController {
    private List<TTTGameResults> scoreTable = new ArrayList<>();
    private TicTacToe game;
    private Image firstPlayerImage;
    private Image secondPlayerImage;
    //private TTTRandomPlayer compPlayer;
    private TTTPlayer compPlayer;
    private boolean isTransferDone;
    private SoundControl soundControl;
    private boolean players;
    private String firstName;
    private String secondName;
    private boolean firstCompTurn;
    private boolean isFirstPlayerStart = true;
    private boolean isFirstPlayerTurn = true;
    private int firstScore = 0;
    private int secondScore = 0;
    private int drawScore = 0;
    private Stage primaryStage;
    private Sound soundPutting;
    private Sound soundWinning;


    @FXML
    private TextField field;
    @FXML
    private GridPane gridBoard;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label secondNameLabel;
    @FXML
    private TextField firstScoreField;
    @FXML
    private TextField secondScoreField;
    @FXML
    private Button buttonRefresh;
    @FXML
    private ImageView imageViewFirst;
    @FXML
    private ImageView imageViewSecond;
    private MainStageController mainStageController;
    private Main mainApp;

    @FXML
    public void initialize() {

        game = new TicTacToe(null);
        refreshTextBoxInfo();
        refreshGame();
        if (isTransferDone) {
            if (!players)
                //compPlayer = new TTTRandomPlayer();
                if (mainApp!=null) {
                    if(mainApp.getComputerHard()) {
                        compPlayer = new CompLogic(game);
                    } else {
                        compPlayer = new TTTRandomPlayer();
                    }
                }


            if (firstCompTurn) {
                computerTurn();
            }
        }

        buttonRefresh.setDisable(true);


    }

    private void refreshTextBoxInfo() {
        TTTResult gameResult = game.checkGameResult();
        switch (gameResult) {
            case PLAYER_X_WIN:
                field.setText(firstName + " win!");
                break;
            case PLAYER_O_WIN:
                field.setText(secondName + " win!");
                break;
            case PENDING:
                if (game.isPlayerXTurn()) {
                    field.setText(firstName + " turn");
                } else {
                    field.setText(secondName + " turn");
                }
                break;
            case DRAW:
                field.setText("Draw!");
                break;
        }
    }


    @FXML
    private void handleBoardClick(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            Button button = (Button) source;
            String id = button.getId();
            TTTPosition position = idToPosition(id);
            game.putMark(position.getX(), position.getY());
            soundPutting.stop();
            soundPutting.play();
            fillButton(button);
            refreshTextBoxInfo();
            if (checkIfWin()) {
                highlightWinningCombo();
                return;
            }
        }
        if (!players) {
            computerTurn();
        }
    }

    private void highlightWinningCombo() {
        List<TTTPosition> positionsList = game.checkWinningCoordinates();
        List<Button> buttonList = positionsList.stream()
                .map(item -> (Button) gridBoard.lookup("#button" + item.getX() + "_" + item.getY()))
                .collect(Collectors.toList());
        for (Button button : buttonList) {
            button.setStyle("-fx-border-width: 8; -fx-border-color: #f08080");
        }
    }

    private void computerTurn() {
        if (isFirstPlayerStart)
            compPlayer.update(FieldState.O, game);
        else
            compPlayer.update(FieldState.X, game);
        TTTPosition compPosition;
        //compPosition = compPlayer.getMarkPosition(game);
        compPosition = compPlayer.getMarkPosition(game);
        game.putMark(compPosition.getX(), compPosition.getY());
        String text = "#button" + compPosition.getX() + "_" + compPosition.getY();
        Button compButton = (Button) gridBoard.lookup(text);
        fillButton(compButton);
        refreshTextBoxInfo();
        if (checkIfWin()) {
            highlightWinningCombo();
            return;
        }
    }

    private void fillButton(Button button) {
        if (isFirstPlayerTurn) {
            ImageView image = new ImageView(firstPlayerImage);
            image.setFitWidth(60);
            image.setFitHeight(60);
            button.setGraphic(image);
        } else {
            ImageView image = new ImageView(secondPlayerImage);
            image.setFitWidth(60);
            image.setFitHeight(60);
            button.setGraphic(image);
        }
        button.setDisable(true);
        isFirstPlayerTurn = !isFirstPlayerTurn;
    }

    private boolean checkIfWin() {
        if (game.checkGameResult() == TTTResult.PENDING) {
            gridBoard.setDisable(false);
            return false;
        } else {
                       buttonRefresh.setDisable(false);
            buttonRefresh.requestFocus();
            gridBoard.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(field.getText());
            if (game.checkGameResult() == TTTResult.PLAYER_X_WIN) {
                if (isFirstPlayerStart) {
                    firstScore++;
                }
                else {
                    secondScore++;
                }
                soundWinning.stop();
                soundWinning.play();
            } else if (game.checkGameResult() == TTTResult.PLAYER_O_WIN) {
                if (isFirstPlayerStart) {
                    secondScore++;
                }
                else {
                    firstScore++;
                }
                soundWinning.stop();
                soundWinning.play();
            } else {
                drawScore++;
            }

            alert.showAndWait();
            scoreUpdate();
            scoreTable.add(new TTTGameResults(firstScore, secondScore, drawScore));

            return true;
        }
    }

    private void scoreUpdate() {
        firstScoreField.setText(String.valueOf(firstScore));
        secondScoreField.setText(String.valueOf(secondScore));
    }

    @FXML
    private void handleStatistic() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/StatisticScene.fxml"));
        AnchorPane pane = loader.load();
        StatisticSceneController controller = loader.getController();
        controller.transfer(scoreTable, firstNameLabel.getText(), secondNameLabel.getText());
        Stage statisticStage = new Stage();
        statisticStage.initModality(Modality.WINDOW_MODAL);
        statisticStage.initOwner(primaryStage);
        Scene scene = new Scene(pane);
        statisticStage.setScene(scene);
        statisticStage.show();
    }


    @FXML
    private void handleRefreshButton() {
        soundWinning.stop();
        String tmp = firstName;
        firstName = secondName;
        secondName = tmp;
        if (!players) {
            firstCompTurn = !firstCompTurn;
        }
        isFirstPlayerStart = !isFirstPlayerStart;
        if (isFirstPlayerStart)
            isFirstPlayerTurn = true;
        else
            isFirstPlayerTurn = false;


        initialize();

    }

    private void refreshGame() {
        gridBoard.setDisable(false);
        // int firstButtonToSetFocus =0;
        ObservableList<Node> children = gridBoard.getChildren();
        for (Node child : children) {
            if (child instanceof Button) {
                Button button = (Button) child;
                //todo
                //if (firstButtonToSetFocus++==0)
                //   button.requestFocus();

                button.setDisable(false);
                button.setGraphic(null);
                button.setStyle("-fx-border-width: 0");
            }
        }
    }

    private TTTPosition idToPosition(String id) {
        String input = id.replace("button", "");
        String[] split = input.split("_");
        if (split.length == 2)
            return new TTTPosition(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        else
            return new TTTPosition(-1, -1);
    }

    public void transferToBoardGame(boolean players, String firstName, String secondName,
                                    Image firstImage, Image secondImage,
                                    SoundControl soundControl) {
        this.players = players;
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstPlayerImage = firstImage;
        this.secondPlayerImage = secondImage;
        this.soundControl = soundControl;
        imageViewFirst.setImage(firstImage);
        imageViewFirst.setFitWidth(25);
        imageViewFirst.setFitHeight(25);
        imageViewSecond.setImage(secondImage);
        imageViewSecond.setFitWidth(25);
        imageViewSecond.setFitHeight(25);

        refreshTextBoxInfo();
        isTransferDone = true;

        firstCompTurn = false;

        if (firstName.length() > 9)
            firstNameLabel.setText(this.firstName.substring(0, 9));
        else
            firstNameLabel.setText(this.firstName);

        if (secondName.length() > 9)
            secondNameLabel.setText(this.secondName.substring(0, 9));
        else
            secondNameLabel.setText(this.secondName);
        scoreUpdate();

        getSounds(soundControl);
        initialize();

    }

    public void getSounds(SoundControl soundControl) {
        List<Sound> sounds = soundControl.getSoundsInApp();
        for (Sound sound : sounds) {
           // System.out.println(sound.getName());
            if (sound.getName().equals("putting.wav")) {
                soundPutting = sound;
            } else if (sound.getName().equals("success.mp3")) {
                soundWinning = sound;
            }
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainStageController(MainStageController mainStageController) {
        this.mainStageController = mainStageController;
        this.mainStageController.unDisableNewGameButton();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp=mainApp;
    }
}
