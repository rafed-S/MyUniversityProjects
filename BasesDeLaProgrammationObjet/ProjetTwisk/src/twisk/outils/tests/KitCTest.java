package twisk.outils.tests;

import org.junit.jupiter.api.Test;
import twisk.outils.KitC;

import java.io.File;

public class KitCTest {
    @Test
    void creerEnvironnement() {
        KitC k = new KitC();
        k.creerEnvironnement();
        File chemin = new File("/tmp/twisk") ;
        boolean estLa = chemin.exists() ;
        assert(estLa):"Prob au niveau de la creation de l'environnement";
    }

    @Test
    void creerFichier() {

        KitC k = new KitC();
        k.creerFichier("");
        File chemin = new File("/tmp/twisk/client.c") ;
        boolean estLa = chemin.exists() ;
        assert(estLa):"Prob au niveau de la creation du fichier";

    }

    @Test
    void creercompiler() {
        KitC k = new KitC();
        k.creerEnvironnement();
        k.creerFichier("");
        k.compiler();
    }

    @Test
    void construireLaLibrairie() {
        KitC k = new KitC();
        k.creerEnvironnement();
        k.creerFichier("");
        k.compiler();
        k.construireLaLibrairie();
    }


}
