#!/bin/bash

declare -a myArray
myArray=(`ls | grep -v "compile.sh"`)
for (( i = 0 ; i < ${#myArray[@]} ; i++))
do
    if [[ ${myArray[$i]} == *"java"* ]]; then
        javac -d ../out ./${myArray[$i]}
    else
        javac -d ../out ./${myArray[$i]}/*.java
    fi
done
