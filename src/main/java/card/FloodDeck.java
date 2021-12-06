package card;

import card.FloodCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class FloodDeck{
    private Stack<FloodCard> activeCards;
    private Stack<FloodCard> discardedCards;
    private Stack<FloodCard> deadCards;

    public FloodDeck(List<FloodCard> floodCards){
        activeCards = new Stack<>();
        discardedCards = new Stack<>();
        deadCards = new Stack<>();
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
        System.out.println(activeCards);
    }

    /* will pass the number of treasure cards to be
    drawn and return the List of card.TreasureCard objects
    after depleting them from activeCards */
    public List<FloodCard> draw(int floodCardsToBeDrawn) {
        List<FloodCard> drawn = new ArrayList<FloodCard>();
        System.out.println(activeCards);
        int maxSize = activeCards.size();
        for(int i = 0 ; i < Math.min(floodCardsToBeDrawn, maxSize); i++){
            if (activeCards.isEmpty()){
                reset();
            }
            FloodCard c = activeCards.peek();
            drawn.add(c);
            discardedCards.push(c);
            activeCards.pop();
        }
        return drawn;
    }

    public void killCard(FloodCard fc){
        discardedCards.remove(fc);
        deadCards.add(fc);
    }

    public Stack<FloodCard> getDiscardedStack() {
        return discardedCards;
    }
}
