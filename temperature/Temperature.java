package temperature;

import java.util.Scanner;
import java.util.Arrays;

public class Temperature {

    final static int NUM_THREADS = 8;
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int hours;
        double[][] data;
        double[][] extremes;
        double[] highest, lowest;
        Double maxInterval;
        
        System.out.println("How many hours?");
        hours = sc.nextInt();

        for(int hour = 0; hour < hours; hour++){
            data = new double[60][];
            extremes = new double[60][];
            highest = new double[5];
            lowest = new double[5];
            maxInterval = Double.MIN_VALUE;
            for(int i = 0; i < 60; i++){
                data[i] = new double[8];
                extremes[i] = new double[2];
            }

            Thread[] threads = new Thread[NUM_THREADS];
            for(int i = 0; i < NUM_THREADS; i++){
                threads[i] = new Thread(new Sensor(data, i));
                threads[i].start();
            }
            for(int i = 0; i < NUM_THREADS; i++){
                try{
                    threads[i].join();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            for(int i = 0; i < 60; i++){
                double[] minute = data[i];
                Arrays.sort(minute);
                for(int j = 0; j < 8; j++){
                    double cur = minute[j];
                    boolean lowSet = false;
                    boolean highSet = false;
                    for(int k = 0; k < 5; k++){
                        if(!lowSet && cur < lowest[k]){
                            lowest[k] = cur;
                            lowSet = true;
                        }
                        if(!highSet && cur > highest[4-k]){
                            highest[4-k] = cur;
                            highSet = true;
                        }
                    }
                }
                extremes[i][0] = minute[0];
                extremes[i][1] = minute[7];
            }

            for(int i = 0; i < 50; i++){
                Double interval1 = Math.abs(extremes[i][0] - extremes[i+10][1]);
                Double interval2 = Math.abs(extremes[i][1] - extremes[i+10][0]);
                maxInterval = (interval1 > maxInterval) ? interval1 : maxInterval;
                maxInterval = (interval2 > maxInterval) ? interval2 : maxInterval;
            }

            System.out.println("Hour "+(hour+1)+":");
            System.out.println("The highest 5 temperatures for hour "+(hour+1)+" were "+Arrays.toString(highest));
            System.out.println("The lowest 5 temperatures for hour "+(hour+1)+" were "+Arrays.toString(lowest));
            System.out.println("The largest temperature change over a 10 minute period was "+maxInterval);
            System.out.println("\n");
        }
        sc.close();
    }
}
