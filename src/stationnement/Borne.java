package stationnement;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class Borne {

    private String place;
    private Transaction transaction;
    private int banque;
    private int duree = 0;
    private int prixStationnement = 0;
    private CarteCredit currentCard;

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

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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

    public CarteCredit getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(CarteCredit currentCard) {
        this.currentCard = currentCard;
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

    public YearMonth parseExp(String exp){
        String monthstr = exp.substring(0,2);
        int month = Integer.parseInt(monthstr);
        String yearstr = exp.substring(3,5);
        int year = Integer.parseInt(yearstr);
        if (month > 12){
            month = 1;
            year = 1;
        }
        return YearMonth.of(2000 + year, month);
    }
    private boolean estExpirer(CarteCredit carte){
        return carte.getExp().isBefore(YearMonth.now());
    }
    public boolean carteValid(CarteCredit carte){

        return (!estExpirer(carte)) && carte.getNum().length() == 16 + 3;
    }

    public void payerCredit(CarteCredit carte){
        if (!estExpirer(carte)){
            int heures = (duree + 59) / 60;
            prixStationnement = heures * (place.startsWith("G") ? 425 : 225);
            carte.setSolde(carte.getSolde() - prixStationnement);
            banque += prixStationnement;
        }
    }



    public void ajouterDuree(int ajout){
        if (duree + ajout > 120){
            duree = 120;
        }
        else {
            duree += ajout;
        }
    }

    public void ajouterMontant(int montant){
        transaction.setPrixTransaction(transaction.getPrixTransaction() + montant);
    }
    public int calcDuree(){
        int tarif = (place.startsWith("G") ? 425 : 225);
        return (int) Math.round((transaction.getPrixTransaction() * 60.0) / tarif);
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
