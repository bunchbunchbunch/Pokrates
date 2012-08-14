package FiveCardDraw;

import HandEvaluator.HandEvaluator;
import Utils.HandUtils;

import java.util.*;

/**
 * User: jobunch
 * Date: 8/12/12
 */
public class ExaustiveDecisionMaker {

    private int drawsLeft;
    private Set<DrawDecision> decisions;
    int[] hand;

    public ExaustiveDecisionMaker(int[] hand, int drawsLeft) {
        this.hand = hand;
        this.drawsLeft = drawsLeft;
        decisions = new TreeSet<DrawDecision>();
    }

    public void calculateDecisions() {
        if(drawsLeft == 0) {
            DrawDecision dd = new DrawDecision(0, HandEvaluator.getInstance().evaluateHand(hand));
            decisions.add(dd);
        } else {
            for(int replacementNumber = 0; replacementNumber < 32; replacementNumber++) {


                DrawHand dh = new DrawHand(Arrays.copyOf(hand, hand.length));
                //This does not work right now, need to replace with Exaustive replace.
                dh.replaceCards(replacementNumber);
                ExaustiveDecisionMaker edm = new ExaustiveDecisionMaker(Arrays.copyOf(dh.hand,hand.length), drawsLeft-1);
                edm.calculateDecisions();

                DrawDecision dd = new DrawDecision(replacementNumber,edm.bestDecision().averageHandMade);
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
            ExaustiveDecisionMaker edm = new ExaustiveDecisionMaker(hand, 1);
            edm.calculateDecisions();
            System.out.println("Decision: " + HandUtils.decisionNumberToString(edm.bestDecision().cardReplacementNumber));
            System.out.println(edm.decisions);

        }
    }
}
