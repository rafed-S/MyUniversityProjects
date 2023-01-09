/**
 *\file jeu.h
 *\brief Bibliothèque de jeu
 *\author SAFOUR Rafed
 *\date 2021/2022
*/
#ifndef JEU
#define JEU
#include "constante.h"

/**
 * \brief Prend en charge la modification des données
 * \param jeu ensemble des données du monde
*/
void jeu_update(jeu_t* jeu);

void position_nb_missile(jeu_t* jeu);

/**
 * \brief modifie la position du hero
 * \param jeu ensemble des données du monde
*/
void update_hero(jeu_t* jeu);

/**
 * \brief modifie la position du missile
 * \param jeu ensemble des données du monde
*/
void update_missile(jeu_t* jeu);

/**
 * \brief modifie la position du hp
 * \param jeu ensemble des données du monde
*/
int update_hp(jeu_t* jeu);

/**
 * \brief mets à jour la vitesse du missile
 * \param jeu ensemble des données du monde
*/
void update_speeds_missile(jeu_t* jeu);

/**
 * \brief mets à jour la vitesse du hero
 * \param jeu ensemble des données du monde
*/
void update_speeds(sprite_t* hero);

/**
 * \brief modifie la position du missile
 * \param jeu ensemble des données du monde
*/
void update_missile(jeu_t* jeu);

/**
 * \brief test la collision entre le hero et les blocs qui l'entourent
 * \param jeu ensemble des données du monde
 * \param hero hitbox du hero
 * \return 1 si pas de collision 0 si collision
*/
int Collision(jeu_t* jeu, SDL_Rect hero);

/**
 * \brief tente de déplacer le hero
 * \param jeu ensemble des données du monde
 * \return 0 si il n'y a pas de collision, 1 sinon
*/
int moving_hero(jeu_t* jeu);

void update_enemy(jeu_t* jeu);

void visible_missilenb(jeu_t* jeu, int vm);

void enemy_entre(int x ,int y ,int b ,jeu_t* jeu, int n);

/**
 * \brief tente de déplacer le hero pixel par pixel pour le coller à un mur
 * \param jeu ensemble des données du monde
 * \param vx vitesse en X
 * \param vy vitesse en Y
 * \return 0 si il n'y a pas de collision, 1 sinon
*/
int deplacement_hero(jeu_t* jeu, double vx, double vy);

/**
 * \brief libere la mémoire allouée 
 * \param jeu ensemble des données du monde
 * \param renderer surface de rendu de la fenêtre
*/
void free_jeu(jeu_t* jeu, SDL_Renderer* renderer);

/**
 * \brief déplace le hero pixel par pixel pour le coller à un mur
 * \param jeu ensemble des données du monde
*/
void mur_hero_collision(jeu_t* jeu);

/**
 * \brief gère les collisions des missiles avec l'ennemis
 * \param jeu ensemble des données du monde
*/
int missile_collision(jeu_t* jeu);

/**
 * \brief déplace le scroll de façon automatique
 * \param jeu ensemble des données du monde
*/
void scroll(jeu_t* jeu);

void kill_enemy(jeu_t* jeu, SDL_Renderer* renderer);

void minus_hp(jeu_t* jeu, SDL_Renderer* renderer);

/**
 * \brief mets à jour les modification et les données
 * \param jeu ensemble des données du monde
*/
void update_game(jeu_t* jeu);

#endif
