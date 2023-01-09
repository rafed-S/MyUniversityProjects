package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.MondeIG;

public class EcouteurSave implements EventHandler<ActionEvent> {
    private MondeIG monde;
    public EcouteurSave(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //monde.sauvegarder("testsave");
    }
}
