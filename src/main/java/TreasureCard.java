public class TreasureCard extends Card, Player{
    private int treasureId;

    public int getTreasureId(){
        return treasureId;
    }

    public boolean isSpecial(){
        return false;
    }

    public boolean isWatersRise(){
        return false;
    }
}
