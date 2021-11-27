package app;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public enum ProgramState {

    BOARD,
    INPUT,
    MAINMENU,
    WIN,
    LOSE;

    private Pane p;

    public Scene getScene(){
        return new Scene(p, 1920, 1080);
    }
    public Pane getPane(){
        return p;
    }
    public void setPane(Pane a){
        p = a;
    }

}
