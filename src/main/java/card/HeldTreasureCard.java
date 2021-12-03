package card;

import board.Treasure;

import java.awt.image.BufferedImage;

public class HeldTreasureCard extends TreasureCard{

    private Treasure treasure;

    public HeldTreasureCard(Treasure treasure, BufferedImage b) {
        super(treasure.getName(), b);
        this.treasure = treasure;
    }

    public Treasure getTreasure() {
        return treasure;
    }
}
