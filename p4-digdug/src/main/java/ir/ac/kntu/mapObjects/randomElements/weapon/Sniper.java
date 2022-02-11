package ir.ac.kntu.mapObjects.randomElements.weapon;

import ir.ac.kntu.mapObjects.randomElements.RandomElement;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Sniper extends RandomElement {

    public Sniper(GridPane gridPane, Node node, String name) {
        super(gridPane, node);
        super.setName(name);
    }

    public String getName() {
        super.setName("sniper");
        return super.getName();
    }
}
