package board;

import app.ForbiddenIsland;
import card.*;
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
        generateIsland();
        initializePlayers(numPlayers);
        TurnManager.setPlayers(players.toArray(new Player[numPlayers]));
    }


    private List<List<Tile>> board;
    private TreasureDeck treasureDeck;
    private FloodDeck floodDeck;
    private List<Player> players;
    private WaterLevel waterLevel;
    private Map<Integer, Boolean> treasuresHeld = new HashMap<>(){
        {
            put(0, false);
            put(1, false);
            put(2, false);
            put(3, false);
        }
    };

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
        File tileImagePath = null;
        try {
            tileImagePath = new File(ForbiddenIsland.class.getResource("/images/tiles").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] images = tileImagePath.listFiles();
        for (int i = 0; i < images.length; i++){
            File reg = images[i];
            i++;
            File flooded = images[i];
            try {
                String name = reg.getName().substring(0, reg.getName().length() - 4);
                int id = Tile.getTileID(name);
                Tile regTile;
                if (id <= 6){
                    regTile = new Gate(name, ImageIO.read(reg), ImageIO.read(flooded));
                } else if (id <= 14){
                    regTile = new TreasureTile(name, ImageIO.read(reg), ImageIO.read(flooded));
                } else {
                    regTile = new Tile(name, ImageIO.read(reg), ImageIO.read(flooded));
                }
                tiles[i/2] = regTile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tiles;
    }

    private void dealCards(){

    }

    private void initializeCards(){
        List<TreasureCard> treasureList = new ArrayList<>();
        File treasureCardImagePath = null;
        TreasureCard treasureCard;
        try {
            treasureCardImagePath = new File(ForbiddenIsland.class.getResource("/images/cards/treasureCards").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] images = treasureCardImagePath.listFiles();
        for(int i = 0; i < images.length; i++){
            File currentImage = images[i];
            String name = images[i].getName().substring(images[i].getName().indexOf("_") + 1, images[i].getName().indexOf("."));
            name = name.replaceAll("_", " ");
            try {
                if (!images[i].getName().equals("Card_Helicopter.png") && !images[i].getName().equals("Card_Sand_Bag.png") && !images[i].getName().equals("Card_Waters_Rise.png")) {
                    for (int f = 0; f < 5; f++) {
                        treasureCard = new TreasureCard(name, ImageIO.read(currentImage));
                        treasureList.add(treasureCard);
                    }
                } else if(images[i].getName().equals("Card_Helicopter.png")){
                    for(int a = 0 ; a < 3; a++){
                        treasureCard = new TreasureCard(name, ImageIO.read(currentImage));
                        treasureList.add(treasureCard);
                    }
                } else if(images[i].getName().equals("Card_Sand_Bag.png")){
                    for(int a = 0 ; a < 2; a++){
                        treasureCard = new TreasureCard(name, ImageIO.read(currentImage));
                        treasureList.add(treasureCard);
                    }
                } else if(images[i].getName().equals("Card_Waters_Rise.png")){
                    for(int a = 0 ; a < 3; a++){
                        treasureCard = new TreasureCard(name, ImageIO.read(currentImage));
                        treasureList.add(treasureCard);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        treasureDeck = new TreasureDeck(treasureList);

        Deque<Card> floodDeck = new ArrayDeque<>();
        File floodCardImagePath = null;
        Card floodCard;
        try {
            floodCardImagePath = new File(ForbiddenIsland.class.getResource("/images/cards/floodCards").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] floodCards = floodCardImagePath.listFiles();
        List<FloodCard> floodCardList = new ArrayList<>();
        for (File f : floodCards){
            try {
                FloodCard fc = new FloodCard(f.getName(), ImageIO.read(f));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initializePlayers(int numPlayers){
        List<Role> roleList = new ArrayList<>(Role.getRoles());
        Collections.shuffle(roleList);
        Queue<Role> roleQueue = new LinkedList<>(roleList);
        for (int i = 0; i < numPlayers; i++){
            Role r = roleQueue.remove();
            BufferedImage im = null;
            try {
                System.out.println("/images/players/"+r.getClass().getSimpleName() + "_Adventurer_Icon.png");
                im = ImageIO.read(ForbiddenIsland.class.getResource("/images/players/"+r.getClass().getSimpleName() + "_Adventurer_Icon.png").toURI().toURL());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
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

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public void watersRise(){

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

    public void revert(BoardGame boardGame){

    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Integer, Boolean> getTreasuresClaimed() {
        return treasuresHeld;
    }
}
