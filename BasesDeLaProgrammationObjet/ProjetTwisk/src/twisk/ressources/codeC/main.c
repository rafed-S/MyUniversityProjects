
#include <stdio.h>
#include <stdlib.h>
#include "def.h"
#define nbEtapes 7
#define nbClients 6
#define nbGuichets 2


int main(int argc, char ** argv){

int  tabJetonsGuichet[nbGuichets];
int* results;
for(int i=0;i<nbGuichets;i++)
{
tabJetonsGuichet[i] = i+3;
}

results = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);
printf("Les clients : ");
for(int i=0;i<nbClients;i++)
{
    printf(" %d",results[i]);
    if(i<1) printf(",");
}
printf("\n");
int* nb;
int d = 0;
do {

nb = ou_sont_les_clients(nbEtapes, nbClients);
sleep(2);
 d = 0;

for(int j = 0 ; j < nbEtapes ; j++)
{
        printf("Etape  %d : %d Client  : ",j,nb[d]);
for(int i=d+1 ; i<d+7 ; i++)
{
        printf("%d, ",nb[i]);

}

d = d + 7;
printf("\n");
}
printf("\n");

} while (nb[nbClients * nbEtapes] != 6) ;
printf("-----------------------");

d = 0;

printf("\n");
nettoyage();

return 0;

}


//export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.
//export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib