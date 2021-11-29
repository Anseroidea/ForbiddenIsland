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
    private static List<String> nameList= Arrays.asList("Iron Gate","Bronze Gate","Copper Gate","Silver Gate","Gold Gate", "Fools Landing", "Cave of Embers","Cave of Shadows","Coral Palace","Tidal Palace","Howling Garden","Whispering Garden","Temple of the Moon","Temple of the Sun","Breakers Bridge","Cliffs of Abandon","Crimson Forest","Dunes of Deceptions","Lost Lagoon","Misty Marsh","Observatory","Phantom Rock","Twilight Hollow","Watchtower");
    private int positionX;
    private int positionY;


    public Tile(String name, BufferedImage landed, BufferedImage flooded)
    {
        floodState= TileFloodState.LAND;
        tileName=name;
        tileID= getTileID(name);
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

    public boolean isLand()
    {
        if(floodState.equals(TileFloodState.SUNK)||floodState.equals(TileFloodState.FLOOD))
        {
            return false;
        }
        else
            return true;
    }
    public boolean isMovable()// will return false if FLOOD;
    {
        return !floodState.equals(TileFloodState.SUNK);
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

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPos(int x, int y){
        positionX = x;
        positionY = y;
    }

    public static int getTileID(String s){
        return nameList.indexOf(s) + 1;
    }

    public boolean equals(Object o){
        if (!(o instanceof Tile)){
            return false;
        } else {
            Tile t = (Tile) o;
            return getTileID(getName()) == getTileID(t.getName());
        }

    }

}



