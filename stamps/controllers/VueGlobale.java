package stamps.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import stamps.models.Livres;
import stamps.models.Stamps;

public class VueGlobale implements Observateur {

    private Stamps stamps;

    @FXML
    private BorderPane gorus;

    @FXML
    private ScrollPane orta;

    @FXML
    private Pane asagi;

    public VueGlobale(Stamps stamps) {
        this.stamps = stamps;
        this.stamps.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        int moliere = 0;

        for (Livres livres : stamps) {
            VueInfo info = new VueInfo(stamps, livres);

            if (livres.isBilgiler()) {
                info.getVueInfo().setLeft(new CouvertureLivre(stamps, livres).getCouvertureBouquin());
                gorus.getChildren().removeAll(orta, asagi);
                gorus.setCenter(info.getVueInfo());
            } else {
                moliere++;
            }

            if (moliere == stamps.getNumero()) {
                gorus.getChildren().remove(info.getVueInfo());
                gorus.setCenter(orta);
                gorus.setBottom(asagi);
            }
        }
    }
}
