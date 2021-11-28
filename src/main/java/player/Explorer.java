package player;

import javafx.scene.paint.Color;

public class Explorer extends Role
{

    public Explorer(){
        super(Color.GREEN);
    }

    @Override
    public int getId() {
        return 3;
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
}
