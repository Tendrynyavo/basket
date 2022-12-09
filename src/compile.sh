#!/bin/bash

declare -a myArray
myArray=(`ls | grep -v "compile.sh"`)
for (( i = 0 ; i < ${#myArray[@]} ; i++))
do
    if [[ ${myArray[$i]} == *"java"* ]]; then
        javac -d /Users/tendrynyavo/Documents/ITUniversity/Programmation/apache-tomcat-10.0.20/webapps/Basket/WEB-INF/classes ./${myArray[$i]}
    else
        javac -d /Users/tendrynyavo/Documents/ITUniversity/Programmation/apache-tomcat-10.0.20/webapps/Basket/WEB-INF/classes ./${myArray[$i]}/*.java
    fi
done
