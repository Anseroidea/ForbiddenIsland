package player;

public class Navigator extends Role
{
    public void doSpecialAction(Player p, int x, int y, Player other)
    {
        //Can move another player for up to 2 adjacent tiles
        other.move(x, y, false);
    }
}