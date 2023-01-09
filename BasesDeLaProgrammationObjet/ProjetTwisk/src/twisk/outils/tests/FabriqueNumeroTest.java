package twisk.outils.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.outils.FabriqueNumero;

class FabriqueNumeroTest {
    @Test
    void getNumeroSemaphore() {

        Guichet g1 = new Guichet("G1");
        Guichet g2 = new Guichet("G2");
        Guichet g3 = new Guichet("G3");
        boolean b = g1.getCptS() == 1 && g2.getCptS() == 2 && g3.getCptS() == 3 ;
        FabriqueNumero s = FabriqueNumero.getInstance();
        s.reset();
        assert(b):"Prob au niveau de la generation des numeroSmaphore";

    }
    @Test
    void getNumeroEtape() {
        Etape g1 = new Activite("A1");
        Etape g2 = new Activite("A2");
        Etape g3 = new Activite("A3");

        boolean b = g1.getNum() == 0 && g2.getNum() == 1 && g3.getNum() == 2 ;
        FabriqueNumero s = FabriqueNumero.getInstance();
        s.reset();
        assert(b):"Prob au niveau de la generation des numeroEtape";
    }


    @Test
    void reset() {
        Etape g1 = new Activite("A1");
        Etape g2 = new Activite("A2");
        Guichet g4 = new Guichet("G2");
        Guichet g3 = new Guichet("G3");
        FabriqueNumero s = FabriqueNumero.getInstance();
        boolean b1 = s.getCptEtape() == 4 && s.getCptSemaphore() == 3  ;
        s.reset();
        boolean b = s.getCptEtape() == 0 && s.getCptSemaphore() == 1  ;
        assert(b && b1):"Prob au niveau de reset()";

    }
}