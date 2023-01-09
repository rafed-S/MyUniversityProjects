package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.ActiviteRestreinte;
import twisk.monde.Etape;
import twisk.monde.Guichet;

class GuichetIGTest {

    @Test
    void toC() {

        Etape g1 = new Guichet("gui 1");
        Etape g2 = new ActiviteRestreinte("act1",5,9);
        g1.ajouterSuccesseur(g2);
        String s2 = "";
        s2 = "P(ids,"+g1.getCptS()+");\n"+"transfert("+g1.getNum()+","+g1.getSuccesseur().getNum()+");\n"+"V(ids,"+g1.getCptS()+");\n";
        boolean b = g1.toC().equals(s2);
        assert(b):"error fonction ToC dans Guichet";
    }

    @Test
    void estUnGuichet() {
        Etape g1 = new Guichet("gui 1");
        boolean b = g1.estUnGuichet();
        assert(b):"BUg au nivea de la fonction estUnGuichet()";
    }
}
