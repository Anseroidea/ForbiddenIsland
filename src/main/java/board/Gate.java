package board;

import board.Tile;

public class Gate extends Tile
{
    //tile ID explenation- Gates-1-6 |treasureTile 7-14 (fire 7,8)(water 9,10)(wind 11,12)(earth 13,14)|Misalanous 15-24
    //"Fools' Landing","Bronze board.Gate","Copper board.Gate","Gold board.Gate","Iron board.Gate","Silver board.Gate"

    public Gate(int Tileints)
    {
        super(Tileints);// IN boardgame Class , must instiate and check for gate
    }
    public Role getRole()
    {
        if(getID()==1)
        {return PILOT;}
        if(getID()==2)
        {return ENGINEER;}
        if(getID()==3)
        {return EXPLORER;}
        if(getID()==4)
        {return NAVIGATOR;}
        if(getID()==5)
        {return DIVER;}
        if(getID()==6)
        {return MESSENGER;}

    }
}
