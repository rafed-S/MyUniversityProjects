package twisk.mondeIG;
import twisk.monde.Etape;
import java.util.HashMap;

public class CorrespondanceEtapes {
    private HashMap<EtapeIG, Etape> convertEtapeIGEtape;
    private HashMap<Etape, EtapeIG> convertEtapeEtapeIG;

    public CorrespondanceEtapes()
    {
        convertEtapeIGEtape = new HashMap<>();
        convertEtapeEtapeIG = new HashMap<>();
    }

    //**----------------- Getters -----------------**//

    public Etape get(EtapeIG e){
        return convertEtapeIGEtape.get(e);
    }
    public EtapeIG get(Etape e){
        return convertEtapeEtapeIG.get(e);
    }
    public int nbEtapes()
    {
        return convertEtapeIGEtape.size();
    }

    //**----------------------------------**//

    public void ajouter(EtapeIG etapeIG, Etape etape){
        convertEtapeIGEtape.put(etapeIG, etape);
        convertEtapeEtapeIG.put(etape, etapeIG);
    }
}
