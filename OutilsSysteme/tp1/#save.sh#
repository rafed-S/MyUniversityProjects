#!/bin/bash

fichier_svrd=0
fichier_vus=0

if [ "$1" == "-b" ]
then
    if [ ! -e "$2_SAVE" ]
    then
	mkdir "$2_SAVE"
    fi
    
    for fichier in `ls $2_SAVE`
    do
        rm "$2_SAVE/$fichier"
    done

    for fichier in `ls $2`
    do
    let fichier_vus=$fichier_vus+1
    let fichier_svrd=$fichier_svrd+1
    
	taille=`stat -c %s "$2/$fichier"`
	if [ "$taille" -gt 1000000 ]
	then
	    echo "Le fichier $fichier dépasse 1 MO, êtes-vous sûr de vouloir le copier dans le dossier $2 ? (y/n) "
	    read choix
	    if [ "$choix" == 'y' ]
        then
            cp "$2/$fichier" "$2_SAVE"
	    else
           let fichier_svrd=$fichier_svrd-1
        fi
	else
	    cp "$2/$fichier" "$2_SAVE"
	fi
    done
    
elif [ "$1" == "-h" ]
then
    if [ ! -e ".$2_SAVE" ]
    then
	mkdir ".$2_SAVE"
    fi
    
    for fichier in `ls .$2_SAVE`
    do
        rm ".$2_SAVE/$fichier"
    done

    for fichier in `ls $2`
    do
    let fichier_vus=$fichier_vus+1
    let fichier_svrd=$fichier_svrd+1
    
	taille=`stat -c %s "$2/$fichier"`
	if [ "$taille" -gt 1000000 ]
	then
	    echo "Le fichier $fichier dépasse 1 MO, êtes-vous sûr de vouloir le copier dans le dossier $2 ? (y/n) "
	    read choix
	    if [ "$choix" == 'y' ]
       	    then
		cp "$2/$fichier" ".$2_SAVE"
	    else
           let fichier_svrd=$fichier_svrd-1
        fi
	else
	    cp "$2/$fichier" ".$2_SAVE"
	fi
    done
    
else
    if [ ! -e "$1_SAVE" ]
    then
	mkdir "$1_SAVE"
    fi
    
    for fichier in `ls $1`
    do
	let fichier_vus=$fichier_vus+1
	if [ "$1/$fichier" -nt "$1_SAVE/$fichier" ]
	then
        let fichier_svrd=$fichier_svrd+1
        
	    taille=`stat -c %s "$1/$fichier"`
	    if [ taille -gt 1000000 ]
	    then
            echo "Le fichier $fichier dépasse 1 MO, êtes-vous sûr de vouloir le copier dans le dossier $2 ? (y/n) "
            read choix
            if [ "$choix" == "y" ]
            then
                cp "$1/$fichier" "$1_SAVE"
            else
                let fichier_svrd=$fichier_svrd-1
            fi
	    fi
	fi
    done
fi

if [ "$fichier_svrd" -lt 0 ]
then
    fichier_svrd=0
fi

echo "$fichier_svrd fichiers ont ete sauvegardes"
echo "$fichier_vus fichiers ont ete vus"
