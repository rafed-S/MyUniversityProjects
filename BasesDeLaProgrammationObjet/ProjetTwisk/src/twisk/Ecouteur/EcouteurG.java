package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.MondeIG;

public class EcouteurG implements EventHandler<ActionEvent>{
    private MondeIG monde;

    public EcouteurG (MondeIG monde) {
        this.monde = monde;

    }
    public void handle(ActionEvent event) {
        this.monde.ajouter("Guichet");
    }
}