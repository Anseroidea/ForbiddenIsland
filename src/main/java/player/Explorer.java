package player;

import app.ForbiddenIsland;
import board.Tile;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Explorer extends Role
{

    public Explorer(){
        super(Color.GREEN);
    }

    @Override
    public int getId() {
        return 3;
    }

    public List<Tile> getMovableTiles(Player p){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        List<Tile> getMovableTiles = new ArrayList<>(super.getMovableTiles(p));
        int x = p.getPositionX();
        int y = p.getPositionY();
        if (x > 0 && y > 0 && board.get(y - 1).get(x - 1) != null && board.get(y - 1).get(x - 1).isMovable()) {
            getMovableTiles.add(board.get(y - 1).get(x - 1));
        }
        if (x < 5 && y > 0 && board.get(y - 1).get(x + 1) != null && board.get(y - 1).get(x + 1).isMovable()) {
            getMovableTiles.add(board.get(y - 1).get(x + 1));
        }
        if (y < 5 && x > 0 && board.get(y - 1).get(x - 1) != null && board.get(y - 1).get(x - 1).isMovable()) {
            getMovableTiles.add(board.get(y + 1).get(x - 1));
        }
        if (y < 5 && x < 5 && board.get(y + 1).get(x + 1) != null && board.get(y + 1).get(x + 1).isMovable()) {
            getMovableTiles.add(board.get(y + 1).get(x + 1));
        }
        return getMovableTiles;
    }

    public void doSpecialAction(Player p, int moveX, int moveY)
    {
        //Can move diagonally
        int x = p.getPositionX();
        int y = p.getPositionY();
        if(Math.abs(moveX - x) <= 1 && Math.abs(moveY - y) <= 1)
            p.move(moveX, moveY);
        else
            System.out.println("Tile is unaccessible!");
    }

    public String toNotation(){
        return "X";
    }
}
