/**
 *\file keyboard_event.c
 *\brief Fonctions des interactions du joueur avec le jeu.
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#include "keyboard_event.h"

void handle_events(jeu_t* jeu, SDL_Event* event)
{
    Uint8 *keystates;
    while (SDL_PollEvent(event))
    {
        if (event->type == SDL_QUIT)
        {
            jeu->endgame = true;
        }
        //Si une touche est appuyée :
        if (event->type == SDL_KEYUP)
        {
            if(jeu->hero->onGround == 0)
            {
                if (event->key.keysym.sym == SDLK_SPACE)
                {
                    if(jeu->hero->vy > -7)
                    {
                        jeu->hero->vy = -7 ;
                    }
                }
            }
            //Si une touche est appuyée :
            if(event->key.keysym.sym == SDLK_LALT)
                {
                    if(jeu->cont_missile < NB_MISSILES)
                    {
                        //On gère le missile à envoyer,
                        jeu->cont_missile = jeu->cont_missile + 1;
                        jeu->missile[jeu->cont_missile]->visible = 1;
                        visible_missile(jeu,jeu->cont_missile);
                    }
                }
        //Si une touche est appuyée :
        }else if(event->type == SDL_KEYDOWN)
        {
            if (event->key.keysym.sym == SDLK_RIGHT)
            {
                if(jeu->hero->vx < 5)
                {
                    if(jeu->hero->vx < 0)
                    {
                        jeu->hero->vx = 0;
                    }else{
                        jeu->hero->vx = 5;
                    }
                }
            }
            //Si une touche est appuyée :
            if (event->key.keysym.sym == SDLK_LEFT)
            {
                if(jeu->hero->vx > -5)
                {
                    if(jeu->hero->vx > 0)
                    {
                        jeu->hero->vx = 0;
                    }else{
                        jeu->hero->vx = -5;
                    }
                }
            }
            //Si l'utilisateur ferme la fenêtre :
            if (event->key.keysym.sym == SDLK_ESCAPE)
            {
                //On indique la fin du jeu.
                jeu->endgame = true;
                jeu->level = 3;
            }
        }
    }
}
