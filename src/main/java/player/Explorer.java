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
        if (y < 5 && x > 0 && board.get(y + 1).get(x - 1) != null && board.get(y + 1).get(x - 1).isMovable()) {
            getMovableTiles.add(board.get(y + 1).get(x - 1));
        }
        if (y < 5 && x < 5 && board.get(y + 1).get(x + 1) != null && board.get(y + 1).get(x + 1).isMovable()) {
            getMovableTiles.add(board.get(y + 1).get(x + 1));
        }
        return getMovableTiles;
    }

    public List<Tile> getNavigatableTiles(Player p){
        List<Tile> navigatableTiles = new ArrayList<>();
        for (List<Tile> tl : ForbiddenIsland.getBoard().getBoard()){
            for (Tile t : tl){
                if (t != null && ((Math.abs(t.getPositionX() - p.getPositionX()) <= 2.5 && t.getPositionY() == p.getPositionY()) || (Math.abs(t.getPositionY() - p.getPositionY()) <= 2.5 && t.getPositionX() == p.getPositionX()) || (Math.abs(t.getPositionY() - p.getPositionY()) == Math.abs(t.getPositionX() - p.getPositionX()) && Math.abs(t.getPositionY() - p.getPositionY()) <= 2.5)) && t.isMovable() && (t.getPositionX() != p.getPositionX() || t.getPositionY() != p.getPositionY())) {
                    navigatableTiles.add(t);
                }
            }
        }
        return navigatableTiles;
    }

    public List<Tile> getShorableTiles(Player p){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        List<Tile> getShorableTiles = new ArrayList<>(super.getShorableTiles(p));
        int x = p.getPositionX();
        int y = p.getPositionY();
        if (x > 0 && y > 0 && board.get(y - 1).get(x - 1) != null && board.get(y - 1).get(x - 1).isFlooded()) {
            getShorableTiles.add(board.get(y - 1).get(x - 1));
        }
        if (x < 5 && y > 0 && board.get(y - 1).get(x + 1) != null && board.get(y - 1).get(x + 1).isFlooded()) {
            getShorableTiles.add(board.get(y - 1).get(x + 1));
        }
        if (y < 5 && x > 0 && board.get(y + 1).get(x - 1) != null && board.get(y + 1).get(x - 1).isFlooded()) {
            getShorableTiles.add(board.get(y + 1).get(x - 1));
        }
        if (y < 5 && x < 5 && board.get(y + 1).get(x + 1) != null && board.get(y + 1).get(x + 1).isFlooded()) {
            getShorableTiles.add(board.get(y + 1).get(x + 1));
        }
        return getShorableTiles;
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
