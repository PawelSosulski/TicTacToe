package TicTacToe.model.audio;

import TicTacToe.model.TicTacToe;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {


    private String file;
    private Media sound;
    private MediaPlayer player;
    private boolean playAgain;
    private double soundLevelModification = 1;


    public Sound(String file) {
        this.file = file;
        //this.sound = new Media(new File(file).toURI().toString());
        this.sound = new Media(getClass().getClassLoader().getResource(file).toExternalForm());
        this.player = new MediaPlayer(sound);


        player.setAutoPlay(false);
        playAgain = false;

    }

    public void play() {
        player.play();
    }

    public void stop() {
        player.stop();
        playAgain = false;

    }

    public void setVolume(double value) {

        player.setVolume(soundLevelModification * value);
       // System.out.println(player.getVolume());
    }

    public double getVolume() {
        return player.getVolume() / soundLevelModification;
    }

    public String getName() {

        String[] pathFragment = file.split("/");
        return pathFragment[pathFragment.length - 1];
    }

    public void loopPlay() {
        playAgain = true;
        player.setAutoPlay(true);
        while (playAgain) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
            play();

        }
    }

}

