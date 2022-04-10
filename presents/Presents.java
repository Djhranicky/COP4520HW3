// David Hranicky
// COP4520
package presents;

import java.util.ArrayList;
import java.util.Collections;

public class Presents{

    public static void main(String[] args){
        ConcurrentLinkedList presents = new ConcurrentLinkedList();
        
        int numGifts = 500000;
        ArrayList<Integer> bag = new ArrayList<Integer>();
        for(int i = 0; i < numGifts; i++){
            bag.add(i);
        }
        Collections.shuffle(bag);

        int numServants = 4;
        Thread[] threads = new Thread[numServants];
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
        System.out.println(presents.toString());
    }
}