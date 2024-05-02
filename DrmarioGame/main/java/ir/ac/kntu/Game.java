package ir.ac.kntu;

import ir.ac.kntu.menu.Menu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * @author Sina Rostami
 */
public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        root.setHgap(0);
        root.setVgap(0);
        Menu menu = new Menu(primaryStage, scene, root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dr.Mario");
        primaryStage.show();
    }
}
