package TicTacToe.model;

import javafx.scene.image.Image;

import java.io.InputStream;


public class LocatedImage extends Image {
private String url;

    public LocatedImage(InputStream url) {
        super(url);
        this.url=url.toString();
    }

    public LocatedImage(String url) {
        super(url);
        this.url=url;
    }

    public String getUrl(){
        return url;
    }

}
