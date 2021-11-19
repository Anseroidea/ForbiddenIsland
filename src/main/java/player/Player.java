package player;
import card.TreasureCard;


import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Player implements Comparable {
    private List<TreasureCard> deck = new ArrayList<>();
    private String name;
    private Role role;
    private int positionX, positionY;
    private BufferedImage graphics;
    private BoardGame board;

    public Player(String s)
    {
        name = s;
        switch(s)
        {
            case "Diver":
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
        role.setName(s);
    }

    public void BoardGame getBoard()
    {
        return board;
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
        return role;
    }

    public int getX()
    {
        return positionX;
    }

    public int getY()
    {
        return positionY;
    }
    public void move(int x, int y, boolean special)
    {
        if(board.get(x).get(y).isSunk()) {
            System.out.println("Can't move to a sunken tile!");
            return;
        }

        if(special)
        {
            positionX = x;
            positionY= y;
        }
        else if(Math.abs(positionX - x) + Math.abs(positionY - y) <= 1) {
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
        for(int i = 0; i < deck.size(); i++)
            if(deck.get(i).equals(t))
            {
                deck.remove(i);
                return;
            }

        System.out.println("No such card exists!");
    }

    public void giveCard(Player p, TreasureCard t, boolean messenger)
    {
        if(messenger)
        {
            this.removeCard(t);
            p.addCard(t);
        }
        else if(this.positionX == p.positionX || this.positionY == p.positionY)
        {
            this.removeCard(t);
            p.addCard(t);
        }
        else
            System.out.println("Not on same tile -- Cannot give card to another player");
    }

    @Override
    public int compareTo(Object o)
    {
        return this.name.compareTo(((Player)o).name);
    }
}