package card;

import java.awt.image.BufferedImage;

public abstract class Card {
    private String name;
    private BufferedImage card;

    public Card(String n, BufferedImage b){
        name = n; card = b;
    }
    private BufferedImage getGraphic(){
        return card;
    }


}
