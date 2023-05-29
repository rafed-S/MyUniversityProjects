package stamps.models;

import javafx.scene.control.Alert;
import stamps.controllers.Observateur;
import stamps.excpetions.AfficheErreur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class Livres{
    private ArrayList<String> kitapIsim = new ArrayList<>();
    private String isim;
    private String resminYolu;
    private String ozet;
    private String donnee;
    private String yazar;
    private String baska;
    private boolean resimdir, bilgiler = false;
    private Date cikisTarihi;

    public Livres(String isim){
        this.isim = isim;
        this.resminYolu = "/ajouter.jpg";
        this.setCikisTarihi("02/06/2023");
        this.yazar = "";
        this.baska = "";

    }

    public Livres(String isim, String cekim){
        this.isim = isim;
        this.resminYolu = "/ajouter.jpg";
        this.setCikisTarihi("02/06/2023");
        this.yazar = "";
        this.baska = "";
    }

    public String getIsim() {
        return isim;
    }

    public void setDonnee(String donnee) {
        this.donnee = donnee;
    }

    public String getKitapIsmKelime(ArrayList<String> kitapIsim){
        return kitapIsim.stream().collect(Collectors.joining(" "));
    }

    public ArrayList<String> getKitapIsim() {
        return kitapIsim;
    }

    public void setKitapIsim(ArrayList<String> kitapIsim) {
        this.kitapIsim = kitapIsim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public boolean isBilgiler() {
        return bilgiler;
    }

    public void addGenreWord(String... kelime){
        for (String laf : kelime){
            this.kitapIsim.add(laf);
        }
    }

    public void setBilgiler(boolean bilgiler){
        this.bilgiler = bilgiler;
    }

    public String getResminYolu(){
        return resminYolu;
    }

    public void setResminYolu(String resminYolu){
        this.resminYolu = resminYolu;
    }

    public boolean resimdir() {
        return resimdir;
    }

    public void setCikisTarihi(String cikisTarihi) {
        try {
            LocalDate date;

            // Vérifier si la date est au format "jour/mois/année"
            if (cikisTarihi.matches("\\d{2}/\\d{2}/\\d{4}")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(cikisTarihi, formatter);
            }
            // Vérifier si la date est au format "année"
            else if (cikisTarihi.matches("\\d{4}")) {
                int annee = Integer.parseInt(cikisTarihi);
                date = LocalDate.of(annee, 1, 1); // 1er janvier de l'année spécifiée
            }
            // Vérifier si la date est au format "jour mois année av. J.-C."
            else if (cikisTarihi.matches("\\d+ av\\. J\\.-C\\.")) {
                int anneeAvantJC = Integer.parseInt(cikisTarihi.split(" ")[0]);
                date = LocalDate.of(-anneeAvantJC, 1, 1); // 1er janvier avant J.-C.
            }
            else {
                throw new ParseException("Format de date incorrect", 0);
            }

            this.cikisTarihi = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (ParseException d) {
            try {
                throw new AfficheErreur("Erruer", d);
            }catch (AfficheErreur a){

            }
        }
    }


    public Date getDate(){
        return cikisTarihi;
    }

    public String getCikisTarihi() {
        LocalDate localDate = this.cikisTarihi.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String cikisTarihi = "";

        // Vérifier si la date est avant J.-C.
        if (localDate.getYear() < 0) {
            int anneeAvantJC = -localDate.getYear();
            cikisTarihi = anneeAvantJC + " av. J.-C.";
        }
        // Vérifier si la date est au format "jour/mois/année" ou "année"
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            cikisTarihi = localDate.format(formatter);
        }

        return cikisTarihi;
    }


    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        this.yazar = yazar;
    }


    public void setBaska(String baska) {
        this.baska = baska;
    }

    public String getBaska() {
        return baska;
    }

    public String getOzet() {
        return ozet;
    }

    public void setOzet(String ozet) {
        this.ozet = ozet;
    }

    @Override
    public boolean equals(Object mot) {
        return super.equals(mot);
    }

}
