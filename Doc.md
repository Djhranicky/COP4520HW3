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

With this implementation, this is impossible as all of the methods are thread-safe and prevent situations like the one above from happening

# Temperature Sensors

## Running Temperature.java

To run Temperature.java, navigate to the root directory in a terminal shell. Compile with

    javac temperature/Temperature.java

and run with

    java temperature/Temperature

Alternatively, use the shell script by running (use git bash or cygwin on windows)

    . runner.sh temp

## Proof of Correctness
There are three things to ensure are correct in the execution of the code: The 5 highest temperatures, the 5 lowest temperatures, and the largest temperature change.

The 5 highest temperatures is correct because the code checks every piece of data and only keeps the 5 highest values. If it finds a higher value as it is checking, it removes the lowest value and adds that one in instead. A similar process takes place with the lowest values, ensuring the 5 highest are kept.