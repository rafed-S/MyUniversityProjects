/**
 *\file init_jeu.c
 *\brief Installations du monde.
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#include "init_jeu.h"

//On initialise les données du monde :
void init_jeu(jeu_t* jeu, SDL_Renderer* renderer)
{
    jeu->endgame = false;
    jeu->victory = false;
    jeu->cont_missile = 0;
    jeu->hero = init_hero();
    jeu->cont_hp = 0;
    
    init_data_enemy(jeu->enemy,NB_ENEMY);
    init_data_missile(jeu->missile,NB_MISSILES+1);
    init_data_hp(jeu->hp,NB_HP+1);
    init_data_nb_missile(jeu->nb_missile,NB_MISSILES+1);
    
    SDL_Surface* image_heroD = SDL_LoadBMP("ressources/heroD.bmp");
    SDL_Surface* image_heroG = SDL_LoadBMP("ressources/heroG.bmp");
    SDL_Surface* image_enemy = SDL_LoadBMP("ressources/enemy.bmp");
    SDL_Surface* image_missile = SDL_LoadBMP("ressources/m.bmp");
    SDL_Surface* image_hpH = SDL_LoadBMP("ressources/hpH.bmp");
    SDL_Surface* image_hp = SDL_LoadBMP("ressources/hp.bmp");
    SDL_Surface* image_nb_missile0 = SDL_LoadBMP("ressources/nbm0.bmp");
    SDL_Surface* image_nb_missile1 = SDL_LoadBMP("ressources/nbm1.bmp");
    SDL_Surface* image_nb_missile2 = SDL_LoadBMP("ressources/nbm2.bmp");
    SDL_Surface* image_nb_missile3 = SDL_LoadBMP("ressources/nbm3.bmp");
    SDL_Surface* image_nb_missile4 = SDL_LoadBMP("ressources/nbm4.bmp");
    
    jeu->texture_heroD = SDL_CreateTextureFromSurface(renderer, image_heroD);
    jeu->texture_heroG = SDL_CreateTextureFromSurface(renderer, image_heroG);
    
    for(int j=0;j<NB_ENEMY;j++){
        jeu->texture_enemy[j] = SDL_CreateTextureFromSurface(renderer, image_enemy);
    }
    
    for(int l=0;l<NB_MISSILES+1;l++){
        jeu->texture_missile[l] = SDL_CreateTextureFromSurface(renderer, image_missile);
    }
    
    for(int k=0;k<NB_MISSILES+1;k++){
        if(k==0)
        {
            jeu->texture_nb_missile[k] = SDL_CreateTextureFromSurface(renderer, image_nb_missile0);
        }else if(k==1)
        {
            jeu->texture_nb_missile[k] = SDL_CreateTextureFromSurface(renderer, image_nb_missile1);
        }else if(k==2)
        {
            jeu->texture_nb_missile[k] = SDL_CreateTextureFromSurface(renderer, image_nb_missile2);
        }else if(k==3){
            jeu->texture_nb_missile[k] = SDL_CreateTextureFromSurface(renderer, image_nb_missile3);
        }else{
            jeu->texture_nb_missile[k] = SDL_CreateTextureFromSurface(renderer, image_nb_missile4);
        }
    }

    for(int i=0;i<NB_HP+1;i++)
    {
        if(i == 0)
        {
            jeu->texture_hp[i] = SDL_CreateTextureFromSurface(renderer, image_hpH);
        }else{
            jeu->texture_hp[i] = SDL_CreateTextureFromSurface(renderer, image_hp);
        }
    }
    
    SDL_FreeSurface(image_heroD);
    SDL_FreeSurface(image_heroG);
    SDL_FreeSurface(image_enemy);
    SDL_FreeSurface(image_missile);
    SDL_FreeSurface(image_hpH);
    SDL_FreeSurface(image_hp);
    SDL_FreeSurface(image_nb_missile0);
    SDL_FreeSurface(image_nb_missile1);
    SDL_FreeSurface(image_nb_missile2);
    SDL_FreeSurface(image_nb_missile3);
    SDL_FreeSurface(image_nb_missile4);
    
    jeu->map = charger_map(jeu, renderer);
    jeu->cont_hero = 0;
    
    position_enemy(jeu);
}

//l'installation des donnees de hp:
void init_data_hp(sprite_t *tab[], int total)
{
    for (int i = 0; i < total; i++)
    {
        tab[i] = init_hp();
    }
}

//l'installation des donnees de nb_missile:
void init_data_nb_missile(sprite_t *tab[], int total)
{
    for (int i = 0; i < total; i++)
    {
        tab[i] = init_nb_missile();
    }
}

//l'installation des donnees de enemy:
void init_data_enemy(sprite_t *tab[], int total)
{
    for (int i = 0; i < total; i++)
    {
        tab[i] = init_enemy();
    }
}

//l'installation des donnees de missile:
void init_data_missile(sprite_t *tab[], int total)
{
    for (int i = 0; i < total; i++)
    {
        tab[i] = init_missile();
    }
}

//Gestion des sprites hp.
sprite_t* init_hp()
{
    sprite_t* hp = calloc(1,sizeof(sprite_t));
    hp->h = 100;
    hp->w = 100;
    SDL_Rect dest = {fenetLargeur+100, fenetHauteur/2, hp->w, hp->h};
    hp->dest = dest;
    hp->vx = 0.0;
    hp->vy = 0.0;
    hp->visible = 1;
    return hp;
}

//Gestion des sprites nb_missile.
sprite_t* init_nb_missile()
{
    sprite_t* nb_missile = calloc(1,sizeof(sprite_t));
    nb_missile->h = 100;
    nb_missile->w = 100;
    SDL_Rect dest = {fenetLargeur+100, fenetHauteur/2, nb_missile->w, nb_missile->h};
    nb_missile->dest = dest;
    nb_missile->vx = 0.0;
    nb_missile->vy = 0.0;
    return nb_missile;
}

//Gestion des sprites enemy.
sprite_t* init_enemy()
{
    sprite_t* enemy = calloc(1,sizeof(sprite_t));
    enemy->h = 100;
    enemy->w = 100;
    SDL_Rect dest = {fenetLargeur+100, fenetHauteur/2, enemy->w, enemy->h};
    enemy->dest = dest;
    enemy->vx = 0.0;
    enemy->vy = 0.0;
    return enemy;
}

//Gestion des sprites missile.
sprite_t* init_missile()
{
    sprite_t* missile = calloc(1,sizeof(sprite_t));
    missile->h = 100;
    missile->w = 100;
    SDL_Rect dest = {fenetLargeur/2, fenetHauteur - HeroHauteur*5, missile->w, missile->h};
    missile->dest = dest;
    missile->vx = 0.0;
    missile->vy = 0.0;
    missile->visible = 0;
    return missile;
}

//Gestion des sprites hero.
sprite_t* init_hero()
{
    sprite_t* hero = calloc(1,sizeof(sprite_t));
    hero->h = HeroHauteur;
    hero->w = HeroLargeur;
    SDL_Rect dest = {fenetLargeur/6 - hero->w/6, fenetHauteur - HeroHauteur*4, hero->w, hero->h};
    hero->dest = dest;
    hero->vx = 0.0;
    hero->vy = 0.0;
    return hero;
}

SDL_Renderer* init_sdl()
{   
    if (SDL_Init(SDL_INIT_VIDEO) != 0 )
    {
        fprintf(stdout,"Échec de l'init_jeu de la SDL (%s)\n",SDL_GetError());
        exit(EXIT_FAILURE);
    }
    SDL_Window *window = SDL_CreateWindow("The Game",SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED,fenetLargeur, fenetHauteur,SDL_WINDOW_SHOWN);
    SDL_Renderer *pRenderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
    return pRenderer;
}

//La fonction gère l chargement de nos map 1 et 2:
map_t* charger_map(jeu_t* jeu, SDL_Renderer* renderer)
{   
    FILE* F;
    if(jeu->level == 1){
        F = fopen("map1.txt", "r");
    }else if(jeu->level == 2)
    {
        F = fopen("map2.txt", "r");
    }
    map_t* map = calloc(1, sizeof(map_t));
    if(!F)
    {
        printf("[[[ Fichier Introuvable ]]]");
        SDL_Quit();
        exit(-1);
    }
    charger_tileset(F, map, renderer);
    charger_level(F, map);
    fclose(F);
    map->xCamera =0;
    map->yCamera = map->Y_jeu*TileJeu_Hauteur - fenetHauteur ;
    return map;
}

//La fonction gère les types de tiles dans l map :
void charger_tileset(FILE* F, map_t* m, SDL_Renderer* renderer)
{   
    int N,i,j;
    char info[1000];
    char info2[1000]; 
    if(fscanf(F,"%s",info) != 1)
    {
        printf(" ");
    }
    SDL_Surface* tmp = SDL_LoadBMP("ressources/game.bmp");
    if (tmp==NULL)
    {
        printf("[[[ Image Introuvable ]]] \n");
        SDL_Quit();
        exit(-1);
    }
    m->tileset = SDL_CreateTextureFromSurface(renderer, tmp);
    SDL_FreeSurface(tmp);
    if(fscanf(F,"%d %d",&m->xt,&m->yt) != 1)
    {
        printf(" ");
    }
    m->tabT = malloc(m->xt*m->yt*sizeof(tile_t));
    for(j=0,N=0;j<m->yt;j++)
    {
        for(i=0;i<m->xt;i++,N++)
        {
            m->tabT[N].rect.w = TileJeu_Largeur;
            m->tabT[N].rect.h = TileJeu_Hauteur;
            m->tabT[N].rect.x = i*TileJeu_Largeur;
            m->tabT[N].rect.y = j*TileJeu_Hauteur;
            if(fscanf(F,"%s %s",info,info2) != 1)
            {
                printf(" ");
            }
            m->tabT[N].typeTile = 1;
            if ((strcmp(info2,"plein")==0) || (strcmp(info2,"autre")==0) || (strcmp(info2,"mur")==0))
            {
                m->tabT[N].typeTile = 0;
            }
            if (strcmp(info2,"victory") == 0)
            {
                m->tabT[N].typeTile = 2;
            }
        }
    }
}

void charger_level(FILE* F,map_t* m)
{
    int i,j;
    char info[1000];    
    if(fscanf(F,"%d %d",&m->X_jeu,&m->Y_jeu) != 1)
    {
        printf(" ");
    }
    m->numT = malloc(m->X_jeu*sizeof(int*));
    for(i=0;i<m->X_jeu;i++)
    {
        m->numT[i] = malloc(m->Y_jeu*sizeof(int));
    }
    for(j=0;j<m->Y_jeu;j++)
    {
        for(i=0;i<m->X_jeu;i++)
        {
        int tmp;
        if(fscanf(F,"%d",&tmp) !=1)
        {
            printf(" ");
        }
            if (tmp>=m->xt*m->yt)
            {
                printf("(( level tile hors limite )) \n");
                SDL_Quit();
                exit(-1);
            }
            m->numT[i][j] = tmp;
        }
    }
}

void position_enemy(jeu_t* jeu)
{
    jeu->kill_enemy = malloc(NB_ENEMY*sizeof(int*));
    jeu->visible_enemy = malloc(NB_ENEMY*sizeof(int*));
    jeu->pos_enemy = malloc(NB_ENEMY*sizeof(int*));
    for(int i=0;i<NB_ENEMY;i++)
    {
        jeu->kill_enemy[i] = 0;
        jeu->visible_enemy[i] = 0;
        jeu->pos_enemy[i] = malloc(2*sizeof(int));
    }
    int xenemy = 10;
    int yenemy = 10;
    for(int j=0;j<2;j++)
    {
        for(int l=0;l<2;l++)
        {
            if(l==0)
            {
                jeu->pos_enemy[j][l] = xenemy;
                jeu->pos_enemy[j][l+1] = yenemy;
                yenemy += 10;
            }else{
                jeu->pos_enemy[j][l] = xenemy;
                jeu->pos_enemy[j][l+1] = yenemy;
                yenemy -= 10;
            }
        }
        xenemy += 10;
    }
    
    
}
