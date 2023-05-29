package stamps.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import stamps.Controller;
import stamps.models.Livres;
import stamps.models.Stamps;

public class controllersEdition implements Observateur {

    private Stamps stamps;

    @FXML
    private Pane controllersEdition;

    @FXML
    private Button dumeDuzle;

    @FXML
    private Button dumeEkle;

    public controllersEdition(Stamps stamps){
        this.stamps = stamps;
        this.stamps.ajouterObservateur(this);
    }

    @FXML
    public void addLivre(){
        this.stamps.addLivre(new Livres("Aucun livre"));
    }

    @FXML
    public void inittialize(){
        ContextMenu choix = new ContextMenu();

        MenuItem trierParEcrivain = new MenuItem("Trier par ecrivain");
        MenuItem trierParPublicationBasHAut = new MenuItem("Trier par date croissant");
        MenuItem trierParPublicationHautBas = new MenuItem("Trier par date decroissant");
        MenuItem trierParIsimBasHaut = new MenuItem("Trier par titre croissant");
        MenuItem trierParIsimHautBas = new MenuItem("Trier par titre decroissant");

        choix.getItems().addAll(trierParEcrivain, trierParPublicationBasHAut, trierParPublicationHautBas, trierParIsimBasHaut, trierParIsimHautBas);

        trierParEcrivain.setOnAction(new EventHandler<ActionEvent>() {
                                         @Override
                                         public void handle(ActionEvent actionEvent) {
                                             stamps.afficherParOrdreEcrivain();
                                         }
                                     }
        );

        trierParPublicationBasHAut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stamps.afficherParOrdreDateDebut();
            }
        });

        trierParPublicationHautBas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stamps.afficherParOrdreDateFin();
            }
        });

        trierParIsimBasHaut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stamps.afficherNameParOrdreDebut();
            }
        });

        trierParIsimHautBas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stamps.afficherNameParOrdreFin();
            }
        });

        dumeDuzle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getSource() instanceof Button){
                    Button dume = (Button) mouseEvent.getSource();
                    choix.show(dume, mouseEvent.getScreenX(), mouseEvent.getSceneY());
                }
            }
        });

        choix.setOnShown(event ->{
            if (event.getSource() instanceof ContextMenu){
                ContextMenu pli = (ContextMenu) event.getSource();
                if(pli.getOwnerNode() instanceof Button){
                    Button click = (Button) pli.getOwnerNode();
                    double x = click.getScene().getWindow().getX() + click.getBoundsInParent().getMaxX();
                    double y = click.getScene().getWindow().getY() + click.getBoundsInParent().getMaxY();
                    pli.show(click, x , y);
                }
            }
        });

        if(stamps.enVision()){
            controllersEdition.getChildren().remove(dumeEkle);
        }


    }

    @Override
    public void reagir() {
        if (stamps.enVision()){
            controllersEdition.getChildren().remove(dumeEkle);
        } else if (stamps.enModif()) {
            controllersEdition.getChildren().clear();
            controllersEdition.getChildren().add(dumeEkle);
            controllersEdition.getChildren().add(dumeDuzle);
        }
    }
}
