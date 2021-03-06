package graphics;

import app.ForbiddenIsland;
import card.Card;
import card.FloodCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class DiscardPilePopUp extends NonDefaultPoppable implements Initializable {

    public Label topLabel;
    public VBox vBox;

    public void initializePopUp(Stack<? extends Card> cards){
        if (cards.peek() instanceof FloodCard){
            topLabel.setText("Flood Deck Discard Pile");
        } else {
            topLabel.setText("Treasure Deck Discard Pile");
        }
        vBox.getChildren().removeIf(n -> n instanceof GridPane);
        GridPane discardGrid = new GridPane();
        discardGrid.setVgap(10);
        discardGrid.setHgap(10);
        List<Card> cardList = new ArrayList<>(cards);
        for (int i = cardList.size() - 1; i >= 0; i--) {
            ImageView img = new ImageView(SwingFXUtils.toFXImage(cardList.get(i).getGraphic(), null));
            img.setFitHeight(180);
            img.setFitWidth(121);
            img.setPickOnBounds(true);
            int index = cardList.size() - 1 - i;
            discardGrid.add(img, index % 5, index/5);
            Tooltip tooltip = new Tooltip(cardList.get(i).getName());
            tooltip.setShowDelay(Duration.millis(100));
            Tooltip.install(img, tooltip);
        }
        vBox.getChildren().add(discardGrid);
    }

    @Override
    public void close() {
        Stage thisStage = (Stage) topLabel.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(20));
    }
}
