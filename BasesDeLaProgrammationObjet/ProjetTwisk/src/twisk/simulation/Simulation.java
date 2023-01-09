package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Monde;
import twisk.mondeIG.SujetObservé;
import twisk.outils.KitC;

import java.util.Iterator;

public class Simulation extends SujetObservé implements Iterable<Client>{
    public Monde monde;
    private KitC kitC;
    private int nbClients = 9;
    private GestionnaireClients gs;

    public Simulation(){
        super();
        kitC = new KitC();
        kitC.creerEnvironnement();
        this.gs = new GestionnaireClients();
    }

    public void simuler(Monde monde) {
        try {
            kitC.creerFichier(monde.toC());
            kitC.compiler();
            kitC.construireLaLibrairie();
            System.load("/tmp/twisk/libTwisk"+kitC.getCptt()+".so") ;

            int [] tabJetonsGuichet = new int [monde.nbGuichets()];
            int [] results;
            for(int i=0;i<monde.nbGuichets();i++)
            {
                tabJetonsGuichet[i] = monde.getGuichets(i+1).getNbjetons();
            }
            results = start_simulation(monde.nbEtapes(), monde.nbGuichets(), nbClients, tabJetonsGuichet);
            gs.setClients(results);
            notifierObservateurs();
            int[] nb;
            int d = 0;
            int nbSassortie = monde.getSo().getNum();
            do {
                int cptt=0;
                int h = 0;
                int EtapeAct = 0;

                nb = ou_sont_les_clients(monde.nbEtapes(), nbClients);

                while(cptt<nbClients*monde.nbEtapes()+monde.nbEtapes()) {
                    Etape etp = monde.getEtp(EtapeAct);
                    if (nb[cptt] != 0) {
                        for (int y = 1; y < nbClients+1; y++){
                            if (nb[cptt + y] != 0)
                                gs.allerA(nb[cptt + y], etp, y);
                        }
                    }
                    cptt +=nbClients+1;
                    EtapeAct+=1;
                }
                notifierObservateurs();
                d = 0;

                for(int j = 0 ; j < monde.nbEtapes() ; j++)
                {
                    if(nb[d] !=0) {
                        for (int i = 1; i <nb[d]+1; i++) {
                        }
                    }
                    d = d + nbClients +1;
                }
                Thread.sleep(1500);
            } while (nb[(nbClients*nbSassortie) + nbSassortie] != nbClients) ;
            d = 0;
            this.nettoyage();
            gs.reset();
        } catch (InterruptedException e) {
            nettoyage();
        }
    }

    public void setNbClients(int i)
    {
        this.nbClients = i;
    }
    public GestionnaireClients getGs() {
        return gs;
    }
    public native int[]  start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetons);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();
    @Override
    public Iterator<Client> iterator() {
        return gs.iterator();
    }
}
