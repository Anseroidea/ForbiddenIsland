package player;

import javafx.scene.paint.Color;

public class Engineer extends Role
{

    public Engineer(){
        super(Color.web("#ff8282"));
    }

    @Override
    public int getId() {
        return 2;
    }

    public void doSpecialAction(Player p, int shoreX, int shoreY)
    {
        //Can shore up 2 tiles
        p.shoreUpTile(shoreX, shoreY);
    }

    public String toNotation(){
        return "E";
    }

}
