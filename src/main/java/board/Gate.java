package board;

import board.Tile;
import player.*;

import java.awt.image.BufferedImage;

public class Gate extends Tile
{
    //tile ID explanation- Gates-1-6 |treasureTile 7-14 (fire 7,8)(water 9,10)(wind 11,12)(earth 13,14)|Miscellaneous 15-24
    //"Fools' Landing","Bronze board.Gate","Copper board.Gate","Gold board.Gate","Iron board.Gate","Silver board.Gate"

    public Gate(String name, BufferedImage flooded, BufferedImage landed)
    {
        super(name, flooded, landed);// IN boardgame Class , must instiate and check for gate
    }
    public Role getRole()
    {
        if(getID()==1)
        {return new Pilot();}
        if(getID()==2)
        {return new Engineer();}
        if(getID()==3)
        {return new Explorer();}
        if(getID()==4)
        {return new Navigator();}
        if(getID()==5)
        {return new Diver();}
        else
        {return new Messenger();}

    }


}
