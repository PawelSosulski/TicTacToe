package TicTacToe.model.audio;

import java.util.ArrayList;
import java.util.List;

public class SoundControl {

    private static SoundControl soundControl = null;

    private List<Sound> soundsInApp = new ArrayList<>();
    private double soundVolume = 0.5;

    private SoundControl() {
    }

    public static SoundControl getInstance() {
        if (soundControl == null) {
            soundControl = new SoundControl();
        }
        return soundControl;
    }

    private void refreshVolume() {
        if (soundsInApp.size() > 0) {
            for (Sound sounds : soundsInApp) {
                //System.out.println("Sound volume: "+soundVolume);
                sounds.setVolume(soundVolume);
            }
        }
    }

    public void addSounds(Sound sound) {
        soundsInApp.add(sound);
        refreshVolume();
    }

    public void setVolume(double soundVolume) {
        this.soundVolume = soundVolume;
        refreshVolume();
    }

    public double getSoundVolume() {
        return soundVolume;
    }


    public List<Sound> getSoundsInApp() {
        return soundsInApp;
    }
}
