package FiveCardDrawHigh;

import HandEvaluator.HandEvaluator;
import Utils.HandUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jobunch
 * Date: 11/24/11
 * Time: 2:07 PM
 */
public class DecisionMaker {
    private int[] hand;
    private int drawsLeft;
    private int trialsPerDraw;
    private List<DrawDecision> decisions;
    public DecisionMaker(int[] hand, int drawsLeft, int trialsPerDraw) {
        this.hand = hand;
        this.drawsLeft = drawsLeft;
        this.trialsPerDraw = trialsPerDraw;
        decisions = new ArrayList<DrawDecision>();
    }

    public void calculateDecisions() {
        if(drawsLeft == 0) {
            DrawDecision dd = new DrawDecision(0,HandEvaluator.getInstance().evaluateHand(hand));
            decisions.add(dd);
        } else {
            for(int replacementNumber = 0; replacementNumber < 32; replacementNumber++) {
                double average = 0;
                for(int trial = 0; trial < trialsPerDraw; trial++) {

                    DrawHand dh = new DrawHand(hand);
                    dh.replaceCards(replacementNumber);
                    DecisionMaker dm = new DecisionMaker(hand, drawsLeft-1, trialsPerDraw);
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
            if(best == null || best.averageHandMade < dd.averageHandMade) {
                best = dd;
            }
        }
        return best;
    }

    public static void main(String args[]) {
        int[] acesUp = {0,13,1,14,2};
        System.out.println(HandUtils.handToString(acesUp));
        DecisionMaker dm = new DecisionMaker(acesUp,1,100);
        dm.calculateDecisions();
        DrawDecision best = dm.bestDecision();
        System.out.println(HandUtils.handToString(acesUp));
        System.out.println(best.averageHandMade);
        System.out.println(best.cardReplacementNumber);
    }

}
