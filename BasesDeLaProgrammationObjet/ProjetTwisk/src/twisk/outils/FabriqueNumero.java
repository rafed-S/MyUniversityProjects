package twisk.outils;

public class FabriqueNumero {
    private static final FabriqueNumero instance = new FabriqueNumero();
    private int cptEtape;
    private int cptSemaphore;
    private int cptLib;

    private FabriqueNumero(){
        this.cptEtape = 0;
        this.cptSemaphore = 1;
        this.cptLib = 0 ;
    }

    public int getCptEtape() {
        return this.cptEtape;
    }

    public int getCptSemaphore() {
        return this.cptSemaphore;
    }

    public static FabriqueNumero getInstance(){return instance;}

    public int getNumeroEtape(){
        this.cptEtape++;
        return this.cptEtape-1;
    }

    public int getNumeroLib()    {
        return cptLib++;
    }

    public int getNumeroSemaphore()    {
        return this.cptSemaphore++;
    }

    public void reset(){
        this.cptEtape = 0;
        this.cptSemaphore = 1;
    }
}
