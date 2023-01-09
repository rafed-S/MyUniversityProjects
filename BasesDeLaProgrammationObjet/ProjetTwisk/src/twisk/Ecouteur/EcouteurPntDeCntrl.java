package twisk.Ecouteur;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;


public class EcouteurPntDeCntrl implements EventHandler<javafx.scene.input.MouseEvent> {
   private MondeIG monde ;
   private PointDeControleIG p1;

    public EcouteurPntDeCntrl(MondeIG monde, PointDeControleIG p) {
        this.monde = monde;
        p1 = p;

    }

    @Override
    public void handle (MouseEvent mouseEvent) {
        if(!monde.isSimualtionActive()) {
            if (!this.monde.isFull()) this.monde.ajouterPntDeCntrl(p1);
            if (monde.isFull()) {
               try {
                   monde.ajouter(monde.getPntDeCntrl(0), monde.getPntDeCntrl(1));
               } catch (TwiskException e) {
                   e.printStackTrace();
                   monde.setFull(false);
                   monde.resetpnt1();
                   monde.resetpnt2();
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Erreur");
                   alert.setContentText(e.getMessage());
                   alert.show();
                   PauseTransition p = new PauseTransition(Duration.seconds(3));
                   p.setOnFinished(ev -> alert.close());
                   p.play();
               }
            }
        }
    }
}
