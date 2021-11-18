package player;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class Role
{
    private static List<Role> roleList = Arrays.asList(new Engineer(), new Explorer(), new Pilot(), new Navigator(), new Diver(), new Messenger());
    private String role;

    public List<Role> getRoles()
    {
        return roleList;
    }

    public void doSpecialAction(Player p, int x, int y){}
    public void setName(String s)
    {
        role = s;
    }
}