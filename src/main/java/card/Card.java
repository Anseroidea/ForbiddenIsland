package card;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

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
