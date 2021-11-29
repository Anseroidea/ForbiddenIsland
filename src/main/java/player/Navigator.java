package player;

import javafx.scene.paint.Color;

public class Navigator extends Role
{

    public Navigator(){
        super(Color.YELLOW);
    }

    @Override
    public int getId() {
        return 5;
    }

    public String toNotation(){
        return "N";
    }

    public void doSpecialAction(Player p)
    {
        //Can move another player for up to 2 adjacent tiles
    }
}