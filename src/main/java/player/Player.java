package player;

import app.ForbiddenIsland;
import board.Tile;
import card.TreasureCard;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

public class Player
{
    private List<TreasureCard> deck = new ArrayList<>();
    private Role role;
    private int positionX, positionY;
    private BufferedImage graphics;

    public Player(Role r, int x, int y, BufferedImage icon)
    {
        role = r;
        positionX = x;
        positionY = y;
        graphics = icon;
    }

    public Player(Role r, BufferedImage icon){
        role = r;
        graphics = icon;
    }

    public void setPos(int x, int y){
        positionX = x;
        positionY = y;
    }

    public List<TreasureCard> getDeck()
    {
        return deck;
    }

    public Role getRole()
    {
        return role;
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

    public void shoreUpTile(int x, int y)
    {
        Tile check = ForbiddenIsland.getBoard().getBoard().get(x).get(y);
        check.shoreUp();
    }
            
    public void addCard(TreasureCard t)
    {
        deck.add(t);
        if (deck.size() > 5)
            System.out.println("More than 5 cards! Choose 1 card to discard");

    }

    public void addCards(List<TreasureCard> t){
        deck.addAll(t);
        if (deck.size() > 5)
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

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public BufferedImage getGraphics() {
        return graphics;
    }

    public boolean equals(Object o){
        if (o instanceof Player){
            Player p = (Player) o;
            return getRole().getId() == p.getRole().getId();
        }
        return false;
    }
}
