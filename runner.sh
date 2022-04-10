#!/bin/bash

if [ "$OSTYPE" == "linux-gnu" ]; then
    LIB=".:./lib/*"
elif [ "$OSTYPE" == "msys" ]; then
    LIB=".;./lib/*"
elif [ "$OSTYPE" == "cygwin" ]; then
    LIB=".;./lib/*"
fi

clean() {
    rm presents/*.class
    rm temperature/*.class
}
presents() {
    javac presents/Presents.java
    java presents/Presents
}
temp() {
    javac temperature/Temperature.java
    java temperature/Temperature
}

case "$1" in
    "presents"|"Presents")
        presents
        ;;

    "temp"|"Temp"|"temperature"|"Temperature")
        temp
        ;;

    "clean"|"Clean")
        clean
        ;;
    *)
        echo "Usage: . sh.sh <mode>"
        echo "Supported modes: presents and clean."
        ;;
esac