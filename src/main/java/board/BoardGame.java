package board;

import app.ForbiddenIsland;
import card.FloodDeck;
import card.TreasureDeck;
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

    private String tileGraphicsURL;
    private String cardGraphicsURL;
    private String playerGraphicsURL;

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

    private void initializeCards(){
        treasureDeck = new TreasureDeck();
    }

    private void initializePlayers(int numPlayers){
        List<Role> roleList = Role.getRoles();
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

    public List<Tile> getMovableTilePos(Player player){
        List<Tile> getMovableTiles = new ArrayList<>();
        int x = player.getPositionX();
        int y = player.getPositionY();
        System.out.println(player.getRole().getName() + " " + x + ", " + y);
        if (x > 0 && board.get(y).get(x - 1) != null && board.get(y).get(x - 1).isMovable()){
            getMovableTiles.add(board.get(y).get(x - 1));
        }
        if (x < 5 && board.get(y).get(x + 1) != null && board.get(y).get(x + 1).isMovable()){
            getMovableTiles.add(board.get(y).get(x + 1));
        }
        if (y > 0 && board.get(y - 1).get(x) != null && board.get(y - 1).get(x).isMovable()){
            getMovableTiles.add(board.get(y - 1).get(x));
        }
        if (x < 5 && board.get(y + 1).get(x) != null && board.get(y + 1).get(x).isMovable()){
            getMovableTiles.add(board.get(y + 1).get(x));
        }
        return getMovableTiles;
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

}
