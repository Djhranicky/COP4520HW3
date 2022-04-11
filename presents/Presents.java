// David Hranicky
// COP4520
package presents;

import java.util.ArrayList;
import java.util.Collections;

public class Presents{

    public static void main(String[] args){
        // presents: The chain that the servants add the presents to
        // numGifts: Number of gifts the Minotaur received
        // bag: Unordered bag the servants pull from
        // numServants: Number of servants
        // threads: Array of threads to spawn the servants
        ConcurrentLinkedList presents = new ConcurrentLinkedList();
        int numGifts = 500000;
        ArrayList<Integer> bag;
        int numServants;
        Thread[] threads;

        // Create the unordered bag to draw from
        bag = new ArrayList<Integer>();
        for(int i = 0; i < numGifts; i++){
            bag.add(i);
        }
        Collections.shuffle(bag);

        numServants = 4;
        threads = new Thread[numServants];
        for(int i = 0; i < numServants; i++){
            threads[i] = new Thread(new Servants(presents, bag, numGifts, i));
            threads[i].start();
        }
        for(int i = 0; i < numServants; i++){
            try{
                threads[i].join();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        // Print out the final state of the chain for verification
        System.out.println(presents.toString());
    }
}