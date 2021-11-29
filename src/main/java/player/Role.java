package player;

import app.ForbiddenIsland;
import board.Tile;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Role
{
    private static List<Role> roleList = Arrays.asList(new Diver(), new Engineer(), new Explorer(), new Messenger(), new Navigator(), new Pilot());
    private Color color;

    public Role(Color c){
        color = c;
    }

    public static List<Role> getRoles()
    {
        return roleList;
    }

    public abstract int getId();
    public abstract String toNotation();

    public void doSpecialAction(Player p, int aimX, int aimY){}

    public String getName(){
        return getClass().getSimpleName();
    }

    public Color getColor(){
        return color;
    }

    public List<Tile> getMovableTiles(Player p){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        List<Tile> getMovableTiles = new ArrayList<>();
        int x = p.getPositionX();
        int y = p.getPositionY();
        if (x > 0 && board.get(y).get(x - 1) != null && board.get(y).get(x - 1).isMovable()) {
            getMovableTiles.add(board.get(y).get(x - 1));
        }
        if (x < 5 && board.get(y).get(x + 1) != null && board.get(y).get(x + 1).isMovable()) {
            getMovableTiles.add(board.get(y).get(x + 1));
        }
        if (y > 0 && board.get(y - 1).get(x) != null && board.get(y - 1).get(x).isMovable()) {
            getMovableTiles.add(board.get(y - 1).get(x));
        }
        if (y < 5 && board.get(y + 1).get(x) != null && board.get(y + 1).get(x).isMovable()) {
            getMovableTiles.add(board.get(y + 1).get(x));
        }
        return getMovableTiles;
    }

    public List<Tile> getNavigatableTiles(Player p){
        List<Tile> navigatableTiles = new ArrayList<>();
        for (List<Tile> tl : ForbiddenIsland.getBoard().getBoard()){
            for (Tile t : tl){
                if (t != null && Math.abs(t.getPositionX() - p.getPositionX()) + Math.abs(t.getPositionY() - p.getPositionY()) <= 2.5 && t.isMovable() && (t.getPositionX() != p.getPositionX() || t.getPositionY() != p.getPositionY())){
                    navigatableTiles.add(t);
                }
            }
        }
        return navigatableTiles;
    }

    public List<Tile> getShorableTiles(Player p){
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        int x = p.getPositionX();
        int y = p.getPositionY();
        List<Tile> getShorableTiles = new ArrayList<>();
        if (x > 0 && board.get(y).get(x - 1) != null && board.get(y).get(x - 1).isFlooded()) {
            getShorableTiles.add(board.get(y).get(x - 1));
        }
        if (x < 5 && board.get(y).get(x + 1) != null && board.get(y).get(x + 1).isFlooded()) {
            getShorableTiles.add(board.get(y).get(x + 1));
        }
        if (y > 0 && board.get(y - 1).get(x) != null && board.get(y - 1).get(x).isFlooded()) {
            getShorableTiles.add(board.get(y - 1).get(x));
        }
        if (y < 5 && board.get(y + 1).get(x) != null && board.get(y + 1).get(x).isFlooded()) {
            getShorableTiles.add(board.get(y + 1).get(x));
        }
        return getShorableTiles;
    }

    public static Role fromNotation(String s){
        if (s.length() != 1){
            return null;
        } else {
            List<String> notationList = Arrays.asList("D", "E", "X", "G", "N", "P");
            System.out.println(notationList.indexOf(s));
            System.out.println(roleList);
            return roleList.get(notationList.indexOf(s));
        }
    }
}