package app;

public class ProgramStateManager {

    private ProgramState currentState = ProgramState.MAINMENU;

    public ProgramState getCurrentState() {
        return currentState;
    }

    public void goToState(ProgramState ps){
        currentState = ps;
    }
}

