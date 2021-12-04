package board;

import app.ForbiddenIsland;
import app.PopUp;
import card.*;
import org.apache.commons.io.FileUtils;
import player.Player;
import player.Role;
import player.TurnManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class BoardGame {

    public BoardGame(int diff, int numPlayers) {
        try {
            waterLevel = new WaterLevel(diff);
        } catch (InvalidDifficultyException e) {
            e.printStackTrace();
            try {
                waterLevel = new WaterLevel();
            } catch (InvalidDifficultyException ex) {
                ex.printStackTrace();
            }
        }
        board = new ArrayList<>();
        players = new ArrayList<>();
        treasures = new ArrayList<>();
        initializeTreasures();
        generateIsland();
        initializePlayers(numPlayers);
        TurnManager.setPlayers(players.toArray(new Player[numPlayers]));
        initializeCards();
        initialFlood();
    }


    private List<List<Tile>> board;
    private TreasureDeck treasureDeck;
    private FloodDeck floodDeck;
    private List<Player> players;
    private WaterLevel waterLevel;
    private List<Treasure> treasures;

    private void generateIsland(){
        List<Tile> tiles = Arrays.asList(initializeTiles());
        Collections.shuffle(tiles);
        int in = 0;
        for (int r = 0; r < 6; r++){
            board.add(new ArrayList<>());
            for (int i = 0; i < Math.floor(Math.abs(2.5 - r)); i++){
                board.get(r).add(null);
            }
            for (int i = 0; i < (3 - Math.floor(Math.abs(2.5 - r))) * 2; i++){
                Tile tile = tiles.get(in++);
                tile.setPos((int) Math.floor(Math.abs(2.5 - r)) + i, r);
                board.get(r).add(tile);
            }
            for (int i = 0; i < Math.floor(Math.abs(2.5 - r)); i++){
                board.get(r).add(null);
            }
        }
        System.out.println(board);
    }

    private Tile[] initializeTiles() {
        Tile[] tiles = new Tile[24];
        List<String> tileNames = new ArrayList<>(Tile.nameList);
        Collections.sort(tileNames);
        for (int i = 0; i < tileNames.size(); i++){
            String n = tileNames.get(i);
            try {
                BufferedImage reg = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/tiles/" + n + ".png")));
                BufferedImage flooded = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/tiles/" + n + "_flood.png")));
                int id = Tile.getTileID(n);
                Tile regTile;
                if (id <= 6){
                    regTile = new Gate(n, reg, flooded);
                } else if (id <= 14){
                    Treasure t;
                    if (id <= 8){
                        t = treasures.get(0);
                    } else if (id <= 10){
                        t = treasures.get(1);
                    } else if (id <= 12){
                        t = treasures.get(2);
                    } else {
                        t = treasures.get(3);
                    }
                    System.out.println(id + " " + n + " " + t.getName());
                    regTile = new TreasureTile(n, reg, flooded, t);
                } else {
                    regTile = new Tile(n, reg, flooded);
                }
                tiles[i] = regTile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tiles;
    }

    private void dealTreasureCards(){
        for (Player p : players){
            p.addCards(treasureDeck.draw(2));
        }
    }

    private void initializeCards(){
        List<TreasureCard> treasureList = new ArrayList<>();
        List<String> treasureListName = new ArrayList<>(TreasureCard.cardNames);
        TreasureCard treasureCard = null;
        Collections.sort(treasureListName);
        for (int i = 0; i < treasureListName.size(); i++) {
            String name = treasureListName.get(i);
            String fileName = "Card_" + name.replace(" ", "_") + ".png";
            try {
                if (!fileName.equals("Card_Helicopter.png") && !fileName.equals("Card_Sand_Bag.png") && !fileName.equals("Card_Waters_Rise.png")) {
                    for (int f = 0; f < 5; f++) {
                        treasureCard = new HeldTreasureCard(treasures.get(Treasure.nameToID(name)), ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/cards/treasureCards/" + fileName))));
                        treasureList.add(treasureCard);
                    }
                    treasureCard.getTreasure().setCard((HeldTreasureCard) treasureCard);
                } else if(fileName.equals("Card_Helicopter.png")){
                    for(int a = 0 ; a < 3; a++){
                        treasureCard = new SpecialActionCard(name, ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/cards/treasureCards/" + fileName))));
                        treasureList.add(treasureCard);
                    }
                } else if(fileName.equals("Card_Sand_Bag.png")){
                    for(int a = 0 ; a < 2; a++){
                        treasureCard = new SpecialActionCard(name, ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/cards/treasureCards/" + fileName))));
                        treasureList.add(treasureCard);
                    }
                } else {
                    treasureDeck = new TreasureDeck(treasureList);
                    dealTreasureCards();
                    for(int a = 0 ; a < 3; a++){
                        treasureCard = new WatersRiseCard(name, ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/cards/treasureCards/" + fileName))));
                        treasureDeck.addCards(List.of(treasureCard));
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        List<FloodCard> floodCardList = new ArrayList<>();
        List<String> floodCardNames = new ArrayList<>(Tile.nameList);
        Collections.sort(floodCardNames);
        for (int i = 0; i < floodCardNames.size(); i++) {
            String name = floodCardNames.get(i);
            String fileName = "images/cards/floodCards/" + name + ".png";
            try {
                FloodCard floodCard = new FloodCard(Objects.requireNonNull(Tile.getTile(name)), ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(fileName)));
                floodCardList.add(floodCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        floodDeck = new FloodDeck(floodCardList);
    }

    private void initializePlayers(int numPlayers){
        List<Role> roleList = new ArrayList<>(Role.getRoles());
        Collections.shuffle(roleList);
        Queue<Role> roleQueue = new LinkedList<>(roleList);
        for (int i = 0; i < numPlayers; i++){
            Role r = roleQueue.remove();
            BufferedImage im = null;
            try {
                im = ImageIO.read(Objects.requireNonNull(ForbiddenIsland.class.getResourceAsStream("/images/players/icons/" + r.getClass().getSimpleName() + "_Adventurer_Icon.png")));
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            players.add(new Player(r, im));
        }
        Map<Integer, Player> playingRoleIDs = players.stream().collect(Collectors.toMap((p) -> p.getRole().getId(), (p) -> p));
        System.out.println(playingRoleIDs);
        for (int r = 0; r < 6; r++){
            for (int c = 0; c < 6; c++){
                if (board.get(r).get(c) != null && playingRoleIDs.containsKey(board.get(r).get(c).getID())){
                    System.out.println(playingRoleIDs.get(board.get(r).get(c).getID()).getRole().getClass().getSimpleName() + " " + r + ", " + c);
                    playingRoleIDs.get(board.get(r).get(c).getID()).setPos(c, r);
                }
            }
        }
    }

    public void initializeTreasures(){
        Treasure t;
        List<String> treasures = new ArrayList<>(Treasure.names);
        Collections.sort(treasures);
        for (String treasure : treasures) {
            String fileName = treasure.replace(" ", "_");
            try {
                BufferedImage reg = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/treasures/" + fileName + ".png")));
                BufferedImage gray = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/treasures/" + fileName + "_Grayed.png")));
                BufferedImage icon = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/treasures/" + fileName + "_Icon.png")));

                t = new Treasure(treasure, reg, gray, icon);
                this.treasures.add(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(this.treasures);
    }

    public void initialFlood(){
        List<FloodCard> fc = floodDeck.draw(6);
        for (int i = 0; i < 6; i++){
            FloodCard card = fc.get(i);
            Tile t = card.getTile();
            t.floodTile();
        }
    }

    public void nextTurn(){
        Player currentPlayer = TurnManager.getCurrentPlayer();
        List<TreasureCard> cardsDrawn = treasureDeck.draw(2);
        boolean isWaterRisen = false;
        for (int i = 1; i >= 0; i--){
            if (cardsDrawn.get(i) instanceof WatersRiseCard){
                isWaterRisen = true;
                waterLevel.raiseLevel();
                cardsDrawn.remove(i);
                System.out.println("Waters Rise drawn!");
            }
        }
        if (isWaterRisen){
            floodDeck.reset();
        }
        currentPlayer.addCards(cardsDrawn);
        List<FloodCard> floodCards = floodDeck.draw(waterLevel.getLevel());
        for (FloodCard fc : floodCards){
            fc.getTile().floodTile();
            if (fc.getTile().isSunk()){
                floodDeck.killCard(fc);
            }
        }
        if (currentPlayer.getDeck().size() > 5){
            PopUp.DISCARD.load();
        }
        TurnManager.endTurn();
    }

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public TreasureDeck getTreasureDeck(){
        return treasureDeck;
    }

    public FloodDeck getFloodDeck(){
        return floodDeck;
    }

    public boolean isWon(){
        return false; //return true or false
    }

    public boolean isLost(){
        return false; //return true or false
    }

    public List<List<Tile>> getBoard(){
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }
}
