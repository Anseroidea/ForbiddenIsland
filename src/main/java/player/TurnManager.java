package player;

import app.ForbiddenIsland;
import board.BoardGame;
import board.Treasure;
import card.FloodCard;
import card.FloodDeck;
import card.TreasureCard;
import card.TreasureDeck;

import java.util.*;

public class TurnManager {

    private static int actions;
    private static Stack<Action> totalActionStrings = new Stack<>();
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
        totalActionStrings.clear();
        currentPlayer = playerQueue.remove();
        playerQueue.add(currentPlayer);
    }

    public static void addNonAction(String s){
        totalActionStrings.push(new Action(s, false));
    }

    public static boolean addAction(String s){
        if (actions == 0){
            totalActionStrings.push(new Action(s, true));
            actions++;
            return true;
        } else if (actions <= 3){
            String lastAction = null;
            List<Action> actionsList = new ArrayList<>(totalActionStrings);
            for (int i = actionsList.size() - 1; i >= 0; i--){
                if (actionsList.get(i).isCounts()){
                    lastAction = actionsList.get(i).getAction();
                    break;
                }
            }
            if (lastAction.startsWith("S") && currentPlayer.getRole().getName().equals("Engineer") && s.startsWith("S")) {
                String shoreCoord = lastAction.substring(lastAction.indexOf("("), lastAction.indexOf("(") + 6);
                String sShoreCoord = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                totalActionStrings.pop();
                String finalAction = "E " + shoreCoord + ", " + sShoreCoord;
                totalActionStrings.push(new Action(finalAction, true));
                return true;
            } else if (actions == 3){
                return false;
            } else {
                totalActionStrings.push(new Action(s, true));
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
        List<Action> actions = new ArrayList<>(totalActionStrings);
        List<String> formattedStrings = new ArrayList<>();
        for (Action ac : actions){
            String s = ac.getAction();
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
                    String coord1 = s.substring(s.lastIndexOf("("), s.lastIndexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    sb.append(ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).getName());
                    formattedStrings.add(sb.toString());
                    break;
                }
                case "H": {//H [destination] [playernotations] [starting1] [starting2] [starting3...]
                    String playersMoved = s.substring(s.lastIndexOf(" ") + 1);
                    StringJoiner sj = new StringJoiner(", ");
                    for (int i = 0; i < playersMoved.length(); i++){
                        sj.add(Role.fromNotation(playersMoved.substring(i, i+1)).getName());
                    }
                    sb.append(sj).append(" to ");
                    String coord1 = s.substring(s.indexOf("("), s.indexOf("(") + 6);
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

    public static void undoAction(){
        if (totalActionStrings.size() > 0) {
            Action lastAction = totalActionStrings.peek();
            String s = lastAction.getAction();
            String firstLetter = s.substring(0, 1);
            switch (firstLetter){
                case "M":
                case "P": {
                    String start = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                    String[] start1 = start.replace("(", "").replace(")", "").split(", ");
                    currentPlayer.move(Integer.parseInt(start1[0]), Integer.parseInt(start1[1]));
                    break;
                }
                case "E": {
                    String coord1 = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    String coord2 = s.substring(s.lastIndexOf("("), s.lastIndexOf("(") + 6);
                    String[] coords2 = coord2.replace("(", "").replace(")", "").split(", ");
                    ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).floodTile();
                    ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords2[1])).get(Integer.parseInt(coords2[0])).floodTile();
                    break;
                }
                case "G": {
                    Player destination = ForbiddenIsland.getBoard().getPlayers().stream().filter(p -> p.getRole().equals(Role.fromNotation(s.substring(1, 2)))).findFirst().get();
                    destination.giveCard(currentPlayer, s.substring(s.indexOf(" ") + 1));
                    break;
                }
                case "B": {
                    TreasureDeck deck = ForbiddenIsland.getBoard().getTreasureDeck();
                    Player destination = ForbiddenIsland.getBoard().getPlayers().stream().filter(p -> p.getRole().equals(Role.fromNotation(s.substring(1, 2)))).findFirst().get();
                    List<TreasureCard> deckList = new ArrayList<>(deck.getDiscardedStack());
                    for (int i = deckList.size() - 1; i >= 0; i--) {
                        if (deckList.get(i).getName().equalsIgnoreCase("Sand Bag")){
                            destination.addCard(deckList.get(i));
                            deckList.remove(i);
                            break;
                        }
                    }
                    deck.getDiscardedStack().clear();
                    deck.getDiscardedStack().addAll(deckList);
                }
                case "S": {
                    String coord1 = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    ForbiddenIsland.getBoard().getBoard().get(Integer.parseInt(coords1[1])).get(Integer.parseInt(coords1[0])).floodTile();
                    break;
                }
                case "N": {
                    String coord1 = s.substring(s.indexOf("("), s.indexOf("(") + 6);
                    String[] coords1 = coord1.replace("(", "").replace(")", "").split(", ");
                    Player destination = ForbiddenIsland.getBoard().getPlayers().stream().filter(p -> p.getRole().equals(Role.fromNotation(s.substring(1, 2)))).findFirst().get();
                    destination.move(Integer.parseInt(coords1[0]), Integer.parseInt(coords1[1]));
                    break;
                }
                case "H": {//H[player] [destination] [playernotations] [starting1] [starting2] [starting3...]
                    TreasureDeck deck = ForbiddenIsland.getBoard().getTreasureDeck();
                    Player destination = ForbiddenIsland.getBoard().getPlayers().stream().filter(p -> p.getRole().equals(Role.fromNotation(s.substring(1, 2)))).findFirst().get();
                    List<TreasureCard> deckList = new ArrayList<>(deck.getDiscardedStack());
                    for (int i = deckList.size() - 1; i >= 0; i--) {
                        if (deckList.get(i).getName().equalsIgnoreCase("Helicopter")){
                            destination.addCard(deckList.get(i));
                            deckList.remove(i);
                            break;
                        }
                    }
                    String moveStuff = s.substring(10);
                    System.out.println(s);
                    String[] playerNames = moveStuff.substring(0, moveStuff.indexOf(" ")).split("");
                    System.out.println(Arrays.toString(playerNames));
                    String starting = moveStuff.substring(moveStuff.indexOf(" ") + 1);
                    String coords = starting.substring(0, 6);
                    String[] coords1 = coords.replace("(", "").replace(")", "").split(", ");
                    System.out.println(coords);
                    for (int i = 0; i < playerNames.length; i++){
                        int finalI = i;
                        Player playerToMove = ForbiddenIsland.getBoard().getPlayers().stream().filter(p -> p.getRole().toNotation().equals(playerNames[finalI])).findFirst().get();
                        playerToMove.move(Integer.parseInt(coords1[0]), Integer.parseInt(coords1[1]));
                    }
                    deck.getDiscardedStack().clear();
                    deck.getDiscardedStack().addAll(deckList);
                    break;
                }
                case "C": {//C [treasureId];
                    TreasureDeck deck = ForbiddenIsland.getBoard().getTreasureDeck();
                    List<TreasureCard> deckList = new ArrayList<>(deck.getDiscardedStack());
                    int count = 0;
                    for (int i = deckList.size() - 1; i >= 0 && count < 4; i--) {
                        if (deckList.get(i).getName().equalsIgnoreCase("Helicopter")){
                            currentPlayer.addCard(deckList.get(i));
                            deckList.remove(i);
                            count++;
                        }
                    }
                    ForbiddenIsland.getBoard().getTreasures().get(Integer.parseInt(s.substring(s.indexOf(" ") + 1))).unclaim();
                    deck.getDiscardedStack().clear();
                    deck.getDiscardedStack().addAll(deckList);
                    break;
                }
            }
            if (lastAction.isCounts()){
                actions--;
            }
            totalActionStrings.pop();
        }
    }

    public static Queue<Player> getPlayers() {
        return playerQueue;
    }

    public static boolean hasDoneSpecialAction(){
        List<Action> actions = new ArrayList<>(totalActionStrings);
        for (Action s : actions){
            if (s.getAction().startsWith(currentPlayer.getRole().toNotation())){
                return true;
            }
        }
        return false;
    }

    public static Stack<Action> getTotalActionStrings() {
        return totalActionStrings;
    }
}
