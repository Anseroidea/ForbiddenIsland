package player;

import app.ForbiddenIsland;
import board.Tile;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Pilot extends Role
{

    public Pilot(){
        super(Color.BLUE);
    }

    @Override
    public int getId() {
        return 6;
    }

    @Override
    public List<Tile> getMovableTiles(Player p) {
        List<Tile> getMovableTiles = new ArrayList<>();
        int x = p.getPositionX();
        int y = p.getPositionY();
        List<List<Tile>> board = ForbiddenIsland.getBoard().getBoard();
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (!(r == y && c == x) && board.get(r).get(c) != null && board.get(r).get(c).isMovable()) {
                    getMovableTiles.add(board.get(r).get(c));
                }
            }
        }
        return getMovableTiles;
    }

    @Override
    public List<Tile> getFloodRelocTiles(Player p) {
        return getMovableTiles(p);
    }

    public String toNotation(){
        return "P";
    }

    public void doSpecialAction(Player p)
    {
        //Can move to any unsunken tile

    }
}