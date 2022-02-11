package ir.ac.kntu.game;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Menu extends Application {

    private Scene scene;
    private GridPane gridPane;
    private Button button;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage){
        gridPane = new GridPane();
        Node mainMenu = new ImageView(new Image("assets/menu/main menu.png", 800, 600, false, true));
        gridPane.getChildren().add(mainMenu);
        button = new Button("Start the game");
        gridPane.add(button, 0, 0);
        GridPane.setHalignment(button, HPos.CENTER);
        scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(800);
        stage.setTitle("Dig Dug");
        File audio = new File("src/main/resources/assets/music/Start Music.wav");
        String path = audio.toURI().toString();
        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.show();
    }

    public Button getPlayButton() {
        return button;
    }
}
