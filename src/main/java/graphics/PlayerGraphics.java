package graphics;

import app.ForbiddenIsland;
import board.Tile;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import player.Player;
import player.TurnManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerGraphics implements Initializable {
    public Rectangle topLabelBox;
    public Label topLabel;
    public StackPane hand1;
    public StackPane hand2;
    public StackPane hand3;
    public StackPane mainHand;
    public GridPane board;
    public AnchorPane screenPane;
    private Player playerClicked;
    private Player currentPlayer;

    public void playSpecialCards(MouseEvent mouseEvent) {
        System.out.println("The player wants to play special cards!");
    }

    public void endTurn(MouseEvent mouseEvent) {
        System.out.println("The player wants to end their turn!");
    }

    public void undo(MouseEvent mouseEvent) {
        System.out.println("The player wants to undo!");
    }


    public void refreshPlayers(){
        currentPlayer = TurnManager.getCurrentPlayer();
        List<Player> players = ForbiddenIsland.getBoard().getPlayers();
        board.getChildren().clear();
        for (List<Tile> t : ForbiddenIsland.getBoard().getBoard()){
            for (Tile ti : t){
                if (ti != null)
                    board.add(new StackPane(), ti.getPositionX(), ti.getPositionY());
            }
        }
        System.out.println(board.getChildren());
        for (Player p : players){
            int x = p.getPositionX();
            int y = p.getPositionY();
            StackPane s = (StackPane) board.getChildren().get(to1DArrayIndex(x, y));
            ImageView img = new ImageView(SwingFXUtils.toFXImage(p.getGraphics(), null));
            if (p.equals(currentPlayer)) {
                img.setOnMouseClicked((event) -> {
                    for (Node n : board.getChildren()) {
                        StackPane s1 = (StackPane) n;
                        for (int i = 0; i < s1.getChildren().size(); i++) {
                            if (s1.getChildren().get(i) instanceof Circle) {
                                s1.getChildren().remove(i--);
                            }
                        }
                    }
                    playerClicked = p;
                    for (Tile t : ForbiddenIsland.getBoard().getMovableTilePos(p)) {
                        int x1 = t.getPositionX();
                        int y1 = t.getPositionY();
                        StackPane sp = (StackPane) board.getChildren().get(to1DArrayIndex(x1, y1));
                        Circle c = new Circle();
                        c.setRadius(20);
                        c.setFill(Color.GRAY);
                        c.setOnMouseClicked((event1) -> {
                            ContextMenu menu = new ContextMenu();
                            MenuItem moveMenu = new MenuItem("Move");
                            moveMenu.setOnAction(event2 -> {
                                if (TurnManager.addAction("M (" + x + ", " + y + "), (" + x1 + ", " + y1 + ")")) {
                                    p.setPos(x1, y1);
                                    refreshDisplay();
                                }
                            });
                            menu.getItems().add(moveMenu);
                            menu.getItems().add(new MenuItem("Shore Up"));
                            menu.show(c, Side.BOTTOM, 0, 0);
                            playerClicked = p;
                        });
                        sp.getChildren().add(c);
                    }
                });
            }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board.setGridLinesVisible(true);
        screenPane.setOnMouseClicked((event) -> {
            System.out.println("Player Clicked" + playerClicked);
            if (playerClicked == null){
                for (Node n : board.getChildren()){
                    StackPane s = (StackPane) n;
                    for (int i = 0; i < s.getChildren().size(); i++){
                        if (s.getChildren().get(i) instanceof Circle){
                            s.getChildren().remove(i--);
                        }
                    }
                }
            } else {
                playerClicked = null;
            }
        });
    }

    public void refreshDisplay(){
        refreshPlayers();
    }


}
