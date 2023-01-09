package twisk.monde;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape> {
    private ArrayList<Etape> etp;

    public GestionnaireEtapes() {etp = new ArrayList<Etape>();}

    //**----------------- Getters -----------------**//
    public int nbEtapes()
    {
        return this.etp.size();
    }
    public int nbGuichets()
    {
        int i=0;
        for(Etape e : etp)
        {
            if(e.estUnGuichet())  i++;
        }
        return i;
    }
    public Etape getGuichets(int semaph)
    {
    Etape [] aa = new Guichet[this.nbGuichets()];
    int i=0;
        for(Etape e : etp)
        {
            if(e.estUnGuichet())
            {
                aa[i] = e;
                i++;
            }
        }

        for (Etape gui : aa)
        {
            if(gui.getCptS() == semaph) {
                return gui;
            }
        }

        return null;
    }
    public String getNomEtape(int i) {

        for (Etape etp : etp){
            if (etp.getNum() == i)
                return etp.getNom();
        }
        return null;
    }
    public int getNumEtape(int i)
    {
        return etp.get(i).getNum();
    }
    public Etape getEtape(int i)
    {
        for(Etape e : etp)
        {
            if(e.getNum() == i)
            {
                return e;
            }
        }
        return null;
    }

    //**----------------------------------**//
    public void ajouter(Etape... etapes)
    {
        this.etp.addAll(Arrays.asList(etapes));
    }
    @Override
    public Iterator<Etape> iterator() {
        return etp.iterator();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        for (Etape etape : etp) {
            sb.append(etape.toString()).append("\n");
        }
        return sb.toString();
    }
}
