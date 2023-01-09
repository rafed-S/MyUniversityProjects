/**
 *\file jeu.c
 *\brief Gestion du monde.
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#define SIGNE(X) (((X)==0)?(0):(((X)<0)?(-1):(1)))
#include "jeu.h"

void jeu_update(jeu_t* jeu)
{
        update_hero(jeu);
        scroll(jeu);
        update_speeds(jeu->hero);
        update_enemy(jeu);
        update_missile(jeu);
        update_speeds_missile(jeu);
        update_hp(jeu);
        position_nb_missile(jeu);
        update_game(jeu);
}

//La fonction gère la gravité
void update_speeds(sprite_t* hero)
{
    if(hero->vy < 7)
    {
        hero->vy += 0.1 ;
    }
}

//La fonction gère le movement de hero
int moving_hero(jeu_t* jeu)
{
    SDL_Rect next;
    SDL_Rect prev;
    next = jeu->hero->dest;
    next.x += jeu->hero->vx;
    next.y += jeu->hero->vy;
    jeu->cont_hero = next.x;  
    if(Collision(jeu, next) == 1)
    {
        prev = jeu->hero->dest;
        jeu->hero->dest = next;
        return 0;
    }
    return 1;
}

//misa à jour de hero
void update_hero(jeu_t* jeu)
{    
    if(moving_hero(jeu) == 1)
    {
        mur_hero_collision(jeu);
    }
    SDL_Rect pHero, pEnemy;
    for(int i=0;i<NB_ENEMY;i++)
    {
        pHero = jeu->hero->dest;
        pEnemy = jeu->enemy[i]->dest;
        if(pHero.x >= pEnemy.x-50 && pHero.y >= pEnemy.y-50)
        {
            if(pHero.x <= pEnemy.x+50 && pHero.y <= pEnemy.y+50)
            {
                //On retire un point de vie et on le rend invisible
                jeu->cont_hp +=1;
                jeu->hp[i+1]->visible=0;
            }
        }
    }
}

//misa à jour des missiles
void update_missile(jeu_t* jeu)
{
    for(int i=0;i<NB_MISSILES+1;i++){
        if(jeu->missile[i]->visible ==1)
        {
            SDL_Rect next,next2;
            next = jeu->missile[i]->dest;
            next.x += 3;
            next.x = next.x - jeu->hero->vx;
            next2 = next;
            if(jeu->hero->vy > 7)
            {
            }else{
                next.y -=jeu->hero->vy;
                next.y +=1;
            }
            jeu->missile[i]->dest = next;
        }
        if(jeu->missile[i]->visible ==1)
        {
            jeu->missile[i-1]->visible ==0;

            SDL_Rect next3;
            next3.x = 0;
            next3.y = 0;
            jeu->missile[i-1]->dest =next3;
        }
        int test = missile_collision(jeu);
        SDL_Rect next3;
        next3.x = Out_Map_X;
        next3.y = Out_Map_Y;
        jeu->missile[test]->dest =next3;
    }

}

//La fonction gère l vitesse des missiles
void update_speeds_missile(jeu_t* jeu){
    for(int i=1;i<NB_MISSILES+1;i++)
    {
        if(jeu->missile[i]->visible == 1)
        {
            jeu->missile[i]->dest.x += 3;
        }
    }
}

//La fonction gère l'emplacement de hp
int update_hp(jeu_t* jeu)
{
    int x = 700;
    int y = 400;
    for(int i=0;i<NB_HP+1;i++)
    {
        SDL_Rect next;
        next = jeu->hero->dest;
        next.x += jeu->hero->vx - x;
        next.y += jeu->hero->vy - y;
        jeu->hp[i]->dest = next;
        x = x -100;
    }
}

//La fonction gère l'emplacement de l'ennemis
void update_enemy(jeu_t* jeu){
    int maxiX;
    maxiX = ((jeu->hero->dest.x + jeu->hero->dest.w -1) + jeu->map->xCamera) / TileJeu_Largeur;
    //int NbTileX = 19;
    int NbTileX = jeu->pos_enemy[0][0]+9;
    int NbTileY = jeu->pos_enemy[0][1]-4;
    int x = NbTileX * 100 - jeu->map->xCamera;
    int y = NbTileY * 100 - jeu->map->yCamera;
    //on rend invisible l'ennemis quand l tile de hero 7
    if(jeu->level ==2){
        if(maxiX >= 7){
            jeu->visible_enemy[0] = 1;
            jeu->visible_enemy[1] = 1;
            jeu->visible_enemy[2] = 1;
            jeu->visible_enemy[3] = 1;
            for(int i=0;i<NB_ENEMY;i++){
                enemy_entre(x,y,1,jeu,i);
            }
        }else{
            jeu->visible_enemy[0] = 0;
            jeu->visible_enemy[1] = 0;
            jeu->visible_enemy[2] = 0;
            jeu->visible_enemy[3] = 0;
            for(int i=0;i<NB_ENEMY;i++){
                enemy_entre(x,y,0,jeu,i);
            }
        }
    //on rend invisible l'ennemis quand l tile de hero 3
    }else{
        if(maxiX >= 3){
            jeu->visible_enemy[0] = 1;
            jeu->visible_enemy[1] = 1;
            jeu->visible_enemy[2] = 1;
            jeu->visible_enemy[3] = 1;
            for(int i=0;i<NB_ENEMY;i++){
                enemy_entre(x,y,1,jeu,i);
            }
        }else{
            jeu->visible_enemy[0] = 0;
            jeu->visible_enemy[1] = 0;
            jeu->visible_enemy[2] = 0;
            jeu->visible_enemy[3] = 0;
            for(int i=0;i<NB_ENEMY;i++){
                enemy_entre(x,y,0,jeu,i);
            }
        }
    }
}


void visible_missile(jeu_t* jeu, int nb){
    jeu->missile[nb]->visible = 1;
    SDL_Rect next;
    next = jeu->hero->dest;
    jeu->missile[nb]->dest = next;
}

//La fonction gère les ordonnées des ennemis
void enemy_entre(int x ,int y ,int b ,jeu_t* jeu, int n){
    if(jeu->level ==2)
    {    
        if(jeu->visible_enemy[n] == 1){
            SDL_Rect next;
            next = jeu->hero->dest;
            next.x = x;
            next.y = y;
            if(n==0){
                next.x = next.x-700;
                next.y += 100;
            }else if(n==1){
                next.x -= 500;
                next.y -= 0;
            }else if(n==2){
                next.x += -100;
                next.y -= 300;
            }else if(n==3){
                next.x += 400;
                next.y -= 400;
            }
            jeu->enemy[n]->dest = next;
        }
    }else{
        if(jeu->visible_enemy[n] == 1){
            SDL_Rect next;
            next = jeu->hero->dest;
            next.x = x;
            next.y = y;
            if(n==0){
                next.x = next.x-1100;
                next.y += 0;
            }else if(n==1){
                next.x -= 600;
                next.y -= 100;
            }else if(n==2){
                next.x += -100;
                next.y -= 200;
            }else if(n==3){
                next.x += 400;
                next.y -= 300;
            }
            jeu->enemy[n]->dest = next;
        }
    }
}

//La fonction gère la colision entre le hero et les murs
void mur_hero_collision(jeu_t* jeu)
{
    int i;  
    for(i=0;i<abs(jeu->hero->vx);i++)
    {
        if(deplacement_hero(jeu, SIGNE(jeu->hero->vx), 0) == 1)
        {
            break;
        }
    }
    for(i=0;i<abs(jeu->hero->vy);i++)
    {
        if(deplacement_hero(jeu, 0, SIGNE(jeu->hero->vy)) == 1)
        {
            break;
        }          
    }
}

//On gère les collisions des missiles avec l'ennemis.
int missile_collision(jeu_t* jeu)
{
    SDL_Rect pMissile, pEnemy;
    for(int j=0;j<NB_ENEMY+1;j++)
    {
        for(int i=0;i<NB_MISSILES+1;i++)
        {
            pMissile = jeu->missile[i]->dest;
            pEnemy = jeu->enemy[j]->dest;
            if(pMissile.x >= pEnemy.x && pMissile.y >= pEnemy.y-50)
            {
                if(pMissile.x <= pEnemy.x+50 && pMissile.y <= pEnemy.y+50)
                {
                    jeu->kill_enemy[j]=1;
                    return i;
                }
            }
        }
    }
    return NB_MISSILES*2;
}


//La fonction gère le movement de hero
int deplacement_hero(jeu_t* jeu, double vx, double vy)
{
    SDL_Rect next;
    SDL_Rect prev;
    next = jeu->hero->dest;
    next.x += vx;
    next.y += vy;
    SDL_Rect test = {0,0,0,0};
    int resTest = Collision(jeu, next);
    if(resTest == 1)
    {
        prev = jeu->hero->dest;
        jeu->hero->dest = next;
        return 0;
    }
    return 1;
}


//La fonction gère le movement de hero avec TileMap
int Collision(jeu_t* jeu, SDL_Rect hero)
{
    int miniX,maxiX,miniY,maxiY;
    int i,j,indiceT;
    miniX = (hero.x + jeu->map->xCamera) / TileJeu_Largeur;
    miniY = (hero.y + jeu->map->yCamera) / TileJeu_Hauteur;
    maxiX = ((hero.x + hero.w -1) + jeu->map->xCamera) / TileJeu_Largeur;
    maxiY = ((hero.y + hero.h -1) + jeu->map->yCamera) / TileJeu_Hauteur;
    if (miniX<0 || miniY<0 || maxiX>=jeu->map->X_jeu || maxiY>=jeu->map->Y_jeu)
        return 0;
    for(i=miniX;i<=maxiX;i++)
    {
        for(j=miniY;j<=maxiY;j++)
        {
            indiceT = jeu->map->numT[i][j];
            if (jeu->map->tabT[indiceT].typeTile == 0)
            {
                jeu->hero->onGround = 0;                
                //0->stop
                return 0;
            }
            if(jeu->map->tabT[indiceT].typeTile == 2)
            {
                jeu->endgame = true;
                jeu->victory = true;
            }
        }
    }
    jeu->hero->onGround = 1;
    //1->move
    return 1;
}

void position_nb_missile(jeu_t* jeu)
{     
    int x = -600;
    int y = 400;
    if(jeu->cont_missile ==0){
        SDL_Rect next;
        next = jeu->hero->dest;
        next.x += jeu->hero->vx - x;
        next.y += jeu->hero->vy - y;
        jeu->nb_missile[4]->dest = next;
    }else if(jeu->cont_missile ==1){
        SDL_Rect next;
        next = jeu->hero->dest;
        next.x += jeu->hero->vx - x+1;
        next.y += jeu->hero->vy - y;
        jeu->nb_missile[3]->dest = next;
    }else if(jeu->cont_missile ==2){
        SDL_Rect next;
        next = jeu->hero->dest;
        next.x += jeu->hero->vx - x;
        next.y += jeu->hero->vy - y;
        jeu->nb_missile[2]->dest = next;
    }else if(jeu->cont_missile ==3){
        SDL_Rect next;
        next = jeu->hero->dest;
        next.x += jeu->hero->vx - x;
        next.y += jeu->hero->vy - y;
        jeu->nb_missile[1]->dest = next;
    
    }
}

//La fonction gère le scroll de camera
void scroll(jeu_t* jeu)
{    
    int yp;
    yp = jeu->hero->dest.y + TileJeu_Hauteur/2 + jeu->map->yCamera;
    jeu->map->yCamera = yp - (fenetHauteur/2);
    jeu->hero->dest.y = fenetHauteur/2 - TileJeu_Hauteur/2;
    
    int xp;
    xp = jeu->hero->dest.x + TileJeu_Largeur/2 + jeu->map->xCamera;
    jeu->map->xCamera = xp - (fenetLargeur/2);
    jeu->hero->dest.x = fenetLargeur/2 - TileJeu_Largeur/2;
}

//Si l'ennemi kill sont à 1,on retire le vaisseau de l'affichage.
void kill_enemy(jeu_t* jeu, SDL_Renderer* renderer)
{
    for(int i=0;i<NB_ENEMY;i++)
    {
        if(jeu->kill_enemy[i]==1)
        {
            //printf(" #Good ! enemy has been killed \n");
            SDL_DestroyTexture(jeu->texture_enemy[i]);
            SDL_Rect next3;
            next3.x = Out_Map_X;
            next3.y = Out_Map_Y;
            jeu->enemy[i]->dest =next3;
        }
    }    
}

//On retire un point de vie et on le rend invisible
void minus_hp(jeu_t* jeu, SDL_Renderer* renderer)
{
    if(jeu->hp[1]->visible ==0)
    {
        SDL_DestroyTexture(jeu->texture_hp[3]);
    }
    if(jeu->hp[2]->visible ==0)
    {
        SDL_DestroyTexture(jeu->texture_hp[2]);
    }
    if(jeu->hp[3]->visible ==0)
    {
        SDL_DestroyTexture(jeu->texture_hp[1]);
    }
}

//On libère l'espace mémoire des données du monde
void free_jeu(jeu_t* jeu, SDL_Renderer* renderer)
{
    int i;
    free(jeu->hero);
    SDL_DestroyRenderer(renderer);
    SDL_DestroyTexture(jeu->texture_heroD);
    SDL_DestroyTexture(jeu->texture_heroG);
    SDL_DestroyTexture(jeu->map->tileset);
    free(jeu->map->tabT);
    for(i = 0; i < jeu->map->X_jeu; i++)
    {
        free(jeu->map->numT[i]);
    }
    free(jeu->map->numT);
    free(jeu->map);
}

//misa à jour du jeu:
void update_game(jeu_t* jeu)
{
    int cont_game = 0;
    int victoryX = ((jeu->hero->dest.x + jeu->hero->dest.w -1) + jeu->map->xCamera) / TileJeu_Largeur;
    int victoryY = ((jeu->hero->dest.y + jeu->hero->dest.w -1) + jeu->map->yCamera) / TileJeu_Hauteur;
    for(int i=0;i<NB_ENEMY;i++)
    {
        if(jeu->kill_enemy[i]==1)
        {
            cont_game +=1;
        }
    }
    if(victoryX == 28)
    {
        if(victoryY == 4)
        {
            if(cont_game == 4)
            {
                //Le joueur a gagné
                jeu->victory = true;
                jeu->endgame = true;
                jeu->level += 1;
            }else if(cont_game != 4){
                //Le joueur a perdu
                jeu->victory = false;
                jeu->endgame = true;
                jeu->level = 1;
            }
        }
    }
    int con =0;
    for(int l=0;l<NB_HP+1;l++){
        if(jeu->hp[l]->visible ==0)
        {
            con+=1;
        }
    }
    if(con>=3)
    {
        //Le joueur a perdu
        jeu->victory = false;
        jeu->endgame = true;
        jeu->level = 1;
    }
}
