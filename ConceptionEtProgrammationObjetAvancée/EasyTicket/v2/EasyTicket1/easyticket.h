#ifndef EASYTICKET_H
#define EASYTICKET_H
#include "ticket.h"
#include "utilisateur.h"
#include <vector>
#include <QString>
#include <QVector>
#include <QList>
using namespace std;

class easyticket
{
private:
    QList<Ticket> listTicket;
    QList<Utilisateur> listUtilisateurs;
public:
    easyticket();
    Utilisateur getUtilisateurs(QString nom);
    Ticket getTicket(Ticket ticket);
    void ModifTicket(Ticket ticket);
    void Verification(QString nom, QString mail);
    QList<Ticket> getListTickets();
    QList<Utilisateur> getListUtilisateurs();
    bool VerifCompteExistant(QString nom, QString mail);
    void CreerCompte(QString nom, QString mail);

};

#endif // EASYTICKET_H
