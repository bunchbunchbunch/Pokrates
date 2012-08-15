package FiveCardDraw;

import HandEvaluator.EquivalenceHandEvaluator;
import Utils.HandUtils;
import Utils.SubsetUtils;

import java.util.*;

/**
 * User: jobunch
 * Date: 11/24/11
 * Time: 12:17 AM
 */
public class DrawHand implements Cloneable {
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

    protected Object clone(){
        DrawHand rval = new DrawHand(hand.clone());
        rval.deadCards = new ArrayList<Integer>(deadCards);
        return rval;
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

    public List<Integer> getDeck() {
        List<Integer> retval = new ArrayList<Integer>();
        for(int count = 0; count < 52; count++) {
            if(!deadCards.contains(count)) retval.add(count);
        }
        return retval;
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
    public List<DrawHand> replaceCardsAllPossibilities(int cardsToReplace, int maxSize) {
        List<DrawHand> retval = new ArrayList<DrawHand>();
        if(cardReplacementPossibilites(cardsToReplace) > maxSize) {
            for(int count = 0; count < maxSize; count++) {
                DrawHand temp = (DrawHand)this.clone();
                temp.replaceCards(cardsToReplace);
                retval.add(temp);
            }
        } else {
            System.out.println("HERE");
            System.out.println(getDeck());
            System.out.println(SubsetUtils.onesInBaseTwo(cardsToReplace));
            List<List<Integer>> replacementSets = SubsetUtils.subset(getDeck(), SubsetUtils.onesInBaseTwo(cardsToReplace));
            System.out.println(replacementSets);
            for(List<Integer> replacementCards : replacementSets) {
                DrawHand temp = (DrawHand)this.clone();
                temp.replaceCards(cardsToReplace,replacementCards);
                retval.add(temp);
            }

        }
        return retval;
    }

    public void replaceCards(int cardsToReplace, List<Integer> replacementCards) {
        int index = 0;
        int replacementCardIndex = 0;
        while(cardsToReplace != 0) {

            if(cardsToReplace % 2 == 1) {
                hand[index] = replacementCards.get(replacementCardIndex);
                deadCards.add(replacementCards.get(replacementCardIndex));
                replacementCardIndex++;
            }
            index++;
            cardsToReplace/=2;
        }
    }

    public int cardReplacementPossibilites(int cardsToReplace) {
        int numCardsToReplace = 0;

        while(cardsToReplace != 0) {
            if(cardsToReplace % 2 == 1) {
                numCardsToReplace++;
            }
            cardsToReplace/=2;
        }
        if(numCardsToReplace == 0) return 0;
        int retval = 52-deadCards.size();
        for(int count = 51-deadCards.size(); count > 51-deadCards.size()-numCardsToReplace+1;count--) {
            retval *= count;
        }

        return retval;
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
    public static void main(String[] args) throws CloneNotSupportedException {
        DrawHand dh = new DrawHand();
        EquivalenceHandEvaluator he = EquivalenceHandEvaluator.getInstance();
        //System.out.println(he.getHandName(he.evaluateHand(dh.hand)));
        System.out.println(HandUtils.handToString(dh.hand));
        List<DrawHand> rp = dh.replaceCardsAllPossibilities(1,10000);
        for(DrawHand temp: rp) {
            System.out.println(HandUtils.handToString(temp.hand));
        }
        System.out.println();
//        dh.replaceCards(3);
//        System.out.println(HandUtils.handToString(dh.hand));
//
//        System.out.println(he.getHandName(he.evaluateHand(dh.hand)));
//        System.out.println(HandUtils.handToString(dh.hand));
//        System.out.println(dh.deadCards);
    }
}
