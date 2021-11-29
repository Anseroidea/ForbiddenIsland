package card;

import java.awt.image.BufferedImage;

public class SpecialActionCard extends TreasureCard {
    private String name;

    public SpecialActionCard(String n, BufferedImage b){
        super(n, b);
    }
    public String getName(){
        return name;
    }

    public boolean isSpecial(){
        return true;
    }
}
