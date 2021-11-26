package app;

public class ProgramStateManager {

    private static ProgramState currentState = ProgramState.MAINMENU;

    public static ProgramState getCurrentState() {
        return currentState;
    }

    public static void goToState(ProgramState ps){
        currentState = ps;
    }
}

