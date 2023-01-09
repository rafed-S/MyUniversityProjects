package twisk.Ecouteur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;

public class EcouteurSelectArc implements EventHandler<MouseEvent> {
    private MondeIG monde;
    private ArcIG arc;
    private String p1;
    private String p2;
    public EcouteurSelectArc(MondeIG monde , ArcIG arc, String p1,String p2) {
        this.monde = monde;
        this.arc = arc;
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

if(!monde.isSimualtionActive())
        monde.AjoutSelectArc(this.arc, this.p1,this.p2);

    }

}
