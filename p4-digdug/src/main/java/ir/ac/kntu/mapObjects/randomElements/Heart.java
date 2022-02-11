package ir.ac.kntu.mapObjects.randomElements;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Heart extends RandomElement {

    public Heart(GridPane gridPane, Node node) {
        super(gridPane, node);
    }

    public Heart(GridPane gridPane, Node node, String name) {
        super(gridPane, node);
        super.setName(name);
    }

    public String getName() {
        super.setName("heart");
        return super.getName();
    }
}
