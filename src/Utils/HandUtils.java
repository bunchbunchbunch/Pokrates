package Utils;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.List;

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
    
}
