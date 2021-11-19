package player;

public class Engineer extends Role
{
    public void doSpecialAction(Player p, int x, int y, Player other)
    {
        //Can shore up 2 tiles
        p.getBoard().get(x).get(y).shoreUp();
    }
}