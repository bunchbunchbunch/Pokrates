package FiveCardDraw;

import HandEvaluator.EquivalenceHandEvaluator;
import HandEvaluator.HandEvaluator;
import Utils.HandUtils;

import java.util.*;

/**
 * User: jobunch
 * Date: 8/12/12
 */
public class ExaustiveDecisionMaker {

    private Set<DrawDecision> decisions;
    private HandEvaluator handEvaluator;
    private int bruteForceThreshold;
    private int[] hand;

    public ExaustiveDecisionMaker(int[] hand, HandEvaluator handEvaluator) {
        this.hand = hand;
        this.handEvaluator = handEvaluator;
        decisions = new TreeSet<DrawDecision>();
    }

    public ExaustiveDecisionMaker(int[] hand, HandEvaluator handEvaluator, int bruteForceThreshold) {
        this(hand, handEvaluator);
        this.bruteForceThreshold = bruteForceThreshold;

    }
    public void calculateDecisions() {

            for(int replacementNumber = 0; replacementNumber < 32; replacementNumber++) {


                DrawHand dh = new DrawHand(Arrays.copyOf(hand, hand.length));
                //This does not work right now, need to replace with Exaustive replace.
                dh.replaceCards(replacementNumber);
//                ExaustiveDecisionMaker edm = new ExaustiveDecisionMaker(Arrays.copyOf(dh.hand,hand.length), drawsLeft-1);
//                edm.calculateDecisions();
//
//                DrawDecision dd = new DrawDecision(replacementNumber,edm.bestDecision().averageHandMade);
//                decisions.add(dd);
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

    public static void main(String args[] ) {
//        //testing loading up a collection with 2.6 million items
//        Map<Integer, String> test = new HashMap<Integer, String>();
//        for(int count = 0; count < 2600000; count++ ) {
//            test.put(new Integer(count), ""+count);
//            if(count % 10000 == 0 ) System.out.println("At: " + count);
//        }
//        System.out.println("Done");

        for(int trials = 0; trials < 10; trials++) {
            int[] hand = HandUtils.randomHand();
            System.out.println("Hand: " + HandUtils.handToString(hand));
//            ExaustiveDecisionMaker edm = new ExaustiveDecisionMaker(hand, 1);
//            edm.calculateDecisions();
//            System.out.println("Decision: " + HandUtils.decisionNumberToString(edm.bestDecision().cardReplacementNumber));
//            System.out.println(edm.decisions);

        }
    }
}
