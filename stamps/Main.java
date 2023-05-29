package stamps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stamps.controllers.*;
import stamps.models.Livres;
import stamps.models.Stamps;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader livre = new FXMLLoader(Main.class.getResource("/stamps/vues/vueGlobale.fxml"));

        Stamps stamps = new Stamps();

        VueGlobale vG = new VueGlobale(stamps);
        VueInfo vI = new VueInfo(stamps, new Livres(" "));
        AfficheLivres aL = new AfficheLivres(stamps);
        VueMenu vM = new VueMenu(stamps);
        controllersEdition cE = new controllersEdition(stamps);
        CouvertureLivre cL = new CouvertureLivre(stamps, new Livres(""));
        DonneeLivre dL = new DonneeLivre(stamps, new Livres(""));
        //root = new BorderPane();
        //root.setCenter(new VueGlobale());

        livre.setControllerFactory(kbt -> {
            if (kbt.equals(Stamps.class)) {
                return stamps;
            } else if (kbt.equals(AfficheLivres.class)) {
                return aL;
            } else if (kbt.equals(VueGlobale.class)) {
                return vG;
            } else if (kbt.equals(VueMenu.class)) {
                return vM;
            } else if (kbt.equals(VueInfo.class)) {
                return vI;
            } else if (kbt.equals(controllersEdition.class)) {
                return cE;
            } else if (kbt.equals(CouvertureLivre.class)) {
                return cL;
            } else if (kbt.equals(DonneeLivre.class)) {
                return dL;
            } else {
                return null;
            }
        });


        Scene scene = new Scene(livre.load(), 1400, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stamps Livres");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
