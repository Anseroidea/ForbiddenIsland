package card;

import card.Card;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class TreasureCard extends Card implements Comparable<TreasureCard> {
    private Integer treasureId;
    private static List<String> cardNames = Arrays.asList("Earth Stone", "Ocean's Chalice", "Crystal of Fire", "Statue of the Wind", "Helicopter", "Sand Bag", "Waters Rise");
    private BufferedImage smallGraphic;

    public TreasureCard(String n, BufferedImage b){
        super(n, b);
        treasureId = cardNames.indexOf(n) + 1;
        smallGraphic = resize(b, 81, 120);
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

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
