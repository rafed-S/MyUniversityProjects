package twisk.mondeIG;
import twisk.outils.FabriqueIdentifiant;

public class GuichetIG extends EtapeIG {
    private int nbjetons;
    private String cptS;
    private int sensdecirculation ;

    public GuichetIG(String nom, String idf, int haut, int larg) {
        super(nom, idf, haut, larg);
        this.nbjetons = 9;
        FabriqueIdentifiant fab = FabriqueIdentifiant.getInstance();
        String idf1 = fab.getIdentifiantSema();
        this.cptS = idf1;
        this.addPntControl();
        this.sensdecirculation = 3;
    }

    //**----------------- Getters -----------------**//

    public int getNbjetons() {
        return this.nbjetons;
    }

    //**----------------- Setters -----------------**//

    public void setNbjetons(int nbjetons) {
        this.nbjetons = nbjetons;
    }

    //**----------------------------------**//

    public void addPntControl(){
        double x1 = this.posX + this.getHauteur();
        double x2 = this.posX;
        double h1 = this.largeur / 2.0;
        pntDeCntr[0] = new PointDeControleIG(x1, this.posY + h1, "1-" + this.identifiant, this);
        pntDeCntr[1] = new PointDeControleIG(x2, this.posY + h1, "2-" + this.identifiant, this);
    }
    public void ModifPntControl()
    {
        double x1 = this.posX + this.getHauteur()+100;
        double x2 = this.posX;
        double y = this.largeur / 3.0;
        pntDeCntr[0].ModifCentre(x1, this.posY + y);
        pntDeCntr[1].ModifCentre(x2, this.posY + y);
    }
    public boolean estUnGuichet() {return true;}
}
