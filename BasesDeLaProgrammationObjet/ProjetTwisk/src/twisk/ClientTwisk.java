package twisk;

import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.FabriqueNumero;
import twisk.simulation.Simulation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {


    public ClientTwisk() {
        Monde monde = new Monde();
        Activite zoo = new Activite("balade au zoo", 3, 1);
        Guichet guichet = new Guichet("acces au toboggan", 3);
        Activite tob = new ActiviteRestreinte("toboggan", 2, 1);
        Guichet guichet1 = new Guichet("acces au toboggan1", 1);
        Activite tob1 = new ActiviteRestreinte("toboggan1", 2, 1);
        zoo.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(tob);
        tob.ajouterSuccesseur(guichet1);
        guichet1.ajouterSuccesseur(tob1);
        monde.ajouter(zoo,guichet1, tob,tob1,guichet);
        monde.aCommeEntree(zoo);
        monde.aCommeSortie(tob1);
        ClassLoaderPerso c = new ClassLoaderPerso(this.getClass().getClassLoader());
        try {
            Class<?> clSimulation = c.loadClass("twisk.simulation.Simulation");
            Object ob = clSimulation.newInstance();
            Method methdSetNbClients = clSimulation.getDeclaredMethod("setNbClients", int.class) ;
            methdSetNbClients.invoke(ob,2) ;
            Method methodSimuler = clSimulation.getDeclaredMethod("simuler", twisk.monde.Monde.class) ;
            methodSimuler.invoke(ob,monde) ;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        FabriqueNumero s = FabriqueNumero.getInstance();
        s.reset();
        Monde monde1 = new Monde();
        Etape etape11 = new Activite("musee") ;
        Etape etape21 = new Activite("boutique") ;
        Etape etape51 = new Activite("b2") ;
        Guichet guichet11 = new Guichet("Guichet111", 2) ;
        Etape act11 = new ActiviteRestreinte("toboggan", 2, 1) ;
        Etape etape31 = new Activite("Toboggan2") ;
        Etape etape41 = new Activite("Toboggan3") ;
        etape11.ajouterSuccesseur(etape21,etape51) ;
        etape21.ajouterSuccesseur(guichet11) ;
        guichet11.ajouterSuccesseur(act11);
        act11.ajouterSuccesseur(etape31);
        etape31.ajouterSuccesseur(etape41);
        monde1.ajouter(etape11, etape21) ;
        monde1.ajouter(guichet11) ;
        monde1.ajouter(act11) ;
        monde1.ajouter(etape31) ;
        monde1.ajouter(etape41) ;
        monde1.aCommeEntree(etape11);
        monde1.aCommeSortie(etape41,etape51) ;
        ClassLoaderPerso c1 = new ClassLoaderPerso(this.getClass().getClassLoader());
        try {
            Class<?> clSimulation = c1.loadClass("twisk.simulation.Simulation");
            Object ob = clSimulation.newInstance();
            Method methdSetNbClients = clSimulation.getDeclaredMethod("setNbClients", int.class) ;
            methdSetNbClients.invoke(ob,2) ;
            Method methodSimuler = clSimulation.getDeclaredMethod("simuler", twisk.monde.Monde.class) ;
            methodSimuler.invoke(ob,monde1) ;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientTwisk cl = new ClientTwisk();
    }
}

