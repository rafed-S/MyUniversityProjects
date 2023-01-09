package twisk.Ecouteur;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;

import java.util.Optional;

public class EcouteurRename implements EventHandler<ActionEvent> {
    MondeIG monde;

    public EcouteurRename(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TextInputDialog txt = new TextInputDialog();
        txt.setTitle("Renommer une activité");
        txt.setHeaderText("Entrez un nouveau nom");
        txt.setContentText("Veuillez entrer le nouveau nom : ");
        String s = "";
        if(monde.getNbrEtpSelect() == 1) {
            Optional<String> result = txt.showAndWait();
            if (result.isPresent()) {
                TextField d = txt.getEditor();
            if (!d.getText().equals("")) {
                monde.RenommerEtp(d.getText());
                monde.deselectEtapes();
            }
        }
        }
        else if (monde.getNbrEtpSelect() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez sélectionner une activité !").getMessage());
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez sélectionner une seul activité !").getMessage());
            alert.show();
            PauseTransition p = new PauseTransition(Duration.seconds(3));
            p.setOnFinished(ev -> alert.close());
            p.play();
        }
    }
}
