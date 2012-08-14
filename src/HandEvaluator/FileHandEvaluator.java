package HandEvaluator;

import Utils.HandUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: jobunch
 * Date: 8/14/12
 * Takes a file that has a hand number and a ranking, and allows look ups based on those rankings
 */

public class FileHandEvaluator implements HandEvaluator {

    Map<Integer,Integer> handMap;

    public FileHandEvaluator(String fileName) {
        handMap = new HashMap<Integer,Integer>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader in = new InputStreamReader(fis, "UTF-8");
            BufferedReader reader = new BufferedReader(in);
            String line;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                handMap.put(new Integer(tokens[0]), new Integer(tokens[1]));

            }
            reader.close();
        } catch (Exception e) {
        }
    }

    public int evaluateHand(int[] hand) {
        return handMap.get(HandUtils.handToHandNumber(hand));
    }
    /**
     * Generates the input file for all normal hand ranks
     * @param fileName
     */
    public static void generateInitialFile(String fileName) {
        EquivalenceHandEvaluator he = new EquivalenceHandEvaluator();
        int count = 0;
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter writer = new BufferedWriter(out);
            for(int card1 = 0; card1 < 52; card1++) {
                for(int card2 = 0; card2 < 52; card2++) {
                    for(int card3 = 0; card3 < 52; card3++) {
                        for(int card4 = 0; card4 < 52; card4++) {
                            for(int card5 = 0; card5 < 52; card5++) {
                                if(card1 < card2 && card2 < card3 && card3 < card4 && card4 < card5) {
                                    int hand[] = {card1,card2,card3,card4,card5};
                                    writer.write("" + HandUtils.handToHandNumber(hand) + "," + he.evaluateHand(hand) + "\n");
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
            writer.close();
        } catch (Exception e) {

        }
        System.out.println(count);
    }

    public static void main(String args[]) {
        //generateInitialFile("resources/BaseRanks.csv");
        FileHandEvaluator fhe = new FileHandEvaluator("resources/BaseRanks.csv");
        System.out.println("loaded");
        EquivalenceHandEvaluator he = new EquivalenceHandEvaluator();
        for(int trials = 0; trials < 100; trials++) {
            int[] hand = HandUtils.randomHand();
            System.out.println(HandUtils.handToString(hand) + ": " + he.evaluateHand(hand) + "," +fhe.evaluateHand(hand) + "," + (he.evaluateHand(hand) == fhe.evaluateHand(hand)));

        }

    }
}
