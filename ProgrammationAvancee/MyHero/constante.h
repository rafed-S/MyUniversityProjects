/**
 *\file constante.c
 *\brief Bibliothèque
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#ifndef CONSTANTE
#define CONSTANTE
#include <stdbool.h>
#include <stdio.h>
#include <SDL2/SDL.h>
#define fenetHauteur 900
#define fenetLargeur 1500
#define HeroHauteur 100
#define HeroLargeur 100
#define TileJeu_Hauteur 100
#define TileJeu_Largeur 100
#define Out_Map_X 1000
#define Out_Map_Y 1000
#define NB_ENEMY 4
#define NB_MISSILES 4
#define NB_HP 4

/*!< Représentation d'un sprite */
struct sprite_s         
{
    double vx, vy;
    int w, h;
    SDL_Rect dest;
    int visible;
    int onGround;
};
typedef struct sprite_s sprite_t;

/*!< til mapping */
struct tile_s           
{
    SDL_Rect rect;
    int typeTile;
};
typedef struct tile_s tile_t;

/*!< map du jeu */
struct map_s           
{
    int xt, yt;
    int X_jeu, Y_jeu;
    tile_t* tabT;
    SDL_Texture* tileset;
    int** numT;
    int xCamera, yCamera;
};
typedef struct map_s map_t;

/*!< Représentation du monde du jeu*/
struct jeu_s            
{
    map_t* map;
    bool endgame;
    bool victory;
    
    int level;
    int cont_hero;
    int cont_missile;
    int cont_hp;
    
    int* visible_enemy;
    int* kill_enemy;
    int** pos_enemy; 
        
    sprite_t* hero;
    sprite_t* enemy[NB_ENEMY]; 
    sprite_t* hp[NB_HP+1];
    sprite_t* missile[NB_MISSILES+1]; 
    sprite_t* nb_missile[NB_MISSILES+1];
    
    SDL_Texture* texture_heroD;
    SDL_Texture* texture_heroG;
    SDL_Texture* texture_enemy[NB_ENEMY];
    SDL_Texture* texture_missile[NB_MISSILES+1]; 
    SDL_Texture* texture_hp[NB_HP+1]; 
    SDL_Texture* texture_nb_missile[NB_MISSILES+1]; 
};
typedef struct jeu_s jeu_t;

#endif
