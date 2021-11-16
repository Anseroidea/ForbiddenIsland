package player;

import board.BoardGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TurnManager {

    private static int actions;
    private static Object ArrayList;
    private static Stack<String> actionStrings = new Stack<>(); //notation [role first letter] [coords]
    private static Stack<BoardGame> boardGameStates = new Stack<>();
    private static Player currentPlayer;

    public static Player getCurrentPlayer(){
        return currentPlayer;
    }

    public static int getActions(){
        return actions;
    }

    public static void endTurn() {

    }

    public static boolean addAction(String s){
        if (actions < 3){
            String lastAction = actionStrings.peek();
            String player = lastAction.substring(1);
            String coord1 = lastAction.substring(lastAction.indexOf("("), lastAction.indexOf("(") + 6);
            String coord2 = lastAction.substring(lastAction.indexOf(")"), lastAction.indexOf("(") + 6);
            if (lastAction.startsWith("N") && isTwoSpaces(coord1, coord2){
                actionStrings.pop();

            } else if (lastAction.startsWith("E"))
        }
    }


    /**
     * @param coord1 initial coordinate of the moved player ex: (1, 4)
     * @param coord2 final coordinate of the moved player ex: (1, 4)
     * @return true/false whether those coordinates are within two spaces of each other
     */
    private static boolean isTwoSpaces(String coord1, String coord2){
        String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
        String[] coords2 = coord2.replace("(", "").replace(")", "").split(", ");
        return isTwoSpaces(coords1[0], coords1[1], coords2[0], coords2[1]);
    }

    private static boolean isTwoSpaces(String x1, String x2, String y1, String y2){
        return isTwoSpaces(Integer.parseInt(x1), Integer.parseInt(x2), Integer.parseInt(y1), Integer.parseInt(y2));
    }

    private static boolean isTwoSpaces(int x1, int x2, int y1, int y2){
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) <= 2;
    }

    public static List<String> toFormattedStrings(){
        return actionStrings;
    }

    public static boolean undoAction(){
        if (actions > 0){
            actionStrings.pop();
            boardGameStates.pop();
            actions--;
            return true;
        } else {
            return false;
        }
    }

}
