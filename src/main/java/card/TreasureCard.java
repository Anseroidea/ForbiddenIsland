package card;

import card.Card;

import java.awt.image.BufferedImage;

public class TreasureCard extends Card {
    private int treasureId;


    public TreasureCard(String n, BufferedImage b){
        super(n, b);
    }
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
