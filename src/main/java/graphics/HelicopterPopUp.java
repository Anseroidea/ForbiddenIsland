package graphics;

import app.ForbiddenIsland;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import player.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HelicopterPopUp extends NonDefaultPoppable implements Initializable {
    public HBox playerBox;
    public AnchorPane pane;
    public Button selectButton;
    public Label selectLabel;
    public Button cancelButton;
    private BufferedImage selectionIcon;
    {
        try {
            selectionIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/players/extra/Player_Selection_Icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Map<Player, Boolean> selectionMap = new HashMap<>();
    
    public void initializePopUp(List<Player> playerList){
        playerBox.getChildren().clear();
        pane.getChildren().removeIf(n -> n instanceof StackPane);
        for (Player p : playerList){
            selectionMap.put(p, false);
            ImageView im = new ImageView(SwingFXUtils.toFXImage(p.getGraphics(), null));
            im.setFitWidth(100);
            im.setFitHeight(146);
            im.setOnMouseClicked(event -> {
                selectionMap.put(p, true);
                ImageView sel = new ImageView(SwingFXUtils.toFXImage(selectionIcon, null));
                StackPane sp = new StackPane(sel);
                sp.setLayoutX(im.getLayoutX() - 8);
                sp.setLayoutY(124);
                sp.setOnMouseClicked(event1 ->{
                    selectionMap.put(p, false);
                    pane.getChildren().remove(sp);
                    refreshSelectButton();
                });
                pane.getChildren().add(sp);
                refreshSelectButton();
            });
            playerBox.getChildren().add(im);
        }
    }

    public void select(ActionEvent actionEvent) {
        close();
    }

    public void cancel(ActionEvent actionEvent) {
        selectionMap.clear();
        close();
    }

    public void close(){
        Stage stage = (Stage) pane.getScene().getWindow();
        pane.getScene().setRoot(new AnchorPane());
        stage.close();
    }

    public void refreshSelectButton(){
        if (selectedPlayers().size() > 0){
            selectButton.setDisable(false);
        } else {
            selectButton.setDisable(true);
        }
    }

    public List<Player> selectedPlayers() {
        return selectionMap.keySet().stream().filter(selectionMap::get).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(24));
        selectButton.setFont(ForbiddenIsland.getForbiddenIslandFont(20));
        cancelButton.setFont(ForbiddenIsland.getForbiddenIslandFont(20));
    }
}
