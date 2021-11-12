package board;

public class TreasureTilepublic extends Tile
{
    //tile ID explenation- Gates-1-6 |treasureTile 7-14 (fire 7,8)(water 9,10)(wind 11,12)(SPACE 13,14)|Misalanous 15-24
    //"Fools' Landing","Bronze board.Gate","Copper board.Gate","Gold board.Gate","Iron board.Gate","Silver board.Gate"

    public TreasureTilepublic(int Tileints)
    {
        super(Tileints);// IN boardgame Class , must instiate and check for gate
    }
    public Role getRole()
    {
        if(getID()==7||getID()==8)
        {return FIRE;}
        if(getID()==9||getID()==10)
        {return WATER;}
        if(getID()==11||getID()==12)
        {return WIND;}
        if(getID()==13||getID()==14)
        {return SPACE;}
    }
}
