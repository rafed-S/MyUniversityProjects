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

public class EcouteurEcart implements EventHandler<ActionEvent> {
    MondeIG monde;
    public EcouteurEcart(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TextInputDialog txt = new TextInputDialog();
        txt.setTitle("Modifier l'ecart d'une activité");
        txt.setHeaderText("Entrez un nouveau ecart");
        txt.setContentText("Veuillez entrer le nouveau ecart : ");
        String s = "";
        if (monde.getNbrEtpSelect() == 1) {
            if (monde.getActSelect(0).estUnGuichet()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setContentText(new TwiskException("Veuillez sélectionner une activité et non pas un guichet !").getMessage());
                alert.showAndWait();
            }else{
            Optional<String> result = txt.showAndWait();
            if (result.isPresent()) {
                TextField d = txt.getEditor();
                if (!d.getText().equals("")) {
                    try {
                        monde.ChangeEcart(d.getText());
                        monde.deselectEtapes();
                    } catch (ParamException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                        handle(actionEvent);
                    }
                }else{
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
            alert.setContentText(new TwiskException("Veuillez sélectionner une activité !").getMessage());
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez sélectionner une seul activité !").getMessage());
            alert.showAndWait();
        }
    }
}