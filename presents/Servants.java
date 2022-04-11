package presents;

import java.util.Random;
import java.util.ArrayList;
import presents.ConcurrentLinkedList.Node;

public class Servants implements Runnable{
    // presents: The chain that the servants add the presents to
    // bag: Unordered bag the servants pull from
    // numGifts: Number of gifts the Minotaur received
    // threadNum: Number to ID which thread this is
    // r: Used to generate random number to check for in the chain
    ConcurrentLinkedList presents;
    ArrayList<Integer> bag;
    int numGifts;
    int threadNum;
    Random r;
    final int CHECK_FREQ = 10000;

    public Servants(ConcurrentLinkedList presents, ArrayList<Integer> bag, int numGifts, int threadNum){
        this.presents = presents;
        this.bag = bag;
        this.numGifts = numGifts;
        this.threadNum = threadNum;
        this.r = new Random();
    }

    @Override
    public void run(){
        int step = 0;
        int num;
        boolean running = true;

        // Divides the bag into fourths for each servant to pull from
        int giftNum = ((numGifts/4)*threadNum);
        int lastGift = giftNum + (numGifts/4)-1;

        while(running){
            // Calls a contains every CHECK_FREQ number of steps
            if((step+1) % CHECK_FREQ == 0){
                int check = r.nextInt(numGifts);
                if(presents.contains(check)) {System.out.println("Is "+check+" in the chain? Yes.");}
                else {System.out.println("Is "+check+" in the chain? No.");}
            }
            // Alternates between adding and removing values from the list
            if((step+1) % 2 == 0){
                Node bleh = presents.poll();
                if(bleh == null){
                    continue;
                }
                presents.remove(bleh.key);
            } else {
                if(giftNum>lastGift){
                    step++;
                    continue;
                }
                num = bag.get(giftNum);
                presents.add(num);
                giftNum++;
            }
            // Stops running when all presents have been added and the list is empty
            if(giftNum > lastGift && presents.isEmpty()){
                running=false;
                break;
            }
            step++;
        }
    }
    
}
