package Utils;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * User: jobunch
 * Date: 11/24/11
 * Time: 1:39 PM
 */
public class HandUtils {
    public static String handToString(int[] hand) {
        String retval = "[";
        for(int card: hand) {
            if(card%13 == 0) retval+= "A";
            else if(card%13 == 1) retval+= "2";
            else if(card%13 == 2) retval+= "3";
            else if(card%13 == 3) retval+= "4";
            else if(card%13 == 4) retval+= "5";
            else if(card%13 == 5) retval+= "6";
            else if(card%13 == 6) retval+= "7";
            else if(card%13 == 7) retval+= "8";
            else if(card%13 == 8) retval+= "9";
            else if(card%13 == 9) retval+= "T";
            else if(card%13 == 10) retval+= "J";
            else if(card%13 == 11) retval+= "Q";
            else if(card%13 == 12) retval+= "K";

            if(card/13 == 0) retval += "C";
            else if(card/13 == 1) retval += "D";
            else if(card/13 == 2) retval += "H";
            else if(card/13 == 3) retval += "S";

            retval += ",";
        }
        retval = retval.substring(0,retval.length()-1);
        retval += "]";
        return retval;
    }

    public static int[] stringToHand(String hand) {
        int[] retval = new int[hand.length()/2];
        for(int count = 0; count < hand.length(); count += 2) {
            int rank = 0;
            int suit = 0;
            switch (hand.charAt(count)) {
                case 'A': case 'a': rank = 0; break;
                case '2': rank = 1; break;
                case '3': rank = 2; break;
                case '4': rank = 3; break;
                case '5': rank = 4; break;
                case '6': rank = 5; break;
                case '7': rank = 6; break;
                case '8': rank = 7; break;
                case '9': rank = 8; break;
                case 'T': case 't': rank = 9; break;
                case 'J': case 'j': rank = 10; break;
                case 'Q': case 'q': rank = 11; break;
                case 'K': case 'k': rank = 12; break;
            }
            switch (hand.charAt(count+1)) {
                case 'C': case 'c': suit = 0; break;
                case 'D': case 'd': suit = 1; break;
                case 'H': case 'h': suit = 2; break;
                case 'S': case 's': suit = 3; break;
            }
            retval[count/2] = rank + suit*13;
        }
        return retval;
    }

    public static int[] randomHand() {
        //Uncomment this to not make it a random hand
        //Random r = new Random(1234);
        Random r = new Random();
        int[] retval = new int[5];
        List<Integer> hand = new ArrayList<Integer>();
        while(hand.size() < 5) {
            int temp = r.nextInt(52);
            if(!hand.contains(temp)) hand.add(temp);
        }
        for(int count = 0; count < 5; count++ ) {
            retval[count] = hand.get(count);
        }
        return retval;
    }

    public static String decisionNumberToString(int decisionNumber) {
        String rval = "[";
        for(int index = 0; index < 5; index++) {
            rval += decisionNumber%2 + ",";
            decisionNumber /= 2;
        }
        rval = rval.substring(0,rval.length()-1);
        rval += "]";
        return rval;
    }

    public static int handToHandNumber(int hand[]) {
        Arrays.sort(hand);
        int retval = 0;
        for(int count = 0; count < hand.length; count++) {
            retval *= 52;
            retval += hand[count];
        }
        return retval;
    }
}
