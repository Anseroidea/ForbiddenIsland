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
    public Rectangle topLabelBox;
    public Label topLabel;
    public StackPane hand1;
    public StackPane hand2;
    public StackPane hand3;
    public StackPane mainHand;

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

    public void initializePlayers(){
        List<Player> players = ForbiddenIsland.getBoard().getPlayers();
        for (Player p : players){
            int x = p.getPositionX();
            int y = p.getPositionY();
            StackPane s = (StackPane) board.getChildren().get(to1DArrayIndex(x, y));
            ImageView img = new ImageView(SwingFXUtils.toFXImage(p.getGraphics(), null));
            img.setOnMouseClicked((event) -> {
                for (Tile t : ForbiddenIsland.getBoard().getMovableTilePos(p)){
                    StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(t.getPositionX(), t.getPositionY()));
                    Circle c = new Circle();
                    c.setRadius(20);
                    c.setFill(Color.GRAY);
                    sp.getChildren().add(c);
                }
            });
            s.getChildren().add(img);
        }
        hand2.setVisible(false);
        hand3.setVisible(false);
        Polygon p = (Polygon) mainHand.getChildren().get(0);
        p.setFill(players.get(0).getRole().getColor());
        topLabelBox.setFill(players.get(0).getRole().getColor());
        topLabel.setText(players.get(0).getRole().getClass().getSimpleName());
        if (players.size() >= 2){
            Polygon p1 = (Polygon) hand1.getChildren().get(0);
            p1.setFill(players.get(1).getRole().getColor());
        }
        if (players.size() >= 3){
            hand2.setVisible(true);
            Polygon p1 = (Polygon) hand2.getChildren().get(0);
            p1.setFill(players.get(2).getRole().getColor());
        }
        if (players.size() == 4){
            hand3.setVisible(true);
            Polygon p1 = (Polygon) hand3.getChildren().get(0);
            p1.setFill(players.get(3).getRole().getColor());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        waterLevelGauge = GaugeBuilder.create().minValue(0).maxValue(2).build();
        waterPane.getChildren().add(waterLevelGauge);

         */
    }

    public static int to1DArrayIndex(int x, int y){
        return switch (y) {
            case 0 -> x - 2;
            case 1 -> x - 1 + 2;
            case 2 -> x + 6;
            case 3 -> x + 12;
            case 4 -> x - 1 + 18;
            case 5 -> x - 2 + 22;
            default -> -1;
        };
    }

}

