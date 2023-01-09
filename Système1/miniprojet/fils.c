#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <sys/types.h> 
#include <unistd.h> 
#include <time.h>
#include <fcntl.h>
#include <string.h>
#include <fcntl.h>
#include "projet_sys1_exo1.h"


void write_file(int file_output, void *buffer, size_t size)
{
    if (write(file_output, buffer, size) < 0)
    {
        printf("Erreur écriture\n");
        exit(EXIT_FAILURE);
    }
}

int open_file(char *path, int mode, void *mode2)
{
    int file = open(path, mode, mode2);
    if (file == -1)
    {
        printf("Erreur : Fichier introuvable: %s.\n", path);
        exit(EXIT_FAILURE);
    }
    return file;
}

int main(int argc, char ** argv){
    
    char* mes = malloc(600);
    char* new = malloc(600);
    char* res = malloc(600);
    float ind_res = 0.05;
    
    mes = argv[1];
    char c = *argv[2];
    char* cf_res;
    
    //char buff2[26];
    
    printf("\n");
    printf("- le fils : %d || le pere : %d \n",getpid(),getppid());
    printf("- ( %s ) \n",argv[2]);
    printf(" %s \n",argv[1]);
    
    for(int i=0;i<26;i++)
    {
        char* cf = &c;
        char cpt = 97 +i;
        char* t = &cpt;
        cf[1] = *t;
        
        decryptage(mes,new,cf,2);
        float indice = indice_de_coincidence(new);
        printf("indice = %f \n",indice);
        
        if(((0.0746 - indice) < (0.0746-ind_res)))
        {
            ind_res = indice;
            res = strdup(new);
            cf_res = strdup(cf);
            printf("ind_res = %f \n",ind_res);
            //buff2[i] = cf_res;
            //printf("cf_res = %s \n",buff2[i]);
        }
    }
    
    /*ecriture*/
    
    char fd[30] = "save/res_";
    fd[9] = c;
    strcat(fd,".txt");
    struct stat s;

    int fd2 = open_file(fd, O_CREAT | O_WRONLY | O_TRUNC, 0777);
    //int fd2 = open_file(fd, O_CREAT | O_WRONLY | O_TRUNC);
    char buffer[10];
    sprintf(buffer,"%f",ind_res);
    write_file(fd2, &buffer,strlen(buffer));
    /*
    for(int l=0;l<26;l++)
    {
        write_file(fd2, &buff2[l],strlen(buffer));
    }
    */
   
    return EXIT_SUCCESS;
}
