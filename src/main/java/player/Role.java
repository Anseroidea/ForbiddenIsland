package player;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class Role
{
    private static List<Role> roleList = Arrays.toList(new Engineer(), new Explorer(), new Pilot(), new Navigator(), new Diver(), new Messenger());

    public Role()
    {
        roleList.add(new Engineer());
        roleList.add(new Explorer());
        roleList.add(new Pilot());
        roleList.add(new Navigator());
        roleList.add(new Diver());
        roleList.add(new Messenger());

    }
    public List<Role> getRoles()
    {
        return roleList;
    }

    public String identify(int i)
    {

    }
    public void doSpecialAction(Player p){}
}