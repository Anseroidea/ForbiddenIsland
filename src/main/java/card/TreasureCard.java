package card;

import card.Card;

public class TreasureCard extends Card {
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
