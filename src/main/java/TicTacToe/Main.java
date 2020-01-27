package TicTacToe;

import TicTacToe.controllers.MainSceneController;
import TicTacToe.controllers.MainStageController;
import TicTacToe.model.audio.Sound;
import TicTacToe.model.audio.SoundControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Sound soundPutting;
    private Sound soundWinning;
    private Sound mainMusic;


    private BorderPane root;
    private SoundControl soundControl;
    private boolean isComputerHard = false;
    private Thread musicThread;

    public SoundControl getSoundControl() {
        return soundControl;
    }

    public Sound getMainMusic() {
        return mainMusic;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            soundPutting = new Sound("audio/putting.wav");
            soundWinning = new Sound("audio/success.mp3");
            mainMusic = new Sound("audio/mainMusic.mp3");


            musicThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mainMusic.setVolume(0.5);
                    mainMusic.loopPlay();
                }
            });
            musicThread.start();


            soundControl = SoundControl.getInstance();
            soundControl.addSounds(soundPutting);
            soundControl.addSounds(soundWinning);
            FXMLLoader loaderMainStage = new FXMLLoader();
            loaderMainStage.setLocation(getClass().getResource("/view/MainStage.fxml"));
            root = loaderMainStage.load();
            MainStageController mainStageController = loaderMainStage.getController();
            mainStageController.setMainApp(this);
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
            primaryStage.setResizable(false);
            Image imageIcon = new Image("/view/main/main_image.png");
            primaryStage.getIcons().add(imageIcon);

            loadMainScene(mainStageController);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        mainMusic.stop();
        musicThread.interrupt();
    }

    public void loadMainScene(MainStageController mainStageController) throws java.io.IOException {
        FXMLLoader loaderMainScene = new FXMLLoader();
        loaderMainScene.setLocation(getClass().getResource("/view/MainScene.fxml"));
        AnchorPane mainScene = loaderMainScene.load();
        MainSceneController controller = loaderMainScene.getController();
        controller.setRoot(root);
        controller.setMainStageController(mainStageController);
        controller.setMainApp(this);
        root.setCenter(mainScene);
    }

    public boolean getComputerHard() {
        return isComputerHard;
    }

    public void setComputerHard(boolean computerLevel) {
        isComputerHard = computerLevel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
