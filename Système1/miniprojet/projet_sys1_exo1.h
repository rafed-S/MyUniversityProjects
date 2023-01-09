#ifndef PROJET_SYS1_EXO1
#define PROJET_SYS1_EXO1

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/*************************/


float indice_de_coincidence(char* text);

void cryptage(char* message, char* new_mess, char* clef, int tailleClef);

void decryptage(char* message, char* new_mess, char* clef, int tailleClef);

/*************************/

#endif
