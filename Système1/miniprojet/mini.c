#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h> 
#include <unistd.h> 
#include <time.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "projet_sys1_exo1.h"

void read_file(int input, void *buffer, size_t size)
{
    if (read(input, buffer, size) < 0)
    {
        printf("Erreur : read_file\n");
        exit(EXIT_FAILURE);
    }
}

int main(int argc, char ** argv){
    
    pid_t pid;
    int pf;
    int status;
    char* lettres ="abcdefghijklmnopqrstuvwxyz"; 
    
    /** read **/
    unsigned int fd_read;
    int size = (sizeof(char)*fd_read+1);
    char buf[size];
    
    fd_read = open("charabia.txt", O_RDONLY);
    if(fd_read == -1)
    {
        printf("Filed to open and read the file charabia\n");
        exit(1);
    }
    read_file(fd_read, buf, size);
    /***/
    
    char* read_lettre = (char*) malloc(sizeof(char)*fd_read+1);
    for(int i=0;i<26;i++){
        
         switch (pf = fork()){
            case -1:
                perror("error");
                exit(1);
        
            case 0:
                read_lettre[0] = lettres[i];
                read_lettre[1] = '\0';
                //execl("./fils" , "fils" ,argv[1] , read_lettre , NULL );
                execl("./fils" , "./fils" ,buf , read_lettre , NULL );
                perror("Erreur fils \n");                
                exit(0);
            
            default:
                //waitpid(pf,&status,0);
                if (waitpid(pf,&status,0) < 0) {
                    perror("waitpid");
                    exit(EXIT_FAILURE);
                }
                
        }
    }
    
     return EXIT_SUCCESS;
}
