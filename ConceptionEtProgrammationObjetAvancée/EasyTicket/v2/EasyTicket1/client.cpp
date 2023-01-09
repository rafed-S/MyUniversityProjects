#include "client.h"

Client::Client(QString nom, QString mail)
{
    this->nom = nom;
    this->mail = mail;
    // Initialiser l'identifiant via une fabrique
}
