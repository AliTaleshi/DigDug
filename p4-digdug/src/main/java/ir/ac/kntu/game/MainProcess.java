package ir.ac.kntu.game;

import ir.ac.kntu.mapObjects.*;
import ir.ac.kntu.mapObjects.randomElements.RandomElement;
import ir.ac.kntu.mapObjects.solidObjects.Rock;
import ir.ac.kntu.mapObjects.solidObjects.Soil;
import ir.ac.kntu.utility.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainProcess extends Application implements Serializable {

    private Map map;
    private Scene scene;
    private GridPane gridPane;
    private Thread timer;
    private List<Soil> soils;
    private List<Rock> rocks;
    private List<Enemy> enemies;
    private List<RandomElement> randomElements;
    private Player player;
    private boolean isFinished;
    private int sec;

    public MainProcess(Map map) {
        this.map = map;
        scene = map.getScene();
        gridPane = map.getGridPane();
        soils = map.getSoils();
        rocks = map.getRocks();
        enemies = map.getEnemies();
        randomElements = map.getRandomElements();
        player = map.getPlayer();
        sec = 0;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getSec() {
        return sec;
    }

    @Override
    public void start(Stage stage) {
        initializeTimerThread();
        initializeScene();
        stage.setScene(scene);
        stage.setTitle("Dig Dug");
        stage.show();
    }

    public void initializeTimerThread() {
        timer = new Thread(() -> {
            int sec = 0;
            while (!isFinished) {


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

                Platform.runLater(() -> {
                    this.sec += 1000;
                    String timeLine = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(getSec()), TimeUnit.MILLISECONDS.toMinutes(getSec()), TimeUnit.MILLISECONDS.toSeconds(getSec()));
                    Text text = new Text(timeLine);
                    gridPane.getChildren().removeAll(text);
                    gridPane.add(text, 13, 0);
                    scene = new Scene(gridPane);
                });
            }
        });

        timer.start();
    }

    public void initializeScene() {
        player.setMainProcess(this);

        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (player.isPlayerExist() && player.getKeyCodes().contains(keyCode)) {
                player.inGameMove(keyCode);
            }
        });
    }

    public void gameEnds() {
        isFinished = true;
        Platform.runLater(() -> {
            int i = -2;
            if (gridPane.getChildren().contains(player.getNode())) {
                gridPane.getChildren().remove(player.getNode());
            }
            gridPane.addColumn(0, player.getNode());
            Text finalScore = new Text(player.getPoints());
            gridPane.addColumn(1, finalScore);
            GridPane.setHalignment(player.getNode(), HPos.CENTER);
            GridPane.setHalignment(finalScore, HPos.CENTER);
        });
    }

    public void saveTheGame() {
        try {
            FileOutputStream file = new FileOutputStream("saved game.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(file);

            outputStream.writeObject(this);

            outputStream.close();
            file.close();

            System.out.println("Game saved!");

        } catch(IOException exception) {
            exception.printStackTrace();
            System.out.println("IOException is caught");
        }
    }

    public MainProcess loadTheGame() {

        try {

            FileInputStream file = new FileInputStream("saved game.txt");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            MainProcess mainProcess = (MainProcess) in.readObject();

            in.close();
            file.close();

            System.out.println("Game loaded!");

            return mainProcess;
        } catch(IOException ex) {
            System.out.println("IOException is caught");
        } catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

        return null;
    }
}