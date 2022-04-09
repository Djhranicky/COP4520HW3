#!/bin/sh

if [ "$OSTYPE" == "linux-gnu" ]; then
    LIB=".:./lib/*"
elif [ "$OSTYPE" == "msys" ]; then
    LIB=".;./lib/*"
elif [ "$OSTYPE" == "cygwin" ]; then
    LIB=".;./lib/*"
fi

clean() {
    rm *.class
}
presents() {
    javac Presents.java
    java Presents
}
gui() {
    javac -cp $LIB src/gui/GUI.java
    java -cp $LIB src/gui/GUI
}

case "$1" in
    "presents"|"Presents")
        presents
        ;;

    "gui"|"Gui"|"GUI")
        gui
        ;;

    "clean"|"Clean")
        clean
        ;;
    *)
        echo "Usage: . sh.sh <mode>"
        echo "Supported modes: presents, gui, and clean."
        ;;
esac