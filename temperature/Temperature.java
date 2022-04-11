package temperature;

import java.util.Scanner;
import java.util.Arrays;

public class Temperature {

    final static int NUM_THREADS = 8;
    
    public static void main(String[] args){
        // sc: Read input from terminal
        // hours: Number of hours to read temperatures for
        // data: Shared resource that the sensors write the data to
        // extremes: Gathers the highest and lowest temperatures each sensor recorded for each minute
        // highest: Array containing the 5 highest temperatures
        // lowest: Array containing the 5 lowest temperatures
        // maxInterval: The largest temperature change over any 10 minute period
        // finished: Array of flags for the sensors to tell the robot when an hour has passed
        Scanner sc = new Scanner(System.in);
        int hours;
        double[][] data;
        double[][] extremes;
        double[] highest, lowest;
        Double maxInterval;
        boolean[][] finished;
        
        System.out.println("How many hours?");
        hours = sc.nextInt();

        // Initializing the variables
        data = new double[60][];
        extremes = new double[60][];
        highest = new double[5];
        lowest = new double[5];
        maxInterval = Double.MIN_VALUE;
        for(int i = 0; i < 60; i++){
            data[i] = new double[8];
            extremes[i] = new double[2];
        }

        // Start the threads
        finished = new boolean[hours][NUM_THREADS];
        Thread[] threads = new Thread[NUM_THREADS];
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i] = new Thread(new Sensor(data, i, hours, finished));
            threads[i].start();
        }

        int hour = 0;

        // Run while at least one sensor is still working
        while(!finished[hours-1][0] || 
        !finished[hours-1][1] ||
        !finished[hours-1][2] ||
        !finished[hours-1][3] ||
        !finished[hours-1][4] ||
        !finished[hours-1][5] ||
        !finished[hours-1][6] ||
        !finished[hours-1][7]){

            // Wait for the sensors to gather the data for each hour
            while(!finished[hour][0] ||
            !finished[hour][1] ||
            !finished[hour][2] ||
            !finished[hour][3] ||
            !finished[hour][4] ||
            !finished[hour][5] ||
            !finished[hour][6] ||
            !finished[hour][7]){
                System.out.print("");
            }
            // Data gethered, calulate the report
            long start = System.currentTimeMillis();

            // Search through each minute
            for(int i = 0; i < 60; i++){

                double[] minute = data[i];
                Arrays.sort(minute);

                // Search through data gethered for each minute
                for(int j = 0; j < 8; j++){
                    double cur = minute[j];

                    // Check if any data is missing
                    if(cur == 0.0){
                        System.out.println("SENSOR DATA WAS MOST LIKELY NOT PROPERLY SAVED");
                    }

                    // Replace highest low value if a new low value is found
                    if(cur < lowest[4]){
                        lowest[4] = cur;
                        Arrays.sort(lowest);
                    }

                    // Replace lowest high value if a new high value is found
                    if(cur > highest[0]){
                        highest[0] = cur;
                        Arrays.sort(highest);
                    }
                }

                // Capture the extremes of each minute
                extremes[i][0] = minute[0];
                extremes[i][1] = minute[7];
            }

            // Calculate all 10 minute intervals, save the highest
            for(int i = 0; i < 50; i++){
                Double interval1 = Math.abs(extremes[i][0] - extremes[i+10][1]);
                Double interval2 = Math.abs(extremes[i][1] - extremes[i+10][0]);
                maxInterval = (interval1 > maxInterval) ? interval1 : maxInterval;
                maxInterval = (interval2 > maxInterval) ? interval2 : maxInterval;
            }

            // Generate report
            System.out.println("Hour "+(hour+1)+":");
            System.out.println("The highest 5 temperatures for hour "+(hour+1)+" were "+Arrays.toString(highest));
            System.out.println("The lowest 5 temperatures for hour "+(hour+1)+" were "+Arrays.toString(lowest));
            System.out.println("The largest temperature change over a 10 minute period was "+maxInterval);

            // Zero out data for the next hour
            highest = new double[5];
            lowest = new double[5];
            maxInterval = Double.MIN_VALUE;
            for(int i = 0; i < 60; i++){
                data[i] = new double[8];
                extremes[i] = new double[2];
            }

            long end = System.currentTimeMillis();

            System.out.println("The report was generated in "+(end-start)+" milliseconds");
            System.out.println("");

            hour++;
        }

        for(int i = 0; i < NUM_THREADS; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
