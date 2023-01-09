#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <unistd.h>
#include "mes_semaphores.h"


#define TAILLE 1024
#define TAILLE_NB_LETTRE 26

/* Fonction d'�critue de l'histogramme dans un fichier */
void ecrireFichier(char *nomFichier, long int cpt, int h[]) {

  FILE *fichOut ;
  int j ;


  fichOut = fopen(nomFichier, "w") ;
  if (!fichOut) {
    printf("erreur � l'ouverture du fichier %s\n", nomFichier) ;
    exit(2) ;
  }

  //printf("�criture du fichier %s\n", nomFichier) ;
  fprintf(fichOut, "nombre de caract�res lus : %ld\n", cpt) ;
  for (j = 0 ; j < 26 ; j++) {
    fprintf(fichOut, "%c : %d\n", j + 'a', h[j]) ;
  }
  fclose(fichOut) ;
}

/********************
*
* Calcul de la fr�quence d'apparition des lettres dans un fichier texte.
* (g�n�ration de fichiers de progression)
*
******************/


int main(int argc, char* argv[])
{

  FILE *fichierCorpus ;
  char nomFichierGenere[100] ;
  int i, car, nbLignes, cptFichier ;
  long int nbCar ;
  char chaine[TAILLE] ;
  int nbLignesFichier=0;

  int debut = atoi(argv[3]);
  int nomLignesTraiter = atoi(argv[4]);
  int numLigne=debut;

  /* Tableau pour l'histogramme*/
  int histo[TAILLE_NB_LETTRE] = {0} ;


  /******/

    int *tableauPartagee;
    int mem_id;

    key_t key;
    char *path = "TP5.c";
    int id = 0;
    key = ftok(path, id);

    // Création d'un segment de memoire partagée privé
    mem_id = shmget(key, TAILLE_NB_LETTRE, IPC_CREAT | 0660);
    if (mem_id == -1)
    {
        perror("Erreur de création du segment de memoire partagé");
        exit(1);
    }

    // Attachement du segment pour pouvoir l'utiliser
    tableauPartagee = (int *)shmat(mem_id, NULL, 0);
    if (tableauPartagee == NULL)
    {
        perror("Erreur d'attachement au segment de memoire partagé");
        exit(1);
    }
  /******/



  if (argc != 5) {
    printf("usage : %s <fichier � traiter> <nom g�n�rique fichiers r�sultats>\n", argv[0]) ;
    exit(1) ;
  }

  fichierCorpus = fopen(argv[1], "r") ;
  if (!fichierCorpus) {
    printf("erreur � l'ouverture du fichier %s\n", argv[1]) ;
    exit(2) ;
  }

  while (fgets(chaine, TAILLE, fichierCorpus)) {
    nbLignesFichier++;
  }

  fseek(fichierCorpus, 0, SEEK_SET); //on se replace au d�but du fichier

  nbCar = 0 ;
  nbLignes = 0 ;
  cptFichier = 0 ;
  /* Traitement ligne par ligne */
  while (fgets(chaine, TAILLE, fichierCorpus)) {
    if( (nbLignes>=debut &&  numLigne != nomLignesTraiter) || nomLignesTraiter==0 ){
	/* D�compte des lettres (minuscules)*/
      for (i = 0 ; i < strlen(chaine) ; i++) {
          car = chaine[i] - 'a' ;
          if ((car >= 0) && (car < 26)) {
              histo[car] ++ ;
              nbCar++ ;
          }
      }

    }
    numLigne++;

	/* G�n�ration du fichier de d�compte */
	nbLignes++ ;
	if (nbLignes % 10000 == 0) {
		sprintf(nomFichierGenere, "%s_%d_%d.txt", argv[2], getpid(), cptFichier) ;
		ecrireFichier(nomFichierGenere, nbCar, histo) ;
		printf(".");fflush(stdout);
		cptFichier++ ;
	}
  }

  /******/
  for(int i=0;i<26;i++){
    tableauPartagee[i] = histo[i];
    printf("%d \n", tableauPartagee[i]);
  }
  /******/



  printf("\n");

  /* Ecriture dans le dernier fichier */
  sprintf(nomFichierGenere, "%s_%d_final.txt", argv[2], getpid()) ;
  ecrireFichier(nomFichierGenere, nbCar, histo) ;
  printf("Dernier fichier g�n�r� : %s [nombre de caract�res trait�s : %ld, nb lignes trait�es : %d sur %d]\n", nomFichierGenere, nbCar, nbLignes, nbLignesFichier) ;


  fclose(fichierCorpus) ;
  return 0 ;
}
