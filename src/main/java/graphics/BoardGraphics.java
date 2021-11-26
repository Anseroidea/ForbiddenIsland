package graphics;

import app.ForbiddenIsland;
import board.Tile;
import board.WaterLevel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BoardGraphics implements Initializable {
    public GridPane board;
    public Label waterLevelLabel;
    public Gauge waterLevelGauge;
    public StackPane waterPane;
    public static Font castellar = Font.loadFont(ForbiddenIsland.class.getResource("/fonts/castellar.ttf").toExternalForm(), 10);

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

    public void initializeTiles(){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        for (int r = 0; r < 6; r++){
            for (int c = 0; c < 6; c++){
                if (board.get(r).get(c) != null){
                    StackPane s = new StackPane();
                    s.getChildren().add(new ImageView(SwingFXUtils.toFXImage(board.get(r).get(c).getGraphic(), null)));
                    Label l = new Label(board.get(r).get(c).getName());
                    l.setPadding(new Insets(0, 0, -130, 0));
                    l.setFont(castellar);
                    s.getChildren().add(l);
                    this.board.add(s, c, r); //this is not a mistake, gridpane works like this ik its weird
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        waterLevelGauge = GaugeBuilder.create().minValue(0).maxValue(2).build();
        waterPane.getChildren().add(waterLevelGauge);

         */
    }
}
