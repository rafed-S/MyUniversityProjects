package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.MondeIG;

public class EcouteurSuppSelect implements EventHandler<ActionEvent> {
    MondeIG monde;

    public EcouteurSuppSelect(MondeIG monde) {
        this.monde =  monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        monde.SuppSelect();
        monde.SuppSelectArc();

    }
}
