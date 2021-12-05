package card;

import java.util.*;
import java.util.stream.Collectors;

public class TreasureDeck{
    private Stack<TreasureCard> activeCards;
    private Stack<TreasureCard> discardedCards;


    public TreasureDeck(List<TreasureCard> treasureCards){
        activeCards = new Stack<>();
        discardedCards = new Stack<>();
        activeCards.addAll(treasureCards);
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
    public List<TreasureCard> draw(int treasureCardsToBeDrawn) {
        List<TreasureCard> drawn = new ArrayList<>();
        for(int i = 0 ; i < treasureCardsToBeDrawn; i++){
            if (activeCards.isEmpty()){
                reset();
            }
            TreasureCard c = activeCards.pop();
            drawn.add(c);
        }
        return drawn;
    }

    public void addCards(List<TreasureCard> tc){
        activeCards.addAll(tc);
        shuffle();
    }

    public void discardCard(TreasureCard treasureCard){
        discardedCards.add(treasureCard);
    }

    public Stack<TreasureCard> getDiscardedStack() {
        return discardedCards;
    }
}
