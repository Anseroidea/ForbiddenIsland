package graphics;

import app.ForbiddenIsland;
import app.PopUp;
import board.Tile;
import board.TreasureTile;
import card.FloodCard;
import card.SpecialActionCard;
import card.TreasureCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import player.Player;
import player.TurnManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class PlayerGraphics implements Initializable {
    public Rectangle topLabelBox;
    public Label topLabel;
    public StackPane hand1;
    public StackPane hand2;
    public StackPane hand3;
    public StackPane mainHand;
    public GridPane board;
    public AnchorPane screenPane;
    public Label actionStrings;
    public Label actionsLeftLabel;
    public ImageView treasureDiscard;
    public ImageView floodDiscard;
    private Player currentPlayer;
    public static BufferedImage selectIcon;
    private BufferedImage cardSelectIcon;
    private boolean cardSelected;

    public void playSpecialCards(MouseEvent mouseEvent) {
        PopUp.USEHAND.load();
    }

    public void endTurn(MouseEvent mouseEvent) {
        ForbiddenIsland.getBoard().nextTurn();
        BoardStateGraphicsInitializer.refreshDisplay();
    }

    public void undo(MouseEvent mouseEvent) {
        System.out.println("The player wants to undo!");
    }


    public void refreshPlayers(){
        currentPlayer = TurnManager.getCurrentPlayer();
        List<Player> players = new ArrayList<>(ForbiddenIsland.getBoard().getPlayers());
        board.getChildren().clear();
        for (List<Tile> t : ForbiddenIsland.getBoard().getBoard()){
            for (Tile ti : t){
                if (ti != null) {
                    StackPane s = new StackPane();
                    s.setAlignment(Pos.CENTER);
                    board.add(s, ti.getPositionX(), ti.getPositionY());
                }
            }
        }
        for (Player p : players){
            int x = p.getPositionX();
            int y = p.getPositionY();
            StackPane s = (StackPane) board.getChildren().get(to1DArrayIndex(x, y));
            ImageView img = new ImageView(SwingFXUtils.toFXImage(p.getGraphics(), null));
            if (p.equals(currentPlayer)) {
                img.setOnMouseClicked((event) -> {
                    System.out.println(currentPlayer.getRole().getName() + " clicked");
                    ContextMenu menu = new ContextMenu();
                    if (currentPlayer.getRole().getName().equalsIgnoreCase("Pilot")){
                        MenuItem moveMenu = new MenuItem("Fly");
                        moveMenu.setOnAction(event2 -> {
                            for (Tile t : p.getRole().getMovableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                StackPane pa = new StackPane(c);
                                pa.setOnMouseClicked((event1) -> {
                                    if (TurnManager.addAction("P (" + x + ", " + y + "), (" + x1 + ", " + y1 + ")")) {
                                        p.setPos(x1, y1);
                                        refreshDisplay();
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                        });
                        menu.getItems().add(moveMenu);
                        menu.show(img, Side.BOTTOM, 0, 0);
                    } else {
                        MenuItem moveMenu = new MenuItem("Move");
                        moveMenu.setOnAction(event2 -> {
                            System.out.println(currentPlayer.getRole().getName() + " move clicked");
                            for (Tile t : p.getRole().getMovableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                StackPane pa = new StackPane(c);
                                pa.setOnMouseClicked((event1) -> {
                                    if (TurnManager.addAction("M (" + x + ", " + y + "), (" + x1 + ", " + y1 + ")")) {
                                        p.setPos(x1, y1);
                                        refreshDisplay();
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                        });
                        menu.getItems().add(moveMenu);
                        menu.show(img, Side.BOTTOM, 0, 0);
                    }
                    if (p.getRole().getShorableTiles(p).size() > 0) {
                        MenuItem shoreMenu = new MenuItem("Shore Up");
                        shoreMenu.setOnAction(event1 -> {
                            for (Tile t : p.getRole().getShorableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                StackPane pa = new StackPane(c);
                                pa.setOnMouseClicked((event2) -> {
                                    if (TurnManager.addAction("S (" + x1 + ", " + y1 + ")")) {
                                        t.shoreUp();
                                        BoardStateGraphicsInitializer.refreshTiles();
                                        refreshActions();
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                        });
                        menu.getItems().add(shoreMenu);
                    }
                    if (ForbiddenIsland.getBoard().getBoard().get(p.getPositionY()).get(p.getPositionX()) instanceof TreasureTile treasureTile){
                        if (!treasureTile.getTreasureHELD().isClaimed() && currentPlayer.canClaim(treasureTile.getTreasureHELD())) {
                            MenuItem claimMenu = new MenuItem("Claim Treasure");
                            claimMenu.setOnAction(event1 -> {
                                currentPlayer.claimTreasure(treasureTile.getTreasureHELD());
                                BoardStateGraphicsInitializer.refreshDisplay();
                            });
                            menu.getItems().add(claimMenu);
                        }
                    }
                });
            } else {
                img.setOnMouseClicked(event -> {
                    ContextMenu menu = new ContextMenu();
                    if (currentPlayer.getPositionX() == p.getPositionX() && currentPlayer.getPositionY() == p.getPositionY() || currentPlayer.getRole().getName().equals("Messenger")) {
                        MenuItem giveCards = new MenuItem("Give Cards");
                        giveCards.setOnAction(event1 -> {
                            HBox h2 = (HBox) mainHand.getChildren().get(2);
                            h2.setVisible(true);
                            currentPlayer.getDeck().forEach(card -> {
                                ImageView im = new ImageView(SwingFXUtils.toFXImage(cardSelectIcon, null));
                                StackPane sp = new StackPane(im);
                                sp.setOnMouseClicked(event3 -> {
                                    if (TurnManager.addAction("G" + p.getRole().toNotation() + " " + card.getName())){
                                        currentPlayer.giveCard(p, card);
                                        refreshDisplay();
                                        if (p.getDeck().size() > 5){
                                            PopUp.DISCARD.load();
                                        }
                                    }
                                });
                                h2.getChildren().add(sp);
                            });;
                        });
                        menu.getItems().add(giveCards);
                    }
                    if (currentPlayer.getRole().getName().equals("Navigator")){
                        MenuItem navigateMenu = new MenuItem("Navigate");
                        navigateMenu.setOnAction(event1 -> {
                            for (Tile t : p.getRole().getNavigatableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                StackPane pa = new StackPane(c);
                                pa.setOnMouseClicked((event2) -> {
                                    if (TurnManager.addAction("N" + p.getRole().toNotation() + " (" + x + ", " + y + "), (" + x1 + ", " + y1 + ")")) {
                                        p.setPos(x1, y1);
                                        refreshDisplay();
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                        });
                        menu.getItems().add(navigateMenu);
                    }
                    menu.show(img, Side.BOTTOM, 0, 0);
                });
            }
            if (s.getChildren().size() >= 1 && !(s.getChildren().get(0) instanceof GridPane)){
                GridPane gp = new GridPane();
                gp.add(s.getChildren().get(0), 0, 0);
                gp.setPrefSize(50, 73);
                gp.setAlignment(Pos.CENTER);
                s.getChildren().add(gp);
            }
            if (s.getChildren().size() >= 1){
                GridPane gp = (GridPane) s.getChildren().get(0);
                int numOnTile = gp.getChildren().size();
                gp.add(img, numOnTile % 2, numOnTile/2);
            }else {
                s.getChildren().add(img);
            }
        }
        //hands
        players = new ArrayList<>(TurnManager.getPlayers());
        hand2.setVisible(false);
        hand3.setVisible(false);
        Polygon p = (Polygon) mainHand.getChildren().get(0);
        HBox h = (HBox) mainHand.getChildren().get(1);
        h.getChildren().clear();
        List<TreasureCard> deck1 = players.get(players.size() - 1).getDeck();
        Collections.sort(deck1);
        for (int i = 0; i < 5 && i < deck1.size(); i++){
            TreasureCard tc = deck1.get(i);
            ImageView e = new ImageView(SwingFXUtils.toFXImage(tc.getGraphic(), null));
            e.setStyle("-fx-border-color: black;");
            if (tc instanceof SpecialActionCard){
                if (tc.getName().equals("Helicopter")){
                    e.setOnMouseClicked(event -> {
                        refreshSelections();
                        cardSelected = true;
                        for (Player pl : ForbiddenIsland.getBoard().getPlayers()){
                            int x = pl.getPositionX();
                            int y = pl.getPositionY();
                            StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x, y));
                            if (sp.getChildren().size() <= 1){
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                StackPane pa = new StackPane(c);
                                pa.setOnMouseClicked((event2) -> {
                                    List<Player> playersHere = new ArrayList<>();
                                    for (Player pl1 : ForbiddenIsland.getBoard().getPlayers()){
                                        if (pl1.getPositionX() == x && pl1.getPositionY() == y){
                                            playersHere.add(pl1);
                                        }
                                    }
                                    cardSelected = true;
                                    for (List<Tile> ti :ForbiddenIsland.getBoard().getBoard()){
                                        for (Tile t : ti){
                                            if (t != null && t.isMovable()){
                                                StackPane sp1 = (StackPane) board.getChildren().get(to1DArrayIndex(t.getPositionX(), t.getPositionY()));
                                                if (t.getPositionX() == x && t.getPositionY() == y){
                                                    for (int i1 = 0; i1 < sp1.getChildren().size(); i1++) {
                                                        if (sp1.getChildren().get(i1) instanceof StackPane){
                                                            sp1.getChildren().remove(i1--);
                                                        }
                                                    }
                                                } else {
                                                    ImageView c1 = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                                    StackPane pa1 = new StackPane(c1);
                                                    pa1.setOnMouseClicked(event3 -> {
                                                        List<Player> playersSelected;
                                                        if (playersHere.size() > 1){
                                                            playersSelected = Objects.requireNonNull(PopUp.HELICOPTER.loadHelicopter(playersHere));
                                                        } else {
                                                            playersSelected = new ArrayList<>(playersHere);
                                                        }
                                                        if (playersSelected.size() >= 1){
                                                            for (Player pla : playersSelected){
                                                                pla.move(t.getPositionX(), t.getPositionY());
                                                            }
                                                            currentPlayer.removeCard(tc);
                                                            refreshDisplay();
                                                        }
                                                    });
                                                    sp1.getChildren().add(pa1);
                                                }
                                            }
                                        }
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                        }
                    });
                } else {
                    e.setOnMouseClicked(event -> {
                        refreshSelections();
                        cardSelected = true;
                        for (List<Tile> ti : ForbiddenIsland.getBoard().getBoard()) {
                            for (Tile t : ti){
                                if (t != null && t.isFlooded()) {
                                    int x1 = t.getPositionX();
                                    int y1 = t.getPositionY();
                                    StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                    ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                    StackPane pa = new StackPane(c);
                                    pa.setOnMouseClicked((event2) -> {
                                        deck1.remove(tc);
                                        t.shoreUp();
                                        BoardStateGraphicsInitializer.refreshDisplay();
                                    });
                                    sp.getChildren().add(pa);
                                }
                            }
                        }
                    });
                }
            }
            h.getChildren().add(e);
        }
        p.setFill(players.get(players.size() - 1).getRole().getColor());
        topLabelBox.setFill(players.get(players.size() - 1).getRole().getColor());
        topLabel.setText(players.get(players.size() - 1).getRole().getClass().getSimpleName());
        if (players.size() >= 2){
            Polygon p1 = (Polygon) hand1.getChildren().get(0);
            p1.setFill(players.get(0).getRole().getColor());
            HBox h1 = (HBox) hand1.getChildren().get(1);
            h1.getChildren().clear();
            List<TreasureCard> deck = players.get(0).getDeck();
            for (int i = 0; i < 5 && i < deck.size(); i++){
                Collections.sort(deck);
                TreasureCard tc = deck.get(i);
                ImageView e = new ImageView(SwingFXUtils.toFXImage(tc.getGraphic(), null));
                e.setFitHeight(120);
                e.setFitWidth(81);
                h1.getChildren().add(e);
            }
        }
        if (players.size() >= 3){
            hand2.setVisible(true);
            Polygon p1 = (Polygon) hand2.getChildren().get(0);
            p1.setFill(players.get(1).getRole().getColor());
            HBox h1 = (HBox) hand2.getChildren().get(1);
            h1.getChildren().clear();
            List<TreasureCard> deck = players.get(1).getDeck();
            for (int i = 0; i < 5 && i < deck.size(); i++){
                Collections.sort(deck);
                TreasureCard tc = deck.get(i);
                ImageView e = new ImageView(SwingFXUtils.toFXImage(tc.getGraphic(), null));
                e.setFitHeight(120);
                e.setFitWidth(81);
                h1.getChildren().add(e);
            }
        }
        if (players.size() == 4){
            hand3.setVisible(true);
            Polygon p1 = (Polygon) hand3.getChildren().get(0);
            p1.setFill(players.get(2).getRole().getColor());
            HBox h1 = (HBox) hand3.getChildren().get(1);
            h1.getChildren().clear();
            List<TreasureCard> deck = players.get(2).getDeck();
            for (int i = 0; i < 5 && i < deck.size(); i++){
                Collections.sort(deck);
                TreasureCard tc = deck.get(i);
                ImageView e = new ImageView(SwingFXUtils.toFXImage(tc.getGraphic(), null));
                e.setFitHeight(120);
                e.setFitWidth(81);
                h1.getChildren().add(e);
            }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenPane.setOnMouseClicked((event) -> {
            refreshSelections();
        });
        try {
            selectIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("images/players/extra/Tile_Movement_Icon.png")));
            cardSelectIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("images/cards/treasureCards/Card_Selection_Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshSelections(){
        if (!cardSelected) {
            for (Node n : board.getChildren()) {
                StackPane s = (StackPane) n;
                for (int i = 0; i < s.getChildren().size(); i++) {
                    if (s.getChildren().get(i) instanceof StackPane) {
                        s.getChildren().remove(i--);
                    }
                }
            }
        } else {
            cardSelected = false;
        }
        HBox h1 = (HBox) mainHand.getChildren().get(2);
        h1.getChildren().clear();
        h1.setVisible(false);
    }

    public void refreshDisplay(){
        refreshPlayers();
        refreshActions();
        refreshDiscardPiles();
    }

    private void refreshDiscardPiles() {
        treasureDiscard.setRotate(90);
        floodDiscard.setRotate(90);
        if (ForbiddenIsland.getBoard().getTreasureDeck().getDiscardedStack().size() > 0) {
            treasureDiscard.setVisible(true);
            treasureDiscard.setImage(SwingFXUtils.toFXImage(ForbiddenIsland.getBoard().getTreasureDeck().getDiscardedStack().peek().getGraphic(), null));
        } else {
            treasureDiscard.setVisible(false);
        }
        if (ForbiddenIsland.getBoard().getFloodDeck().getDiscardedStack().size() > 0) {
            floodDiscard.setVisible(true);
            floodDiscard.setImage(SwingFXUtils.toFXImage(ForbiddenIsland.getBoard().getFloodDeck().getDiscardedStack().peek().getGraphic(), null));
        } else {
            floodDiscard.setVisible(false);
        }
    }

    public void refreshActions() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TurnManager.toFormattedStrings().size(); i++){
            sb.append((i + 1)).append(". ").append(TurnManager.toFormattedStrings().get(i)).append("\n");
        }
        actionStrings.setText(sb.toString());
        actionsLeftLabel.setText(3 - TurnManager.getActions() + "");
    }


    public void openTreasureDiscard(MouseEvent mouseEvent) {
        PopUp.DISCARDPILE.loadDiscardPile(ForbiddenIsland.getBoard().getTreasureDeck().getDiscardedStack());
    }

    public void openFloodDiscard(MouseEvent contextMenuEvent) {
        PopUp.DISCARDPILE.loadDiscardPile(ForbiddenIsland.getBoard().getFloodDeck().getDiscardedStack());
    }
}
