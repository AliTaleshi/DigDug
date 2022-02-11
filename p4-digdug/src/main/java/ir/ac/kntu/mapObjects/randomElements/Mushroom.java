package ir.ac.kntu.mapObjects.randomElements;

import ir.ac.kntu.mapObjects.randomElements.RandomElement;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Mushroom extends RandomElement {

    public Mushroom(GridPane gridPane, Node node, String name) {
        super(gridPane, node);
        super.setName(name);
    }

    public String getName() {
        super.setName("mushroom");
        return super.getName();
    }
}
