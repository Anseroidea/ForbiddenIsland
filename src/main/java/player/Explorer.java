package player;

public class Explorer extends Role
{
    public void doSpecialAction(Player p, int x, int y, Player other)
    {
        //Can move diagonally
        int a = Math.abs(p.getX() - x);
        int b = Math.abs(p.getY() - y);
        if(a + b <= 1 || (a == b && a == 1))
            p.move(x, y, true);
        else
            System.out.println("Move out of range!");
    }
}