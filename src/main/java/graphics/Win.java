package graphics;

import app.ForbiddenIsland;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Win implements Initializable {
    public Label reasonLabel;
    public Label lostLabel;
    public Button quitButton;

    public void quit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lostLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(300));
        reasonLabel.setFont(ForbiddenIsland.getForbiddenIslandFont(32));
        quitButton.setFont(ForbiddenIsland.getForbiddenIslandFont(50));
    }
}
