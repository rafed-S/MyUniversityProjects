package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.*;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {

    @Test
    void ajouter() {
        GestionnaireEtapes Ges = new GestionnaireEtapes();
        Etape Et1 = new Activite("Activité 1");
        Etape Et2 = new Activite("Activité 2");
        Ges.ajouter(Et1);
        Ges.ajouter(Et2);
        assert(Ges.nbEtapes() == 2 ) :"Bug dans la fonction ajouter dans GestionnaireEtapes";
    }

    @Test
    void nbEtapes() {
        GestionnaireEtapes Ges = new GestionnaireEtapes();
        Etape Et1 = new Activite("Activité 1");
        Etape Et2 = new Activite("Activité 2");
        Etape Et3 = new Guichet("Guichet 1 ");
        Etape Et4 = new Guichet("Guichet 2 ");
        Ges.ajouter(Et1,Et2);
        Ges.ajouter(Et3,Et4);
        assert(Ges.nbEtapes() == 4 ) :"Bug dans la fonction nbEtapes dans GestionnaireEtapes";
    }
}