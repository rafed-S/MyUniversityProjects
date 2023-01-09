package twisk.monde;
import twisk.outils.FabriqueNumero;
import java.util.Iterator;

public abstract class Etape implements Iterable<Etape> {
    protected String nom;
    protected GestionnaireSuccesseurs gSucc;
    protected  int num;

    public Etape (String nom)
    {
        this.nom = nom;
        gSucc = new GestionnaireSuccesseurs();
        FabriqueNumero s = FabriqueNumero.getInstance();
        this.num = s.getNumeroEtape();
    }

    //**----------------- Getters -----------------**//
    public int getNum() {
        return num;
    }
    public String getNom() {
        return nom;
    }
    public int nbSuccesseurs()
    {
        return gSucc.nbEtapes();
    }
    public int getNbjetons() {return 0;}
    public Etape getSuccesseur()
    {
        if(gSucc == null) return null;
        return gSucc.getEtape(0);
    }
    public Etape getSuccesseur(int i)
    {
        if(gSucc == null) return null;
        return gSucc.getEtape(i);
    }
    public int getCptS() {
        return 0;
    }

    //**----------------- abstract -----------------**//

    public abstract String toC();
    public abstract int getTemps();
    public abstract int getEcartTemps();

    //**----------------------------------**//
    public void ajouterSuccesseur(Etape... e)
    {
        gSucc.ajouter(e);
    }
    public boolean estUneActivite()
    {
        return false;
    }
    public boolean estUnGuichet()
    {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(nom).append(" :  - ").append(this.nbSuccesseurs()).append(" Successeur -  ").append(this.gSucc.toString());
        return sb.toString();
    }

    @Override
    public Iterator<Etape> iterator() {
        return gSucc.iterator();
    }
}
