package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    private int numeroClient;
    private int rang;
    private Etape etape;

    public Client(int numero) {
        this.numeroClient = numero;
    }

    public int getNumeroClient() {
        return numeroClient;
    }
    public Etape getEtape() {
        return etape;
    }

    public void allerA(Etape etape, int rang)
    {
        this.etape = etape;
        this.rang = rang;
    }
}
