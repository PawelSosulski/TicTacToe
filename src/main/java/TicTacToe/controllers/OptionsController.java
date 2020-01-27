package TicTacToe.controllers;

import TicTacToe.Main;
import TicTacToe.model.audio.Sound;
import TicTacToe.model.audio.SoundControl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class OptionsController {

    private ImageView soundIcon = new ImageView(new Image("view/audio/audio_icon.jpg"));
    private ImageView soundMuteIcon = new ImageView(new Image("view/audio/audio_icon_mute.jpg"));
    private ImageView musicIcon = new ImageView(new Image("view/audio/audio_icon.jpg"));
    private ImageView musicMuteIcon = new ImageView(new Image("view/audio/audio_icon_mute.jpg"));
    private boolean soundMute;
    private Main mainApp;
    private SoundControl soundControl;

    @FXML
    private RadioButton easyRadioButton;
    @FXML
    private RadioButton hardRadioButton;
    @FXML
    private Slider soundSlider;
    @FXML
    private Slider musicSlider;
    @FXML
    private Button okButton;
    @FXML
    private Button soundButton;
    @FXML
    private Button musicButton;
    private boolean musicMute;
    private Sound music;
    final ToggleGroup group = new ToggleGroup();

    @FXML
    private void initialize() {
        easyRadioButton.setToggleGroup(group);
        hardRadioButton.setToggleGroup(group);

        if (mainApp != null) {
            if (mainApp.getComputerHard()) {
                hardRadioButton.setSelected(true);
            } else {
                easyRadioButton.setSelected(true);
            }
        }

        okButton.requestFocus();
        soundIcon.setFitHeight(25);
        soundIcon.setFitWidth(25);
        soundMuteIcon.setFitHeight(25);
        soundMuteIcon.setFitWidth(25);
        musicIcon.setFitHeight(25);
        musicIcon.setFitWidth(25);
        musicMuteIcon.setFitHeight(25);
        musicMuteIcon.setFitWidth(25);
        prepareSound();
        prepareMusic();

    }

    private void prepareMusic() {
        if (mainApp != null) {
          // musicSlider.valueProperty().bindBidirectional(mainApp.getMainMusic());
            music = mainApp.getMainMusic();
            musicSlider.setMin(0);
            musicSlider.setMax(1);
            musicSlider.setValue(music.getVolume());
            if (Double.compare(music.getVolume(), 0) == 0) {
                musicButton.setGraphic(musicMuteIcon);
                musicSlider.setDisable(true);
                musicMute = true;
            } else {
                musicButton.setGraphic(musicIcon);
                musicSlider.setDisable(false);
                musicMute = false;
            }
        }
    }

    private void prepareSound() {
        if (mainApp != null) {
            soundControl = mainApp.getSoundControl();
            soundSlider.setMin(0);
            soundSlider.setMax(1);
            soundSlider.setValue(soundControl.getSoundVolume());
            if (Double.compare(soundControl.getSoundVolume(), 0) == 0) {
                soundButton.setGraphic(soundMuteIcon);
                soundSlider.setDisable(true);
                soundMute = true;
            } else {
                soundButton.setGraphic(soundIcon);
                soundMute = false;
                soundSlider.setDisable(false);
            }
        }
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        initialize();
    }

    @FXML
    private void handleOk(ActionEvent event) {
        if (soundMute) {
            soundControl.setVolume(0);
        } else {
            soundControl.setVolume(soundSlider.getValue());
        }
        if (musicMute) {
            music.setVolume(0);
        } else {
            music.setVolume(musicSlider.getValue());
        }

        if (hardRadioButton.isSelected()) {
            mainApp.setComputerHard(true);
        } else {
            mainApp.setComputerHard(false);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    private void handleSoundButton() {
        if (soundMute) {
            soundButton.setGraphic(soundIcon);
            soundSlider.setDisable(false);
            soundSlider.setValue((soundSlider.getMax() + soundSlider.getMin()) / 2);
            soundMute = !soundMute;
        } else {
            soundButton.setGraphic(soundMuteIcon);
            soundSlider.setDisable(true);
            soundMute = !soundMute;
        }
    }

    @FXML
    private void musicSoundButton() {
        if (musicMute) {
            musicButton.setGraphic(musicIcon);
            musicSlider.setDisable(false);
            musicSlider.setValue((musicSlider.getMax() + musicSlider.getMin()) / 2);
            musicMute = !musicMute;
        } else {
            musicButton.setGraphic(musicMuteIcon);
            musicSlider.setDisable(true);
            musicMute = !musicMute;
        }
    }

}
