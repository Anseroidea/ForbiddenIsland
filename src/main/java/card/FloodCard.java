package card;

import board.Tile;
import card.Card;

import java.awt.image.BufferedImage;

public class FloodCard extends Card {
    private Tile tile;

    public FloodCard(String n, BufferedImage b){
        super(n, b);
    }
    public Tile getTile(){
        return tile;
    }


}
