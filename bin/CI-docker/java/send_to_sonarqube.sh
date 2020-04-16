#!/bin/bash


SRC=$1
SONAR_HOST=$2
SONAR_LOGIN=$3


cd $SRC

for i in $(find . -name target); do # Not recommended, will break on whitespace
    echo "Sending $i"
done
