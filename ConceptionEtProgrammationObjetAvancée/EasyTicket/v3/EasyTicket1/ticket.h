#ifndef TICKET_H
#define TICKET_H
#include <ctime>

using namespace std;

class Ticket
{
public:
    Ticket();
    //void setCategorie(Categorie c);
    void afficherTicket();
    int calculTime();
    void setResponsable(int id);
private:
    int id_ticket;
    char* nom_client;
    int id_responsable;
    time_t date_creation;
    time_t date_cloture;
    time_t date_reattribution;
    // Categorie categorie;
    char* systeme;
    char* logiciel;
};

#endif // TICKET_H
