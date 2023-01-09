package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;

public class EcouteurEntree implements EventHandler<ActionEvent> {
    MondeIG monde;

    public EcouteurEntree(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
          if (monde.getNbrEtpSelect() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez sélectionner au moins une activité !").getMessage());
            alert.showAndWait();
        }
          else {
              monde.ajouterEntree();
              monde.deselectEtapes();
          }
    }
}
