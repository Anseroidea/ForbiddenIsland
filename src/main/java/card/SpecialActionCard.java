package card;

import java.awt.image.BufferedImage;

public class SpecialActionCard extends TreasureCard {

    public SpecialActionCard(String n, BufferedImage b){
        super(n, b);
    }

    public boolean isSpecial(){
        return true;
    }
}
