package HandEvaluator;

import Utils.HandUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: jobunch
 * Date: 11/23/11
 * Time: 9:49 AM
 */
public class HandEvaluator {

    private static HandEvaluator instance;
    public Map<Integer, Integer> flushHands;
    public Map<Integer, Integer> nonFlushHands;
    public Map<Integer, String> handNames;

    protected HandEvaluator() {
        flushHands = new HashMap<Integer, Integer>();
        nonFlushHands = new HashMap<Integer, Integer>();
        handNames = new HashMap<Integer, String>();
        try {
            FileInputStream fis = new FileInputStream("resources/PokerEquivalenceClasses.txt");
            InputStreamReader in = new InputStreamReader(fis, "UTF-8");
            BufferedReader reader = new BufferedReader(in);
            String line;
            int count = 0;
            while((line = reader.readLine()) != null) {
                count++;
                String[] tokens = line.split(",");
                if(tokens[12].equals("1")) {
                    flushHands.put(Integer.valueOf(tokens[13]),count);
                } else {
                    nonFlushHands.put(Integer.valueOf(tokens[13]),count);
                }
                handNames.put(count, tokens[11]);

            }
            reader.close();
        } catch (Exception e) {
        }

    }

    public static HandEvaluator getInstance() {
        if(instance == null)
            instance = new HandEvaluator();
        return instance;
    }
    public int evaluateHand(int[] hand) {
        if(isFlush(hand)) {
            return flushHands.get(getEquivalenceClassNumber(hand));
        } else {
            return nonFlushHands.get(getEquivalenceClassNumber(hand));
        }
    }




    public static boolean isFlush(int[] hand) {
        int firstSuit = hand[0]/13;
        return hand[1]/13 == firstSuit &&
                hand[2]/13 == firstSuit &&
                hand[3]/13 == firstSuit &&
                hand[4]/13 == firstSuit;
    }

    public static int getEquivalenceClassNumber(int[] hand) {
        int retval = 1;
        for(int card: hand)
            retval *= Primes.getPrime(card%13);
        return retval;
    }

    public String getHandName(int handNumber) {
        return handNames.get(handNumber);
    }

    public static void main(String args[]) {
        HandEvaluator he = HandEvaluator.getInstance();
        int[] royalFlush = {0,12,11,10,9};
        System.out.println(he.evaluateHand(royalFlush));
        System.out.println(he.getHandName(he.evaluateHand(royalFlush)));

        int[] aceHighStraight = {9,10,11,12,13};
        System.out.println(he.evaluateHand(aceHighStraight));
        System.out.println(he.getHandName(he.evaluateHand(aceHighStraight)));

        System.out.println(he.getHandName(he.evaluateHand(HandUtils.stringToHand("AdAsAhKcKs"))));
    }

}
