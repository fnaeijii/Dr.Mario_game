package ir.ac.kntu.gameobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class GameObject {
    private Image image;
    private int rowIndex;
    private int columnIndex;
    private boolean alive=true;
    private boolean visible=true;

    public GameObject(int rowIndex, int columnIndex){
        this.rowIndex=rowIndex;
        this.columnIndex=columnIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setImage(Image imlage){
        this.image=image;
    }

    public Image getImage() throws FileNotFoundException {
        return this.image;
    }

    public boolean isColliding(GameObject gameObject){
        return (rowIndex==gameObject.rowIndex)&&(columnIndex==gameObject.columnIndex);
    }

    public boolean isColliding(int rowIndex,int columnIndex){
        return (rowIndex==this.rowIndex)&&(columnIndex==this.columnIndex);
    }

    public void die(){
        alive=false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setVisible(boolean visible){
        this.visible=visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setDeadTime(int deltaTime) {
    }

}
