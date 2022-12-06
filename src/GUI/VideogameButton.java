package GUI;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import models.Videogame;

import javax.swing.text.html.ImageView;


public class VideogameButton extends AnchorPane{

    private Videogame videogame;

    public VideogameButton(){

    }

    public VideogameButton(Node... nodes) {
        super(nodes);
    }

    public Videogame getVideogame() {
        return videogame;
    }

    public void setVideogame(Videogame videogame) {
        this.videogame = videogame;
    }
}
