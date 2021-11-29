package board;

import board.Tile;
import player.*;

import java.awt.image.BufferedImage;

public class Gate extends Tile
{

    public Gate(String name, BufferedImage flooded, BufferedImage landed)
    {
        super(name, flooded, landed);// IN boardgame Class , must instiate and check for gate
    }
    public Role getRole()
    {
        if(getID()==1)
        {return new Diver();}
        if(getID()==2)
        {return new Engineer();}
        if(getID()==3)
        {return new Explorer();}
        if(getID()==4)
        {return new Navigator();}
        if(getID()==5)
        {return new Pilot();}
        else
        {return new Messenger();}

    }


}
