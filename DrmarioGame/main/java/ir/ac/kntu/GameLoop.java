package ir.ac.kntu;

import ir.ac.kntu.gamecontroller.Handling;
import ir.ac.kntu.gameobjects.Capsule;
import ir.ac.kntu.gameobjects.Doctor;
import ir.ac.kntu.gameobjects.PlayerInfo;
import ir.ac.kntu.gameobjects.SmallVirus;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.random.AddRandomObject;
import ir.ac.kntu.save.BinaryPlayerDAO;
import ir.ac.kntu.save.PlayerDAO;
import javafx.animation.AnimationTimer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;


public class GameLoop implements KeyListener {

    private Stage stage;
    private GridPane root;
    private Scene scene;

    private ArrayList<PlayerInfo> players;
    public static ArrayList<Capsule> finalCapsule;
    Handling handler;
    private AnimationTimer animationTimer;
    public static boolean end = false;
    private int deltaTime;
    private long startTime;
    private final int gameTime = 185000;

    public GameLoop(Stage stage, Scene scene, GridPane root, ArrayList<PlayerInfo> players) {
        this.stage = stage;
        this.players = players;
        root.setHgap(0);
        root.setVgap(0);
        Background background;
        background = getBackground(new File("src/main/resources/images/gameScene/1.png"));
        root.setBackground(background);
        this.root = root;
        this.scene = scene;
        finalCapsule = new ArrayList<>();
        setAnimationTimer();
        handler = new Handling();

    }

    private static Background getBackground(File file) {
        BackgroundFill backgroundFill = null;
        try {
            backgroundFill = new BackgroundFill(new ImagePattern(
                    new Image(new FileInputStream(file))), CornerRadii.EMPTY, Insets.EMPTY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(backgroundFill);
    }

    public void startGame() throws FileNotFoundException, InterruptedException {

        animationTimer.start();
        drawRoot();
        addKeyListener(this);
        Thread thread = new Thread(handler);
        thread.start();

    }

    private void setAnimationTimer() {
        animationTimer = new AnimationTimer() {
            private int count = 0;

            @Override
            public void handle(long now) {
                if (count < 5) {
                    root.getChildren().clear();
                    count++;
                    TextField textField = new TextField((String.valueOf(count)));
                    textField.setId("textField");
                    root.add(textField, 0, 0);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (end) {
                    endGame();
                    return;
                }
                deltaTime = (int) (new Date().getTime() - startTime) / 1000000000;
                try {
                    startGame();
                } catch (FileNotFoundException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };

    }

    public void drawRoot() throws FileNotFoundException, InterruptedException {
        root.getChildren().clear();
        GridPane pane = new GridPane();
        Canvas canvas = new Canvas(180, 450);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        pane.setAlignment(Pos.BOTTOM_CENTER);
        pane.setMinHeight(480);
        pane.setMinWidth(210);
        Background background;
        background = getBackground(new File("src/main/resources/images/gameScene/2.png"));
        pane.setBackground(background);
        addVirus(gc);
        addCapsule(addDoctor(), gc, 12 * (handler.pillX + (handler.pillSide ? 1 : 0)), 10 * (((handler.pillY - (handler.pillSide ? 0 : 1)) + Capsule.speed)));
        for (Capsule c : finalCapsule) {
            addCapsule(c, gc, c.getRowIndex(), c.getColumnIndex());
        }
        pane.setHgap(0);
        pane.setVgap(0);
        pane.add(canvas, 1, 1);
        root.add(pane, 1, 1, 1, 2);
        addBigVirus();
        addInfo();
    }

    public void addPillToArray(int x, int y, boolean s) throws FileNotFoundException {
        addDoctor().setRowIndex(12 * (x + (s ? 1 : 0)));
        addDoctor().setColumnIndex(10 * (((y - (s ? 0 : 1)) + Capsule.speed)));
        finalCapsule.add(addDoctor());
    }

    private void addVirus(GraphicsContext gc) throws FileNotFoundException {
        ArrayList<SmallVirus> smallVirus = new ArrayList<>();
        for (int i = 0; i < AddRandomObject.numberOfVirus; i++) {
            smallVirus.addAll(AddRandomObject.randomVirus());
        }
        block:
        {
            int n = 0;
            for (int g = 0; g < 8; g++) {
                for (int j = 0; j < 16; j++) {
                    if (handler.getBorderPills()[g][j] != null) {
                        gc.drawImage(smallVirus.get(n).getImage(), g * 20, j * 25);
                        n++;
                        if (n == AddRandomObject.numberOfVirus * 4 - 1) {
                            break block;
                        }
                    }
                }
            }
        }
    }

    public void addCapsule(Capsule capsule, GraphicsContext gc, int x, int y) throws FileNotFoundException {
        gc.drawImage(capsule.getImage(), x, y);
    }

    private void addBigVirus() {
        BorderPane borderPane = new BorderPane();
        Background background;
        background = getBackground(new File("src/main/resources/images/gameScene/4.png"));
        borderPane.setBackground(background);
        borderPane.setMinHeight(150);
        borderPane.setMaxHeight(150);
        borderPane.setMaxWidth(100);
        ImageView imageView = new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/viruses/Virus_movements.gif"));
        ImageView imageView1 = new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/gameScene/5.png"));

        imageView.setFitWidth(200);
        imageView.setFitHeight(70);
        borderPane.setCenter(imageView);
        root.add(borderPane, 0, 2, 1, 8);
        root.add(imageView1, 0, 1);
        GridPane.setHalignment(borderPane, HPos.LEFT);
    }

    private void addInfo() {
        BorderPane borderPane = new BorderPane();
        Background background;
        background = getBackground(new File("src/main/resources/images/gameScene/6.png"));
        borderPane.setBackground(background);
        borderPane.setMaxHeight(200);
        borderPane.setMinWidth(50);
        Text text = new Text("\n\n\n              " + AddRandomObject.numberOfVirus);
        Text text1 = new Text("\n\n             " + Capsule.speed);
        Text text2 = new Text("\n\n             " + AddRandomObject.numberOfVirus * 4);
        VBox vBox = new VBox(15);
        vBox.getChildren().add(text);
        vBox.getChildren().add(text1);
        vBox.getChildren().add(text2);
        borderPane.setCenter(vBox);
        root.add(borderPane, 2, 2);
        BorderPane borderPane1 = new BorderPane();
        Background background1;
        background1 = getBackground(new File("src/main/resources/images/gameScene/7.png"));
        borderPane1.setBackground(background1);
        borderPane1.setMinHeight(100);
        borderPane1.setMaxWidth(100);
        Text text3 = new Text();
        int time = (gameTime / 1000 - deltaTime);
        int minute = time / 60;
        int seconds = time % 60;
        text3.setText(minute + ":" + seconds);
        borderPane1.setCenter(text3);
        root.add(borderPane1, 0, 0, 5, 1);

    }

    private Capsule addDoctor() throws FileNotFoundException {
        BorderPane borderPane = new BorderPane();
        Background background;
        background = getBackground(new File("src/main/resources/images/gameScene/3.png"));
        borderPane.setBackground(background);
        borderPane.setMaxHeight(100);
        borderPane.setMinWidth(100);
        Doctor doctor = new Doctor(2, 1, newPill());
        borderPane.setCenter(new ImageView(doctor.getImage()));
        root.add(borderPane, doctor.getRowIndex(), doctor.getColumnIndex(), 1, 1);
        GridPane.setHalignment(borderPane, HPos.RIGHT);
        return doctor.getNextCapsule();
    }

    public static Capsule newPill() {
        return AddRandomObject.randomPill();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            handler.rotate();
        }
        if (e.getKeyCode() == 37) {
            handler.moveLeft();
        }
        if (e.getKeyCode() == 40 && !handler.fastFall) {
            handler.fastFall = true;
            if (handler.commandId == 0) {
                handler.fall();
            }
        }
        if (e.getKeyCode() == 39) {
            handler.moveRight();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == 40) {
            handler.fastFall = false;
        }
    }

    public void endGame() {
        animationTimer.stop();
        if (Capsule.speed == 4) {
            players.get(0).setScore((int) (players.get(0).getScore() * 1.1));
        } else if (Capsule.speed == 6) {
            players.get(0).setScore((int) (players.get(0).getScore() * 1.5));
        }

        PlayerDAO playerDAO = new BinaryPlayerDAO();
        playerDAO.saveAllPlayers(this.players);
        showRanks();

    }

    private void showRanks() {
        root.getChildren().clear();
        Collections.sort(players);
        ListView listView = new ListView();
        listView.setPrefWidth(500);
        listView.setPrefHeight(600);
        listView.getStyleClass().add("listView");
        int i = 0;
        for (PlayerInfo player : players) {
            i++;
            listView.getItems().add(i + ". " + player.getName() + "             " + player.getLastScore());
        }
        root.add(listView, 0, 0);
        Button button = new Button("BACK");
        button.setOnAction(e -> {
            Menu menu = new Menu(stage, scene, root);
        });
        root.add(button, 1, 0);
    }

    public Scene getScene() {
        return this.scene;
    }

}

