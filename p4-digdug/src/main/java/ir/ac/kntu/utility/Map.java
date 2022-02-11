package ir.ac.kntu.utility;

import ir.ac.kntu.mapObjects.*;
import ir.ac.kntu.mapObjects.randomElements.Heart;
import ir.ac.kntu.mapObjects.randomElements.Mushroom;
import ir.ac.kntu.mapObjects.randomElements.RandomElement;
import ir.ac.kntu.mapObjects.randomElements.weapon.Sniper;
import ir.ac.kntu.mapObjects.solidObjects.Rock;
import ir.ac.kntu.mapObjects.solidObjects.Soil;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {

    private GridPane gridPane;
    private int[][] items;
    private Player player;
    private ArrayList<Rock> rocks;
    private ArrayList<Soil> soils;
    private ArrayList<RandomElement> randomElements;
    private ArrayList<Enemy> enemies;
    private Scene scene;

    public Map() {
        rocks = new ArrayList<>();
        soils = new ArrayList<>();
        enemies = new ArrayList<>();
        randomElements = new ArrayList<>();
        player = new Player();
        gridPane = new GridPane();
        items = new int[13][13];
        loadMapDetails();
        scene = new Scene(gridPane, gridPane.getColumnCount() * 50 + 85, gridPane.getRowCount() * 50 + 70, Color.GREEN);
        putRandomElements();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public ArrayList<Soil> getSoils() {
        return soils;
    }

    public ArrayList<RandomElement> getRandomElements() {
        return randomElements;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Scene getScene() {
        return scene;
    }

    public void putRandomElements() {
        new Thread(() -> {
            while (true) {
                Node node = null;

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

                int randomElement = (int)(Math.random() * 3) + 1;
                int randomRowNumber = (int)(Math.random() * gridPane.getRowCount()) + 1;
                int randomColumnNumber = (int)(Math.random() * gridPane.getColumnCount()) + 1;

                while (!barrierExistenceForRandomElements(randomRowNumber, randomColumnNumber)) {
                    randomRowNumber = (int)(Math.random() * gridPane.getRowCount()) + 1;
                    randomColumnNumber = (int)(Math.random() * gridPane.getColumnCount()) + 1;
                }

                setRandomElementsGridPane(node, randomElement, randomRowNumber, randomColumnNumber);
            }
        }).start();
    }

    public void setRandomElementsGridPane(Node node, int randomElement, int rowNumber, int columnNumber) {
        switch (randomElement) {
            case 1: // mushroom
                node = new ImageView(new Image("assets/random elements/mushroom.png"));
                randomElements.add(new Mushroom(gridPane, node, "mushroom"));
                break;
            case 2: // heart
                node = new ImageView(new Image("assets/random elements/heart.png"));
                randomElements.add(new Heart(gridPane, node, "heart"));
                break;
            case 3: // sniper
                node = new ImageView(new Image("assets/map/oneway/gun.png"));
                randomElements.add(new Sniper(gridPane, node, "sniper"));
                break;
            default:
                break;
        }

        if(node != null) {
            Node finalNode = node;
            Platform.runLater(() -> gridPane.add(finalNode, columnNumber, rowNumber));
        }
    }

    public boolean barrierExistenceForRandomElements(int rowNumber, int columnNumber) {
        for (Soil soil : soils) {
            if (soil.getRowNumber() == rowNumber && soil.getColumnNumber() == columnNumber) {
                return true;
            }
        }

        for (Rock rock : rocks) {
            if (rock.getRowNumber() == rowNumber && rock.getColumnNumber() == columnNumber) {
                return true;
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.getRowNumber() == rowNumber && enemy.getColumnNumber() == columnNumber) {
                return true;
            }
        }

        for (RandomElement randomElement : randomElements) {
            if (randomElement.getRowNumber() == rowNumber && randomElement.getColumnNumber() == columnNumber) {
                return true;
            }
        }

        return player.getRowNumber() == rowNumber && player.getColumnNumber() == columnNumber;
    }

    public void loadMapDetails() {
        //loadMapFromTxtFile();
        initializeBackGround();
        givingValueToItemArray();
        initializeMapObjects();
    }

    public void loadMapFromTxtFile() {
        File file = new File("assets/map/map.txt");

        if (file.exists()) {
            try (Scanner input = new Scanner(file)) {
                for (int i = 0; i < items.length; i++) {
                    for (int j = 0; j < items[i].length; j++) {
                        items[i][j] = input.nextInt();
                    }
                }
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void initializeBackGround() {
        for (int j = 0; j < items[0].length; j++) {
            for (int i = 0; i < items.length; i++) {
                Node node = new ImageView(new Image("assets/back ground.png"));
                gridPane.add(node, j, i);
            }
        }
    }

    public void initializeMapObjects() {
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[0].length; j++) {
                Node node = null;

                switch (items[i][j]) {
                    case 1:
                        node = new ImageView(new Image("assets/soil/1.png"));
                        soils.add(new Soil(gridPane, node));
                        break;
                    case 2:
                        node = new ImageView(new Image("assets/soil/2.png"));
                        soils.add(new Soil(gridPane, node));
                        break;
                    case 3:
                        node = new ImageView(new Image("assets/soil/3.png"));
                        soils.add(new Soil(gridPane, node));
                        break;
                    case 4:
                        node = new ImageView(new Image("assets/soil/4.png"));
                        soils.add(new Soil(gridPane, node));
                        break;
                    case 5:
                        node = new ImageView(new Image("assets/rock.png"));
                        rocks.add(new Rock(gridPane, node));
                        break;
                    case 6:
                        int randomNumber = (int)(Math.random() * 3) + 1;
                        chooseRandomElement(node, randomNumber);
                        break;
                    case 7:
                        node = new ImageView(new Image("assets/dragon/dragon_right1.png"));
                        enemies.add(new Enemy(gridPane, node));
                        break;
                    case 8:
                        node = new ImageView(new Image("assets/baloon/baloon_right1.png"));
                        enemies.add(new Enemy(gridPane, node));
                    case 9:
                        node = new ImageView(new Image("assets/player/right1.png"));
                        player = new Player(gridPane, node);
                    default:
                        break;
                }

                if (node != null) {
                    gridPane.add(node, j, i);
                }
            }
        }

        initializePlayerLists();
    }

    public void chooseRandomElement(Node node, int randomNumber) {
        switch (randomNumber) {
            case 1:
                node = new ImageView(new Image("assets/random elements/mushroom.png"));
                randomElements.add(new RandomElement(gridPane, node));
                break;
            case 2:
                node = new ImageView(new Image("assets/random elements/gun.png"));
                randomElements.add(new RandomElement(gridPane, node));
                break;
            case 3:
                node = new ImageView(new Image("assets/random elements/heart.png"));
                randomElements.add(new RandomElement(gridPane, node));
                break;
            default:
                break;
        }
    }

    public void givingValueToItemArray() {
        for (int i = 0; i < items[0].length; i++) {
            items[0][i] = 0;
        }

        for (int i = 0; i < items[1].length; i++) {
            items[1][i] = 1;
        }

        items[1][5] = 0;

        for (int i = 0; i < items[2].length; i++) {
            items[2][i] = 1;
        }

        items[2][5] = 0;
        items[2][1] = 7;
        items[2][9] = 7;
        items[2][10] = 0;
        items[2][11] = 0;

        for (int i = 0; i < items[3].length; i++) {
            items[3][i] = 1;
        }

        items[3][1] = 0;
        items[3][3] = 5;
        items[3][5] = 0;

        for (int i = 0; i < items[4].length; i++) {
            items[4][i] = 2;
        }

        items[4][1] = 0;
        items[4][5] = 0;

        for (int i = 0; i < items[5].length; i++) {
            items[5][i] = 2;
        }

        items[5][1] = 0;
        items[5][5] = 0;

        for (int i = 0; i < items[6].length; i++) {
            items[6][i] = 2;
        }

        items[6][4] = 0;
        items[6][5] = 9;
        items[6][6] = 0;

        for (int i = 0; i < items[7].length; i++) {
            items[7][i] = 3;
        }

        items[7][8] = 0;

        for (int i = 0; i < items[8].length; i++) {
            items[8][i] = 3;
        }

        items[8][1] = 0;
        items[8][2] = 0;
        items[8][3] = 0;
        items[8][4] = 7;
        items[8][8] = 0;

        for (int i = 0; i < items[9].length; i++) {
            items[9][i] = 3;
        }

        items[9][8] = 0;

        for (int i = 0; i < items[10].length; i++) {
            items[10][i] = 4;
        }

        items[10][2] = 5;
        items[10][8] = 0;

        for (int i = 0; i < items[11].length; i++) {
            items[11][i] = 4;
        }

        items[11][8] = 7;

        for (int i = 0; i < items[12].length; i++) {
            items[12][i] = 4;
        }
    }

    public void initializePlayerLists() {
        player.setListOfMapObjects(rocks, soils, randomElements, enemies);
    }
}