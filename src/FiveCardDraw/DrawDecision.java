package FiveCardDraw;

import Utils.HandUtils;

/**
 * User: jobunch
 * Date: 11/24/11
 * Time: 2:19 PM
 */
public class DrawDecision implements Comparable {
    public int cardReplacementNumber;
    public double averageHandMade;
    
    public DrawDecision(int cardReplacementNumber, double averageHandMade) {
        this.cardReplacementNumber = cardReplacementNumber;
        this.averageHandMade = averageHandMade;
    }


    public int compareTo(Object o) {
        if(((DrawDecision) o).averageHandMade > averageHandMade)
            return -1;
        else if (((DrawDecision) o).averageHandMade < averageHandMade)
            return 1;
        return 0;
    }

    public String toString() {
        return "[" + HandUtils.decisionNumberToString(cardReplacementNumber) + "," + averageHandMade + "]\n";
    }
}
