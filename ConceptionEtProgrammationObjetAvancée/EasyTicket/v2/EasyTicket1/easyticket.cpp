#include "easyticket.h"

#include <QVector>
#include <QList>
using namespace std;
easyticket::easyticket()
{

}

/* getters */
Ticket easyticket::getTicket(Ticket ticket){
    QList<Ticket> list = this->getListTickets();
    for(int i=0; i<list.size();i++){
        //faire fonction recup id ticket
        //puis finir cette fonction sur modele getUtilisateurs
        //if(list[i].)
    }
}

void easyticket::CreerCompte(QString nom, QString mail){
    Utilisateur *user = new Utilisateur(nom,mail);
    //rentrer *user BDD
}

void easyticket::Verification(QString nom, QString mail){
    //verif sur la base de donnée
}

QList<Ticket> easyticket::getListTickets(){
    return this->listTicket;
}

QList<Utilisateur> easyticket::getListUtilisateurs(){
    return this->listUtilisateurs;
}

Utilisateur easyticket::getUtilisateurs(QString nom){
    QList<Utilisateur> list = this->getListUtilisateurs();
    for(int i =0;i<list.size();i++){
        if(list[i].getNom()==nom) return list[i];
    }
}

bool easyticket::VerifCompteExistant(QString nom, QString mail){
    /*
   Utilisateur *user = new Utilisateur(nom,mail);
   QList<Utilisateur> list = this->getListUtilisateurs();
   return list.contains(*user);
   */
    return true;
}





