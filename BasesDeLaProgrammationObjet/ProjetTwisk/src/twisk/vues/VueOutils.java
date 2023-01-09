package twisk.vues;

import twisk.mondeIG.MondeIG;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import twisk.Ecouteur.EcouteurB;
import twisk.Ecouteur.EcouteurG;
import twisk.Ecouteur.EcouteurStartSimulation;
import twisk.TailleComposants;

public class VueOutils extends TilePane implements Observateur {
    private MondeIG monde;
    private Button bta ;
    private Button btg ;
    private Button btStartStopSmulation ;

    public VueOutils(MondeIG monde) {
        this.monde = monde;
        monde.ajouterObservateur(this);
        bta = new Button("");
        btg = new Button("");
        btStartStopSmulation = new Button("");
        bta.setPadding(new Insets(10, 10, 10, 10));
        btg.setPadding(new Insets(10, 10, 10, 10));
        btStartStopSmulation.setPadding(new Insets(10, 10, 10, 10));
        TailleComposants tt = TailleComposants.getInstance();
        TailleComposants tt1 = TailleComposants.getInstance();
        bta.setMinHeight(tt.getSizeButtonHeight());
        bta.setMinWidth(tt.getSizeButtonWidth());
        btg.setMinHeight(tt1.getSizeButtonHeight());
        btg.setMinWidth(tt1.getSizeButtonWidth());
        btStartStopSmulation.setMinHeight(tt1.getSizeButtonHeight());
        btStartStopSmulation.setMinWidth(tt1.getSizeButtonWidth());
        Image image = new Image(getClass().getResourceAsStream("/image/plus.png")) ;
        Image image1 = new Image(getClass().getResourceAsStream("/image/PlusGuichet.png"));
        Image image2 = new Image(getClass().getResourceAsStream("/image/start1.png"));
        Image image3 = new Image(getClass().getResourceAsStream("/image/pause1.png"));
        ImageView icon = new ImageView(image);
        ImageView icon1 = new ImageView(image1);
        ImageView icon2 = new ImageView(image2);
        ImageView icon3 = new ImageView(image3);
        bta.setGraphic(icon);
        btg.setGraphic(icon1);
        btStartStopSmulation.setGraphic(icon2);
        bta.setOnAction(new EcouteurB(monde));
        btg.setOnAction(new EcouteurG(monde));
        btStartStopSmulation.setOnAction(new EcouteurStartSimulation(monde));
        Tooltip a = new Tooltip("cliqué ici pour commencer la simulation");
        Tooltip aa = new Tooltip("appuyé ici pour ajouter une activité");
        Tooltip aa1 = new Tooltip("appuyé ici pour ajouter une guichet");
        bta.setTooltip(aa);
        btg.setTooltip(aa1);
        btStartStopSmulation.setTooltip(a);
        this.getChildren().addAll(bta,btg,btStartStopSmulation);
    }

    @Override
    public void reagir() {
        Tooltip a = new Tooltip("cliqué ici pour commencer la simulation");
        Tooltip b = new Tooltip("cliqué ici pour arreter la simulation");
        Image image2 = new Image(getClass().getResourceAsStream("/image/start1.png"));
        Image image3 = new Image(getClass().getResourceAsStream("/image/pause1.png"));
        ImageView icon2 = new ImageView(image2);
        ImageView icon3 = new ImageView(image3);
        Pane panneau = this;
        Runnable command = new Runnable() {
            @Override
            public void run() {
                if(monde.isSimualtionActive())
                {
                    bta.setDisable(true);
                    btg.setDisable(true);
                    btStartStopSmulation.setTooltip(b);
                    btStartStopSmulation.setGraphic(icon3);
                }
                else
                {
                    bta.setDisable(false);
                    btg.setDisable(false);
                    btStartStopSmulation.setTooltip(a);
                    btStartStopSmulation.setGraphic(icon2);
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
