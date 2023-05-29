package stamps.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Builder;
import stamps.models.Livres;
import stamps.models.Stamps;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class VueInfo implements Observateur {

    private Stamps stamps;
    private Livres bouquin;
    protected TextArea insereInfo;

    @FXML
    private BorderPane afficheInfo;

    @FXML
    private HBox accueilSaved;

    @FXML
    private Button accueilPage;

    @FXML
    private Button enregistrer;

    @FXML
    private HBox avInfoApre;

    @FXML
    private Button avant;

    @FXML
    private Label kitabinIsmi;

    @FXML
    private Button apres;

    @FXML
    private VBox detail;

    public VueInfo(Stamps stamps, Livres bouquin) {
        this.stamps = stamps;
        this.bouquin = bouquin;
    }

    public BorderPane getVueInfo() {
        try {
            FXMLLoader infoFichier = new FXMLLoader(getClass().getResource("/stamps/vues/VueDetail.fxml"));

            DonneeLivre manathan = new DonneeLivre(stamps, bouquin);
            VueInfo info = new VueInfo(stamps, bouquin);

            infoFichier.setBuilderFactory(type -> {
                if (type.equals(VueInfo.class)) {
                    return (Builder<?>) info;
                } else if (type.equals(DonneeLivre.class)) {
                    return (Builder<?>) manathan;
                } else {
                    return null;
                }
            });

            afficheInfo = infoFichier.load();

        } catch (IOException affiche) {
            affiche.printStackTrace();
        }

        CouvertureLivre kitap = new CouvertureLivre(stamps, bouquin);

        kitap.isLarg(200);
        kitap.isHeight(200);

        if (Objects.equals(bouquin.getResminYolu(), "/.add.png")) {
            kitap.isLarg(100);
            kitap.isHeight(100);
        }

        AnchorPane anch = new AnchorPane(kitap.getCouvertureBouquin());
        afficheInfo.setLeft(anch);

        if (stamps.enModif()) {
            Button ajouter = new Button("Ajoutez une couverture");
            ajouter.setPrefWidth(20);

            anch.getChildren().add(ajouter);
            anch.getChildren().get(1).setLayoutX(90);
            anch.getChildren().get(1).setLayoutY(490);

            ajouter.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stamps.resimDegistir(bouquin, picture());
                }
            });
        }

        BorderPane.setMargin(anch, new Insets(50, 90, 90, 90));

        return afficheInfo;
    }

    private String picture() {
        FileChooser picture1 = new FileChooser();
        picture1.setTitle("Inserer une couverture");
        picture1.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Type fichier", "*.jpeg", "*.png", "*.jpg"));

        File active = picture1.showOpenDialog(null);
        if (active != null) {
            try {
                String s = active.getAbsolutePath();
                URL l = new URL("document " + s);

                return l.toString();
            } catch (MalformedURLException af) {
                af.printStackTrace();
            }
        }
        return bouquin.getResminYolu();
    }

    @FXML
    public void larousseCollection() {
        this.stamps.voirStamps();
    }

    @FXML
    public void enregisterLivre() {

    }

    @FXML
    public void previousBook() {
        this.stamps.previousBouquin();
    }

    @FXML
    public void next() {
        this.stamps.bouquinNext();
    }

    @FXML
    public void initialize() {
        this.kitabinIsmi.setText(bouquin.getIsim());
        if (stamps.enModif()) {
            insereInfo = new TextArea((bouquin.getIsim()));
            insereInfo.setPromptText("Saisir le titre du livre");
            insereInfo.setPrefWidth(kitabinIsmi.getPrefWidth());

            avInfoApre.getChildren().clear();
            avInfoApre.setSpacing(19);
            avInfoApre.getChildren().addAll(avant, insereInfo, apres);

            accueilSaved.getChildren().clear();
            accueilSaved.getChildren().addAll(accueilPage, enregistrer);
        } else {
            accueilSaved.getChildren().remove(enregistrer);
        }
    }

    @Override
    public void reagir() {

    }
}
