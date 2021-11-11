import java.util.List;
import java.util.BufferedImage;

public class Player
{
    private List<TreasureCard> deck = new ArrayList<>();
    private String name;
    private Role role;
    private int positionX, positionY;
    private BufferedImage graphics;

    public Player(String s)
    {
        name = s;
        //role = r;
    }
    public List<TreasureCard> getDeck()
    {
        return deck;
    }

    public String getName()
    {
        return name;
    }

    public Role getRole()
    {
        return Role;
    }

    public void move(int x, int y)
    {
        positionX = x;
        positionY = y;
    }

    public void addCard(TreasureCard t)
    {
        deck.add(t);
        while(deck.size() > 5)
            System.out.println("More than 5 cards! Choose 1 card to discard");

    }

    public void removeCard(TreasureCard t)
    {
        if(!deck.remove(t))
            System.out.println("No such card exists!");
    }

    public void giveCard(Player p, TreasureCard t)
    {
        this.removeCard(t);
        p.addCard(t);
    }

}