package graphics;

import app.ForbiddenIsland;
import app.PopUp;
import board.Tile;
import board.TreasureTile;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import player.Player;
import player.TurnManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
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
    private Player playerClicked;
    private Player currentPlayer;
    private BufferedImage selectIcon;
    private BufferedImage cardSelectIcon;
    private boolean isGivingCard;

    public void playSpecialCards(MouseEvent mouseEvent) {
        System.out.println("The player wants to play special cards!");
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
                            System.out.println(playerClicked);
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
                        System.out.println(playerClicked + " q ");
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
        for (int i = 0; i < 5 && i < deck1.size(); i++){
            Collections.sort(deck1);
            TreasureCard tc = deck1.get(i);
            h.getChildren().add(new ImageView(SwingFXUtils.toFXImage(tc.getGraphic(), null)));
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
        System.out.println("Current state: " + playerClicked);
        for (Node n : board.getChildren()){
            StackPane s = (StackPane) n;
            for (int i = 0; i < s.getChildren().size(); i++){
                if (s.getChildren().get(i) instanceof StackPane){
                    s.getChildren().remove(i--);
                }
            }
        }
        HBox h1 = (HBox) mainHand.getChildren().get(2);
        h1.getChildren().clear();
    }

    public void refreshDisplay(){
        refreshPlayers();
        refreshActions();
    }

    public void refreshActions() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TurnManager.toFormattedStrings().size(); i++){
            sb.append((i + 1)).append(". ").append(TurnManager.toFormattedStrings().get(i)).append("\n");
        }
        actionStrings.setText(sb.toString());
        actionsLeftLabel.setText(3 - TurnManager.getActions() + "");
    }


}
