package FiveCardDraw;

import HandEvaluator.HandEvaluator;
import Utils.HandUtils;

import java.util.*;

/**
 * User: jobunch
 * Date: 11/24/11
 * Time: 2:07 PM
 */
public class DecisionMakerHigh {
    private int[] hand;
    private int drawsLeft;
    private int trialsPerDraw;
    private Set<DrawDecision> decisions;
    private boolean first = false;

    public DecisionMakerHigh(int[] hand, int drawsLeft, int trialsPerDraw) {
        this.hand = hand;
        this.drawsLeft = drawsLeft;
        this.trialsPerDraw = trialsPerDraw;
        decisions = new TreeSet<DrawDecision>();
    }

    public DecisionMakerHigh(int[] hand, int drawsLeft, int trialsPerDraw, boolean first) {
        this.hand = hand;
        this.drawsLeft = drawsLeft;
        this.trialsPerDraw = trialsPerDraw;
        decisions = new TreeSet<DrawDecision>();
        this.first = first;
    }

    public void calculateDecisions() {
        if(drawsLeft == 0) {
            DrawDecision dd = new DrawDecision(0,HandEvaluator.getInstance().evaluateHand(hand));
            decisions.add(dd);
        } else {
            for(int replacementNumber = 0; replacementNumber < 32; replacementNumber++) {
                if(first) System.out.println("On #" + replacementNumber);
                double average = 0;
                for(int trial = 0; trial < trialsPerDraw; trial++) {

                    DrawHand dh = new DrawHand(Arrays.copyOf(hand,hand.length));
                    dh.replaceCards(replacementNumber);
                    DecisionMakerHigh dm = new DecisionMakerHigh(Arrays.copyOf(dh.hand,hand.length), drawsLeft-1, trialsPerDraw);
                    dm.calculateDecisions();
                    average += dm.bestDecision().averageHandMade;
                }
                average /= trialsPerDraw;
                DrawDecision dd = new DrawDecision(replacementNumber,average);
                decisions.add(dd);
            }
        }
    }

    public DrawDecision bestDecision() {
        DrawDecision best = null;
        for(DrawDecision dd : decisions) {
            if(best == null || best.averageHandMade > dd.averageHandMade) {
                best = dd;
            }
        }
        return best;
    }



    public String overallDecisionString() {
        String retval = "";
        for(DrawDecision dd: decisions) {
            retval += dd.averageHandMade + " " + HandUtils.decisionNumberToString(dd.cardReplacementNumber) + "\n";
        }
        return retval;
    }

    
    public static void main(String args[]) {
        int[] acesUp = {0,13,1,14,2};
        int[] other = {19,13,1,14,2};
        int[] royal = {0,12,11,10,9};
        int[] lowPairWithAce = {0,1,14,32,33};
        System.out.println(HandEvaluator.getInstance().evaluateHand(acesUp));
        System.out.println(HandUtils.handToString(acesUp));
        System.out.println(HandEvaluator.getInstance().evaluateHand(other));
        System.out.println(HandUtils.handToString(other));
        System.out.println(HandEvaluator.getInstance().evaluateHand(royal));
        System.out.println(HandUtils.handToString(royal));
        System.out.println(HandEvaluator.getInstance().evaluateHand(lowPairWithAce));
        System.out.println(HandUtils.handToString(lowPairWithAce));
        DecisionMakerHigh dm = new DecisionMakerHigh(acesUp,2,300,true);
        dm.calculateDecisions();
        System.out.println(dm.overallDecisionString());
        DrawDecision best = dm.bestDecision();
        System.out.println(HandUtils.handToString(acesUp));
        System.out.println(best.averageHandMade);
        System.out.println(best.cardReplacementNumber);
        System.out.println(HandUtils.decisionNumberToString(best.cardReplacementNumber));
    }

}
