package player;

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

    public void doSpecialAction(Player p, int aimX, int aimY){}

    public String getName(){
        return getClass().getSimpleName();
    }

    public Color getColor(){
        return color;
    }
}
