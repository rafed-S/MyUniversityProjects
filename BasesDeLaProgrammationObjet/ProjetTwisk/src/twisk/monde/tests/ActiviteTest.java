package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.*;

class ActiviteTest {

    @Test
    void estUneActivite() {
        Activite a = new Activite("Act 1");
        boolean b = a.estUneActivite();
        assert(b):"error fonction estUneActivité";

        ActiviteRestreinte c = new ActiviteRestreinte("Act 1");
        boolean d = c.estUneActivite();
        assert(d):"error fonction estUneActivité";

        SasEntree e = new SasEntree();
        boolean f = e.estUneActivite();
        assert(f):"error fonction estUneActivité";

        SasSortie g = new SasSortie();
        boolean h = g.estUneActivite();
        assert(h):"error fonction estUneActivité";
    }

    @Test
    void toC() {
        Etape a1 = new Activite("Act 1");
        Etape a2 = new Activite("Act 2");
        a1.ajouterSuccesseur(a2);
        String s2 = "";
        s2 = "delai("+ a1.getTemps() + "," + a1.getEcartTemps() + ");\n"+"transfert(" + a1.getNum() + ","+ a2.getNum() +");\n";
        boolean b = a1.toC().equals(s2);
        assert(b):"error fonction ToC dans Activite";
    }
}