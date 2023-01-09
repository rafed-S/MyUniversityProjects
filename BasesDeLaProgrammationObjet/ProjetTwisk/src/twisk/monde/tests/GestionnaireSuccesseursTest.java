package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireSuccesseurs;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireSuccesseursTest {


    @Test
    void ajouter() {
        GestionnaireSuccesseurs a = new GestionnaireSuccesseurs();
        Etape b = new Activite("Activité 1");
        a.ajouter(b);
        assert(a.nbEtapes() == 1 ) :"Bug dans la fonction ajouter";
    }

    @Test
    void nbEtapes() {
        GestionnaireSuccesseurs a = new GestionnaireSuccesseurs();
        Etape b = new Activite("Activité 1");
        Etape c = new Activite("Activité2");
        Etape d = new Guichet("Guichet1 ");

        a.ajouter(b,c,d);
        assert(a.nbEtapes() == 3 ) :"Bug dans la fonction nbEtapes";

    }
}