package twisk.vues;

import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import javafx.scene.shape.Circle;
import twisk.Ecouteur.EcouteurPntDeCntrl;


public class VuePointDeControleIG extends Circle implements Observateur {
    private MondeIG monde;
    private PointDeControleIG pointDeControleIG;

    public VuePointDeControleIG(PointDeControleIG p, MondeIG monde)   {
        this.pointDeControleIG = p;
        this.monde = monde;
        this.setCenterX(p.getX());
        this.setCenterY(p.getY());
        this.setRadius(5.0);
        this.setOnMouseClicked(new EcouteurPntDeCntrl(monde,p));
    }

    @Override
    public void reagir() {}
}
