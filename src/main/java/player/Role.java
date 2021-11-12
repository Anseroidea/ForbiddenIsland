package player;

import java.util.List;
import java.util.BufferedImage;

public abstract class Role
{
    private List<Role> roleList = new ArrayList<>();

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

    public void doSpecialAction(Player p){}
}