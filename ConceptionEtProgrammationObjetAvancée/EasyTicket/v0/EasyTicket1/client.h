#ifndef CLIENT_H
#define CLIENT_H
#include "utilisateur.h"


class Client : public Utilisateur
{
public:
    Client(QString nom, QString mail);
};

#endif // CLIENT_H
