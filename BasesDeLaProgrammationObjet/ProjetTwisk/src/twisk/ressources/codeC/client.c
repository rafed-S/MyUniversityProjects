#include <stdio.h>
#include <stdlib.h>
#include "def.h"
#define SASENTREE 0
#define Guich1 1
#define actReistreint1 2
#define act2 3
#define Guich2 4
#define actReistreint2 5
#define SASSORTIE 6

void simulation(int ids)
{

entrer(SASENTREE);
delai(4,3);
transfert(SASENTREE,Guich1);
P(ids, 1) ;
transfert(Guich1, actReistreint1);
delai(6,3);
V(ids, 1) ;
transfert(actReistreint1,act2);
delai(4,3);
transfert(act2,Guich2);
P(ids, 2) ;
transfert(Guich2, actReistreint2);
delai(6,3);
V(ids, 2) ;
transfert(actReistreint2,SASSORTIE);

}
