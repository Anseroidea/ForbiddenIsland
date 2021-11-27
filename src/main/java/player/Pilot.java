package player;

import javafx.scene.paint.Color;

public class Pilot extends Role
{

    public Pilot(){
        super(Color.BLUE);
    }

    @Override
    public int getId() {
        return 6;
    }

    public void doSpecialAction(Player p)
    {
        //Can move to any unsunken tile

    }
}