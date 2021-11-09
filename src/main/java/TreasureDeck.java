import java.util.*;

public class TreasureDeck extends TreasureCard{
    private Stack<TreasureCard> activeCards;
    private Stack<TreasureCard> discardedCards;

    /* will shuffle activeCards when the
     initial treasure cards are being initialized */
    public void shuffle(){

    }

    /* will shuffle the discardedCards back into
     activeCards if all treasure cards are drawn */
    public void reset(){

    }

    /* will pass the number of treasure cards to be
    drawn and return the List of TreasureCard objects
    after depleting them from activeCards */
    public List<TreasureCard> draw(int treasureCardsToBeDrawn) {
        List<TreasureCard> drawn = new ArrayList<TreasureCard>();

        return drawn;
    }

}
