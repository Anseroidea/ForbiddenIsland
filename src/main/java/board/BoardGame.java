package board;

import app.ForbiddenIsland;
import card.FloodDeck;
import card.TreasureDeck;
import player.Player;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
        generateIsland();
    }


    private List<List<Tile>> board;
    private TreasureDeck treasureDeck;
    private FloodDeck floodDeck;
    private Queue<Player> players;
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
                board.get(r).add(tiles.get(in++));
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
                Tile regTile = new Tile(reg.getName().substring(0, reg.getName().length() - 4), ImageIO.read(reg), ImageIO.read(flooded));
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
        for (int i = 0; i < numPlayers; i++){

        }
    }

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public void watersRise(){

    }

    public List<Tile> getMovableTilePos(Player player){
        List<Tile> getMovableTiles = new ArrayList<>();
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

    public Queue<Player> getPlayers(){
        return players;
    }

}
