package ir.ac.kntu.gameobjects;

import ir.ac.kntu.constants.ColorPills;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Doctor extends GameObject {

    private Capsule nextCapsule;

    public Doctor(int rowIndex, int columnIndex, Capsule capsule) {
        super(rowIndex, columnIndex);
        nextCapsule = capsule;
    }

    public Doctor(int rowIndex, int columnIndex) {
        super(rowIndex, columnIndex);
    }

    public Capsule getNextCapsule() {
        return nextCapsule;
    }

    public void setNextCapsule(Capsule nextCapsule) {
        this.nextCapsule = nextCapsule;
    }

    public Image findImage() throws FileNotFoundException {
        if (this.getNextCapsule().getRightPill().getColorPills().equals(ColorPills.BLUE)) {
            if (this.getNextCapsule().getLeftPill().getColorPills().equals(ColorPills.BLUE)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_BB.png"));
            } else if (this.getNextCapsule().getLeftPill().getColorPills().equals(ColorPills.RED)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_BR.png"));
            } else {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_BY.png"));
            }
        } else if (this.getNextCapsule().getRightPill().getColorPills().equals(ColorPills.RED)) {
            if (this.getNextCapsule().getLeftPill().getColorPills().equals(ColorPills.RED)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_RR.png"));
            } else if (this.getNextCapsule().getLeftPill().getColorPills().equals(ColorPills.BLUE)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_RB.png"));
            } else {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_RY.png"));
            }
        } else {
            if (this.getNextCapsule().getLeftPill().getColorPills().equals(ColorPills.RED)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_YR.png"));
            } else if (this.getNextCapsule().getLeftPill().getColorPills().equals(ColorPills.BLUE)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_YB.png"));
            } else {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/doctor/Dr_YY.png"));
            }
        }
    }


    @Override
    public Image getImage() throws FileNotFoundException {

        return findImage();
    }
}
