#!/bin/bash


SONAR_HOST=$1
SONAR_LOGIN=$2


for i in $(find -name target); do # Not recommended, will break on whitespace
    echo "Sending $i"
done
