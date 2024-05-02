package ir.ac.kntu.gameobjects;

import ir.ac.kntu.constants.ColorPills;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SmallVirus extends GameObject {
    private ColorPills color;

    public SmallVirus(int rowIndex, int columnIndex, ColorPills color) {
        super(rowIndex, columnIndex);
        this.color = color;
    }

    public Image findImage() throws FileNotFoundException {
        if (color.equals(ColorPills.BLUE)) {
            return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/viruses/Bsmallv.png"));

        } else if (color.equals(ColorPills.RED)) {
            return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/viruses/Rsmallv.png"));

        } else {
            return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/viruses/Ysmallv.png"));

        }
    }

    @Override
    public Image getImage() throws FileNotFoundException {
        return findImage();
    }
}
