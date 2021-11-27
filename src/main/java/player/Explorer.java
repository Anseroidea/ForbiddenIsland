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

    public void doSpecialAction(Player p)
    {
        //Can move diagonally
    }
}