package stamps.excpetions;

import javafx.scene.control.Alert;

import java.text.ParseException;

public class AfficheErreur extends Exception {
    public AfficheErreur(String erruer, ParseException d) {
        super(erruer, d);
    }

    public void Maivais(){
        Alert info = new Alert(Alert.AlertType.ERROR);
        info.setTitle("Erreur de saisie");
        info.setHeaderText(null);
        info.setContentText("Ce que vous venez de saisir n'est pas correct veuillez ressaisir");
        info.showAndWait();
    }
}
