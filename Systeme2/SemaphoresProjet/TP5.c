#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <sys/sem.h>
#include "mes_semaphores.h"
#define TAILLE 1024
#define TAILLE_TABLEAU 26


long double nombre_lettre_lue(int *tableau, int taille){
    long double t = 0;
    for (int i = 0; i < taille; i++)
    {
        t += tableau[i];
    }
    return t;
}

void affichage_pourcentage(int *tableau, int taille){
    char lettre = 'a';
    long double nbChar = nombre_lettre_lue(tableau, TAILLE_TABLEAU);
    for (int i = 0; i < TAILLE_TABLEAU; i++)
    {
        printf("%c : %Lf %% \n", lettre+i, ((double)tableau[i] * 100)/nbChar);
        fflush(stdout);
    }
}

int main(int argc, char const *argv[])
{

    //**** creer tableau Partage ****//
    char strNbLignes[100], strDebut[100], chaine[TAILLE];
    int* tableauPartage = NULL;
    int semid, memid, nbFils, nbLignes = 0, div, reste;
    int id = 0;
    char *path = "TP5.c";
    key_t key = ftok(path, id);
    FILE *fichier ;


    // Vérification des paramètres
    if (argc!=3)
    {
        printf("usage : %s <nom_fichier> <nombre de fils>\n", argv[0]);
        fflush(stdout);
        exit(EXIT_FAILURE);
    } else if (atoi(argv[2])<=0)
    {
        printf("Le nombre de fils doit être supérieur à 0 !\nusage : %s <nom_fichier> <nombre de fils>\n", argv[0]);
        fflush(stdout);
        exit(EXIT_FAILURE);
    }


    // **** Recup nb de fils ****//
    nbFils = atoi(argv[2]);

    //**** creer tableau Partage ****//
    memid = shmget(key, sizeof(int)*TAILLE_TABLEAU, IPC_CREAT|0660|IPC_EXCL);
    if (memid==-1){
        perror("Erreur creation segment memoire partagé");
        exit(EXIT_FAILURE);
    }

    // Attachement du segment au pere
    tableauPartage = shmat(memid, NULL, 0);
    if(tableauPartage == NULL){
        perror("Exe : Erreur d'attachement au segment memoire partagé");
        exit(EXIT_FAILURE);
    }

    // Initialisation des valeurs du segment de mémoire partagée
    for (int i = 0; i < TAILLE_TABLEAU; i++)
    {
        tableauPartage[i] = 0;
    }

    // Création du sémaphore
    if(sem_creation(&semid, 1)==-1){
        perror("Exe : Erreur lors de la création des sémaphores A\n");
        exit(EXIT_FAILURE);
    }

    // Initialisation du sémaphore
    if (sem_initialisation(semid, 0, 1)==-1)
    {
        perror("Exe : Erreur lors de l'initialisation du sémaphore A");
        sem_destruction(semid);
        exit(EXIT_FAILURE);
    }

    // Ouverture du fichier
    fichier = fopen(argv[1], "r") ;
    if (!fichier) {
        printf("erreur a l'ouverture du fichier %s\n", argv[1]) ;
        exit(2) ;
    }

    // Calcul nombre de lignes du fichier
    while (fgets(chaine, TAILLE, fichier)) {
        nbLignes++;
    }
    fclose(fichier);

    //**** Calcul pour chaque fils de son depart et du nb ligne a lire ****//
    div = nbLignes / nbFils;
    reste = nbLignes % nbFils;
    int debut[nbFils];
    int ligneALire[nbFils];
    for (int i = 0; i < nbFils; i++)
    {
        if (i==0)
        {
            debut[i] = 0;
            if (reste >0)
            {
                ligneALire[i] = div+1;
            } else {
                ligneALire[i] = div;
            }
        } else {
            debut[i] = debut[i-1]+ligneALire[i-1];
            if (reste>0) {
                ligneALire[i] = div+1;
            } else {
                ligneALire[i] = div;
            }
        }
        reste--;
    }

    // Création des fils
    pid_t fils[nbFils];
    for (int i = 0; i < nbFils; ++i)
    {
        fils[i] = fork();
        if (fils[i]==-1)
        {
            perror("Erreur lors de la creation du fils");
            fflush(stdout);
            exit(EXIT_FAILURE);
        } else if(fils[i] == 0){
            sprintf(strDebut, "%d", debut[i]);
            sprintf(strNbLignes, "%d", ligneALire[i]);
            execl("./LectureCorpusLettre", "LectureCorpusLettre", argv[1], "tp", strDebut, strNbLignes, NULL);
        }
    }

    // Attente de la fin des fils
    for (int i = 0; i < nbFils; ++i)
    {
        wait(&fils[i]);
    }

    // Affichage des occupences en pourcentage
    affichage_pourcentage(tableauPartage, TAILLE_TABLEAU);

    // Détachement et destruction du segment de mémoire partagé
    shmdt(tableauPartage);
    if(shmctl(memid, IPC_RMID, 0)==-1){
        perror("Erreur lors de la suppression du segment");
        exit(EXIT_FAILURE);
    }

    // On détruit les sémaphores
    sem_destruction(semid);
    return 0;
}
