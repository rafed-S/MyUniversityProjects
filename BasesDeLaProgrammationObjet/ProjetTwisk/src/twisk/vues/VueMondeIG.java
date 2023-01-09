package twisk.vues;

import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.simulation.Client;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import twisk.Ecouteur.EcouteurDragDropped;
import twisk.Ecouteur.EcouteurDragOver;
import twisk.TailleComposants;

public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;
    private int e;

    public VueMondeIG(MondeIG monde) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        TailleComposants tt = TailleComposants.getInstance();
        this.setPrefSize(tt.getSizeMondeHeight(), tt.getSizeMondeWidth());
        VueEtapeIG gui1 = new VueGuichetIG(monde, monde.getEtapes("1"));
        VueEtapeIG act1 = new VueActiviteIG(monde, monde.getEtapes("0"));
        this.getChildren().add(act1);
        this.getChildren().add(gui1);
        e = 0;
        this.setOnDragOver(new EcouteurDragOver(this.monde));
        this.setOnDragDropped(new EcouteurDragDropped(this.monde));
    }

    @Override
    public void reagir() {
        Pane panneau = this;
        Runnable command = new Runnable() {
            @Override
            public void run() {
                panneau.getChildren().clear();
                VueEtapeIG[] act = new VueEtapeIG[monde.getNbrEtapes()];
                VuePointDeControleIG[] pntDeCntr = new VuePointDeControleIG[4];
                for (int i = 0; i < monde.getNbrArcs(); i++) {
                    VueArcIG varc = new VueArcIG(monde.getArcs(i), monde);
                    panneau.getChildren().add(varc);
                }
                for (int i = 0; i < monde.getNbrEtapes(); i++) {
                    if (monde.getEtapes("" + monde.getKey(i)) instanceof GuichetIG) {
                        act[i] = new VueGuichetIG(monde, monde.getEtapes("" + monde.getKey(i)));
                    }else{
                        act[i] = new VueActiviteIG(monde, monde.getEtapes("" + monde.getKey(i)));
                    }
                    if (!monde.getEtapes("" + monde.getKey(i)).estUnGuichet()) {
                        pntDeCntr[0] = new VuePointDeControleIG(monde.getEtapes("" + monde.getKey(i)).getPntDeCntr()[0], monde);
                        pntDeCntr[1] = new VuePointDeControleIG(monde.getEtapes("" + monde.getKey(i)).getPntDeCntr()[1], monde);
                        pntDeCntr[2] = new VuePointDeControleIG(monde.getEtapes("" + monde.getKey(i)).getPntDeCntr()[2], monde);
                        pntDeCntr[3] = new VuePointDeControleIG(monde.getEtapes("" + monde.getKey(i)).getPntDeCntr()[3], monde);
                        panneau.getChildren().addAll(act[i], pntDeCntr[0], pntDeCntr[1], pntDeCntr[2], pntDeCntr[3]);
                    }else{
                        pntDeCntr[0] = new VuePointDeControleIG(monde.getEtapes("" + monde.getKey(i)).getPntDeCntr()[0], monde);
                        pntDeCntr[1] = new VuePointDeControleIG(monde.getEtapes("" + monde.getKey(i)).getPntDeCntr()[1], monde);
                        panneau.getChildren().addAll(act[i], pntDeCntr[0], pntDeCntr[1]);
                    }
                    e = e + 10;
                }
                //**--------- les clients ------------**//
                if (monde.isSimualtionActive()) {
                    Iterator<Client> client = monde.IteratorClient();
                    if (client == null) return;
                    while (client.hasNext()) {
                        VueClient cl = new VueClient();
                        Client c = client.next();
                        if (monde.getEtape(c) != null) {
                            double a = monde.getXClients(c);
                            double b = monde.getYClients(c);
                            cl.setCenterX(a);
                            cl.setCenterY(b);
                            panneau.getChildren().add(cl);
                        }
                    }
                }
                e = 0;
            }
        };
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
        monde.setNb(0);
    }
}
