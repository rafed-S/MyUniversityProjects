package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import twisk.exceptions.MondeException;
import twisk.mondeIG.MondeIG;

public class EcouteurStartSimulation implements EventHandler<ActionEvent> {
    private MondeIG monde;

    public EcouteurStartSimulation(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            if(!monde.isSimualtionActive()) {
                monde.simuler();

            }
            else {
                monde.setSimualtionActive(false);
               monde.stopSimulation();
            }
        } catch (MondeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
}
