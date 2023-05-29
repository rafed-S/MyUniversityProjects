package stamps.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import stamps.controllers.CouvertureLivre;
import stamps.controllers.Observateur;
import stamps.models.Livres;
import stamps.models.Stamps;

public class AfficheLivres implements Observateur {

    private Stamps stamps;
    @FXML
    private GridPane livres;

    public AfficheLivres(Stamps stamps){
        this.stamps = stamps;
        this.stamps.ajouterObservateur(this);
    }

    @FXML
    public void initialize(){
        this.stamps.setNumero(livres.getChildren().size());

        int debut = 0;
        int nombre = stamps.getNumero();

        for (Livres bouquin : stamps){
            if (!bouquin.isBilgiler()){
                CouvertureLivre cl = new CouvertureLivre(stamps, bouquin);
                livres.add(cl.getCouvertureBouquin(), nombre % 6, debut);

                if (bouquin.resimdir()){
                    cl.setCouvertureBouquin(bouquin);
                }

                nombre ++;

                if (nombre % (6 * (debut + 1)) == 0){
                    livres.addRow(debut);
                    debut++;
                }
            }
        }
    }

    @Override
    public void reagir() {
        livres.getChildren().clear();

        int debut = 0;
        int nombre = stamps.getNumero();

        for (Livres bouquin : stamps){
            if (!bouquin.isBilgiler()){
                CouvertureLivre cl = new CouvertureLivre(stamps, bouquin);
                livres.add(cl.getCouvertureBouquin(), nombre % 6, debut);

                if (bouquin.resimdir()){
                    cl.setCouvertureBouquin(bouquin);
                }

                nombre ++;

                if (nombre % (6 * (debut + 1)) == 0){
                    livres.addRow(debut);
                    debut++;
                }
            }
        }
    }
}
