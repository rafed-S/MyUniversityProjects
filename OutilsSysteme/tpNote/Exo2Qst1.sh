#!/bin/bash


awk -F';' 'BEGIN{ nb=0;sum=0 } $0 ~/Volvo/ { print $0 ; nb++;sum+=$5 } END{ print "Moyenne=" sum/FNR }' cars.csv
