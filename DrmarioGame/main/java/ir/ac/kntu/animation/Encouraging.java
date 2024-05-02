package ir.ac.kntu.animation;

import ir.ac.kntu.random.AddRandomObject;
import ir.ac.kntu.random.RandomHelper;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;

public class Encouraging implements Runnable {
    boolean isOk;
    private GridPane root;

    public Encouraging(GridPane root, boolean isOk) {
        this.root = root;
        this.isOk = isOk;
    }

    @Override
    public void run() {
        if (isOk) {

        } else {
            try {
                root.add(new ImageView(AddRandomObject.randomVirus().get(RandomHelper.nextInt(4)).getImage()), RandomHelper.nextInt(2), RandomHelper.nextInt(2));
                root.add(new ImageView(AddRandomObject.randomVirus().get(RandomHelper.nextInt(4)).getImage()), RandomHelper.nextInt(2), RandomHelper.nextInt(2));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
