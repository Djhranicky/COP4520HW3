import java.util.Random;

public class Servants implements Runnable{

    ConcurrentLinkedList presents;

    public Servants(ConcurrentLinkedList presents){
        this.presents = presents;
    }

    @Override
    public void run(){
        Random r = new Random();
        int bound = 100;
        for(int i = 0; i < 10; i++){
            int num = r.nextInt(bound);
            System.out.println("Print number "+i+", adding "+num);
            presents.add(num);
        }
        for(int i = 0; i < 100; i++){
            presents.remove(i);
        }
    }
    
}
