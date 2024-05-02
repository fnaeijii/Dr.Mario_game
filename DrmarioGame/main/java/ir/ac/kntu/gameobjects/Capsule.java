package ir.ac.kntu.gameobjects;

import com.sun.javafx.scene.traversal.Direction;
import ir.ac.kntu.constants.ColorPills;
import ir.ac.kntu.constants.Condition;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Capsule extends GameObject {
    public static int speed;
    private double xPos;
    private double yPos;
    private HalfCapsule rightPill;
    private HalfCapsule leftPill;
    private Direction direction;
    private Condition condition;

    public Capsule(ColorPills colorPill1, ColorPills colorPill2, int rowIndex, int columnIndex) {
        super(rowIndex, columnIndex);
        this.rightPill = new HalfCapsule(colorPill1);
        this.leftPill = new HalfCapsule(colorPill2);
        xPos=rowIndex;
        yPos=columnIndex;
    }


    public void move(int speed, Direction direction) {

        }


    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Capsule.speed = speed;
    }



    public HalfCapsule getRightPill() {
        return rightPill;
    }

    public void setRightPill(HalfCapsule rightPill) {
        this.rightPill = rightPill;
    }

    public HalfCapsule getLeftPill() {
        return leftPill;
    }

    public void setLeftPill(HalfCapsule leftPill) {
        this.leftPill = leftPill;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }


    public Image findImage() throws FileNotFoundException {
        if (getRightPill().getColorPills().equals(ColorPills.BLUE)) {
            if (getLeftPill().getColorPills().equals(ColorPills.BLUE)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/BB_V.png"));
            } else if (getLeftPill().getColorPills().equals(ColorPills.RED)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/BR_V.png"));
            } else {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/BY_V.png"));
            }
        }
       else if (getRightPill().getColorPills().equals(ColorPills.RED)) {
            if (getLeftPill().getColorPills().equals(ColorPills.RED)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/RR_V.png"));
            } else if (getLeftPill().getColorPills().equals(ColorPills.BLUE)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/RB_V.png"));
            } else {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/RY_V.png"));
            }
        }
        else  {
            if (getLeftPill().getColorPills().equals(ColorPills.RED)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/YR_V.png"));
            } else if (getLeftPill().getColorPills().equals(ColorPills.BLUE)) {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/YB_V.png"));
            } else {
                return new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/pills/YY_V.png"));
            }
        }
    }

    @Override
    public Image getImage() throws FileNotFoundException {

        return findImage();
    }
}
