package presents;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Servants implements Runnable{

    ConcurrentLinkedList presents;
    ArrayList<Integer> bag;
    int numGifts;
    AtomicInteger TYnote;
    Random r;
    ArrayList<Integer> addedGifts = new ArrayList<>();
    final int CHECK_FREQ = 5000;

    public Servants(ConcurrentLinkedList presents, ArrayList<Integer> bag, int numGifts, AtomicInteger TYnote){
        this.presents = presents;
        this.bag = bag;
        this.numGifts = numGifts;
        this.TYnote = TYnote;
        this.r = new Random();
    }

    @Override
    public void run(){
        int step = 0;
        int num;
        boolean running = true;
        while(running){
            if((step+1) % CHECK_FREQ == 0){
                int check = r.nextInt(numGifts);
                if(presents.contains(check)) {System.out.println("Is "+check+" in the chain? Yes.");}
                else {System.out.println("Is "+check+" in the chain? No.");}
            }
            else if((step+1) % 5 == 0){
                if(addedGifts.isEmpty()){
                    running=false;
                    break;
                }
                int bleh = addedGifts.remove(0);
                presents.remove(bleh);
                if(presents.isEmpty()){
                    running=false;
                    break;
                }
            } else if (TYnote.get() < numGifts) {
                int giftNum = TYnote.getAndIncrement();
                if(giftNum >= numGifts) continue;
                num = bag.get(giftNum);
                presents.add(num);
                addedGifts.add(num);
            }
            step++;
        }
    }
    
}
