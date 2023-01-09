package twisk.mondeIG;

public class ActiviteIG extends EtapeIG {
    private double delai;
    private double ecartTemp;

    public ActiviteIG(String nom, String idf, int haut, int larg) {
        super(nom, idf, haut, larg);
        this.delai = 4.0;
        this.ecartTemp = 2.0;
        this.addPntControl();
    }

    //**----------------- Getters -----------------**//

    public double getDelai() {
        return delai;
    }
    public double getEcartTemp() {
        return ecartTemp;
    }

    //**----------------- setters -----------------**//

    public void setDelai(double delai) {
        this.delai = delai;
    }
    public void setEcartTemp(double ecartTemp) {
        this.ecartTemp = ecartTemp;
    }
    public boolean estUneActivite() {return true;}

    //**----------------------------------**//

    public boolean estUneActiviteRestreinte()
    {
        return false;
    }
    public void addPntControl(){
        double x1 = this.posX + this.getHauteur();
        double x2 = this.posX;
        double Y1 = this.posY;
        double Y2 = this.posY + this.getLargeur();
        double h1 = this.largeur / 2.0;
        double w1 = this.hauteur / 2.0;

        pntDeCntr[0] = new PointDeControleIG(x1, this.posY + h1, "1-" + this.identifiant, this);
        pntDeCntr[1] = new PointDeControleIG(x2, this.posY + h1, "2-" + this.identifiant, this);
        pntDeCntr[2] = new PointDeControleIG(this.posX + w1, Y1, "3-" + this.identifiant, this);
        pntDeCntr[3] = new PointDeControleIG(this.posX + w1, Y2, "4-" + this.identifiant, this);
    }
    public void ModifPntControl()
    {
        double x1 = this.posX + this.getHauteur();
        double x2 = this.posX;
        double Y1 = this.posY;
        double Y2 = this.posY + this.getLargeur();
        double h1 = this.largeur / 2.0;
        double w1 = this.hauteur / 2.0;
        pntDeCntr[0].ModifCentre(x1, this.posY + h1);
        pntDeCntr[1].ModifCentre(x2, this.posY + h1);
        pntDeCntr[2].ModifCentre(this.posX + w1, Y1);
        pntDeCntr[3].ModifCentre(this.posX + w1, Y2);
    }
}
