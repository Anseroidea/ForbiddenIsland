package player;

public class Pilot extends Role
{
    public void doSpecialAction(Player p, int x, int y, Player other)
    {
        //Can move to any unsunken tile
        p.move(x, y, true);

    }
}