/**
 *\file keyboard_event.h
 *\brief Bibliothèque de keyboard_event
 *\author SAFOUR Rafed
 *\date 2021/2022
*/

#ifndef KEYBOARD_EVENT 
#define KEYBOARD_EVENT
#include "constante.h"

/**
 * \brief fonction qui prend en charges les evenements
 * \param jeu ensemble des données du monde
 * \param event evenement 
 */
void handle_events(jeu_t* jeu, SDL_Event* event);


#endif
