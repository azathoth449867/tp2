package stationnement;

import java.awt.image.BandCombineOp;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class Borne {

    private String place;
    private Transaction transaction;
    private int banque;
    private int duree = 0;
    private int prixStationnement = 0;

    public Borne(){

    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        if (verifPlace(place)){
            this.place = place;
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public int getBanque() {
        return banque;
    }

    public void setBanque(int banque) {
        this.banque = banque;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public boolean verifPlace(String place){
        String valid = "^(G|SQ)\\d{3}$";
        return place.matches(valid);
    }
    public boolean estTarifableG(LocalDateTime jour){
        if (jour.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() && jour.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()){
            return jour.getHour() >= 8 && jour.getHour() < 23;
        }
        else if (jour.getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue()) {
            return jour.getHour() >= 9 && jour.getHour() < 23;
        }
        else if (jour.getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue()) {
            return jour.getHour() >= 13 && jour.getHour() <= 18;
        }
        return false;
    }
    public boolean estTarifableSQ(LocalDateTime jour){
        if (jour.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() && jour.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()){
            return jour.getHour() >= 9 && jour.getHour() < 21;
        }
        else if (jour.getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue()) {
            return jour.getHour() >= 9 && jour.getHour() < 18;
        }
        return false;
    }

    public boolean estTarifable(LocalDateTime jour){
        if(place.startsWith("G")){
            return estTarifableG(jour);
        }
        else{
            return estTarifableSQ(jour);
        }
    }


    private boolean estExpirer(CarteCredit carte){
        return carte.getExp().isBefore(YearMonth.now());
    }
    public void payerCredit(CarteCredit carte){
        if (!estExpirer(carte)){
            int heures = (duree + 59) / 60;
            prixStationnement = heures * (place.startsWith("G") ? 425 : 225);
            carte.setSolde(carte.getSolde() - prixStationnement);
            banque += prixStationnement;
        }
    }

    public void createTransaction(){
        this.transaction = new Transaction(duree, prixStationnement, place);
    }

    public void ajouterDuree(int ajout){
        if (duree + ajout > 120){
            duree = 120;
        }
        else {
            duree += ajout;
        }
    }


    public int genererRapport(){
        return banque;
    }























    //verif place, si bon creer transaction
    //ajouter une durée a partir de now pour un max de 120minute
    //completTransaction()       pour confirmer
    //ramasser L,argent dans carte et ajouter banque
    //transaction remis a null
}
