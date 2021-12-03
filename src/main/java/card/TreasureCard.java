package card;

import app.ForbiddenIsland;
import board.Treasure;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class TreasureCard extends Card implements Comparable<TreasureCard> {
    private Integer treasureId;
    private static List<String> cardNames = Arrays.asList("Crystal of Fire", "Ocean's Chalice", "Statue of the Wind", "Earth Stone", "Helicopter", "Sand Bag", "Waters Rise");
    private BufferedImage smallGraphic;

    public TreasureCard(String n, BufferedImage b){
        super(n, b);
        treasureId = cardNames.indexOf(n);
        smallGraphic = ForbiddenIsland.resize(b, 81, 120);
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

    public BufferedImage getSmallGraphic() {
        return smallGraphic;
    }

    @Override
    public int compareTo(TreasureCard o) {
        return treasureId.compareTo(o.treasureId);
    }

    public boolean equals(Object o){
        if (o instanceof TreasureCard){
            return treasureId.equals(((TreasureCard) o).treasureId);
        } else {
            return false;
        }
    }

    public Treasure getTreasure(){
        return null;
    }
}
