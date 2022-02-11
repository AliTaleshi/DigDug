package ir.ac.kntu.mapObjects;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class MapObject {

    private GridPane gridPane;
    private int columnNumber;
    private int rowNumber;
    private Node node;
    private boolean exist;

    public MapObject() {}

    public MapObject(GridPane gridPane, Node node, boolean exist) {
        this.gridPane = gridPane;
        this.node = node;
        this.exist = exist;
    }

    public MapObject(GridPane gridPane, Node node) {
        this.gridPane = gridPane;
        this.node = node;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Node getNode() {
        return node;
    }

    public void disappear() {
        Platform.runLater(() -> gridPane.getChildren().remove(node));
    }
}
