package player;

import app.ForbiddenIsland;
import board.BoardGame;
import board.Treasure;

import java.util.*;

public class TurnManager {

    private static int actions;
    private static Stack<String> actionStrings = new Stack<>(); //notation [role first letter] [coords]
    private static Stack<String> totalActionStrings = new Stack<>();
    private static Player currentPlayer;
    private static Queue<Player> playerQueue = new LinkedList<>();
    private static Map<String, String> moveMap = new HashMap<>(){
        {
            put("M", "Moved to ");
            put("S", "Shored up ");
            put("E", "Shored up ");
            put("G", "Gave ");
            put("N", "Navigated ");
            put("P", "Flew to ");
            put("H", "Heli'd ");
            put("B", "Sandbagged ");
            put("C", "Claimed ");
        }
    };

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
        totalActionStrings.clear();
        currentPlayer = playerQueue.remove();
        playerQueue.add(currentPlayer);
    }

    public static void addNonAction(String s){
        totalActionStrings.push(s);
    }

    public static boolean addAction(String s){
        if (actions == 0){
            actionStrings.push(s);
            totalActionStrings.push(s);
            actions++;
            return true;
        } else if (actions <= 3){
            String lastAction = actionStrings.peek();
            if (lastAction.startsWith("S") && currentPlayer.getRole().getName().equals("Engineer") && s.startsWith("S")) {
                String shoreCoord = lastAction.substring(lastAction.indexOf("("), lastAction.indexOf("(") + 6);
                String sShoreCoord = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                totalActionStrings.pop();
                actionStrings.pop();
                String finalAction = "E " + shoreCoord + ", " + sShoreCoord;
                actionStrings.push(finalAction);
                totalActionStrings.push(finalAction);
                return true;
            } else if (actions == 3){
                return false;
            } else {
                totalActionStrings.push(s);
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
        System.out.println(x1 + ", " + x2 + ")(" + y1 + ", " + y2);
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) <= 2.5;
    }

    public static List<String> toFormattedStrings(){
        List<String> actions = new ArrayList<>(totalActionStrings);
        List<String> formattedStrings = new ArrayList<>();
        for (String s : actions){
            System.out.println(s);
            StringBuilder sb = new StringBuilder();
            String move = s.substring(0, 1);
            System.out.println(s);
            sb.append(moveMap.get(move));
            switch(move){
                case "M":
                case "P": {
                    String coord2 = s.substring(s.lastIndexOf("("), s.lastIndexOf("(") + 6);
                    String[] coords2 = coord2.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords2[1])).get(Integer.parseInt(coords2[0])).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "E": {
                    String coord1 = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).getName());
                    sb.append(" and ");
                    String coord2 = s.substring(s.lastIndexOf("("), s.lastIndexOf("(") + 6);
                    String[] coords2 = coord2.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords2[1])).get(Integer.parseInt(coords2[0])).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "G": {
                    sb.append(s.substring(s.indexOf(" ") + 1)).append(" card to ");
                    sb.append(Role.fromNotation(s.substring(1, 2)).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "S":
                case "B": {
                    String coord1 = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "N": {
                    sb.append(Role.fromNotation(s.substring(1, 2)).getName()).append(" to ");
                    System.out.println(Role.fromNotation("N"));
                    String coord1 = s.substring(s.lastIndexOf("("), s.lastIndexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "H": {//H [starting] [ending] [playernotations]
                    String playersMoved = s.substring(s.lastIndexOf(" ") + 1);
                    StringJoiner sj = new StringJoiner(", ");
                    for (int i = 0; i < playersMoved.length(); i++){
                        sj.add(Role.fromNotation(playersMoved.substring(i, i+1)).getName());
                    }
                    sb.append(sj).append(" to ");
                    String coord1 = s.substring(s.indexOf("(", s.indexOf("(") + 1), s.indexOf("(", s.indexOf("(") + 1) + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "C": {//C [treasureId]
                    sb.append(Treasure.IDToString(Integer.parseInt(s.substring(s.length() - 1))));
                    formattedStrings.add(sb.toString());
                    break;
                }
            }
        }
        return formattedStrings;
    }

    public static boolean undoAction(){
        if (actions > 0){
            actionStrings.pop();
            actions--;
            return true;
        } else {
            return false;
        }
    }

    public static Queue<Player> getPlayers() {
        return playerQueue;
    }

    public static boolean hasDoneSpecialAction(){
        List<String> actions = new ArrayList<>(actionStrings);
        for (String s : actions){
            if (s.startsWith(currentPlayer.getRole().toNotation())){
                return true;
            }
        }
        return false;
    }
}
