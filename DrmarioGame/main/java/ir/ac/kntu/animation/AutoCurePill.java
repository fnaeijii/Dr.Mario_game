package ir.ac.kntu.animation;

import ir.ac.kntu.random.RandomHelper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class AutoCurePill implements Runnable {

    private GridPane root;


    public AutoCurePill(GridPane root) {
        this.root = root;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (RandomHelper.nextInt(3) == 2) {
            ImageView imageView = new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/B.png"));
            root.add(imageView, 1, RandomHelper.nextInt(2), RandomHelper.nextInt(5) + 1, RandomHelper.nextInt(5) + 1);

        } else {
            if (RandomHelper.nextInt(5) % 2 == 0) {
                ImageView imageView = new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/Y.png"));
                root.add(imageView, RandomHelper.nextInt(2), 1, RandomHelper.nextInt(4) + 1, RandomHelper.nextInt(10) + 1);
            } else {
                ImageView imageView = new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/R.png"));
                root.add(imageView, RandomHelper.nextInt(2), RandomHelper.nextInt(2), RandomHelper.nextInt(6) + 1, RandomHelper.nextInt(8) + 1);
            }
        }
    }
}
