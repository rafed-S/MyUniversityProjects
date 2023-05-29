package stamps.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import stamps.models.Stamps;

public class VueAfficheLivres implements Observateur{

    private Stamps stamps;

    @FXML
    private ScrollPane afficheLivres;

    public VueAfficheLivres(Stamps stamps){
        this.stamps = stamps;
    }

    @FXML
    public void initialize(){
        afficheLivres.setStyle("-fx-background-color: gray" + "--fx-background-size: cover;");
    }

    @Override
    public void reagir() {

    }

}
