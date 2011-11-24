package HandEvaluator;


import java.io.*;

public class TextEditor {

    public static void main(String args[]) throws IOException, UnsupportedEncodingException {
        FileInputStream fis = new FileInputStream("/Users/jobunch/workspace/Pokrates/resources/PokerEquivalenceClassesE.txt.bak");
        InputStreamReader in = new InputStreamReader(fis, "UTF-8");
        BufferedReader reader = new BufferedReader(in);

        FileOutputStream fos = new FileOutputStream("/Users/jobunch/workspace/Pokrates/resources/PokerEquivalenceClasses.txt");
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
        String line = "first";
        while(line != null) {
            line = reader.readLine();
            try {
                String[] tokens = line.split(",");
//                int number = 1;
//                number *= Primes.getPrime(Integer.valueOf(tokens[5]));
//                number *= Primes.getPrime(Integer.valueOf(tokens[6]));
//                number *= Primes.getPrime(Integer.valueOf(tokens[7]));
//                number *= Primes.getPrime(Integer.valueOf(tokens[8]));
//                number *= Primes.getPrime(Integer.valueOf(tokens[9]));
//                out.write(line + "," + number + "\n");
                for(int index = 0; index < tokens.length; index++) {
                    if(index < 5)
                        out.write(tokens[index] + ",");
                    else if (index == tokens.length-1)
                        out.write(tokens[index] + "\n");
                    else if (index > 9) {
                        out.write(tokens[index] + ",");
                    }
                    else {
                        out.write((Integer.valueOf(tokens[index])-1) + ",");
                    }
                }
//                if(line.contains("Flush"))
//                out.write(line+",1\n");
//                else
//                    out.write(line+",0\n");
//                if(line.charAt(0)==',')
//                    out.write(line.substring(1)+"\n");
//                else
//                    out.write(line+"\n");
            } catch (Exception e) {

            }
        }
        out.flush();
        out.close();
    }
}
