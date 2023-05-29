package stamps.models;

public class Donnee {

    private Livres bouquin;

    public Donnee(Livres bouquin){
        this.bouquin = bouquin;
    }

    public String getDonne(){
        return "\nAuteur : " + bouquin.getYazar() +
                "\nPublier le : " + bouquin.getCikisTarihi() +
                "\n" + bouquin.getBaska();
    }
}
