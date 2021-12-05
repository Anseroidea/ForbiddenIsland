package card;

import java.awt.image.BufferedImage;

public abstract class Card{
    private String name;
    private BufferedImage card;


    public Card(String n, BufferedImage b){
        name = n; card = b;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getGraphic(){
        return card;
    }


}
