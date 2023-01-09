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

public class EcouteurNbClients implements EventHandler<ActionEvent> {

    private MondeIG monde;

    public EcouteurNbClients(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TextInputDialog txt = new TextInputDialog();
        txt.setTitle("Modifier le nombre de clients");
        txt.setHeaderText("Entrez le nombre de clients que vous voulez (entre 1 est 50)");
        txt.setContentText("Veuillez entrer le nombre de clients : ");
        String s = "";
        Optional<String> result = txt.showAndWait();
        if (result.isPresent()) {
            TextField d = txt.getEditor();
        if (!d.getText().equals("")) {
            try {
                monde.setNbClients(d.getText());

            } catch (ParamException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                handle(actionEvent);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText(new TwiskException("Veuillez saisir un numero !! ").getMessage());
            alert.showAndWait();
            handle(actionEvent);
        }

    }


    }
}
