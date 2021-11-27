package player;

import javafx.scene.paint.Color;

public class Engineer extends Role
{

    public Engineer(){
        super(Color.RED);
    }

    @Override
    public int getId() {
        return 2;
    }

    public void doSpecialAction(Player p)
    {
        //Can shore up 2 tiles
    }
}