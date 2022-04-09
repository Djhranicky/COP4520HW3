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
}
presents() {
    javac presents/Presents.java
    java presents/Presents
}

case "$1" in
    "presents"|"Presents")
        presents
        ;;

    "clean"|"Clean")
        clean
        ;;
    *)
        echo "Usage: . sh.sh <mode>"
        echo "Supported modes: presents and clean."
        ;;
esac