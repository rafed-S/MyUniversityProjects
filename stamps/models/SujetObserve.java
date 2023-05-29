package stamps.models;

import stamps.controllers.Observateur;

import java.util.ArrayList;

public class SujetObserve {
    protected ArrayList<Observateur> obs = new ArrayList<>();

    public SujetObserve(){
        for (Observateur v: obs) {
            ajouterObservateur(v);
        }
        notifierObservateurs();
    }

    public void ajouterObservateur(Observateur v){
        this.obs.add(v);
    }

    public void notifierObservateurs(){
        for (Observateur v: this.obs) {
            v.reagir();
        }
    }
}
