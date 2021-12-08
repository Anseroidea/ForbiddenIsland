package graphics;

import app.ForbiddenIsland;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class Lose implements Initializable {
    public Label lostLabel;
    public Label reasonLabel;
    public Button quitButton;
    public WritableImage boardPreview;
    public Button boardButton;

    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setLoseReason(String s){
        reasonLabel.setText(s);
    }

    public void setBoardPreview(WritableImage b){
        boardPreview = b;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lostLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(300));
        reasonLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(32));
        quitButton.setFont(ForbiddenIsland.getForbiddenIslandFont(50));
        boardButton.setFont(ForbiddenIsland.getForbiddenIslandFont(50));
    }

    public void seeBoard(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setScene(new Scene(new AnchorPane(new ImageView(boardPreview))));
        stage.show();
    }
}
