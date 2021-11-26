package board;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Tile
{
    //tile ID explenation- Gates-1-6 |treasureTile 7-14 (fire 7,8)(water 9,10)(wind 11,12)(SPACE 13,14)|Misalanous 15-24



    private TileFloodState floodState;
    private String tileName;
    private int tileID;
    private BufferedImage graphic;
    private BufferedImage floodedGraphic;
    private static List<String> nameList= Arrays.asList("Fools' Landing","Bronze board.Gate","Copper board.Gate","Gold board.Gate","Iron board.Gate","Silver board.Gate","Cave of Embers","Cave of Shadows","Coral Palace","Tidal Palace","Howling Garden","Whispering Garden","Temple of the Moon","Temple of the Sun","Breakers Bridge","Cliffs of Abandon","Crimson Forest","Dunes of Deceptions","Lost Lagoon","Misty Marsh","Observatory","Phantom Rock","Twilight Hollow","Watchtower");

    public Tile(String name, BufferedImage landed, BufferedImage flooded)
    {
        floodState= TileFloodState.LAND;
        tileName=name;
        tileID= nameList.indexOf(name);
        graphic = landed;
        floodedGraphic = flooded;
    }


    public String getName()
    {
        return tileName;
    }
    public int getID()
    {
        return tileID;
    }
    public BufferedImage getGraphic(){
        if (floodState.equals(TileFloodState.LAND)){
            return graphic;
        } else {
            return floodedGraphic;
        }
    }


    public boolean isFlooded()// will return false if sunk;
    {
        if(floodState.equals(TileFloodState.LAND)||floodState.equals(TileFloodState.SUNK))
        {
            return false;
        }
        else
            return true;
    }
    public boolean isSunk()// will return false if FLOOD;
    {
        if(floodState.equals(TileFloodState.LAND)||floodState.equals(TileFloodState.FLOOD))
        {
            return false;
        }
        else
            return true;
    }

    public boolean isMovable()// will return false if FLOOD;
    {
        System.out.print("isMovable(0 in tile class Is a work inprogress,need player class/location, return false");
        return false;
    }
    public void floodTile()
    {
        if(floodState.equals(TileFloodState.LAND))
        {
            floodState= TileFloodState.FLOOD;
        }
        else if(floodState.equals(TileFloodState.FLOOD))
        {
            floodState= TileFloodState.SUNK;
        }
        else if(floodState.equals(TileFloodState.SUNK))
        {
            System.out.print("Error in board.Tile,trying to flood a sunken tile");
        }
    }
    public void shoreUp()
    {
        if(floodState.equals(TileFloodState.LAND))
        {
            System.out.print("Error in board.Tile,trying to shore up a dry tile");
        }
        else if(floodState.equals(TileFloodState.FLOOD))
        {
            floodState= TileFloodState.LAND;
        }
        else if(floodState.equals(TileFloodState.SUNK))
        {
            System.out.print("You can not shore up a SUNK TILE");
        }
    }
}



