package player;

import app.ForbiddenIsland;
import board.Tile;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Diver extends Role {

	public Diver() {
		super(Color.web("#3b3b3b"));
	}

	@Override
	public int getId() {
		return 1;
	}

	public List<Tile> getMovableTiles(Player p){
		return diverMovement(p.getPositionX(), p.getPositionY());
	}

	public List<Tile> diverMovement(int x, int y){
		List<Tile> tiles = new ArrayList<>();
		diverMovement(x, y, tiles, new ArrayList<>());
		tiles.remove(ForbiddenIsland.getBoard().getBoard().get(y).get(x));
		return tiles;
	}

	//precondition, x, y not in tiles already
	public void diverMovement(int x, int y, List<Tile> tiles, List<Tile> sunk){
		List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
		if (sunk.contains(board.get(y).get(x))){
			return;
		}
		if (!board.get(y).get(x).isSunk()){
			tiles.add(board.get(y).get(x));
		} else {
			sunk.add(board.get(y).get(x));
		}
		if (x > 0 && board.get(y).get(x - 1) != null && !board.get(y).get(x - 1).isLand() && !tiles.contains(board.get(y).get(x - 1))) {
			diverMovement(x - 1, y, tiles, sunk);
		} else if (x > 0 && board.get(y).get(x - 1) != null){
			if (!tiles.contains(board.get(y).get(x - 1)))
				tiles.add(board.get(y).get(x - 1));
		}
		if (x < 5 && board.get(y).get(x + 1) != null && !board.get(y).get(x + 1).isLand() && !tiles.contains(board.get(y).get(x + 1))) {
			diverMovement(x + 1, y, tiles, sunk);
		} else if (x < 5 && board.get(y).get(x + 1) != null){
			if (!tiles.contains(board.get(y).get(x + 1)))
				tiles.add(board.get(y).get(x + 1));
		}
		if (y > 0 && board.get(y - 1).get(x) != null && !board.get(y - 1).get(x).isLand() && !tiles.contains(board.get(y - 1).get(x))) {
			diverMovement(x, y - 1, tiles, sunk);
		} else if (y > 0 && board.get(y - 1).get(x) != null){
			if (!tiles.contains(board.get(y - 1).get(x)))
				tiles.add(board.get(y - 1).get(x));
		}
		if (y < 5 && board.get(y + 1).get(x) != null && !board.get(y + 1).get(x).isLand() && !tiles.contains(board.get(y + 1).get(x))) {
			diverMovement(x, y + 1, tiles, sunk);
		} else if (y < 5 && board.get(y + 1).get(x) != null){
			if (!tiles.contains(board.get(y + 1).get(x)))
				tiles.add(board.get(y + 1).get(x));
		}
	}

	@Override
	public List<Tile> getFloodRelocTiles(Player p) {
		List<Tile> floodRelocTiles = new ArrayList<>();
		int steps = Integer.MAX_VALUE;
		int x = p.getPositionX();
		int y = p.getPositionY();
		List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
		for (int i = 0; i < board.size(); i++) {
			for (int i1 = 0; i1 < board.get(i).size(); i1++) {
				Tile t = board.get(i).get(i1);
				if (t != null && t.isMovable() && (y != i || x != i1)) {
					int x1 = t.getPositionX();
					int y1 = t.getPositionY();
					int tSteps = Math.abs(x - x1) + Math.abs(y - y1);
					if (tSteps < steps){
						floodRelocTiles.clear();
						floodRelocTiles.add(t);
						steps = tSteps;
					} else if (tSteps == steps){
						floodRelocTiles.add(t);
					}
				}
			}
		}
		return floodRelocTiles;
	}

	public String toNotation(){
		return "D";
	}

	public void doSpecialAction(Player p, int goToX, int goToY) {
		/*
        //Can move through adjacent/missing tiles
        int x = p.getPositionX();
	int y = p.getPositionY();
	int goalX = goToX;
	int goalY = goToY;
	int baseX = Math.min(x, goalX);
	int baseY = Math.min(y, goalY);
	Tile[][] miniBoard = new Tile[Math.abs(x-goalX) + 1][Math.abs(y-goalY) + 1];
	int[][] paths = new int[miniBoard.length][miniBoard[0].length];
	List<List<Tile>> board = getBoard();
	

	for(int i = 0; i < miniBoard.length; i++)
	{
	    for(int j = 0; j < miniBoard[i].length; j++)
	    {
	    	miniBoard[i][j] = board.get(baseX + i).get(baseY + j);
	    }
	}
	 
	paths[0][0] = 1;
	   
	for(int i = 1; i < paths.length; i++)
	{
		if (miniBoard[i][0].isFlooded() || miniBoard[i][0].isSunk())
	        	paths[i][0] = paths[i - 1][0];
	}
	 

	for(int j = 1; j < paths[0].length; j++)
	{
	     if (miniBoard[0][j].isFlooded() || miniBoard[0][j].isSunk())
	        paths[0][j] = paths[0][j - 1];
	}  
	 
	for(int i = 1; i < paths.length; i++)
	{
	    for(int j = 1; j < paths[i].length; j++)
	    {
	        if (miniBoard[i][j].isFlooded() || miniBoard[i][j].isSunk())
	          paths[i][j] = paths[i - 1][j] + paths[i][j - 1];
	    } 
	}
	 
	if(paths[paths.length-1][paths[0].length-1] > 0)
	    p.move(goalX, goalY);
    }
}

		 */
	}
}