package twisk.mondeIG;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public abstract class EtapeIG implements Iterable<PointDeControleIG>{
    protected String nom;
    protected String identifiant;
    protected double posX = 0;
    protected double posY = 0;
    protected int largeur;
    protected int hauteur;
    protected PointDeControleIG [] pntDeCntr;
    protected boolean select;
    protected boolean entree;
    protected boolean sortie;
    protected ArrayList<EtapeIG> succ = new ArrayList<EtapeIG>();

    public EtapeIG(String nom, String idf, int haut, int larg)
    {
        this.nom = nom;
        this.identifiant = idf;
        this.largeur = larg;
        this.hauteur = haut;
        pntDeCntr = new PointDeControleIG[4];
        this.select = false;
        this.entree = false;
        this.sortie = false;
        this.addPntControl();
    }

    //**----------------- abstract -----------------**//

    public abstract void addPntControl();
    public abstract void ModifPntControl();

    //**----------------- Getters -----------------**//

    public PointDeControleIG[] getPntDeCntr() {
        return pntDeCntr;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
    public int getLargeur() {
        return largeur;
    }
    public int getHauteur() {
        return hauteur;
    }
    public String getNom() {
        return this.nom;
    }
    public double getDelai() {
        return 0;
    }
    public double getEcartTemp() {
        return 0;
    }
    public int getNbjetons() {
        return 0;
    }
    public ArrayList<EtapeIG> getSucc() {
        return succ;
    }
    public int succSize()
    {
        return this.succ.size();
    }

    //**----------------- Setters -----------------**//

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPosX(double posX) {
        this.posX = posX;
        this.ModifPntControl();
    }
    public void setPosY(double posY) {
        this.posY = posY;
        this.ModifPntControl();
    }
    public void setDelai(double delai) {
    }

    public void setEcartTemp(double ecartTemp) {
    }
    public void setNbjetons(int nbjetons) {}

    //**----------------------------------**//

    @Override
    public Iterator<PointDeControleIG> iterator() {
        ArrayList<PointDeControleIG> aa = new ArrayList<PointDeControleIG>();
        aa.addAll(Arrays.asList(this.pntDeCntr).subList(0, 4));
        return aa.iterator();
    }
    public void addSucc(EtapeIG etape)
    {
        this.succ.add(etape);

    }
    public void deleteSucc(EtapeIG etape)
    {
        int i=0;
        for(EtapeIG e : succ)
        {
            if(e.identifiant.equals(etape.identifiant))
            {
                i++;
            }
        }
        if (i==1)    this.succ.remove(etape);
    }
    public boolean estUnGuichet()
    {
        return false;
    }
    public boolean estUneActivite()
    {
        return false;
    }
    public boolean estUneActiviteRestreinte()
    {
        return false;
    }
    public boolean estAccessibleDepuis(EtapeIG etape) {
    for(EtapeIG etp : etape.succ)
    {
        if(etp.equals(this))
            return true;
        else
            return estAccessibleDepuis(etp);
    }
        return false;
    }
}
