package graphics;

import app.ForbiddenIsland;
import board.Tile;
import board.Treasure;
import board.TreasureTile;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Player;
import player.TurnManager;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static graphics.PlayerGraphics.to1DArrayIndex;

public class RelocatePopUp extends NonDefaultPoppable implements Initializable {

    public Rectangle topLabelBox;
    public Label topLabel;
    public GridPane tileBoard;
    public GridPane selectionBoard;
    public Label infoLabel;

    public void initializePopUp(Player p) {
        tileBoard.getChildren().clear();
        selectionBoard.getChildren().clear();
        topLabel.setText(p.getRole().getName());
        infoLabel.setText("The tile under " + p.getRole().getName() + " has sunk! Click on a selected square to relocate to that tile.");
        if (p.getRole().getName().equals("Diver")){
            topLabel.setTextFill(Color.WHITE);
        } else {
            topLabel.setTextFill(Color.BLACK);
        }
        topLabelBox.setFill(p.getRole().getColor());
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        int treasures = 0;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                Tile tile = board.get(r).get(c);
                if (tile != null){
                    if (tile.isMovable()) {
                        StackPane s = new StackPane();
                        s.getChildren().add(new ImageView(SwingFXUtils.toFXImage(tile.getGraphic(), null)));
                        Label l = new Label(tile.getName());
                        l.setPadding(new Insets(0, 0, -130, 0));
                        l.setFont(BoardGraphics.castellar);
                        s.getChildren().add(l);
                        if (tile instanceof TreasureTile treasureTile && !treasureTile.getTreasureHELD().isClaimed()){
                            ImageView img = new ImageView(SwingFXUtils.toFXImage(treasureTile.getTreasureHELD().getIcon(), null));
                            img.setFitWidth(49);
                            img.setFitHeight(53);
                            StackPane.setAlignment(img, Pos.BOTTOM_RIGHT);
                            StackPane.setMargin(img, new Insets(0, 0, 20, 0));
                            s.getChildren().add(img);
                        }
                        tileBoard.add(s, c, r); //this is not a mistake, gridpane works like this ik its weird
                    }
                    selectionBoard.add(new StackPane(), c, r);
                } else if ("(0, 1)(0, 4)(5, 1)(5, 4)".contains("(" + r + ", " + c + ")")){
                    Treasure t = ForbiddenIsland.getBoard().getTreasures().get(treasures++);
                    BufferedImage graphic;
                    if (t.isClaimed()){
                        graphic = t.getGray();
                    } else {
                        graphic = t.getColor();
                    }
                    ImageView img = new ImageView(SwingFXUtils.toFXImage(graphic, null));
                    img.setFitHeight(155);
                    img.setFitWidth(155);
                    tileBoard.add(img, c, r);
                }
            }
        }
        for (Player pl : ForbiddenIsland.getBoard().getPlayers()){
            StackPane sp = (StackPane) selectionBoard.getChildren().get(to1DArrayIndex(pl.getPositionX(), pl.getPositionY()));
            ImageView img = new ImageView(SwingFXUtils.toFXImage(pl.getGraphics(), null));
            if (sp.getChildren().size() >= 1 && !(sp.getChildren().get(0) instanceof GridPane)){
                GridPane gp = new GridPane();
                gp.add(sp.getChildren().get(0), 0, 0);
                gp.setPrefSize(50, 73);
                gp.setAlignment(Pos.CENTER);
                sp.getChildren().add(gp);
            }
            if (sp.getChildren().size() >= 1){
                GridPane gp = (GridPane) sp.getChildren().get(0);
                int numOnTile = gp.getChildren().size();
                gp.add(img, numOnTile % 2, numOnTile/2);
            }else {
                sp.getChildren().add(img);
            }
        }
        List<Tile> floodRelocTiles = p.getRole().getFloodRelocTiles(p);
        for (Tile t : floodRelocTiles){
            StackPane sp = (StackPane) selectionBoard.getChildren().get(to1DArrayIndex(t.getPositionX(), t.getPositionY()));
            ImageView im = new ImageView(SwingFXUtils.toFXImage(PlayerGraphics.selectIcon, null));
            sp.setOnMouseClicked(event -> {
                p.move(t.getPositionX(), t.getPositionY());
                close();
            });
            sp.getChildren().add(im);
        }
    }

    @Override
    public void close() {
        Stage thisStage = (Stage) selectionBoard.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(41));
        topLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(55));
    }
}
