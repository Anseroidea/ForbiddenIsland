package graphics;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PlayerGraphics {
    public Polygon playerHand;
    public Rectangle playerBar;
    public Text undoButton;
    public Polygon playerHand2;
    public Polygon playerHand4;
    public Polygon playerHand3;

    public void playSpecialCards(MouseEvent mouseEvent) {
        System.out.println("The player wants to play special cards!");
    }

    public void endTurn(MouseEvent mouseEvent) {
        System.out.println("The player wants to end their turn!");
    }

    public void undo(MouseEvent mouseEvent) {
        System.out.println("The player wants to undo!");
    }
}
