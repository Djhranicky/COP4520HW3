package presents;

import java.util.Random;
import java.util.ArrayList;

public class Servants implements Runnable{

    ConcurrentLinkedList presents;
    ArrayList<Integer> bag;
    int numGifts;
    int threadNum;
    Random r;
    ArrayList<Integer> addedGifts = new ArrayList<>();
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
        int giftNum = ((numGifts/4)*threadNum);
        int lastGift = giftNum + (numGifts/4)-1;
        while(running){
            if((step+1) % CHECK_FREQ == 0){
                int check = r.nextInt(addedGifts.size());
                if(presents.contains(check)) {System.out.println("Is "+addedGifts.get(check)+" in the chain? Yes.");}
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
            } else if (giftNum < lastGift) {
                num = bag.get(giftNum);
                presents.add(num);
                addedGifts.add(num);
                giftNum++;
            }
            step++;
        }
    }
    
}
