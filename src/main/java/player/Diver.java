package player;

import javafx.scene.paint.Color;

public class Diver extends Role
{

    public Diver() {
        super(Color.BLACK);
    }

    @Override
    public int getId() {
        return 1;
    }

    public void doSpecialAction(Player p, int goToX, int goToY)
    {
        //Can move through adjacent/missing tiles
        int x = 0;
		int y = 0;
		int goalX = 2;
		int goalY = 2;
		int baseX = Math.min(x, goalX);
		int baseY = Math.min(y, goalY);
		Tile[][] miniBoard = new Tile[Math.abs(x-goalX)][Math.abs(y-goalY)];
		int[][] paths = new int[miniBoard.length][miniBoard[0].length];
		List<List<Tile>> board = getBoard();
		// create a 2D-matrix and initializing
	    // with value 0

	    for(int i = 0; i < miniBoard.length; i++)
	    {
	      for(int j = 0; j < miniBoard[i].length; j++)
	      {
	    	 miniBoard[i][j] = board.get(baseX + i).get(baseY + j);
	      }
	    }
	 
	    // Initializing the left corner if
	    // no obstacle there
	    paths[0][0] = 1;
	    // Initializing first column of
	    // the 2D matrix
	    for(int i = 1; i < paths.length; i++)
	    {
	      // If not obstacle
	      if (miniBoard[i][0].isFlooded() || miniBoard[i][0].isSunk())
	        paths[i][0] = paths[i - 1][0];
	    }
	 
	    // Initializing first row of the 2D matrix
	    for(int j = 1; j < paths[0].length; j++)
	    {
	 
	      // If not obstacle
	      if (miniBoard[0][j].isFlooded() || miniBoard[0][j].isSunk())
	        paths[0][j] = paths[0][j - 1];
	    }  
	 
	    for(int i = 1; i < paths.length; i++)
	    {
	      for(int j = 1; j < paths[i].length; j++)
	      {
	 
	        // If current cell is not obstacle
	        if (miniBoard[i][j].isFlooded() || miniBoard[i][j].isSunk())
	          paths[i][j] = paths[i - 1][j] +
	          paths[i][j - 1];
	      } 
	    }
	 
	    // Returning the corner value
	    // of the matrix
	    if(paths[paths.length-1][paths[0].length-1] > 0)
	    	p.move(goalX, goalY);
    }
}
