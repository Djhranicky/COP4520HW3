# Birthday Presents

## Running Presents.java

To run Presents.java, navigate to the root directory in a terminal shell. Compile with 

    javac presents/Presents.java

and run with 

    java presents/Presents

Alternatively, use the shell script by running (use git bash or cygwin on windows)

    . runner.sh presents

## Proof of Correctness
Here, a functioning implementation of a lock-free concurrent linked list is used to ensure that all presents are accounted for and all thank you notes are written. 

The issue that the minotaur was most likely having previously was that a servant would add a node between nodes B and C and then another servant would delete node B. The first servant successfully connects B to the new node, and connects the new node to node C, while the second servant connects node A to node C, skipping over node B. Now the new node can not be reached from the beginning of the chain as node B is not being pointed to by another node and that's the only node pointing to the new node. 

With this implementation, situations like these are impossible as all of the methods are thread-safe.

The program might need to be ran multiple times to have the contains calls result in a success. To keep the size of the chain small, all servants just pull from the front of the chain. This in turn makes random contains calls unlikely to result in a success.

# Temperature Sensors

## Running Temperature.java

To run Temperature.java, navigate to the root directory in a terminal shell. Compile with

    javac temperature/Temperature.java

and run with

    java temperature/Temperature

Alternatively, use the shell script by running (use git bash or cygwin on windows)

    . runner.sh temp

## Proof of Correctness
There are three things to validate to be correct in the execution of the code: The 5 highest temperatures, the 5 lowest temperatures, and the largest temperature change.

The 5 highest temperatures are correct because the code checks every piece of data and only keeps the 5 highest values. If it finds a higher value as it is checking, it replaces the lowest value of the 5 with the higher value. A similar process takes place with the lowest values, ensuring the 5 lowest are kept.

The largest temperature change is found by finding all candidate temperature changes and selecting the largest. A candidate temperature change is the largest change in a given 10 minute interval. Two temperature changes are calculated for each interval with the larger being selected. One being the highest end temp minus the lowest start temp, and the other being the highest start temp minus the lowest end temp. All 100 of these (2 per interval, 50 intervals) are compared to find the highest one.