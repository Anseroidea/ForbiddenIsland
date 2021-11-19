package graphics;

import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InputScreen {
    public RadioButton fourButton;
    public RadioButton twoButton;
    public RadioButton threeButton;
    public RadioButton noviceButton;
    public RadioButton normalButton;
    public RadioButton eliteButton;
    public RadioButton legendaryButton;
    public List<RadioButton> difficultyButtons = Arrays.asList(twoButton, threeButton, fourButton);
    public List<RadioButton> numPlayersButtons = Arrays.asList(noviceButton, normalButton, eliteButton, legendaryButton);
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
        } else if (difficultyButtons.stream().filter(ToggleButton::isSelected).count() == 1){

        }
    }

    public void showErrorText(String s){

    }
}
