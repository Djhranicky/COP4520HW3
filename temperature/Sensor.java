package temperature;

import java.util.Random;

public class Sensor implements Runnable {
    
    // data: Stores the data gathered from the sensors
    // threadNum: Identify which thread this is
    // hours: Number of hours to run for
    // finished: Flags that the sensors set to notify the main thread that an hour has passed
    // r: Used to generate a random double
    // RANGE: Range of temperatures to measure
    // OFFSET: How much to offset the temperatures by
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

        // Run for the given number of hours
        for(int hour = 0; hour < hours; hour++){

            for(int i = 0; i < 60; i++){

                // Simulated minute
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Record data from the sensor
                r = new Random();
                data[i][threadNum] = ((r.nextDouble() * RANGE) + OFFSET);            
            }

            // Mark the hour as completed
            finished[hour][threadNum] = true;
        }
    }
}
