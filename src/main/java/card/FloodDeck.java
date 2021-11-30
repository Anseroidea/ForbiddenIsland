package card;

import card.FloodCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class FloodDeck{
    private Stack<FloodCard> activeCards;
    private Stack<FloodCard> discardedCards;

    public FloodDeck(List<FloodCard> floodCards){
        activeCards = new Stack<>();
        discardedCards = new Stack<>();
        activeCards.addAll(floodCards);
        shuffle();
    }

    /* will shuffle activeCards when the
     initial treasure cards are being initialized */
    public void shuffle(){
        Collections.shuffle(activeCards);
    }

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
    public List<FloodCard> draw(int floodCardsToBeDrawn) {
        List<FloodCard> drawn = new ArrayList<FloodCard>();
        for(int i = 0 ; i < floodCardsToBeDrawn; i++){
            FloodCard c = activeCards.peek();
            drawn.add(c);
            discardedCards.push(c);
            activeCards.pop();
        }
        return drawn;
    }

}
