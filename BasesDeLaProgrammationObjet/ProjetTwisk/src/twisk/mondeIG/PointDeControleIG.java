package twisk.mondeIG;

public class PointDeControleIG {
    private double x;
    private double y;
    private String identif;
    private EtapeIG etp;

    public PointDeControleIG(double x, double y, String identif, EtapeIG etp) {
        this.x = x;
        this.y = y;
        this.identif = identif;
        this.etp = etp;
    }

    //**----------------- Getters -----------------**//

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public String getIdentif() {
        return identif;
    }
    public EtapeIG getEtp() {
        return etp;
    }
    public String getIdentifEtp() {
        return this.etp.getIdentifiant();
    }

    //**----------------------------------**//

    public void ModifCentre(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}
