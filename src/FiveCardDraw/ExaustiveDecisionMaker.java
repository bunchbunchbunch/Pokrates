package FiveCardDraw;

import HandEvaluator.FileHandEvaluator;
import HandEvaluator.HandEvaluator;
import Utils.HandUtils;

import java.util.*;

/**
 * User: jobunch
 * Date: 8/12/12
 */
public class ExaustiveDecisionMaker {

    private List<DrawDecision> decisions;
    private HandEvaluator handEvaluator;
    private int bruteForceThreshold;
    private int[] hand;

    public ExaustiveDecisionMaker(int[] hand, HandEvaluator handEvaluator) {
        this.hand = hand;
        this.handEvaluator = handEvaluator;
        decisions = new ArrayList<DrawDecision>();
    }

    public ExaustiveDecisionMaker(int[] hand, HandEvaluator handEvaluator, int bruteForceThreshold) {
        this(hand, handEvaluator);
        this.bruteForceThreshold = bruteForceThreshold;

    }
    public void calculateDecisions() {
        DrawHand dh = new DrawHand(hand);
        for(int replacementNumber = 0; replacementNumber < 32; replacementNumber++) {


            List<DrawHand> possibleHands = dh.replaceCardsAllPossibilities(replacementNumber, bruteForceThreshold);
            if(possibleHands.size() > 0) {
                double averageHandMade = 0.0;
                for(DrawHand temp: possibleHands) {
                    int handValue = handEvaluator.evaluateHand(temp.hand);
                    averageHandMade += handValue;
                }
                averageHandMade /= possibleHands.size();
                DrawDecision dd = new DrawDecision(replacementNumber, averageHandMade);
                decisions.add(dd);
            } else {
                DrawDecision dd = new DrawDecision(replacementNumber, handEvaluator.evaluateHand(hand));
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

    public DrawDecision worstDecision() {
        DrawDecision worst = null;
        for(DrawDecision dd : decisions) {
            if(worst == null || worst.averageHandMade < dd.averageHandMade) {
                worst = dd;
            }
        }
        return worst;
    }
    public static void main(String args[] ) {

//        HandEvaluator he = new FileHandEvaluator("resources/BaseRanks2.csv");
//        for(int trials = 0; trials < 10; trials++) {
//            int[] hand = HandUtils.randomHand();
//            System.out.println("Hand: " + HandUtils.handToString(hand));
//            System.out.println(HandUtils.handToNumberString(hand));
//
//            ExaustiveDecisionMaker edm = new ExaustiveDecisionMaker(hand, he, 100000);
//
//
//
//            edm.calculateDecisions();
//            Collections.sort(edm.decisions);
//            System.out.println("Decision: " + HandUtils.decisionNumberToString(edm.bestDecision().cardReplacementNumber));
//            System.out.println(edm.decisions);
//
//        }
        HandEvaluator he = new FileHandEvaluator("resources/BaseRanks2.csv");
        int hand[] = {41,36,47,30,34};
        System.out.println(HandUtils.handToString(hand));
        System.out.println(he.evaluateHand(hand));
        ExaustiveDecisionMaker edm = new ExaustiveDecisionMaker(hand, he, 100000);



        edm.calculateDecisions();
        System.out.println("Decision: " + HandUtils.decisionNumberToString(edm.bestDecision().cardReplacementNumber));
        Collections.sort(edm.decisions);
        System.out.println(edm.decisions.size());
        System.out.println(edm.decisions);

    }
}
