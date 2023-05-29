package stamps.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import stamps.models.Livres;
import stamps.models.Stamps;

public class DonneeLivre implements Observateur {

    private Stamps stamps;

    private Livres bouquin;

    @FXML
    private VBox detail;

    @FXML
    private Label detailDonnee;

    @FXML
    private Label detailMotsClee;

    @FXML
    private Label detailResumer;

    @FXML
    private Text donnee;

    @FXML
    private Text genre;

    @FXML
    private StackPane infoDonnee;

    @FXML
    private StackPane infoMotsClee;

    @FXML
    private StackPane infoResumer;

    @FXML
    private Text resumer;

    public DonneeLivre(Stamps stamps, Livres bouquin){
        this.stamps = stamps;
        this.bouquin = bouquin;

    }

    public void initialize(){
        this.resumer.setText(bouquin.getOzet());
        this.detailMotsClee.setText(bouquin.getKitapIsmKelime(bouquin.getKitapIsim()));

        StringBuilder detailBouquin = new StringBuilder();
        detailBouquin.append("Auteur : ").append(bouquin.getYazar()).append("\n");
        detailBouquin.append(" Publier le : ").append(bouquin.getCikisTarihi()).append("\n");
        detailBouquin.append(bouquin.getBaska());
        this.detailDonnee.setText(detailBouquin.toString());

        if (stamps.enModif()){
            detail.getChildren().removeAll(infoDonnee, infoMotsClee, infoResumer, resumer, donnee, genre, detailResumer, detailDonnee, detailMotsClee);

            TextArea changerResumer = new TextArea(bouquin.getOzet());
            TextArea changerEpoque = new TextArea(bouquin.getCikisTarihi());
            TextArea changerEcrivan = new TextArea(bouquin.getYazar());
            TextArea changerQuelqueChose = new TextArea(bouquin.getBaska());

            ComboBox<String> differentsGenre = new ComboBox<>();
            TextArea yeniKelime = new TextArea();

            differentsGenre.getItems().addAll(FXCollections.observableArrayList(stamps.getKitapIsim()));

            changerResumer.setPromptText("Le resumer du livre");
            changerEpoque.setPromptText("La date de publication");
            changerEcrivan.setPromptText("Le nom de l'ecrivain");
            changerQuelqueChose.setPromptText("Une autre donnée a saisir ?");
            yeniKelime.setPromptText("Autres genre ?");
            Button saisir = new Button("+");

            changerResumer.setPrefWidth(detail.getPrefWidth());
            changerEpoque.setPrefWidth(detail.getPrefWidth());
            changerEcrivan.setPrefWidth(detail.getPrefWidth());
            changerQuelqueChose.setPrefWidth(detail.getPrefWidth());
            saisir.setPrefWidth(detail.getPrefWidth());
            yeniKelime.setPrefWidth(detail.getPrefWidth());
            differentsGenre.setPrefWidth(detail.getPrefWidth());

            saisir.setOnAction(b -> {
                bouquin.addGenreWord(yeniKelime.getText());
            });

            differentsGenre.setOnAction(b -> {
                String ama = differentsGenre.getSelectionModel().getSelectedItem();
                bouquin.addGenreWord(ama);
            });

            changerResumer.setWrapText(true);

            detail.getChildren().addAll(detailResumer, changerResumer, detailDonnee, changerEpoque, changerEcrivan, changerQuelqueChose, detailMotsClee, differentsGenre, yeniKelime, saisir);
        }
    }

    @Override
    public void reagir() {

    }
}
