package player;

public class Diver extends Role
{
    public void doSpecialAction(Player p, int x, int y, Player other)
    {
        //Can move through adjacent/missing tiles
        int ogx = p.getX();
        int ogy = p.getY();
        int[][] temp = new int[Math.abs(ogx - x)][Math.abs(ogy - y)];
        for(int i = 0; i < temp.length; i++)
            for(int j = 0; j < temp[i].length; j++)
                if(p.getBoard().get(x).get(y).isLand())
                    temp[i][j] = -1;
        boolean swim = possible(temp);

        if(swim) {
            p.move(x, y, true);
        }
        else
            System.out.println("Can only swim through adjacent flooded / sunken tiles!");
    }

    private boolean possible(int[][] A)
    {
        int r = A.length;
        int[][] paths = new int[r][];
        for(int i = 0; i < r; i++)
        {
            for(int j = 0; j < A[i].length; j++)
            {
                paths[i][j] = 0;
            }
        }
        if (A[0][0] == 0)
            paths[0][0] = 1;

        // Initializing first column of
        // the 2D matrix
        for(int i = 1; i < r; i++)
        {
            // If not obstacle
            if (A[i][0] == 0)
                paths[i][0] = paths[i - 1][0];
        }

        // Initializing first row of the 2D matrix
        for(int j = 1; j < A[0].length; j++)
        {

            // If not obstacle
            if (A[0][j] == 0)
                paths[0][j] = paths[0][j - 1];
        }

        for(int i = 1; i < r; i++)
        {
            for(int j = 1; j < A[i].length; j++)
            {

                // If current cell is not obstacle
                if (A[i][j] == 0)
                    paths[i][j] = paths[i - 1][j] +
                            paths[i][j - 1];
            }
        }

        // Returning the corner value
        // of the matrix
        return paths[r - 1][A[r].length - 1] > 0;
    }
}