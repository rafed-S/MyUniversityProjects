package twisk.Ecouteur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import twisk.exceptions.ParamException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;

import java.util.Optional;

public class EcouteurNbJetons implements EventHandler<ActionEvent> {
    MondeIG monde;
    public EcouteurNbJetons(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TextInputDialog txt = new TextInputDialog();

        txt.setTitle("Modifier le nombre de jetons d'un guichet");
        txt.setHeaderText("Entrez un nouveau nombre de jetons");
        txt.setContentText("Veuillez entrer le nouveau nombre de jetons : ");
        String s = "";
        if(monde.getNbrEtpSelect() == 1) {
            if (!monde.getActSelect(0).estUnGuichet()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setContentText(new TwiskException("Veuillez sélectionner un guichet et non pas une activité !").getMessage());
                alert.showAndWait();
            }
            else {
            Optional<String> result = txt.showAndWait();
            if (result.isPresent()) {
                TextField d = txt.getEditor();
                if (!d.getText().equals("")) {
                    try {
                        monde.ChangenbJetons(d.getText());
                        monde.deselectEtapes();

                    } catch (ParamException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();

                    }
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION");
                    alert.setContentText(new TwiskException("Veuillez saisir un param !! ").getMessage());
                    alert.showAndWait();
                    handle(actionEvent);

                }
            }
        }
        }
        else if (monde.getNbrEtpSelect() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez sélectionner un guichet !").getMessage());
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez sélectionner un seul guichet !").getMessage());
            alert.showAndWait();
        }
    }
}