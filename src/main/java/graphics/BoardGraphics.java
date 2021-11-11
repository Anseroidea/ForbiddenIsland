package graphics;

import eu.hansolo.medusa.Gauge;
import graphics.CustomGauge;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardGraphics {
    public GridPane board;
    public CustomGauge hi;

    public void addGauge(){
        hi.setValue(20);
    }


    public void increaseWaterLevel(MouseEvent mouseEvent) {
    }
}
