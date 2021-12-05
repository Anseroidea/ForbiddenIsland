package graphics;

import app.PopUp;
import card.SpecialActionCard;
import card.TreasureCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Player;
import player.TurnManager;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UseHandPopUp implements Poppable, Initializable {
    public Button useButton;
    public AnchorPane pane;
    public VBox vBox;
    private ImageView selection;
    private boolean cardSelected;
    private TreasureCard selectedCard;
    private Player selected;

    @Override
    public void initializePopUp() {
        selection.setVisible(false);
        useButton.setDisable(true);
        selectedCard = null;
        selected = null;
        cardSelected = false;
        pane.setOnMouseClicked(event -> {
            if (!cardSelected){
                useButton.setDisable(true);
                selection.setVisible(false);
                selectedCard = null;
                selected = null;
            } else {
                cardSelected = false;
            }
        });
        vBox.getChildren().removeIf(n -> n instanceof StackPane);
        List<Player> players = new ArrayList<>(TurnManager.getPlayers());
        for (int i = players.size() - 1; i >= 0; i--){
            Player p = players.get(i);
            StackPane sp = new StackPane();
            sp.setPrefSize(600, 140);
            Rectangle r = new Rectangle();
            r.setFill(p.getRole().getColor());
            r.setHeight(140);
            r.setWidth(600);
            r.setStyle("-fx-stroke-width: 5; -fx-stroke: black;");
            sp.getChildren().add(r);
            HBox h = new HBox();
            h.setSpacing(20);
            for(TreasureCard tc : p.getDeck()){
                if (tc instanceof SpecialActionCard) {
                    ImageView im = new ImageView(SwingFXUtils.toFXImage(tc.getSmallGraphic(), null));
                    int y = 84 + 155 * (i);
                    im.setOnMouseClicked(event -> {
                        useButton.setDisable(false);
                        selectedCard = tc;
                        cardSelected = true;
                        selection.setLayoutX(im.getLayoutX() - 3);
                        selection.setY(y);
                        selection.setVisible(true);
                        selected = p;
                    });
                    h.getChildren().add(im);
                }
            }
            sp.getChildren().add(h);
            h.setAlignment(Pos.CENTER);
            vBox.getChildren().add(1, sp);
        }
    }

    @Override
    public void close() {
        Stage thisStage = (Stage) useButton.getScene().getWindow();
        thisStage.getScene().setRoot(new AnchorPane());
        thisStage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        close();
    }

    public void useCard(ActionEvent actionEvent) {
        PopUp.USE.loadUse(selected, selectedCard);
        initializePopUp();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            selection = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/cards/treasureCards/Card_Selection_Icon.png"))), null));
            selection.setFitHeight(124);
            selection.setFitWidth(83);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().add(selection);
        selection.setVisible(false);
    }
}
