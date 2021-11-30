package card;

import board.Tile;
import card.Card;

import java.awt.image.BufferedImage;

public class FloodCard extends Card {
    private Tile tile;

    public FloodCard(Tile t, BufferedImage b){
        super(t.getName(), b);
        tile = t;
    }
    public Tile getTile(){
        return tile;
    }


}
