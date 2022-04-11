package temperature;

import java.util.Random;

public class Sensor implements Runnable {
    
    double[][] data;
    int threadNum;
    int hours;
    boolean[][] finished;
    Random r;
    final int RANGE = 170;
    final int OFFSET = -100;

    public Sensor(double[][] data, int threadNum, int hours, boolean[][] finished){
        this.data = data;
        this.threadNum = threadNum;
        this.hours = hours;
        this.finished = finished;
    }

    @Override
    public void run(){
        for(int hour = 0; hour < hours; hour++){
            for(int i = 0; i < 60; i++){
                // Simulated minute
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r = new Random();
                data[i][threadNum] = ((r.nextDouble() * RANGE) + OFFSET);            
            }
            finished[hour][threadNum] = true;
        }
    }
}
