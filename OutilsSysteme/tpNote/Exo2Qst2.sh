#!/bin/bash

touch "cars_Europe.tsv"

touch "cars_Japan.tsv"

echo -e "\nQuelle origine ?"

read from

if [[ $from == "Japan" ]]; then
    awk -F';' '$9 ~/Japan/ ' cars.csv >> cars_Japan.tsv
elif [[ $from == "Europe" ]]; then
    awk -F';' '$9 ~/Europe/ ' cars.csv >> cars_Europe.tsv
fi
