package graphics;

import app.ForbiddenIsland;
import board.Tile;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import player.Player;

import java.util.List;

public class PlayerGraphics {
    public Rectangle topLabelBox;
    public Label topLabel;
    public StackPane hand1;
    public StackPane hand2;
    public StackPane hand3;
    public StackPane mainHand;
    public GridPane board;

    public void playSpecialCards(MouseEvent mouseEvent) {
        System.out.println("The player wants to play special cards!");
    }

    public void endTurn(MouseEvent mouseEvent) {
        System.out.println("The player wants to end their turn!");
    }

    public void undo(MouseEvent mouseEvent) {
        System.out.println("The player wants to undo!");
    }


    public void initializePlayers(){
        List<Player> players = ForbiddenIsland.getBoard().getPlayers();
        for (List<Tile> t : ForbiddenIsland.getBoard().getBoard()){
            for (Tile ti : t){
                if (ti != null)
                    board.add(new StackPane(), ti.getPositionX(), ti.getPositionY());
            }
        }
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
