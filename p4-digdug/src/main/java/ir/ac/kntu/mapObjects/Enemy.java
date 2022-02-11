package ir.ac.kntu.mapObjects;

import ir.ac.kntu.game.MainProcess;
import ir.ac.kntu.mapObjects.randomElements.weapon.Weapon;
import ir.ac.kntu.mapObjects.solidObjects.Rock;
import ir.ac.kntu.mapObjects.solidObjects.Soil;
import ir.ac.kntu.utility.Direction;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends MapObject {

    private String name;
    private boolean enemyExist;
    private boolean movementFlag;
    private GridPane gridPane;
    private Node node;
    private MainProcess mainProcess;
    private int rowNumber;
    private int columnNumber;
    private Player player;
    private List<Direction> directions;
    private List<Rock> rocks;
    private List<Soil> soils;
    private String address;
    private Direction bodySituation;

    public Enemy(GridPane gridPane, Node node) {
        super(gridPane, node);
    }

    public String getName() {
        return name;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setListOfMapObjects(ArrayList<Rock> rocks, ArrayList<Soil> soils) {
        this.rocks = rocks;
        this.soils = soils;
    }

    public void logicalMove(Direction direction) {
        if (!moveAvailability(direction)) {
            return;
        }

        switch (direction) {
            case UP:
                rowNumber -= 1;
                break;
            case DOWN:
                rowNumber += 1;
                break;
            case LEFT:
                columnNumber -= 1;
                break;
            case RIGHT:
                columnNumber += 1;
                break;
            default:
                break;
        }
    }

    public void setNormalBodySituationNode(Direction direction) {
        if (movementFlag) {
            address = "assets/dragon/" + direction.toString().toLowerCase() + "1.png";
        } else {
            address = "assets/dragon/" + direction.toString().toLowerCase() + "2.png";
        }

        gridPane.getChildren().remove(node);
        node = new ImageView(new Image(address));
        gridPane.add(node, columnNumber, rowNumber);
        movementFlag = !movementFlag;
    }

    public boolean moveAvailability(Direction direction) {
        switch (direction) {
            case UP:
                if (rowNumber == 0 || barrierExistence(rowNumber - 1, columnNumber)) {
                    return false;
                }
                break;
            case DOWN:
                if (rowNumber == gridPane.getRowCount() - 1 || barrierExistence(rowNumber + 1, columnNumber)) {
                    return false;
                }
                break;
            case LEFT:
                if (columnNumber == 0 || barrierExistence(rowNumber, columnNumber - 1)) {
                    return false;
                }
                break;
            case RIGHT:
                if (columnNumber == gridPane.getColumnCount() - 1 || barrierExistence(rowNumber, columnNumber + 1)) {
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public boolean barrierExistence(int rowNumber, int columnNumber) {
        for (Rock i : rocks) {
            if (i.isExist() && i.getColumnNumber() == columnNumber && i.getRowNumber() == rowNumber) {
                return true;
            }
        }

        for (Soil soil : soils) {
            if (soil.getRowNumber() == rowNumber && soil.getColumnNumber() == columnNumber) {
                return true;
            }
        }

        return false;
    }

    public void inGameMoveAI(KeyCode keyCode) {
        setColumnNumber(gridPane.getColumnCount());
        setRowNumber(gridPane.getRowCount());
        gridPane.getChildren().removeAll(node);

        if (player.getColumnNumber() > columnNumber && !barrierExistence(rowNumber, columnNumber + 1)) {
            logicalMove(Direction.RIGHT);
            bodySituation = Direction.RIGHT;
            if (player.getRowNumber() > rowNumber && !barrierExistence(rowNumber + 1, columnNumber)) {
                logicalMove(Direction.DOWN);
                bodySituation = Direction.LEFT;
            } else if (player.getRowNumber() < rowNumber && !barrierExistence(rowNumber - 1, columnNumber)) {
                logicalMove(Direction.UP);
                bodySituation = Direction.RIGHT;
            }
        } else if (player.getColumnNumber() < columnNumber && !barrierExistence(rowNumber, columnNumber - 1)) {
            logicalMove(Direction.LEFT);
            bodySituation = Direction.LEFT;
        }

        gridPane.add(node, columnNumber, rowNumber);
    }
}
