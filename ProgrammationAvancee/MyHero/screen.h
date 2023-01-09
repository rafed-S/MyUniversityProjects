/**
 *\file screen.h
 *\brief Bibliothèque de screen
 *\author SAFOUR Rafed
 *\date 2021/2022
*/
#ifndef SCREEN 
#define SCREEN
#include "constante.h"

/**
 * \brief Mets les graphismes à jour à chaque tour de jeu
 * \param jeu ensemble des données du monde
 * \param renderer Surface de rendu de la fenêtre
 */
void refresh_screen(jeu_t* jeu, SDL_Renderer* renderer);

/**
 * \brief Affiche sur la surface de rendu les tiles de la carte
 * \param m ensemble des données liées à la carte du monde 
 * \param renderer Surface de rendu de la fenêtre
 */
void afficher_screen(map_t* m, SDL_Renderer* renderer);

/**
 * \brief Affiche l'écran de fin
 * \param jeu ensemble des données du monde
 * \param renderer Surface de rendu de la fenêtre
 */ 
void screen_end(jeu_t* jeu, SDL_Renderer* renderer);

#endif
