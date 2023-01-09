package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.SasEntree;

class SasEntreeTest {

    @Test
    void toC() {
        Etape se1 = new SasEntree();
        Etape etp1 = new Activite("act1");
        se1.ajouterSuccesseur(etp1);
        String s2 = "";
        s2 = "entrer("+se1.getNum()+");\n"+"delai("+ se1.getTemps() + "," + se1.getEcartTemps() + ");\n"+"transfert(" + se1.getNum() + ","+ etp1.getNum() +");\n";
        boolean b = se1.toC().equals(s2);
        assert(b):"error fonction ToC dans SASENTREE";
    }
}
