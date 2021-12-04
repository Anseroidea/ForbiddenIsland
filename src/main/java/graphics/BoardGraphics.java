package graphics;

import app.ForbiddenIsland;
import board.Tile;
import board.Treasure;
import board.TreasureTile;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.awt.image.BufferedImage;
import java.util.List;

public class BoardGraphics{
    public GridPane board;
    public static Font castellar = Font.loadFont(ForbiddenIsland.class.getResource("/fonts/castellar.ttf").toExternalForm(), 10);
    public ImageView stick;
    public final int initialStickY = 287;
    public HBox claimedTreasures;

    public void refreshTiles(){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        this.board.getChildren().clear();
        int treasures = 0;
        for (int r = 0; r < 6; r++){
            for (int c = 0; c < 6; c++){
                if (board.get(r).get(c) != null && board.get(r).get(c).isMovable()){
                    StackPane s = new StackPane();
                    s.getChildren().add(new ImageView(SwingFXUtils.toFXImage(board.get(r).get(c).getGraphic(), null)));
                    Label l = new Label(board.get(r).get(c).getName());
                    l.setPadding(new Insets(0, 0, -130, 0));
                    l.setFont(castellar);
                    s.getChildren().add(l);
                    if (board.get(r).get(c) instanceof TreasureTile tile && !tile.getTreasureHELD().isClaimed()){
                        ImageView img = new ImageView(SwingFXUtils.toFXImage(tile.getTreasureHELD().getIcon(), null));
                        img.setFitWidth(49);
                        img.setFitHeight(53);
                        StackPane.setAlignment(img, Pos.BOTTOM_RIGHT);
                        StackPane.setMargin(img, new Insets(0, 0, 20, 0));
                        s.getChildren().add(img);
                    }
                    this.board.add(s, c, r); //this is not a mistake, gridpane works like this ik its weird
                } else if ("(0, 1)(0, 4)(5, 1)(5, 4)".contains("(" + r + ", " + c + ")")){
                    Treasure t = ForbiddenIsland.getBoard().getTreasures().get(treasures++);
                    BufferedImage graphic = null;
                    if (t.isClaimed()){
                        graphic = t.getGray();
                    } else {
                        graphic = t.getColor();
                    }
                    ImageView img = new ImageView(SwingFXUtils.toFXImage(graphic, null));
                    img.setFitHeight(155);
                    img.setFitWidth(155);
                    this.board.add(img, c, r);
                }
            }
        }
    }

    public void refreshHeldTreasures(){
        claimedTreasures.getChildren().clear();
        for (Treasure t : ForbiddenIsland.getBoard().getTreasures()){
            BufferedImage graphic;
            if (t.isClaimed()){
                graphic = t.getColor();
            } else {
                graphic = t.getGray();
            }
            claimedTreasures.getChildren().add(new ImageView(SwingFXUtils.toFXImage(ForbiddenIsland.resize(graphic, 99, 99), null)));
        }
    }

    public void refreshWaterLevel(){
        stick.setLayoutY(initialStickY - ForbiddenIsland.getBoard().getWaterLevel().getTotalSteps() * 31);
    }


    public void refreshDisplay() {
        refreshTiles();
        refreshWaterLevel();
        refreshHeldTreasures();
    }
}

