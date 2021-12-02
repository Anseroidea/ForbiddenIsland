package graphics;

import app.ForbiddenIsland;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

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
    private Player playerClicked;
    private Player currentPlayer;
    private BufferedImage selectIcon;

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
                    playerClicked = null;
                    ContextMenu menu = new ContextMenu();
                    if (currentPlayer.getRole().getName().equalsIgnoreCase("Pilot")){
                        MenuItem moveMenu = new MenuItem("Fly");
                        moveMenu.setOnAction(event2 -> {
                            playerClicked = p;
                            for (Tile t : p.getRole().getMovableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                Pane pa = new Pane(c);
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
                            playerClicked = p;
                            System.out.println(currentPlayer.getRole().getName() + " move clicked");
                            System.out.println(playerClicked);
                            for (Tile t : p.getRole().getMovableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                Pane pa = new Pane(c);
                                pa.setOnMouseClicked((event1) -> {
                                    if (TurnManager.addAction("M (" + x + ", " + y + "), (" + x1 + ", " + y1 + ")")) {
                                        p.setPos(x1, y1);
                                        refreshDisplay();
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                            playerClicked = null;
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
                                Pane pa = new Pane(c);
                                pa.setOnMouseClicked((event2) -> {
                                    if (TurnManager.addAction("S (" + x + ", " + y + "), (" + x1 + ", " + y1 + ")")) {
                                        p.setPos(x1, y1);
                                        refreshDisplay();
                                    }
                                });
                                sp.getChildren().add(pa);
                            }
                        });
                        menu.getItems().add(shoreMenu);
                    }
                    if (ForbiddenIsland.getBoard().getBoard().get(p.getPositionY()).get(p.getPositionX()) instanceof TreasureTile treasureTile){
                        if (!ForbiddenIsland.getBoard().getTreasuresClaimed().get(treasureTile.getTreasureHELD())) {
                            MenuItem claimMenu = new MenuItem("Claim Treasure");
                            menu.getItems().add(claimMenu);
                        }
                    }
                });
            } else {
                img.setOnMouseClicked(event -> {
                    ContextMenu menu = new ContextMenu();
                    if (currentPlayer.getPositionX() == p.getPositionX() && currentPlayer.getPositionY() == p.getPositionY() || currentPlayer.getRole().getName().equals("Messenger")) {
                        menu.getItems().add(new MenuItem("Give Cards"));
                    }
                    if (currentPlayer.getRole().getName().equals("Navigator")){
                        MenuItem navigateMenu = new MenuItem("Navigate");
                        navigateMenu.setOnAction(event1 -> {
                            for (Tile t : p.getRole().getNavigatableTiles(p)) {
                                int x1 = t.getPositionX();
                                int y1 = t.getPositionY();
                                StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                                ImageView c = new ImageView(SwingFXUtils.toFXImage(selectIcon, null));
                                Pane pa = new Pane(c);
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
        for (int i = 0; i < 5 && i < players.get(players.size() - 1).getDeck().size(); i++){
            ImageView img = (ImageView) h.getChildren().get(i);
            List<TreasureCard> mainHandDeck= players.get(players.size() - 1).getDeck();
            Collections.sort(mainHandDeck);
            TreasureCard tc = mainHandDeck.get(i);
            img.setImage(SwingFXUtils.toFXImage(tc.getGraphic(), null));
        }
        p.setFill(players.get(players.size() - 1).getRole().getColor());
        topLabelBox.setFill(players.get(players.size() - 1).getRole().getColor());
        topLabel.setText(players.get(players.size() - 1).getRole().getClass().getSimpleName());
        if (players.size() >= 2){
            Polygon p1 = (Polygon) hand1.getChildren().get(0);
            p1.setFill(players.get(0).getRole().getColor());
            HBox h1 = (HBox) hand1.getChildren().get(1);
            for (int i = 0; i < 5 && i < players.get(0).getDeck().size(); i++){
                ImageView img = (ImageView) h1.getChildren().get(i);
                List<TreasureCard> deck = players.get(0).getDeck();
                Collections.sort(deck);
                TreasureCard tc = deck.get(i);
                img.setImage(SwingFXUtils.toFXImage(tc.getSmallGraphic(), null));
            }
        }
        if (players.size() >= 3){
            hand2.setVisible(true);
            Polygon p1 = (Polygon) hand2.getChildren().get(0);
            p1.setFill(players.get(1).getRole().getColor());
            HBox h1 = (HBox) hand2.getChildren().get(1);
            for (int i = 0; i < 5 && i < players.get(1).getDeck().size(); i++){
                ImageView img = (ImageView) h1.getChildren().get(i);
                List<TreasureCard> deck = players.get(1).getDeck();
                Collections.sort(deck);
                TreasureCard tc = deck.get(i);
                img.setImage(SwingFXUtils.toFXImage(tc.getSmallGraphic(), null));
            }
        }
        if (players.size() == 4){
            hand3.setVisible(true);
            Polygon p1 = (Polygon) hand3.getChildren().get(0);
            p1.setFill(players.get(2).getRole().getColor());
            HBox h1 = (HBox) hand3.getChildren().get(1);
            for (int i = 0; i < 5 && i < players.get(2).getDeck().size(); i++){
                ImageView img = (ImageView) h1.getChildren().get(i);
                List<TreasureCard> deck = players.get(2).getDeck();
                Collections.sort(deck);
                TreasureCard tc = deck.get(i);
                img.setImage(SwingFXUtils.toFXImage(tc.getSmallGraphic(), null));
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
            selectIcon = ImageIO.read(ForbiddenIsland.class.getResource("/images/players/extra/Tile_Movement_Icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshSelections(){
        System.out.println("Current state: " + playerClicked);
        if (playerClicked == null){
            for (Node n : board.getChildren()){
                StackPane s = (StackPane) n;
                for (int i = 0; i < s.getChildren().size(); i++){
                    if (s.getChildren().get(i) instanceof StackPane){
                        s.getChildren().remove(i--);
                    }
                }
            }
        } else {
            playerClicked = null;
        }
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
    }


}
