package ir.ac.kntu;

import ir.ac.kntu.game.MainProcess;
import ir.ac.kntu.game.Menu;
import ir.ac.kntu.utility.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

    private MainProcess mainProcess;
    private Menu menu;
    private Stage stage;

    public JavaFxApplication() {
        menu = new Menu();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        this.stage = stage;
        Platform.runLater(() -> {
            menu.start(stage);
            menu.getPlayButton().setOnMouseClicked(mouseEvent -> {
                gameStarts(stage);
            });
        });
    }

    public void gameStarts(Stage stage) {
        Runnable checkGameIsFinished = () -> {
            try {
                Thread.sleep(700);
                while (true) {
                    Thread.sleep(100);
                    if (mainProcess.isFinished()) {
                        generateEmergencyButtons();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Platform.runLater(() -> {
            mainProcess = new MainProcess(new Map());
            mainProcess.start(stage);
            new Thread(checkGameIsFinished).start();
        });
    }

    public void generateEmergencyButtons() {
        Button quit = new Button("Quit");
        Button restart = new Button("Restart");
        Platform.runLater(() -> {
            mainProcess.getGridPane().addColumn(0, restart);
            mainProcess.getGridPane().addRow(1, quit);

            restart.setOnMouseClicked(mouseEvent -> {
                stage.close();
                start(new Stage());
            });

            quit.setOnMouseClicked(mouseEvent -> {
                System.exit(0);
            });
        });
    }
}
