package player;

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
        switch(s)
        {
            case "DIVER":
                role = new Diver();
                break;
            case "Engineer":
                role = new Engineer();
                break;
            case "Explorer":
                role = new Explorer();
                break;
            case "Messenger":
                role = new Messenger();
                break;
            case "Navigator":
                role = new Navigator();
                break;
            case "Pilot":
                role = new Pilot();
                break;
        }
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
        if(Math.abs(positionX - x) + Math.abs(positionY - y) <= 1) {
            positionX = x;
            positionY = y;
        }
        else
            System.out.println("Out of moving range!!!");
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
        if(this.positionX == p.positionX || this.positionY == p.positionY)
        {
            this.removeCard(t);
            p.addCard(t);
        }
        else
            System.out.println("Not on same tile -- Cannot give card to another player");
    }

}