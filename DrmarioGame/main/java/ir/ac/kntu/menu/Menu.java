package ir.ac.kntu.menu;

import ir.ac.kntu.GameLoop;
import ir.ac.kntu.gameobjects.Capsule;
import ir.ac.kntu.gameobjects.PlayerInfo;
import ir.ac.kntu.random.AddRandomObject;
import ir.ac.kntu.save.BinaryPlayerDAO;
import ir.ac.kntu.save.PlayerDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Menu {
    private GridPane root;
    private Scene scene;
    TextField textField = new TextField();
    public static PlayerDAO playerDAO;

    //private Map<File, ImageView> maps;
    public Menu(Stage stage, Scene scene, GridPane root) {

        drawMenu(stage, scene, root);
        playerDAO = new BinaryPlayerDAO();
    }

    private void drawMenu(Stage stage, Scene scene, GridPane root) {
        root.getChildren().clear();
        Background background = null;
        background = getBackground(new File("src/main/resources/images/menu0.png"));
        root.setBackground(background);
        root.setAlignment(Pos.CENTER);
        Button newGame = new Button("START GAME");
        newGame.setAlignment(Pos.CENTER);
        newGame.setStyle("-fx-background-color:Red");
        newGame.setMinWidth(200);
        newGame.setMinHeight(40);
        Button tutorial = new Button("TUTORIAL");
        tutorial.setAlignment(Pos.CENTER);
        tutorial.setStyle("-fx-background-color:Orange");
        Button exit = new Button("EXIT");
        exit.setAlignment(Pos.CENTER);
        exit.setStyle("-fx-background-color:Orange");
        newGame.getStyleClass().add("newGame");
        tutorial.getStyleClass().add("tutorial");
        editButtons(stage, scene, root, newGame, tutorial, exit);
        root.setHgap(100);
        root.setVgap(100);
        root.add(newGame, 1, 3);

        VBox vBox = new VBox(5);
        vBox.getChildren().add(tutorial);
        vBox.getChildren().add(exit);
        root.add(vBox, 0, 3);

        HBox hBox = new HBox(50);
        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add(new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/low.png")));
        imageViews.add(new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/med.png")));
        imageViews.add(new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/hi.png")));
        hBox.getChildren().addAll(imageViews);


        root.add(hBox, 1, 2);

        HBox hBox1 = new HBox(30);

        ImageView imageView = new ImageView(new Image("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/level.png"));

        textField.setMaxSize(50, 50);
        hBox1.getChildren().add(imageView);
        hBox1.getChildren().add(textField);
        root.add(hBox1, 1, 1);
        imageViews.get(0).setOnMouseClicked(e -> {
            Capsule.setSpeed(2);
        });
        imageViews.get(1).setOnMouseClicked(e -> {
            Capsule.setSpeed(4);
        });
        imageViews.get(2).setOnMouseClicked(e -> {
            Capsule.setSpeed(6);
        });

    }


    private void editButtons(Stage stage, Scene scene, GridPane root, Button newGame, Button tutorial, Button exit) {
        tutorial.setOnAction(e -> {
            try {
                Background background = null;
                background = getBackground(new File("src/main/resources/images/back2.png"));
                root.setBackground(background);
                ImageView imageView = new ImageView(new Image(new FileInputStream("C:/Users/Naeiji/IdeaProjects/project4/src/main/resources/images/tutorial.png")));
                imageView.setFitWidth(500);
                imageView.setFitHeight(300);
                Button button = new Button("Back");
                button.setStyle("-fx-background-color:Gray");
                button.setOnAction(ev -> {
                    drawMenu(stage, scene, root);
                });
                root.getChildren().clear();
                root.add(imageView, 0, 1, 1, 1);
                root.add(button, 0, 2);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        exit.setOnAction(e -> {
            stage.close();
        });
        exit.getStyleClass().add("exit");
        this.root = root;
        this.scene = scene;
        newGame.setOnAction(e -> {
            AddRandomObject.numberOfVirus = Integer.parseInt(textField.getText());
            setPlayers(stage);
        });

    }

    @NotNull
    private Background getBackground(File file) {
        BackgroundFill backgroundFill = null;
        try {
            backgroundFill = new BackgroundFill(new ImagePattern(
                    new Image(new FileInputStream(file))), CornerRadii.EMPTY, Insets.EMPTY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(backgroundFill);
    }

    private void setPlayers(Stage stage) {
        Background background = null;
        background = getBackground(new File("src/main/resources/images/menu2.png"));
        root.setBackground(background);
        root.getChildren().clear();
        ArrayList<PlayerInfo> players = playerDAO.getAllPlayers();
        ListView listView = new ListView();
        listView.getItems().addAll(players);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ArrayList<PlayerInfo> result = new ArrayList<>();
        listView.setOnMouseClicked(e -> {
            if (result.size() == 1) {
                result.remove(result.size() - 1);
            }

            PlayerInfo playerInfo = (PlayerInfo) listView.getSelectionModel().getSelectedItem();
            if (!result.contains(playerInfo) && playerInfo != null) {
                result.add(playerInfo);
            }
        });

        listView.getStyleClass().add("listView");
        listView.setPrefHeight(200);
        listView.setPrefWidth(300);
        listView.setStyle("-fx-background-color:Green");
        Button submit = new Button("Submit");
        submit.setStyle("-fx-background-color:Green");
        Button add = new Button("AddPlayer");
        add.setStyle("-fx-background-color:Green");
        add.setOnAction(e -> {
            addNewPlayer(listView);
        });
        submit.setOnAction(e -> {
            root.getChildren().clear();

            GameLoop gameLoop = new GameLoop(stage, scene, root, result);
            try {
                gameLoop.startGame();
            } catch (FileNotFoundException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }


        });
        root.setHgap(0);
        root.setVgap(10);
        root.add(listView, 0, 18);
        root.add(add, 0, 19);
        root.add(submit, 0, 20);
    }

    private void addNewPlayer(ListView listView) {
        Stage stage = new Stage();

        GridPane gridPane = new GridPane();
        Background background = null;
        background = getBackground(new File("src/main/resources/images/background.png"));
        gridPane.setBackground(background);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Name");
        TextField name = new TextField();
        gridPane.setVgap(20);
        gridPane.setVgap(20);
        Button button = new Button("Save");
        button.setStyle("-fx-background-color:Gray");

        button.setOnAction(e -> {
            if (!name.getText().isEmpty()) {
                listView.getItems().add(new PlayerInfo(name.getText()));
                stage.close();
            }
        });
        gridPane.add(label, 0, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(button, 0, 2);
        stage.setTitle("add new player");
        stage.setScene(new Scene(gridPane, 300, 300, Color.GREEN));
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }
}
