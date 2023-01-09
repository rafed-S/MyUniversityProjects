#include "utilisateur.h"
#include <QString>

Utilisateur::Utilisateur()
{

}

Utilisateur::Utilisateur(QString nom, QString mail)
{
    this->setNom(nom);
    this->setMail(mail);

}


/* setters */
void Utilisateur::setNom(QString nom){
    this->nom = nom;
}

void Utilisateur::setMail(QString mail){
    this->mail = mail;
}
/* getters */
QString Utilisateur::getNom(){
    return nom;
}

QString Utilisateur::getMail(){
    return mail;
}

int Utilisateur::getId(){
    return id_utilisateur;
}
