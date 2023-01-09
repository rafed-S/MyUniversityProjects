/**
 *\file screen.c
 *\brief Fonctions de l'affichage.
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#include "screen.h"

//Gestion de l'affichage du score 
void refresh_screen(jeu_t* jeu, SDL_Renderer* renderer)
{   
    //On vide le renderer :
    SDL_RenderClear(renderer);
    afficher_screen(jeu->map, renderer);
    
    //Application des ressources dans le renderer :

    if(jeu->hero->vx >= 0)
    {
        SDL_RenderCopy(renderer, jeu->texture_heroD, NULL, &jeu->hero->dest);
    }else if(jeu->hero->vx <= 0)
    {
        SDL_RenderCopy(renderer, jeu->texture_heroG, NULL, &jeu->hero->dest);    
    }
    for(int i=0;i<NB_ENEMY+1;i++){
        if(jeu->visible_enemy[i] == 1){
            SDL_RenderCopy(renderer, jeu->texture_enemy[i], NULL, &jeu->enemy[i]->dest);
        }
    }
    for(int i=0;i<NB_HP;i++){
        SDL_RenderCopy(renderer, jeu->texture_hp[i], NULL, &jeu->hp[i]->dest);
    }    
    for(int i=0;i<NB_MISSILES+1;i++){
        if(jeu->missile[i]->visible == 1){
            SDL_RenderCopy(renderer, jeu->texture_missile[i], NULL, &jeu->missile[jeu->cont_missile]->dest);
        }
    }
    if(jeu->cont_missile ==0)
    {
        SDL_RenderCopy(renderer, jeu->texture_nb_missile[4], NULL, &jeu->nb_missile[4]->dest);
    }else if(jeu->cont_missile ==1)
    {
        SDL_RenderCopy(renderer, jeu->texture_nb_missile[3], NULL, &jeu->nb_missile[3]->dest);
    }else if(jeu->cont_missile ==2)
    {
        SDL_RenderCopy(renderer, jeu->texture_nb_missile[2], NULL, &jeu->nb_missile[2]->dest);
    }else if(jeu->cont_missile ==3)
    {
        SDL_RenderCopy(renderer, jeu->texture_nb_missile[1], NULL, &jeu->nb_missile[1]->dest);
    }
    
    SDL_RenderPresent(renderer);
}

//Gestion de l'affichage de tile map:
void afficher_screen(map_t* m, SDL_Renderer* renderer)
{   
    int i,j;
    SDL_Rect Rect_dest = {0,0,TileJeu_Largeur,TileJeu_Hauteur};
    int N;
    int miniX, maxiX, miniY, maxiY;
    miniX = m->xCamera / TileJeu_Largeur;
    miniY = m->yCamera / TileJeu_Hauteur;
    maxiX = (m->xCamera + fenetLargeur)/TileJeu_Largeur;
    maxiY = (m->yCamera + fenetHauteur)/TileJeu_Hauteur;
    for(i=miniX;i<=maxiX;i++)
    {
        for(j=miniY;j<=maxiY;j++)
        {
            Rect_dest.x = i*TileJeu_Largeur - m->xCamera;
            Rect_dest.y = j*TileJeu_Hauteur - m->yCamera;
            if (i<0 || i>=m->X_jeu || j<0 || j>=m->Y_jeu)
                N = 1;
            else
                N = m->numT[i][j];
            SDL_RenderCopy(renderer,m->tileset,&(m->tabT[N].rect),&Rect_dest);
        }
    }
}

//Gestion de nettoyage et fermeture:
void screen_end(jeu_t* jeu, SDL_Renderer* renderer)
{
    SDL_Surface* t;
    if(jeu->victory == true)
    {
        t = SDL_LoadBMP("ressources/victory.bmp");
    }else if(jeu->endgame == true)
    {
        t = SDL_LoadBMP("ressources/endgame.bmp");
    }
    SDL_Texture* tex = SDL_CreateTextureFromSurface(renderer, t);
    SDL_FreeSurface(t);
    SDL_RenderClear(renderer);
    SDL_RenderCopy(renderer, tex, NULL, NULL);
    SDL_RenderPresent(renderer);
    SDL_DestroyTexture(tex);
    SDL_Delay(3000);
}
