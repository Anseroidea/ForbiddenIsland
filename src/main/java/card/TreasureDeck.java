package card;

import java.util.*;

public class TreasureDeck{
    private Stack<TreasureCard> activeCards;
    private Stack<TreasureCard> discardedCards;

    public TreasureDeck(List<TreasureCard> treasureCards){
        activeCards.addAll(treasureCards);
        reset();
    }

    /* will shuffle activeCards when the
     initial treasure cards are being initialized */
    /*public void shuffle(){
        Collections.shuffle(activeCards);
    }
*/
    /* will shuffle the discardedCards back into
     activeCards if all treasure cards are drawn */
    public void reset(){
        Collections.shuffle(discardedCards);
        activeCards.addAll(discardedCards);
        discardedCards.clear();
    }

    /* will pass the number of treasure cards to be
    drawn and return the List of card.TreasureCard objects
    after depleting them from activeCards */
    public List<TreasureCard> draw(int treasureCardsToBeDrawn) {
        List<TreasureCard> drawn = new ArrayList<TreasureCard>();
        for(int i = 0 ; i < treasureCardsToBeDrawn; i++){
            TreasureCard c = activeCards.peek();
            drawn.add(c);
            discardedCards.push(c);
            activeCards.pop();
        }
        return drawn;
    }

}
