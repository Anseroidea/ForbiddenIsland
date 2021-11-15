package board;

import card.FloodDeck;
import card.TreasureDeck;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private List<List<Tile>> board;
    private TreasureDeck treasureDeck;
    private FloodDeck floodDeck;
    private List<Player> players;
    private WaterLevel waterLevel;

    private String tileGraphicsURL;
    private String cardGraphicsURL;
    private String playerGraphicsURL;

    public BoardGame()
    {
        initializeTiles();
        initializeCards();
        initializePlayers();
        generateIsland();
    }

    public void generateIsland(){
    }

    private void initializeTiles(){

    }

    private void initializeCards(){

    }

    private void initializePlayers(){

    }

    public void watersRise(){

    }

    public List<Tile> getMovableTilePos(Player player){
        List<Tile> listOfMovableTiles = new ArrayList<>();                                                                                                                                          les = new ArrayList<>();

        return listOfMovableTiles;
    }

    private WaterLevel getWaterLevel(){
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

    public void revert(BoardGame boardGame){

    }

    public List<Players> getPlayers(){
        List<Players> players = new ArrayList<Players>();

        return players;
    }

}
