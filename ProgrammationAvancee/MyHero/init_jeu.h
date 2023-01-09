/**
 *\file init_jeu.h
 *\brief Bibliothèque de init_jeu
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#ifndef INIT_JEU 
#define INIT_JEU
#include "constante.h"

/**
 * \brief Initialise les données du monde
 * \param jeu ensemble des données du monde
 * \param renderer Surface de rendu de la fenêtre
 */
void init_jeu(jeu_t* jeu, SDL_Renderer* renderer);

/**
 * \brief Initialise les données du hero
 * \return un pointeur sur la structure du hero
 */
sprite_t* init_hero();

/**
 * \brief Initialise les données du nb_missile
 * \return un pointeur sur la structure du nb_missile
 */
sprite_t* init_nb_missile();

/**
 * \brief Initialise les données du enemy
 * \return un pointeur sur la structure du enemy
 */
sprite_t* init_enemy();

/**
 * \brief Initialise les données du missile
 * \return un pointeur sur la structure du missile
 */
sprite_t* init_missile();

/**
 * \brief Initialise les données du hp
 * \return un pointeur sur la structure du hp
 */
sprite_t* init_hp();

/**
 * \brief Initialise les données du hp
 * \param sprite_t un pointeur tab
 * \param int nombre total
 */
void init_data_hp(sprite_t *tab[], int total);

/**
 * \brief Initialise les données du nb_missile
 * \param sprite_t un pointeur tab
 * \param int nombre total
 */
void init_data_nb_missile(sprite_t *tab[], int total);

/**
 * \brief Initialise les données du enemy
 * \param sprite_t un pointeur tab
 * \param int nombre total
 */
void init_data_enemy(sprite_t *tab[], int total);

/**
 * \brief Initialise les données du missile
 * \param sprite_t un pointeur tab
 * \param int nombre total
 */
void init_data_missile(sprite_t *tab[], int total);

/**
 * \brief inintialise la sdl (fenetre, rendu)
 * \return un pointeur sur une surface de rendu
 */
SDL_Renderer* init_sdl();

/**
 * \brief charge les données de la carte dans une structure
 * \param jeu ensemble des données du monde
 * \param renderer Surface de rendu de la fenêtre
 * \return un pointeur sur une structure map
 */
map_t* charger_map(jeu_t* jeu, SDL_Renderer* renderer);

/**
 * \brief crée un tableau correspondant aux différentes tiles de la bitmap
 * \param F fichier de modification des tiles
 * \param m ensemble des données liées à la carte du monde
 * \param renderer Surface de rendu de la fenêtre
 */
void charger_tileset(FILE* F, map_t* m, SDL_Renderer* renderer);

/**
 * \brief crée un tableau correspondant aux différentes tiles de notre monde 
 * \param F fichier de modification des tiles
 * \param m ensemble des données liées à la carte du monde 
 */
void charger_level(FILE* F, map_t* m);

/**
 * \brief Initialise les données des enemy
 * \param jeu ensemble des données du monde
 */
void position_enemy(jeu_t* jeu);

#endif
