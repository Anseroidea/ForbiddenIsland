package player;

public class Diver extends Role
{
    public void doSpecialAction(Player p, int x, int y)
    {
        //Can move through adjacent/missing tiles
        int ogx = p.getX();
        int ogy = p.getY();
        int[][] temp = new int[Math.abs(ogx - x)][Math.abs(ogy - y)];
        for(int i = 0; i < temp.length; i++)
            for(int j = 0; j < temp[i].length; j++)
                if(board.get(x).get(y).isLand())
                    temp[i][j] = 1;
                
        if(ogx - x == 0)
        {
            for(int i = Math.min(ogy, y); i < Math.max(ogy, y); i++)
                if(board.get(x).get(i).isLand)
            p.move(x, y, true);
        else
            System.out.println("Can only swim through adjacent tiles!")
    }
}