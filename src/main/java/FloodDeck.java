import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FloodDeck{
    private Stack<FloodCard> activeCards;
    private Stack<FloodCard> discardedCards;

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
    public List<FloodCard> draw(int floodCardsToBeDrawn) {
        List<FloodCard> drawn = new ArrayList<FloodCard>();

        return drawn;
    }

}
