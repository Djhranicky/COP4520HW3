// David Hranicky
// COP4520
package presents;

public class Presents{

    public static void main(String[] args){
        ConcurrentLinkedList presents = new ConcurrentLinkedList();

        int numServants = 4;
        Thread[] threads = new Thread[numServants];
        for(int i = 0; i < numServants; i++){
            threads[i] = new Thread(new Servants(presents));
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