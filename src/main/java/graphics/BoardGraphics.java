package graphics;

import app.ForbiddenIsland;
import board.Tile;
import board.WaterLevel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

public class BoardGraphics implements Initializable {
    public GridPane board;
    public Label waterLevelLabel;
    public Gauge waterLevelGauge;
    public static Font castellar = Font.loadFont(ForbiddenIsland.class.getResource("/fonts/castellar.ttf").toExternalForm(), 10);
    public ImageView stick;
    public final int initialStickY = 303;

    public void refreshTiles(){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        this.board.getChildren().clear();
        for (int r = 0; r < 6; r++){
            for (int c = 0; c < 6; c++){
                if (board.get(r).get(c) != null && board.get(r).get(c).isMovable()){
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

    public void refreshWaterLevel(){
        stick.setY(initialStickY - ForbiddenIsland.getBoard().getWaterLevel().getTotalSteps() * 52);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        waterLevelGauge = GaugeBuilder.create().minValue(0).maxValue(2).build();
        waterPane.getChildren().add(waterLevelGauge);

         */
    }


    public void refreshDisplay() {
        refreshTiles();
        refreshWaterLevel();
    }
}

