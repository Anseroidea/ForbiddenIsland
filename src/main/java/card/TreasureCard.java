package card;

import card.Card;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class TreasureCard extends Card implements Comparable {
    private Integer treasureId;
    private static List<String> cardNames = Arrays.asList("Earth Stone", "Ocean's Chalice", "Crystal of Fire", "Statue of the Wind", "Helicopter", "Sand Bag", "Waters Rise");


    public TreasureCard(String n, BufferedImage b){
        super(n, b);
        treasureId = cardNames.indexOf(n) + 1;
    }

    public Integer getTreasureId(){
        return treasureId;
    }

    public boolean isSpecial(){
        return false;
    }

    public boolean isWatersRise(){
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return treasureId.compareTo(((TreasureCard) o).treasureId);
    }
}
