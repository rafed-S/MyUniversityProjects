package twisk.mondeIG;

import twisk.vues.Observateur;
import java.util.ArrayList;

public class SujetObserv√© {
    protected ArrayList<Observateur> vues ;

    public SujetObserv√©() {
        this.vues = new ArrayList<Observateur>();
    }
    public void ajouterObservateur(Observateur v)
    {
        vues.add(v);
    }
    public void notifierObservateurs ()
    {
        for (Observateur v : this.vues) v.reagir() ;
    }

}
