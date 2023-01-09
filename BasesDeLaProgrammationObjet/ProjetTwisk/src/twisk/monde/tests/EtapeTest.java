package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class EtapeTest {

    @org.junit.jupiter.api.Test
    void ajouterSuccesseur() {
        Etape a = new Activite("Activité 1");
        Etape b = new Activite("Activité 2");

        a.ajouterSuccesseur(b);
        assert(a.nbSuccesseurs() == 1):"BUG au niv de la fonction ajouterSuccesseur";
    }

    @org.junit.jupiter.api.Test
    void estUneActivite() {
        Etape a = new Activite("Activité 1");
        boolean b = a.estUneActivite();
        assert(b):"Bug au niv de la fonction estUneActivité";

        Etape c = new Guichet("Guichet1");
        boolean d = c.estUneActivite();
        assert(!d):"Bug au niv de la fonction estUnActivité";

    }

    @org.junit.jupiter.api.Test
    void estUnGuichet() {

        Etape a = new Activite("Activité 1");
        boolean b = a.estUnGuichet();
        assert(!b):"Bug au niv de la fonction estUnGuichet";

        Etape c = new Guichet("Guichet1");
        boolean d = c.estUnGuichet();
        assert(d):"Bug au niv de la fonction estUnGuichet";

    }

    @Test
    void testAjouterSuccesseur() {
        Etape a = new Activite("Activité 1");
        Etape c = new Guichet("Guichet1");
        a.ajouterSuccesseur(c);
        boolean b = a.nbSuccesseurs() == 1;
        assert(b):"Bug au niv de la fonction ajouterSuccesseurs";
    }
}