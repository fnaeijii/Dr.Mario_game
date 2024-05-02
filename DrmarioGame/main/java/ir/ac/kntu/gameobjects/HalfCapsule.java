package ir.ac.kntu.gameobjects;

import ir.ac.kntu.constants.ColorPills;

public class HalfCapsule {
    private ColorPills colorPills;
    private int X;
    private int Y;

    private int row;
    private int column;

    public HalfCapsule(ColorPills colorPills) {
        this.colorPills = colorPills;
    }

    public ColorPills getColorPills() {
        return colorPills;
    }

    public void setColorPills(ColorPills colorPills) {
        this.colorPills = colorPills;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
