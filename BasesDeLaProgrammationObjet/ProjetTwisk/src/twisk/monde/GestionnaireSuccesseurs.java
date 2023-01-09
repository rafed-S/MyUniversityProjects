package twisk.monde;
import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireSuccesseurs implements Iterable<Etape> {
    private ArrayList<Etape> etapes;

    public GestionnaireSuccesseurs()
    {
        etapes = new ArrayList<Etape>(10);
    }

    //**----------------- Getters -----------------**//

    public int nbEtapes()
    {
        return this.etapes.size();
    }
    public Etape getEtape(int i)
    {
        if(etapes.isEmpty()) return null;
        return etapes.get(i);
    }

    //**----------------------------------**//

    public void ajouter(Etape... etapess)
    {
        for (Etape e : etapess)
        {
            this.etapes.add(e);
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        for (Etape etape : etapes) {
            sb.append(etape.getNom()).append(" ; ");
        }
        return sb.toString();
    }
    @Override
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

}
