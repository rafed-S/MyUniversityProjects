package twisk.outils;

public class FabriqueIdentifiant {

    private static final FabriqueIdentifiant instance = new FabriqueIdentifiant();
    private int noEtape = 0;
    private int noSema = 1;

    public static FabriqueIdentifiant getInstance() { return instance; }

    public String getIdentifiantEtape()
    {
        String s=""+this.noEtape;
        this.noEtape++;
        return s;
    }

    public String getIdentifiantSema()
    {
        String s=""+this.noSema;
        this.noSema++;
        return s;
    }
}
