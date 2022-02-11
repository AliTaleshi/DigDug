package ir.ac.kntu.mapObjects.randomElements;

import ir.ac.kntu.mapObjects.MapObject;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class RandomElement extends MapObject {

    private String name;

    public RandomElement(GridPane gridPane, Node node) {
        super(gridPane, node);
    }

    public RandomElement() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
