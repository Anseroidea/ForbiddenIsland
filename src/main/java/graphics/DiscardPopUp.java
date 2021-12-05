package graphics;

import app.PopUp;
import card.SpecialActionCard;
import card.TreasureCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import player.Player;
import player.TurnManager;

public class DiscardPopUp implements Poppable {
    public Label topLabel;
    public HBox cardBox;
    public Button useButton;
    public Button discardButton;
    public ImageView selection;
    public AnchorPane pane;
    private boolean cardSelected;
    private TreasureCard selectedCard;
    private Player curr;

    public void initializePopUp(){
        selection.setVisible(false);
        discardButton.setDisable(true);
        useButton.setDisable(true);
        selectedCard = null;
        cardSelected = false;
        pane.setOnMouseClicked(event -> {
            if (!(selectedCard instanceof SpecialActionCard)){
                useButton.setDisable(true);
            }
            if (!cardSelected){
                discardButton.setDisable(true);
                useButton.setDisable(true);
                selection.setVisible(false);
                selectedCard = null;
            } else {
                cardSelected = false;
            }
        });
        curr = TurnManager.getCurrentPlayer();
        topLabel.setText(TurnManager.getCurrentPlayer().getRole().getName() + " has " + curr.getDeck().size() + " cards! Discard or use them until you have 5 left.");
        cardBox.getChildren().clear();
        for(TreasureCard tc : curr.getDeck()){
            ImageView im = new ImageView(SwingFXUtils.toFXImage(tc.getGraphic(), null));
            im.setOnMouseClicked(event -> {
                discardButton.setDisable(false);
                if (tc instanceof SpecialActionCard){
                    useButton.setDisable(false);
                }
                selectedCard = tc;
                cardSelected = true;
                selection.setLayoutX(im.getLayoutX() - 3);
                selection.setY(132);
                selection.setVisible(true);
            });
            cardBox.getChildren().add(im);
        }
    }

    public void close(){
        Stage thisStage = (Stage) discardButton.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    public void discard(){
        curr.discardCard(selectedCard);
        if (curr.getDeck().size() <= 5){
            close();
        } else {
            initializePopUp();
        }
    }

    public void use(){
        if (selectedCard != null){
            PopUp.USE.loadUse(TurnManager.getCurrentPlayer(), selectedCard);
        }
        if (curr.getDeck().size() <= 5){
            close();
        } else {
            initializePopUp();
        }
    }


}
