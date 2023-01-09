package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireClients implements Iterable<Client> {
    private ArrayList<Client> clients;
    private int nbClients;

    public GestionnaireClients(){
        clients = new ArrayList<Client>();
    }
    public GestionnaireClients(int nbClients){
        clients = new ArrayList<Client>(nbClients);
        this.nbClients = nbClients;
    }
    public void setClients(int ...tabClients)
    {
        for(int num : tabClients)
            clients.add(new Client(num));
        this.nbClients = clients.size();
    }
    public void setNbClients(int nbClients)
    {
        this.nbClients = nbClients;
    }
    public void allerA(int numeroClient, Etape etape,int rang)
    {
        for(Client c : clients) {
            if (c.getNumeroClient() == numeroClient) {
                c.allerA(etape, rang);
            }
        }
    }
    public void reset()
    {
        clients.clear();
        this.nbClients = 0;
    }
    @Override
    public Iterator<Client> iterator() {
        return clients.iterator();
    }
    public void afficherEtat()
    {

        for (Client c : clients)
            System.out.println("Client : "+c.getNumeroClient()+" Etape : "+c.getEtape().getNum());
    }
    public ArrayList<Client> getClients() {
        return clients;
    }
    public int getNbClients() {
        return nbClients;
    }
}
