package twisk.monde;
import twisk.outils.FabriqueNumero;

public class Guichet extends Etape{
    private int nbjetons;
    private int cptS;

    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 5;
        FabriqueNumero s = FabriqueNumero.getInstance();
        this.cptS = s.getNumeroSemaphore();
    }

    //**----------------- Getters -----------------**//

    public int getCptS() {
        return cptS;
    }
    @Override
    public int getTemps() {
        return 0;
    }
    @Override
    public int getEcartTemps() {
        return 0;
    }

    public Guichet(String nom,int nb) {
        super(nom);
        this.nbjetons = nb;
        FabriqueNumero s = FabriqueNumero.getInstance();
        this.cptS = s.getNumeroSemaphore();
    }
    public int getNbjetons() {
        return nbjetons;
    }

    //**----------------------------------**//

    public boolean estUnGuichet() {
        return true;
    }
    @Override
    public String toString() {
        return super.toString();
    }
    public String toC()
    {
        if (this.getSuccesseur() == null){return "";}
        StringBuilder s = new StringBuilder("");
        String s1 = "P(ids,"+this.cptS+");\n";
        String s2 = "transfert("+this.getNum()+","+this.getSuccesseur().getNum()+");\n";
        String s4 = "delai(3,1);\n";
        String s3 =  "V(ids,"+this.cptS+");\n";
        s.append(s1).append(s2).append(s4).append(s3);
        s.append(this.getSuccesseur().toC());
        return s.toString();
    }

}
