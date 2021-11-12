package graphics;

import app.ForbiddenIsland;
import board.WaterLevel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardGraphics implements Initializable {
    public GridPane board;
    public Label waterLevelLabel;
    public Gauge waterLevelGauge;
    public StackPane waterPane;

    public void increaseWaterLevel(MouseEvent mouseEvent) {
        WaterLevel waterLevel = ForbiddenIsland.getBoard().getWaterLevel();
        waterLevel.raiseLevel();
        waterLevelLabel.setText(waterLevel.getLevel() + "" + waterLevel.getSteps());

        if (waterLevelGauge.getMaxValue() != waterLevel.getTotalSteps()){
            System.out.println("q");
            waterLevelGauge.setValue(0);
            waterLevelGauge.setMaxValue(waterLevel.getTotalSteps());
        }
        waterLevelGauge.setValue(waterLevel.getSteps());
        System.out.println(waterLevelGauge);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        waterLevelGauge = GaugeBuilder.create().minValue(0).maxValue(2).build();
        waterPane.getChildren().add(waterLevelGauge);

         */
    }
}
