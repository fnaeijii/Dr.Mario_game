package ir.ac.kntu.animation;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.awt.*;

public class EndAnimation implements Runnable {
    private GridPane root;


    public EndAnimation(GridPane root) {
        this.root = root;
    }

    @Override
    public void run() {

        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/gameScene/8.png"));
        root.getChildren().clear();
        root.add(imageView, 1, 1);
    }
}
