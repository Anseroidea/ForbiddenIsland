package graphics;

import app.ForbiddenIsland;
import app.ProgramState;
import app.ProgramStateManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InputScreen implements Initializable{
    public RadioButton fourButton;
    public RadioButton twoButton;
    public RadioButton threeButton;
    public RadioButton noviceButton;
    public RadioButton normalButton;
    public RadioButton eliteButton;
    public RadioButton legendaryButton;
    public List<RadioButton> difficultyButtons;
    public List<RadioButton> numPlayersButtons;
    public Label errorText;
    private int difficulty;
    private int numPlayers;

    public void submit(){
        if (difficultyButtons.stream().filter(ToggleButton::isSelected).count() == 1 && numPlayersButtons.stream().filter(ToggleButton::isSelected).count() == 1){
            if (noviceButton.isSelected()){
                difficulty = 20;
            } else if (normalButton.isSelected()){
                difficulty = 21;
            } else if (eliteButton.isSelected()) {
                difficulty = 30;
            } else {
                difficulty = 31;
            }
            if (twoButton.isSelected()){
                numPlayers = 2;
            } else if (threeButton.isSelected()){
                numPlayers = 3;
            } else {
                numPlayers = 4;
            }
            ForbiddenIsland.setBoardGame(difficulty, numPlayers);
            BoardStateGraphicsInitializer.initialize();
            ProgramStateManager.goToState(ProgramState.BOARD);
            ForbiddenIsland.refreshDisplay();
        } else if (numPlayersButtons.stream().noneMatch(ToggleButton::isSelected)){
            setErrorText("You must select a number of players to start with!");
        } else if (numPlayersButtons.stream().filter(ToggleButton::isSelected).count() > 1){
            setErrorText("You must only select one number of players to start with!");
        } else if (difficultyButtons.stream().noneMatch(ToggleButton::isSelected)){
            setErrorText("You must select a difficulty to start with!");
        } else {
            setErrorText("You must only select one difficulty to start with!");
        }
    }

    public void setErrorText(String s){
        errorText.setText(s);
        errorText.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numPlayersButtons = Arrays.asList(twoButton, threeButton, fourButton);
        difficultyButtons = Arrays.asList(noviceButton, normalButton, eliteButton, legendaryButton);
    }
}
