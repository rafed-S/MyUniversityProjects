#!/bin/bash

if [ ! -e sujet ]
then
    mkdir sujet
fi

if [ ! -e ressources ]
then
    mkdir ressources
fi

if [ ! -e data ]
then
    mkdir data
fi

#if [ -e *.pdf ]
#then
for fichierPdf in *.pdf
do
    mv "$fichierPdf" sujet
done
#fi

#if [ -e *.zip ]
#then
for fichierZip in *.zip
do
    mv "$fichierZip" ressources
done
#fi


for fichierImage in DATA*.jpg DATA*.png
do
    mv "$fichierImage" data
done
