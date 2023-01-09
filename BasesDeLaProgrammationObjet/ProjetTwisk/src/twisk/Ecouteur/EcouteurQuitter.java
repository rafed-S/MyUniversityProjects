package twisk.Ecouteur;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.MondeIG;

public class EcouteurQuitter implements EventHandler<ActionEvent> {
    MondeIG monde;

    public EcouteurQuitter(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Platform.exit();
        monde.stopSimulation();
    }

}
