package twisk.monde;
import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    private SasEntree en;
    private SasSortie so;
    private GestionnaireEtapes gs;

    public Monde() {
        en = new SasEntree();
        so = new SasSortie();
        gs = new GestionnaireEtapes();
        gs.ajouter(en,so);
    }

    //**----------------- Getters -----------------**//

    public int nbEtapes()
    {
        return gs.nbEtapes();
    }
    public int nbGuichets()
    {
        return gs.nbGuichets();
    }

    public Etape getGuichets(int semaph)
    {

        return gs.getGuichets(semaph);
    }
    public Etape getEtp(int i)
    {
        return gs.getEtape(i);
    }

    public SasEntree getEn() {
        return en;
    }

    public SasSortie getSo() {
        return so;
    }

    //**----------------------------------**//

    public void aCommeEntree(Etape... etapes)
    {
        en.ajouterSuccesseur(etapes);
    }
    public void aCommeSortie(Etape... etapes)
    {
        for (Etape etape : etapes)
            etape.ajouterSuccesseur(this.so);
    }
    public void ajouter(Etape... etapes)
    {
        gs.ajouter(etapes);
    }
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("");
        sb.append(gs.toString());
        return sb.toString();
    }
    @Override
    public Iterator<Etape> iterator() {
        return gs.iterator();
    }
    public String toC()
    {
        StringBuilder s = new StringBuilder("");
        String b = "";
        String a = "#include <stdio.h> \n" +
                "#include <stdlib.h>\n" +
                "#include \"def.h\"\n"
                ;
        String c = "void simulation(int ids)\n" +
                "{\n";
        s.append(a);
        s.append(c);
        s.append(en.toC()).append("}");
        return s.toString();
    }
}
