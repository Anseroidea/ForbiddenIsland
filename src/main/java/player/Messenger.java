package player;

import javafx.scene.paint.Color;

public class Messenger extends Role
{

    public Messenger(){
        super(Color.GHOSTWHITE);
    }

    @Override
    public int getId() {
        return 4;
    }

    public void doSpecialAction(Player p)
    {
        //Can sned a card  to any player
    }

    public String toNotation(){
        return "R";
    }
}