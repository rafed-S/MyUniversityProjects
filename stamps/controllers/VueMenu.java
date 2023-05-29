package stamps.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import stamps.models.Stamps;

import java.io.File;
import java.io.IOException;

public class VueMenu implements Observateur{
    private Stamps stamps;

    @FXML
    private MenuBar menuBar;

    public VueMenu(Stamps stamps){
        this.stamps = stamps;
    }

    @FXML
    void estEnConsultation(ActionEvent event) {
        this.stamps.setEnModif(false);
        this.stamps.setEnVision(true);
    }

    @FXML
    void estEnEdition(ActionEvent event) {
        this.stamps.setEnVision(false);
        this.stamps.setEnModif(true);
    }

    @FXML
    void importerFile(ActionEvent event) {
        FileChooser file = new FileChooser();
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("fichier Json", "*.json", "*.JSON"));
        File fichierChoisi = file.showOpenDialog(null);
        stamps.ouvrir(fichierChoisi);
    }

    @FXML
    void saved(ActionEvent event) {
        try{
            FileChooser file = new FileChooser();
            file.setTitle("Enregistrer le livre");
            FileChooser.ExtensionFilter extensin = new FileChooser.ExtensionFilter("fichier Json (*.json)", "*.json");
            file.getExtensionFilters().add(extensin);

            File file1 = file.showSaveDialog(null);
            stamps.enregistrerSatmps(file1.getAbsolutePath());
        }catch (IOException ext){
            ext.printStackTrace();
        }
    }

    @FXML
    void sortir(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void reagir() {

    }
}
