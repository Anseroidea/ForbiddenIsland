package board;

public class BoardGame {

    private WaterLevel waterLevel;

    public BoardGame(int diff){
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
    }

    /*
    private List<List<Tile>> board;
    private TreasureDeck treasureDeck;
    private FloodDeck floodDeck;
    private List<Player> players;
    private WaterLevel waterLevel;

    private String tileGraphicsURL;
    private String cardGraphicsURL;
    private String playerGraphicsURL;

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

    }
     */

    public WaterLevel getWaterLevel(){
        return waterLevel;
    }

}
