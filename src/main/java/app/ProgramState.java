package app;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public enum ProgramState {

    BOARD,
    INPUT,
    MAINMENU,
    WIN,
    LOSE;

    private AnchorPane ap;

    public Scene getScene(){
        return new Scene(ap, 1920, 1080);
    }
    public AnchorPane getAnchorPane(){
        return ap;
    }
    public void setAnchorPane(AnchorPane a){
        ap = a;
    }

}
