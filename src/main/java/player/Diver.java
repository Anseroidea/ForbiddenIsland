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

    public void doSpecialAction(Player p)
    {
        //Can move through adjacent/missing tiles
    }
}