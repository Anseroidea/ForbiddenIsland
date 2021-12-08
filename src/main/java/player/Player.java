package player;

import app.ForbiddenIsland;
import board.Tile;
import board.Treasure;
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
        positionX = x;
        positionY = y;
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

    public boolean canClaim(Treasure t){
        return deck.stream().filter(c -> c.getTreasure() != null && c.getTreasure().equals(t)).count() >= 4;
    }

    public void giveCard(Player p, TreasureCard t)
    {
        this.removeCard(t);
        p.addCard(t);
    }

    public void giveCard(Player p, String t){
        TreasureCard tc = null;
        for (TreasureCard tca : deck){
            if (tca.getName().equalsIgnoreCase(t)){
                tc = tca;
            }
        }
        if (tc == null){
            System.out.println("Card not found!");
        } else {
            this.removeCard(tc);
            p.addCard(tc);
        }
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

    public void discardCard(TreasureCard tc){
        removeCard(tc);
        ForbiddenIsland.getBoard().getTreasureDeck().discardCard(tc);
    }

    public void claimTreasure(Treasure treasureHELD) {
        if (canClaim(treasureHELD)) {
            int cardsRemoved = 0;
            for (int i = deck.size() - 1; i >= 0 && cardsRemoved <= 3; i--){
                if (deck.get(i).equals(treasureHELD.getCard())){
                    removeCard(deck.get(i));
                    cardsRemoved++;
                }
            }
            treasureHELD.claim();
        }
    }
}
