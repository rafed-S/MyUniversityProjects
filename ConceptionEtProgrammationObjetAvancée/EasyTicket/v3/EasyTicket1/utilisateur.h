#ifndef UTILISATEUR_H
#define UTILISATEUR_H
//#include "ticket.h"
#include <string>
#include <QString>
#include<iostream>

using namespace std;

class Utilisateur
{
protected:
    QString nom;
    QString mail;
    int id_utilisateur;
    bool isClient();
    bool isAdministrateur();
    bool isIngenieur();
    bool isTechnicien();

public:
    Utilisateur();
    Utilisateur(QString nom, QString mail);
    void setNom(QString nom);
    void setMail(QString mail);
    QString getNom();
    QString getMail();
    int getId();
    //void ModifCategorie(Ticket ticket, Categorie categorie)();
};

#endif // UTILISATEUR_H
