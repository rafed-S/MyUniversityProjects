package stamps.models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Stamps extends SujetObserve implements Iterable<Livres>{
    private ArrayList<String> kitapIsim = new ArrayList<>();
    private ArrayList<Livres> bouka;
    private boolean enModif, enVision;
    private boolean voirToutLesBouquin, modifierLesBouquins;
    private boolean trier;
    private int numero = 0;
    private int lectureBouquin;

    public Stamps(){
        this.bouka = new ArrayList<>();
        this.enVision = true;
        this.enModif = false;
        this.voirToutLesBouquin = true;
        this.modifierLesBouquins = false;

        Livres candide = new Livres("Candide", "candide.png");
        Livres LeBourgeoisGentilhomme = new Livres("Le Bourgeois Gentilhomme", "LeBourgeoisGentilhomme.png");
        Livres lOdysse = new Livres("L'Odysse", "LOdysse.png");
        Livres uneVie = new Livres("Une vie", "uneVie.png");

        candide.addGenreWord("Roman", "Satire", "Conte philosophique", "Tragedie", "Fiction picaresque", "Roman d'apprentissage");
        LeBourgeoisGentilhomme.addGenreWord("Comedie-ballet");
        lOdysse.addGenreWord("Epoquée mythologique", "Aventure");
        uneVie.addGenreWord("Roman", "Fiction");

        Donnee candide1 = new Donnee(candide);
        Donnee LeBourgeoisGentilhomme1 = new Donnee(LeBourgeoisGentilhomme);
        Donnee lOdysse1 = new Donnee(lOdysse);
        Donnee uneVie1 = new Donnee(uneVie);

        candide.setOzet("Candide, un jeune homme optimiste, traverse des catastrophes et des injustices qui remettent en question son optimisme. Il abandonne finalement cette croyance naïve et trouve sa propre voie vers le bonheur en cultivant son jardin.");
        LeBourgeoisGentilhomme.setOzet("Monsieur Jourdain, un bourgeois obsédé par la noblesse, tente de s'intégrer dans la haute société. À travers des quiproquos comiques, il réalise que la véritable noblesse réside dans l'éducation et la courtoisie.");
        lOdysse.setOzet("Ulysse, après la guerre de Troie, entreprend un voyage épique rempli d'obstacles et de rencontres mythiques. Il fait preuve d'intelligence et de courage pour rentrer chez lui à Ithaque et libérer sa femme des prétendants indésirables.");
        uneVie.setOzet("Une vie est un roman de Guy de Maupassant qui raconte l'histoire de Jeanne, une jeune femme de la bourgeoisie normande. Le livre dépeint les épreuves et les désillusions auxquelles elle est confrontée, notamment un mariage malheureux avec un mari cruel et infidèle. Le roman explore les thèmes de la condition féminine, de la vanité des apparences et de la fragilité de la vie, offrant un portrait poignant de la réalité de l'époque.");

        candide.setYazar("Voltaire");
        LeBourgeoisGentilhomme.setYazar("Molière");
        lOdysse.setYazar("Homère");
        uneVie.setYazar("Guy de Maupassannt");

        candide.setCikisTarihi("1759");
        LeBourgeoisGentilhomme.setCikisTarihi("1670");
        lOdysse.setCikisTarihi("8 av. J.-C");
        uneVie.setCikisTarihi("1883");

        candide.setBaska("");
        LeBourgeoisGentilhomme.setBaska("");
        lOdysse.setBaska("");
        uneVie.setBaska("");

        candide.setDonnee(candide1.getDonne());
        LeBourgeoisGentilhomme.setDonnee(LeBourgeoisGentilhomme1.getDonne());
        lOdysse.setDonnee(lOdysse1.getDonne());
        uneVie.setDonnee(uneVie1.getDonne());

        this.insererBouquinPicture(candide);
        this.insererBouquinPicture(LeBourgeoisGentilhomme);
        this.insererBouquinPicture(lOdysse);
        this.insererBouquinPicture(uneVie);

        this.forEach(livres -> this.kitapIsim.addAll(livres.getKitapIsim()));
    }

    private void insererBouquinPicture(Livres bouquin) {
        this.bouka.add(bouquin);
        this.notifierObservateurs();
    }

    public void afficherNameParOrdreDebut(){
        bouka.sort((livre1, livre2) -> livre1.getIsim().compareTo((livre2.getIsim())));
        this.trier = true;

        this.notifierObservateurs();
    }

    public void afficherNameParOrdreFin(){
        this.trier = false;
        bouka.sort((livre1, livre2) -> livre2.getIsim().compareTo((livre1.getIsim())));

        this.notifierObservateurs();
    }

    public void afficherParOrdreEcrivain(){
        bouka.sort((livre1, livre2) -> livre1.getYazar().compareTo(livre2.getYazar()));
        this.notifierObservateurs();
    }

    public void afficherParOrdreDateDebut(){
        bouka.sort((livre1, livre2) -> livre1.getCikisTarihi().compareTo(livre2.getCikisTarihi()));
        this.notifierObservateurs();
    }

    public void afficherParOrdreDateFin(){
        bouka.sort((livre1, livre2) -> livre2.getCikisTarihi().compareTo(livre1.getCikisTarihi()));
        this.notifierObservateurs();
    }

    public void deleteBouquin(Livres bouquin){
        this.bouka.remove(bouquin);
        this.notifierObservateurs();
    }

    public void setEnModif(boolean enModif) {
        this.enModif = enModif;
    }

    public void setEnVision(boolean enVision) {
        this.enVision = enVision;
    }

    public boolean enModif(){
        return enModif;
    }

    public boolean enVision(){
        return enVision;
    }

    public void voirStamps(){
        for(Livres li : this){
            li.setBilgiler(false);
        }
        this.notifierObservateurs();
    }

    public void bouquinNext(){
        Livres livrePresent = this.bouka.get(lectureBouquin);
        livrePresent.setBilgiler(false);

        int next = (lectureBouquin + 1) % this.getBouquin();
        Livres prochain = this.bouka.get(next);


        this.notifierObservateurs();
    }

    public void enregistrerDonneLivre(String nom, String info, ArrayList<String> kitapIsimYeni, String yeniTarih, String yeniYazar, String autres){
        Livres bouq = this.bouka.get(lectureBouquin);

        bouq.setOzet(info);
        bouq.setIsim(nom);
        bouq.setKitapIsim(kitapIsimYeni);
        bouq.setCikisTarihi(yeniTarih);
        bouq.setYazar(yeniYazar);
        bouq.setBaska(autres);

        this.notifierObservateurs();
    }

    public void enregistrerSatmps(String dossier) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(dossier)) {
            gson.toJson(bouka, fileWriter);
        }

        this.notifierObservateurs();
    }

    public void ouvrir(File fileOpen){
        try{
            JSONParser f1 = new JSONParser();
            JSONArray f2 = (JSONArray) f1.parse(new FileReader(fileOpen));

            for (JSONObject jsonObject : (Iterable<JSONObject>) f2){
                Livres bouquin = new Livres(
                        (String) jsonObject.get("titre"),
                        (String) jsonObject.get("lienPicture")
                );

                bouquin.setOzet((String) jsonObject.get("resumer"));
                bouquin.setKitapIsim(new ArrayList<>((JSONArray) jsonObject.get("genre")));
                bouquin.setYazar((String) jsonObject.get("ecrivain"));
                bouquin.setCikisTarihi((String) jsonObject.get("publication"));
                bouquin.setBaska((String) jsonObject.get("Autres"));

                this.addLivre(bouquin);
            }

            this.notifierObservateurs();
        }catch(IOException | ParseException f){
            f.printStackTrace();
        }
    }

    public void addLivre(Livres bouquin){
        this.bouka.add(bouquin);
        this.notifierObservateurs();
    }

    public void addLivreWithPicture(Livres bouquin){
        this.bouka.add(bouquin);
        this.notifierObservateurs();
    }

    public int getBouquin(){
        return this.bouka.size();
    }

    public void gosterKiabi(Livres bouquin) {
        int nouvelIndex = bouka.indexOf(bouquin);

        if (nouvelIndex != -1) {
            Livres livresPresent = bouka.get(lectureBouquin);
            livresPresent.setBilgiler(false);

            bouquin.setBilgiler(true);
            this.lectureBouquin = nouvelIndex;

            this.notifierObservateurs();
        }
    }

    public void previousBouquin() {
        Livres livo = this.bouka.get(lectureBouquin);
        livo.setBilgiler(false);

       int indexJeuPrecedent;
        if (lectureBouquin == 0) {
            indexJeuPrecedent = this.getBouquin() - 1;
        } else {
            indexJeuPrecedent = lectureBouquin - 1;
        }

        Livres previousBouquin = this.bouka.get(indexJeuPrecedent);

        gosterKiabi(previousBouquin);
        this.notifierObservateurs();
    }

    public ArrayList<String> getKitapIsim() {
        return kitapIsim;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void resimDegistir(Livres bouquin, String lien){
        bouquin.setResminYolu(lien);
        this.notifierObservateurs();
    }

    @Override
    public Iterator<Livres> iterator() {
        return this.bouka.iterator();
    }
}
