package graphics;

import app.ForbiddenIsland;
import app.PopUp;
import board.BoardGame;
import board.Tile;
import board.Treasure;
import board.TreasureTile;
import card.FloodCard;
import card.FloodDeck;
import card.TreasureCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import player.Player;
import player.TurnManager;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static graphics.PlayerGraphics.to1DArrayIndex;

public class DrawPopUp implements Poppable, Initializable {
    public ImageView treasureDiscard;
    public ImageView floodDiscard;
    public ImageView stick;
    public GridPane tileBoard;
    public ImageView cardUsed;
    public Label infoLabel;
    public Button drawNext;
    public Button skipAll;
    public Button specialActionCard;
    public GridPane playerBoard;
    public Label cardInfoLabel1;
    public Label cardInfoLabel2;
    public Label floodCardInfoLabel;
    private int cardsToDraw;
    private int cardsDrawn = 0;
    private FloodDeck floodDeck;
    private BoardGame bg;

    @Override
    public void initializePopUp() {
        cardsDrawn = 0;
        bg = ForbiddenIsland.getBoard();
        floodDeck = bg.getFloodDeck();
        cardsToDraw = bg.getWaterLevel().getLevel();
        Player currentPlayer = TurnManager.getCurrentPlayer();
        cardInfoLabel1.setText(currentPlayer.getRole().getName() + " drew " + bg.getLastTwoCards().get(0).getName());
        cardInfoLabel2.setText(currentPlayer.getRole().getName() + " drew " + bg.getLastTwoCards().get(1).getName());
        refreshWaterLevel();
        refreshDisplay();
    }

    public void refreshTiles(){
        tileBoard.getChildren().clear();
        playerBoard.getChildren().clear();
        List<List<Tile>> board = bg.getBoard();
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
                    playerBoard.add(new StackPane(), c, r);
                } else if ("(0, 1)(0, 4)(5, 1)(5, 4)".contains("(" + r + ", " + c + ")")){
                    Treasure t = bg.getTreasures().get(treasures++);
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
        for (Player pl : bg.getPlayers()){
            StackPane sp = (StackPane) playerBoard.getChildren().get(to1DArrayIndex(pl.getPositionX(), pl.getPositionY()));
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
    }

    public void refreshWaterLevel(){
        stick.setLayoutY(285 - ForbiddenIsland.getBoard().getWaterLevel().getTotalSteps() * 31);
    }

    public void refreshDiscards(){
        if (bg.getTreasureDeck().getDiscardedStack().size() > 0) {
            treasureDiscard.setVisible(true);
            treasureDiscard.setImage(SwingFXUtils.toFXImage(bg.getTreasureDeck().getDiscardedStack().peek().getGraphic(), null));
        } else {
            treasureDiscard.setVisible(false);
        }
        if (bg.getFloodDeck().getDiscardedStack().size() > 0) {
            floodDiscard.setVisible(true);
            floodDiscard.setImage(SwingFXUtils.toFXImage(bg.getFloodDeck().getDiscardedStack().peek().getGraphic(), null));
        } else {
            floodDiscard.setVisible(false);
        }
    }

    @Override
    public void close() {
        System.out.println(cardInfoLabel2.isVisible());
        Stage thisStage = (Stage) cardInfoLabel2.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    public void refreshButtons(){
        if (cardsDrawn == cardsToDraw){
            drawNext.setText("Next Turn");
            skipAll.setDisable(true);
            specialActionCard.setDisable(true);
        } else {
            drawNext.setText("Draw Next");
            skipAll.setDisable(false);
            specialActionCard.setDisable(false);
        }
    }

    public void openTreasureDiscard(MouseEvent mouseEvent) {
        PopUp.DISCARDPILE.loadDiscardPile(bg.getTreasureDeck().getDiscardedStack());
    }

    public void openFloodDiscard(MouseEvent contextMenuEvent) {
        PopUp.DISCARDPILE.loadDiscardPile(bg.getFloodDeck().getDiscardedStack());
    }

    public void drawNext() {
        if (cardsDrawn == cardsToDraw){
            close();
            return;
        }
        cardsDrawn++;
        FloodCard fc = floodDeck.draw(1).get(0);
        cardUsed.setImage(SwingFXUtils.toFXImage(fc.getGraphic(), null));
        fc.getTile().floodTile();
        if (fc.getTile().isSunk()){
            floodDeck.killCard(fc);
            infoLabel.setText(fc.getName() + " has sunk!");
        } else {
            infoLabel.setText(fc.getName() + " has flooded!");
        }
        if (bg.isLost()){
            close();
            return;
        }
        for (Player p : bg.getPlayers()){
            if (bg.getBoard().get(p.getPositionY()).get(p.getPositionX()).isSunk()){
                PopUp.RELOCATE.loadRelocate(p);
            }
        }
        refreshDisplay();
    }

    public void skipAll(ActionEvent actionEvent) {
        while (cardsDrawn < cardsToDraw){
            drawNext();
            if (bg.isLost()){
                return;
            }
        }
        close();
    }

    public void use(ActionEvent actionEvent) {
        PopUp.USEHAND.load();
        refreshDisplay();
    }

    public void refreshDisplay(){
        refreshTiles();
        refreshDiscards();
        refreshButtons();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardInfoLabel1.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        cardInfoLabel2.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        floodCardInfoLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(41));
        infoLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(22));
        drawNext.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        skipAll.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        specialActionCard.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
    }
}
