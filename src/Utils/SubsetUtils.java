package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jobunch
 * Date: 8/14/12
 */
public class SubsetUtils {
    
    public static <T> List<List<T>> subset(List<T> inList, int size) {
        List<List<T>> rval = new ArrayList<List<T>>();
        for(int num = 0; num < Math.pow(2, inList.size()); num++) {
            if(size == onesInBaseTwo(num)) {
                List<T> row = new ArrayList<T>();
                int tempNum = num;
                int place = 0;
                while( tempNum > 0) {
                    if(tempNum%2 == 1) {
                        row.add(inList.get(place));
                    }
                    place++;
                    tempNum /= 2;
                }
                rval.add(row);
            }
        }
        return rval;
    }

    public static int onesInBaseTwo(int in) {
        if(in <= 0) return 0;
        return in % 2 ==0 ? onesInBaseTwo(in/2) : onesInBaseTwo(in/2) + 1;
    }

    public static void main(String args[] ) {
        List<Integer> test = new ArrayList<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(5);
        List<List<Integer>> subsets = subset(test, 2);
        System.out.println(subsets);
    }
}
