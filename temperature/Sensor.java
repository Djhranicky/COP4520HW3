package temperature;

import java.util.Random;

public class Sensor implements Runnable {
    
    double[][] data;
    int threadNum;
    Random r;
    final int RANGE = 170;
    final int OFFSET = -100;

    public Sensor(double[][] data, int threadNum){
        this.data = data;
        this.threadNum = threadNum;
    }

    @Override
    public void run(){
        for(int i = 0; i < 60; i++){
            r = new Random();
            data[i][threadNum] = ((r.nextDouble() * RANGE) + OFFSET);
        }
    }
}
