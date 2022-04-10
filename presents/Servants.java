package presents;

import java.util.Random;
import java.util.ArrayList;
import presents.ConcurrentLinkedList.Node;

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
                int check = r.nextInt(numGifts);
                if(presents.contains(check)) {System.out.println("Is "+check+" in the chain? Yes.");}
                else {System.out.println("Is "+check+" in the chain? No.");}
            }
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
            if(giftNum > lastGift && presents.isEmpty()){
                running=false;
                break;
            }
            step++;
        }
    }
    
}
