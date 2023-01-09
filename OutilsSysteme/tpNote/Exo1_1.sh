#!/bin/bash

echo "les fichier bash sont : " >> liste.txt
for fichier in *
do
    a='head -c 11 $fichier'
    if [[ $a == "#!/bin/bash" ]]; then
	    echo "fichier bash trouvé"
	    echo "$fichier" >> liste.txt
    fi
    
done



echo "les fichier jpg sont : " >> liste.txt

for fichier in *
do
    a=`head -c 4 $fichier`
echo "$a"
    if [[ $a == "JFIF" ]]; then
	    echo "fichier jpg trouvé"
	    echo "$fichier" >> liste.txt
    fi
    
done


echo "les fichier pdf sont : " >> liste.txt 
for fichier in *
do
    a='head -c 4 $fichier'
    if [[ $a = "%PDF" ]]; then
	    echo "fichier pdf trouvé"
	    echo "$fichier" >> liste.txt
    fi
    
done
