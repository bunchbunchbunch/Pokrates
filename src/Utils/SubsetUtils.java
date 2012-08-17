package Utils;


import java.util.ArrayList;
import java.util.List;

/**
 * User: jobunch
 * Date: 8/14/12
 */
public class SubsetUtils {
    
    public static <T extends Comparable> List<List<T>> subset(List<T> inList, int size) {
        List<List<T>> rval = new ArrayList<List<T>>();
        if(size == 0) return rval;
        for(T value : inList) {
            List<T> toAdd = new ArrayList<T>();
            toAdd.add(value);
            rval.add(toAdd);
        }
        while(rval.get(0).size() < size) {
            List<T> temp = rval.get(0);
            rval.remove(0);
            for(T value : inList) {
                if(temp.get(temp.size()-1).compareTo(value) < 0) {
                    List<T> toAdd = new ArrayList<T>(temp);
                    toAdd.add(value);
                    rval.add(toAdd);
                }
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
