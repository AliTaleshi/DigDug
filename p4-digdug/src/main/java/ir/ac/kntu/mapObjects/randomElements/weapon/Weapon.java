package ir.ac.kntu.mapObjects.randomElements.weapon;

import ir.ac.kntu.mapObjects.Enemy;
import ir.ac.kntu.mapObjects.Player;
import ir.ac.kntu.mapObjects.solidObjects.Rock;
import ir.ac.kntu.mapObjects.solidObjects.Soil;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Weapon {

    private GridPane gridPane;
    private int rowNumber;
    private int columnNumber;
    private Player player;
    private List<Enemy> enemies;
    private List<Rock> rocks;
    private List<Soil> soils;
    private List<Node> ropes;
    private Node rope;
    private int range;

    public Weapon(Player player, GridPane gridPane, int columnNumber, int rowNumber, List<Rock> rocks, List<Soil> soils) {
        this.player = player;
        this.gridPane = gridPane;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.rocks = rocks;
        this.soils = soils;

        if (player.hasSniper()) {
            range = 5;
        } else {
            range = 3;
        }
    }

    public void shoot() {
        rope = new ImageView(new Image("assets/player/rope_" + player.getBodySituation().toString().toLowerCase()) + ".png");
        gridPane.add(rope, player.getColumnNumber(), player.getRowNumber());
        player.setThrowBodySituation(player.getBodySituation());

        switch (player.getBodySituation()) {
            case UP:
                throwRopeUp();
                break;
            case DOWN:
                throwRopeDown();
                break;
            case LEFT:
                throwRopeLeft();
                break;
            case RIGHT:
                throwRopeRight();
                break;
            default:
                break;
        }
    }

    public void throwRopeUp() {
        for (int i = rowNumber + 1; i >= rowNumber - range; i--) {
            if (rockExistence(i, columnNumber) || soilExistence(i, columnNumber)) {
                break;
            } else {
                gridPane.add(rope, columnNumber, i);
                ropes.add(rope);
            }

            for (Enemy enemy : enemies) {
                if (enemy.getRowNumber() == i && enemy.getColumnNumber() == player.getColumnNumber()) {
                    enemy.setExist(false);
                    gridPane.getChildren().remove(enemy.getNode());
                    kill(enemy);
                }
            }
        }
    }

    public void throwRopeDown() {
        for (int i = rowNumber + 1; i <= rowNumber + range && i < gridPane.getRowCount(); i++) {
            if (rockExistence(i, columnNumber) || soilExistence(i, columnNumber)) {
                break;
            } else {
                gridPane.add(rope, columnNumber, i);
                ropes.add(rope);
            }

            for (Enemy enemy : enemies) {
                if (enemy.getRowNumber() == i && enemy.getColumnNumber() == player.getColumnNumber()) {
                    enemy.setExist(false);
                    gridPane.getChildren().remove(enemy.getNode());
                    kill(enemy);
                }
            }
        }
    }

    public void throwRopeRight() {
        for (int i = columnNumber + 1; i <= player.getColumnNumber() + range && i < gridPane.getColumnCount() - 1; i++) {
            if (rockExistence(rowNumber, i) || soilExistence(rowNumber, i)) {
                break;
            } else {
                gridPane.add(rope, i, rowNumber);
                ropes.add(rope);
            }

            for (Enemy enemy : enemies) {
                if (enemy.getRowNumber() == rowNumber && enemy.getColumnNumber() == i) {
                    enemy.setExist(false);
                    gridPane.getChildren().remove(enemy.getNode());
                    kill(enemy);
                }
            }
        }
    }

    public void throwRopeLeft() {
        for (int i = columnNumber + 1; i >= columnNumber - range; i--) {
            if (rockExistence(rowNumber, i) || soilExistence(rowNumber, i)) {
                break;
            } else {
                gridPane.add(rope, i, rowNumber);
                ropes.add(rope);
            }

            for (Enemy enemy : enemies) {
                if (enemy.getRowNumber() == i && enemy.getColumnNumber() == player.getColumnNumber()) {
                    enemy.setExist(false);
                    gridPane.getChildren().remove(enemy.getNode());
                    kill(enemy);
                }
            }
        }
    }

    public void kill(Enemy enemy) {
        if (enemy.getName().contains("dragon")) {
            player.addPoints(8);
        } else if (enemy.getName().contains("balloon")) {
            player.addPoints(3);
        }
    }

    public boolean rockExistence(int rowNumber, int columnNumber) {
        for (Rock i : rocks) {
            if (i.isExist() && i.getColumnNumber() == columnNumber && i.getRowNumber() == rowNumber) {
                return true;
            }
        }

        return false;
    }

    public boolean soilExistence(int rowNumber, int columnNumber) {
        for (Soil i : soils) {
            if (i.isExist() && i.getColumnNumber() == columnNumber && i.getRowNumber() == rowNumber) {
                return true;
            }
        }

        return false;
    }

    public void shootingEnds() {
        gridPane.getChildren().remove(rope);
    }
}
