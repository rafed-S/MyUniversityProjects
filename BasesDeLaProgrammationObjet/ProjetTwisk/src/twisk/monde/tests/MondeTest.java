package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.*;

class MondeTest {

    @Test
    void aCommeEntree() {
        Monde mn = new Monde();
        Etape g1 = new Guichet("gui 1");
        mn.aCommeEntree(g1);
        boolean b = mn.getEn().getSuccesseur().getNum() == g1.getNum();
        assert(b):"BUg au niveau de la fonction aCommeEntree()";
    }

    @Test
    void aCommeSortie() {
        Monde mn = new Monde();
        Etape g1 = new Guichet("gui 1");
        mn.aCommeSortie(g1);
        boolean b = mn.getSo().getNum() == g1.getSuccesseur().getNum();
        assert(b):"BUg au niveau de la fonction aCommeSortie()";
    }

    @Test
    void ajouter() {
        Monde mn = new Monde();
        Etape g1 = new Guichet("gui 1");
        mn.ajouter(g1);
        System.out.println(mn.nbEtapes());
        boolean b = mn.nbEtapes() == 3;
        assert(b):"BUg au niveau de la fonction ajouter()";
    }

    @Test
    void nbGuichets() {
        Monde mn = new Monde();
        Etape g1 = new Guichet("gui 1");
        Etape act1 = new Activite("act 1");

        mn.ajouter(g1,act1);
        boolean b = mn.nbGuichets() ==1;
        assert(b):"BUg au niveau de la fonction nbguichet()";
    }

    @Test
    void toC() {
        Monde monde = new Monde();
        Etape se1 = new Activite("act2");
        monde.ajouter(se1);
        monde.aCommeEntree(se1);
        monde.aCommeSortie(se1);

        String s3 = "";
        String s1 = "#include <stdio.h> \n" +
                "#include <stdlib.h>\n" +
                "#include \"def.h\"\n";
        String s2 = "void simulation(int ids)\n" +
                "{\n";
        s3 = s1+s2+"entrer("+monde.getEn().getNum()+");\n"+"delai("+ monde.getEn().getTemps() + "," + monde.getEn().getEcartTemps() + ");\n"+"transfert(" + monde.getEn().getNum() + ","+ se1.getNum() +");\n"+"delai("+ se1.getTemps() + "," + se1.getEcartTemps() + ");\n"+"transfert(" + se1.getNum() + ","+ monde.getSo().getNum() +");\n"+"}";
        boolean b = monde.toC().equals(s3);
        assert(b):"error fonction ToC dans monde";
    }
}