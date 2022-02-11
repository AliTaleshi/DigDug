package ir.ac.kntu.mapObjects;

import ir.ac.kntu.game.MainProcess;
import ir.ac.kntu.mapObjects.randomElements.RandomElement;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private int points;
    private int healthCount;
    private double speed;
    private Weapon weapon;
    private boolean playerExist;
    private boolean isShootingGun;
    private boolean movementFlag;
    private GridPane gridPane;
    private Node node;
    private MainProcess mainProcess;
    private int rowNumber;
    private int columnNumber;
    private List<RandomElement> randomElements;
    private List<Enemy> enemies;
    private List<KeyCode> keyCodes;
    private List<Rock> rocks;
    private List<Soil> soils;
    private String address;
    private Direction bodySituation;

    public Player(GridPane gridPane, Node node) {
        this.gridPane = gridPane;
        this.node = node;
        points = 0;
        playerExist = true;
        keyCodes = new ArrayList<>();
        bodySituation = Direction.RIGHT;
        healthCount = 3;
        speed = 1;
        movementFlag = true;
        initializeKeyCodes();
    }

    public Player() {}

    private void initializeKeyCodes() {
        keyCodes.add(KeyCode.W);
        keyCodes.add(KeyCode.D);
        keyCodes.add(KeyCode.S);
        keyCodes.add(KeyCode.A);
        keyCodes.add(KeyCode.SPACE);
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Direction getBodySituation() {
        return bodySituation;
    }

    public void setMainProcess(MainProcess mainProcess) {
        this.mainProcess = mainProcess;
    }

    public String getPoints() {
        return String.valueOf(points);
    }

    public Node getNode() {
        return node;
    }

    public boolean isPlayerExist() {
        return playerExist;
    }

    public List<KeyCode> getKeyCodes() {
        return keyCodes;
    }

    public void setListOfMapObjects(ArrayList<Rock> rocks, ArrayList<Soil> soils, ArrayList<RandomElement> randomElements, ArrayList<Enemy> enemies) {
        this.rocks = rocks;
        this.soils = soils;
        this.randomElements = randomElements;
        this.enemies = enemies;
    }

    public boolean hasSniper() {
        for (RandomElement randomElement : randomElements) {
            if (randomElement.getName().equals("sniper")) {
                return true;
            }
        }

        return false;
    }

    public void logicalMove(Direction direction) {
        if (!moveAvailability(direction)) {
            return;
        }

        switch (direction) {
            case UP:
                rowNumber -= speed;
                break;
            case DOWN:
                rowNumber += speed;
                break;
            case LEFT:
                columnNumber -= speed;
                break;
            case RIGHT:
                columnNumber += speed;
                break;
            default:
                break;
        }

        checkMushroomExistence();
        checkHeartExistence();

        if (soilExistence(rowNumber, columnNumber)) {
            setDigBodySituationNode(direction);
        } else {
            setNormalBodySituationNode(direction);
        }
    }

    public void setThrowBodySituation(Direction direction) {
        gridPane.getChildren().remove(node);
        node = new ImageView(new Image("assets/player/" + bodySituation.toString().toLowerCase() + "_throw.png"));
        gridPane.add(node, columnNumber, rowNumber);
    }

    public void setNormalBodySituationNode(Direction direction) {
        if (movementFlag) {
            address = "assets/player/" + direction.toString().toLowerCase() + "1.png";
        } else {
            address = "assets/player/" + direction.toString().toLowerCase() + "2.png";
        }

        gridPane.getChildren().remove(node);
        node = new ImageView(new Image(address));
        gridPane.add(node, columnNumber, rowNumber);
        movementFlag = !movementFlag;
    }

    public void setDigBodySituationNode(Direction direction) {
        if (movementFlag) {
            address = "assets/player/" + direction.toString().toLowerCase() + "_dig1.png";
        } else {
            address = "assets/player/" + direction.toString().toLowerCase() + "_dig2.png";
        }

        gridPane.getChildren().remove(node);
        node = new ImageView(new Image(address));
        gridPane.add(node, columnNumber, rowNumber);
        movementFlag = !movementFlag;
    }

    public void checkMushroomExistence() {
        for (int i = 0; i < randomElements.size(); i++) {
            if (randomElements.get(i).getColumnNumber() == columnNumber && randomElements.get(i).getRowNumber() == rowNumber && randomElements.get(i).getName().contains("mushroom")) {
                speed = 1.5;
            }
        }
    }

    public void checkHeartExistence() {
        for (int i = 0; i < randomElements.size(); i++) {
            if (randomElements.get(i).getColumnNumber() == columnNumber && randomElements.get(i).getRowNumber() == rowNumber && randomElements.get(i).getName().contains("heart")) {
                healthCount++;
            }
        }
    }

    public boolean soilExistence(int rowNumber, int columnNumber) {
        for (int i = 0; i < soils.size(); i++) {
            if (soils.get(i).getRowNumber() == rowNumber && soils.get(i).getColumnNumber() == columnNumber) {
                return true;
            }
        }

        return false;
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

        return false;
    }

    public void inGameMove(KeyCode keyCode) {
        if (isShootingGun) {
            return;
        }

        setColumnNumber(gridPane.getColumnCount());
        setRowNumber(gridPane.getRowCount());
        gridPane.getChildren().removeAll(node);

        if (keyCode.equals(keyCodes.get(0))) {
            logicalMove(Direction.UP);
            bodySituation = Direction.UP;
        } else if (keyCode.equals(keyCodes.get(1))) {
            logicalMove(Direction.RIGHT);
            bodySituation = Direction.RIGHT;
        } else if (keyCode.equals(keyCodes.get(2))) {
            logicalMove(Direction.DOWN);
            bodySituation = Direction.DOWN;
        } else if (keyCode.equals(keyCodes.get(3))) {
            logicalMove(Direction.LEFT);
            bodySituation = Direction.LEFT;
        } else if (keyCode.equals(keyCodes.get(4))) {
            gunShoot();
        }

        gridPane.add(node, columnNumber, rowNumber);

        Runnable setRightState = () -> {
            address = "assets/player/" + Direction.RIGHT.toString().toLowerCase() + "1.png";
            gridPane.getChildren().remove(node);
            node = new ImageView(new Image(address));
            gridPane.add(node, columnNumber, rowNumber);
        };

        new Thread(() -> {
            try {
                Thread.sleep(100);
                Platform.runLater(setRightState);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void gunShoot() {
        if (isShootingGun) {
            return;
        }

        Weapon weapon = new Weapon(this, gridPane, columnNumber, rowNumber, rocks, soils);

        new Thread(() -> {
            try {
                isShootingGun = true;

                Platform.runLater(() -> {
                    weapon.shoot();
                });
                Thread.sleep(800);
                Platform.runLater(() -> {
                    weapon.shootingEnds();
                });
                Thread.sleep(150);

                isShootingGun = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
