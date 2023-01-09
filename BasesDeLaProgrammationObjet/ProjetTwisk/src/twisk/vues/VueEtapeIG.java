package twisk.vues;


import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import twisk.Ecouteur.EcouteurDragSetect;


public abstract class VueEtapeIG extends VBox implements Observateur {
    protected MondeIG monde;
    protected Label lb ;
    protected EtapeIG etp;
    protected double x;
    protected double y;

    public VueEtapeIG(MondeIG monde, EtapeIG etp) {
        this.monde = monde;
        this.etp = etp;
        lb = new Label();
        this.setPadding(new Insets(6, 6, 6, 6));
        this.setOnDragDetected(new EcouteurDragSetect(monde,this));
    }

    //**----------------- Getters -----------------**//

    public String getNom()
    {
        String s = "";
        s = this.etp.getNom();
        return s;
    }
    public EtapeIG getEtp() {
        return etp;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    //**----------------- Setters -----------------**//

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
}
