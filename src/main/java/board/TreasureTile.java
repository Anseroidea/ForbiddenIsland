package board;

import java.awt.image.BufferedImage;

public class TreasureTile extends Tile {
    //tile ID explenation- Gates-1-6 |treasureTile 7-14 (fire 7,8)(water 9,10)(wind 11,12)(earth 13,14)|Misalanous 15-24
    //"Fools' Landing","Bronze board.Gate","Copper board.Gate","Gold board.Gate","Iron board.Gate","Silver board.Gate"

    private Treasure treasureHeld;

    public TreasureTile(String name, BufferedImage flooded, BufferedImage landed, Treasure treasure) {
        super(name, flooded, landed);// IN boardgame Class , must instiate and check for gate
        treasureHeld = treasure;
    }

    public Treasure getTreasureHELD() {
        return treasureHeld;
    }


}

