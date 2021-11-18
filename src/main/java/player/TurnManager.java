package player;

import board.BoardGame;

import java.util.*;

public class TurnManager {

    private static int actions;
    private static Stack<String> actionStrings = new Stack<>(); //notation [role first letter] [coords]
    private static Stack<BoardGame> boardGameStates = new Stack<>();
    private static Player currentPlayer;
    private static Queue<Player> playerQueue = new LinkedList<>();

    public static void setPlayers(Player[] players){
        playerQueue.addAll(Arrays.stream(players).toList());
        endTurn();
    }

    public static Player getCurrentPlayer(){
        return currentPlayer;
    }

    public static int getActions(){
        return actions;
    }

    public static void endTurn() {
        actions = 0;
        actionStrings.clear();
        boardGameStates.clear();
        currentPlayer = playerQueue.remove();
    }

    public static boolean addAction(String s){
        if (actions <= 3){
            String lastAction = actionStrings.peek();
            if (lastAction.startsWith("N")){
                String player = lastAction.substring(1);
                String coord1 = lastAction.substring(lastAction.indexOf("("), lastAction.indexOf("(") + 6);
                String s2 = s.substring(s.lastIndexOf("("), s.lastIndexOf("(") + 6);
                if (isTwoSpaces(coord1, s2)){
                    actionStrings.pop();
                    String finalAction = "N" + player + " " + coord1 + ", " + s2;
                    actionStrings.push(finalAction);
                    return true;
                } else if (actions == 3) {
                    return false;
                } else {
                    actionStrings.push(s);
                    actions++;
                    return true;
                }
            } else if (lastAction.startsWith("E")) {
                String shoreCoord = lastAction.substring(lastAction.indexOf("("), lastAction.indexOf("(") + 6);
                String sShoreCoord = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                actionStrings.pop();
                String finalAction = "E " + shoreCoord + ", " + sShoreCoord;
                actionStrings.push(finalAction);
                return true;
            } else {
                actionStrings.push(s);
                actions++;
                return true;
            }
        } else {
            return false;
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
