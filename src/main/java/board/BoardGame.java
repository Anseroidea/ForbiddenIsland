package board;

import app.ForbiddenIsland;
import app.PopUp;
import app.ProgramState;
import app.ProgramStateManager;
import card.*;
import graphics.BoardStateGraphicsInitializer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import player.Player;
import player.Role;
import player.TurnManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BoardGame {

    public BoardGame(int diff, int numPlayers, boolean manualFlooding) {
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
        this.manualFlooding = manualFlooding;
    }


    private List<List<Tile>> board;
    private TreasureDeck treasureDeck;
    private FloodDeck floodDeck;
    private List<Player> players;
    private WaterLevel waterLevel;
    private List<Treasure> treasures;
    private Tile foolsLanding;
    private boolean manualFlooding = true;
    private List<TreasureCard> lastTwoCards;

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
                    if (id == 6){
                        foolsLanding = regTile;
                    }
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
                    t.addTreasureTile((TreasureTile) regTile);
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
        lastTwoCards = new ArrayList<>(cardsDrawn);
        boolean isWaterRisen = false;
        for (int i = 1; i >= 0; i--){
            if (cardsDrawn.get(i) instanceof WatersRiseCard){
                isWaterRisen = true;
                waterLevel.raiseLevel();
                cardsDrawn.remove(i);
            }
        }
        if (isWaterRisen){
            floodDeck.reset();
        }
        currentPlayer.addCards(cardsDrawn);
        Collections.sort(currentPlayer.getDeck());
        if (currentPlayer.getDeck().size() > 5){
            PopUp.DISCARD.load();
        }
        if (manualFlooding){
            PopUp.DRAW.load();
            if (isLost()){
                lose();
            }
        } else {
            List<FloodCard> floodCardList = floodDeck.draw(waterLevel.getLevel());
            for (FloodCard fc : floodCardList){
                fc.getTile().floodTile();
                if (fc.getTile().isSunk()){
                    floodDeck.killCard(fc);
                }
                if (isLost()){
                    lose();
                }
                for (Player p : players){
                    if (board.get(p.getPositionY()).get(p.getPositionX()).isSunk()){
                        PopUp.RELOCATE.loadRelocate(p);
                    }
                }
                if (isLost()){
                    lose();
                }

            }
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

    public boolean isReadyToWin(){
        if (treasures.stream().anyMatch(t -> !t.isClaimed())){
            return false;
        }
        for (Player p : players){
            if (p.getPositionX() != foolsLanding.getPositionX() || p.getPositionY() != foolsLanding.getPositionY()){
                return false;
            }
        }
        return true;
    }

    public boolean isLost(){
        if (foolsLanding.isSunk()){
            ForbiddenIsland.getLose().setLoseReason("Fools Landing sunk!");
            return true;
        }
        for (Treasure t : treasures){
            if (!t.isClaimed() && t.getTreasureTiles().stream().allMatch(TreasureTile::isSunk)){
                ForbiddenIsland.getLose().setLoseReason("Both treasure tiles for the " + t.getName() + " have sunk");
                return true;
            }
        }
        for (Player p : players){
            if (board.get(p.getPositionY()).get(p.getPositionX()).isSunk()){
                if (p.getRole().getFloodRelocTiles(p).isEmpty()){
                    ForbiddenIsland.getLose().setLoseReason(p.getRole().getName() + " has drowned as they were not able to swim ashore!");
                    return true;
                }
            }
        }
        if (waterLevel.toFullNotation() == WaterLevel.DEATH){
            ForbiddenIsland.getLose().setLoseReason("The water level has reached death!");
            return true;
        } else {
            return false;
        }
    }

    public void lose(){
        BoardStateGraphicsInitializer.refreshDisplay();
        ProgramStateManager.goToState(ProgramState.BOARD);
        BoardStateGraphicsInitializer.refreshTiles();
        ForbiddenIsland.refreshDisplay();
        ForbiddenIsland.getPrimaryStage().setFullScreen(true);
        Robot r = new Robot();
        WritableImage im = r.getScreenCapture(null, new Rectangle2D(ForbiddenIsland.getPrimaryStage().getX(), ForbiddenIsland.getPrimaryStage().getY(), 1920, 1080));
        ForbiddenIsland.getLose().setBoardPreview(im);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgramStateManager.goToState(ProgramState.LOSE);
        ForbiddenIsland.refreshDisplay();

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

    public List<TreasureCard> getLastTwoCards() {
        return lastTwoCards;
    }
}
