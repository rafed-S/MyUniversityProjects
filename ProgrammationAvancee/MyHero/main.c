/**
 *\file main.c
 *\brief Programme principal du jeu.
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#include "init_jeu.h"
#include "jeu.h"
#include "keyboard_event.h"
#include "screen.h"

//Programme principal qui implémente la boucle du jeu.
int main(int argc, char* argv[])
{
    
    SDL_Renderer *renderer = init_sdl();
    SDL_Event event;
    jeu_t jeu;
    jeu.level =1;
    
    printf("  \n");
    printf(" ---- Gooo my hero --- !!! \n");
    
    while(jeu.level == 1){
        //Initialisation du jeu :
        init_jeu(&jeu, renderer);
        while(!jeu.endgame){
            handle_events(&jeu, &event);
            jeu_update(&jeu);
            refresh_screen(&jeu, renderer);
            SDL_Delay(10);
            kill_enemy(&jeu, renderer);
            minus_hp(&jeu, renderer);
        }
    }
    printf(" ---- Level 2 --- !!! \n");
    if(jeu.level == 2)
    {
        //Initialisation du jeu :
        init_jeu(&jeu, renderer);
        while(!jeu.endgame){
            //Gestion des évènements :
            handle_events(&jeu, &event);
            //Mise à jour des données
            jeu_update(&jeu);
            refresh_screen(&jeu, renderer);
            //Pause
            SDL_Delay(10);
            kill_enemy(&jeu, renderer);
            minus_hp(&jeu, renderer);
        }
    }
    
    if(jeu.victory)
    {
        printf(" ---- you win --- !!! \n");
    }else{
        printf(" ---- try again --- !!! \n");
    }
    
    //Nettoyage final :
    screen_end(&jeu, renderer);
    free_jeu(&jeu, renderer);
    SDL_Quit();
    
}
