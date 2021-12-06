package graphics;

import app.ForbiddenIsland;
import app.ProgramState;
import app.ProgramStateManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button start;
    public Button rules;
    public Button quit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setFont(ForbiddenIsland.getForbiddenIslandFont(49));
        rules.setFont(ForbiddenIsland.getForbiddenIslandFont(49));
        quit.setFont(ForbiddenIsland.getForbiddenIslandFont(49));
    }

    public void start(ActionEvent actionEvent) {
        ProgramStateManager.goToState(ProgramState.INPUT);
        ForbiddenIsland.refreshDisplay();
    }

    public void rules(ActionEvent actionEvent) {
        ProgramStateManager.goToState(ProgramState.RULES);
        ForbiddenIsland.refreshDisplay();
    }

    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
