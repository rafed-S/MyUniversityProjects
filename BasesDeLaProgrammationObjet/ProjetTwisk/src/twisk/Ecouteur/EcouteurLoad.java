package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.MondeIG;

public class EcouteurLoad implements EventHandler<ActionEvent> {
    private MondeIG monde;

    public EcouteurLoad(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //monde.charger("TestSauvegarde.txt");
    }
}

