package FiveCardDraw;

import HandEvaluator.HandEvaluator;
import Utils.HandUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * User: jobunch
 * Date: 11/24/11
 * Time: 12:17 AM
 */
public class DrawHand {
    public int[] hand;
    public List<Integer> deadCards;
    public static Random random = new Random();

    public DrawHand(int[] hand) {
        this.hand = hand;
        deadCards = new ArrayList<Integer>();
        for(int card: hand) {
            deadCards.add(card);
        }
    }

    /**
     * Create a random hand
     */
    public DrawHand() {
        hand = new int[5];
        deadCards = new ArrayList<Integer>();
        for(int index = 0; index < hand.length; index++) {
            replaceCardWithRandom(index);
        }
    }

    public void replaceCards(int cardsToReplace) {
        int index = 0;
        while(cardsToReplace != 0) {

            if(cardsToReplace % 2 == 1) {
                replaceCardWithRandom(index);
            }
            index++;
            cardsToReplace/=2;
        }
    }

    //Ouch this hurts -- this takes a lot of time
    public Set<DrawHand> replaceCardsAllPossibilities(int cardsToReplace) {
        return null;
    }



    public void replaceCardWithRandom(int index) {
        boolean replaced = false;
        while(!replaced) {
            int randomCard = random.nextInt(52);
            if(!deadCards.contains(randomCard)) {
                replaced = true;
                deadCards.add(randomCard);
                hand[index] = randomCard;
            }
        }

    }
    public static void main(String[] args) {
        DrawHand dh = new DrawHand();
        HandEvaluator he = HandEvaluator.getInstance();
        System.out.println(he.getHandName(he.evaluateHand(dh.hand)));
        System.out.println(HandUtils.handToString(dh.hand));

        dh.replaceCards(3);

        System.out.println(he.getHandName(he.evaluateHand(dh.hand)));
        System.out.println(HandUtils.handToString(dh.hand));
        System.out.println(dh.deadCards);
    }
}
