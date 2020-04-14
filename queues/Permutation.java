import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args){
        int noOfStrings = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();
        for(int i =0;i<noOfStrings;i++){
            randQ.enqueue(StdIn.readString());
        }

        for(String val : randQ){
            StdOut.println(val);
        }   

    }
 }