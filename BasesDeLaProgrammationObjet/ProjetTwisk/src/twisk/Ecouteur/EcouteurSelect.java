package twisk.Ecouteur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class EcouteurSelect implements EventHandler<MouseEvent> {
    private MondeIG monde;
    private EtapeIG etp;
    public EcouteurSelect(MondeIG monde , EtapeIG etp) {
        this.monde = monde;
        this.etp = etp;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(!monde.isSimualtionActive())
        monde.AjoutSelect(this.etp);
    }

}
