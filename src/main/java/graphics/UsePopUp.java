package graphics;

import app.ForbiddenIsland;
import app.PopUp;
import app.ProgramState;
import app.ProgramStateManager;
import board.Tile;
import board.Treasure;
import board.TreasureTile;
import card.SpecialActionCard;
import card.TreasureCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static graphics.PlayerGraphics.selectIcon;
import static graphics.PlayerGraphics.to1DArrayIndex;

public class UsePopUp extends NonDefaultPoppable implements Initializable {
    public Rectangle topLabelBox;
    public Label topLabel;
    public ImageView cardUsed;
    public Label infoLabel;
    public GridPane tileBoard;
    public GridPane selectionBoard;
    public AnchorPane pane;
    public Button cancelButton;
    private boolean destination;

    public void refreshSelections(Player p, TreasureCard tc) {
        if (!destination && tc.getName().equals("Helicopter")) {
            for (Node n : selectionBoard.getChildren()){
                StackPane q = (StackPane) n;
                for (int i = 0; i < q.getChildren().size(); i++){
                    Node n1 = q.getChildren().get(i);
                    if (n1 instanceof StackPane){
                        q.getChildren().remove(i--);
                    }
                }
            }
            for (Player pl : ForbiddenIsland.getBoard().getPlayers()) {
                int x = pl.getPositionX();
                int y = pl.getPositionY();
                StackPane sp = (StackPane) selectionBoard.getChildren().get(to1DArrayIndex(x, y));
                if (sp.getChildren().size() <= 1) {
                    ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                    StackPane pa = new StackPane(c);
                    pa.setOnMouseClicked((event2) -> {
                        if (ForbiddenIsland.getBoard().getBoard().get(y).get(x).getName().equals("Fools Landing") && ForbiddenIsland.getBoard().isReadyToWin()){
                            ProgramStateManager.goToState(ProgramState.WIN);
                            ForbiddenIsland.refreshDisplay();
                        } else {
                            destination = true;
                            List<Player> playersHere = new ArrayList<>();
                            for (Player pl1 : ForbiddenIsland.getBoard().getPlayers()) {
                                if (pl1.getPositionX() == x && pl1.getPositionY() == y) {
                                    playersHere.add(pl1);
                                }
                            }
                            for (List<Tile> ti : ForbiddenIsland.getBoard().getBoard()) {
                                for (Tile t : ti) {
                                    if (t != null && t.isMovable()) {
                                        StackPane sp1 = (StackPane) selectionBoard.getChildren().get(to1DArrayIndex(t.getPositionX(), t.getPositionY()));
                                        if (t.getPositionX() == x && t.getPositionY() == y) {
                                            for (int i1 = 0; i1 < sp1.getChildren().size(); i1++) {
                                                if (sp1.getChildren().get(i1) instanceof StackPane) {
                                                    sp1.getChildren().remove(i1--);
                                                }
                                            }
                                        } else {
                                            ImageView c1 = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                            StackPane pa1 = new StackPane(c1);
                                            pa1.setOnMouseClicked(event3 -> {
                                                List<Player> playersSelected;
                                                if (playersHere.size() > 1) {
                                                    playersSelected = Objects.requireNonNull(PopUp.HELICOPTER.loadHelicopter(playersHere));
                                                } else {
                                                    playersSelected = new ArrayList<>(playersHere);
                                                }
                                                if (playersSelected.size() >= 1) {
                                                    String people = "H" + p.getRole().toNotation() + " (" + t.getPositionX() + ", " + t.getPositionY() + ") " ;
                                                    String starting = " (" + playersSelected.get(0).getPositionX() + ", " + playersSelected.get(0).getPositionY() + ") ";
                                                    for (Player pla : playersSelected){
                                                        people += pla.getRole().toNotation();
                                                        pla.move(t.getPositionX(), t.getPositionY());
                                                    }
                                                    TurnManager.addNonAction(people + starting);
                                                    p.discardCard(tc);
                                                    BoardStateGraphicsInitializer.getPg().refreshDisplay();
                                                    close();
                                                }
                                            });
                                            sp1.getChildren().add(pa1);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    sp.getChildren().add(pa);
                }
            }
        } else {
            destination = false;
        }
    }


    public void initializePopUp(Player p, TreasureCard tc) {
        pane.setOnMouseClicked(event -> {
            refreshSelections(p, tc);
        });
        cardUsed.setImage(SwingFXUtils.toFXImage(tc.getGraphic(), null));
        tileBoard.getChildren().clear();
        selectionBoard.getChildren().clear();
        topLabel.setText(p.getRole().getName());
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
        for (Player pl : ForbiddenIsland.getBoard().getPlayers()) {
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
        if (tc.getName().equals("Helicopter")){
            refreshSelections(p, tc);
        } else {
            for (List<Tile> ti : ForbiddenIsland.getBoard().getBoard()) {
                for (Tile t : ti){
                    if (t != null && t.isFlooded()) {
                        int x1 = t.getPositionX();
                        int y1 = t.getPositionY();
                        StackPane sp = (StackPane) selectionBoard.getChildren().get(to1DArrayIndex(x1, y1));
                        ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                        StackPane pa = new StackPane(c);
                        pa.setOnMouseClicked((event2) -> {
                            TurnManager.addNonAction("B" + p.getRole().toNotation() + " (" + x1 + ", " + y1 + ")");
                            p.discardCard(tc);
                            t.shoreUp();
                            close();
                            BoardStateGraphicsInitializer.refreshDisplay();
                        });
                        sp.getChildren().add(pa);
                    }
                }
            }
        }
    }

    @Override
    public void close() {
        Stage thisStage = (Stage) selectionBoard.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(55));
        infoLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(41));
        cancelButton.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
    }
}
