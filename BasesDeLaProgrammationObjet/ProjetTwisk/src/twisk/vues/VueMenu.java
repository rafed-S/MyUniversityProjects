package twisk.vues;

import twisk.mondeIG.MondeIG;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import twisk.Ecouteur.*;

public class VueMenu extends MenuBar implements Observateur{

    private MondeIG monde;
    Menu fichier = new Menu("Fichier");
    Menu edition = new Menu("Edition");
    Menu mnd = new Menu("Monde");
    Menu param = new Menu("Paramètres");
    public VueMenu(MondeIG monde) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        fichier = new Menu("Fichier");
        edition = new Menu("Edition");
        mnd = new Menu("Monde");
        param = new Menu("Paramètres");
        MenuItem quit = new MenuItem("Quitter");
        MenuItem supprimer = new MenuItem("Supprimer la sélection");
        MenuItem renommer = new MenuItem("Renommer une étape");
        MenuItem effacer = new MenuItem("Effacer");
        MenuItem entree = new MenuItem("Entree");
        MenuItem sortie = new MenuItem("Sortie");
        MenuItem delai = new MenuItem("Modifier le delai");
        MenuItem ecart = new MenuItem("Modifier l'ecart");
        MenuItem nbJetons = new MenuItem("Modifier le nombre de jetons");
        MenuItem nbClients = new MenuItem("Nombre de clients");

        quit.setOnAction(new EcouteurQuitter(monde));
        supprimer.setOnAction(new EcouteurSuppSelect(monde));
        renommer.setOnAction(new EcouteurRename(monde));
        effacer.setOnAction(new EcouteurEffacer(monde));
        entree.setOnAction(new EcouteurEntree(monde));
        sortie.setOnAction(new EcouteurSortie(monde));
        delai.setOnAction(new EcouteurDelais(monde));
        ecart.setOnAction(new EcouteurEcart(monde));
        nbJetons.setOnAction(new EcouteurNbJetons(monde));
        nbClients.setOnAction(new EcouteurNbClients(monde));
        fichier.getItems().add(quit);
        edition.getItems().add(supprimer);
        edition.getItems().add(renommer);
        edition.getItems().add(effacer);
        mnd.getItems().add(entree);
        mnd.getItems().add(sortie);
        param.getItems().addAll(delai,ecart,nbClients,nbJetons);
        this.getMenus().addAll(fichier,edition,mnd,param);
    }

    public VueMenu(Menu... menus) {
        super(menus);
    }

    @Override
    public void reagir() {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                if(monde.isSimualtionActive())
                {
                    fichier.setDisable(true);
                    edition.setDisable(true);
                    mnd.setDisable(true);
                    param.setDisable(true);
                }
                else
                {
                    fichier.setDisable(false);
                    edition.setDisable(false);
                    mnd.setDisable(false);
                    param.setDisable(false);
                }
            }
        };
        if(Platform.isFxApplicationThread())
        {
            command.run();
        }
        else
        {
            Platform.runLater(command);
        }
    }
}
