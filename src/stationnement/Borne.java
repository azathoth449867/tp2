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
    private Piece currentPiece;

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

    public int getPrixStationnement() {
        return prixStationnement;
    }

    public void setPrixStationnement(int prixStationnement) {
        this.prixStationnement = prixStationnement;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
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
            return jour.getHour() >= 13 && jour.getHour() < 18;
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
        if(exp.matches("\\d{2}.\\d{2}")) {
            String monthstr = exp.substring(0, 2);
            int month = Integer.parseInt(monthstr);
            String yearstr = exp.substring(3, 5);
            int year = Integer.parseInt(yearstr);
            if (month > 12 || month == 0) {
                month = 1;
                year = 1;
            }
            return YearMonth.of(2000 + year, month);
        }
        return null;
    }

    private boolean estExpirer(CarteCredit carte){
        if(carte.getExp() != null)
            return carte.getExp().isBefore(YearMonth.now());
        return true;
    }

    public boolean carteValid(CarteCredit carte){

        return (!estExpirer(carte)) && carte.getNum().length() == 16 + 3;
    }

    private LocalDateTime getHeureFinTarif(LocalDateTime jour) {
        DayOfWeek jourSemaine = jour.getDayOfWeek();
        int year = jour.getYear();
        int month = jour.getMonthValue();
        int day = jour.getDayOfMonth();

        if (place.startsWith("G")) {
            if (jourSemaine == DayOfWeek.SUNDAY)
                return LocalDateTime.of(year, month, day, 18, 0);
            else
                return LocalDateTime.of(year, month, day, 23, 0);
        } else {
            if (jourSemaine == DayOfWeek.SATURDAY)
                return LocalDateTime.of(year, month, day, 18, 0);
            else
                return LocalDateTime.of(year, month, day, 21, 0);
        }
    }

    public int calcDuree(int montant){
        int tarif = (place.startsWith("G") ? 425 : 225);
        return (int) Math.round((montant * 60.0) / tarif);
    }

    public void ajouterMontant(int montant){
        int dureeActuelle = /* calcDuree(transaction.getPrixTransaction());*/ transaction.getTempStationement();
        int tarif = (place.startsWith("G") ? 425 : 225);
        if(dureeActuelle <= 120 && dureeActuelle >= 0 && estTarifable(LocalDateTime.now().plusMinutes(transaction.getTempStationement())) ) {
            int dureeAjoutee = calcDuree(montant);
            LocalDateTime trueEnd = getHeureFinTarif(LocalDateTime.now());

            LocalDateTime currentEnd = LocalDateTime.now().plusMinutes(dureeActuelle + dureeAjoutee);

            if (currentEnd.isBefore(trueEnd) || currentEnd.isEqual(trueEnd)) {
                transaction.setPrixTransaction(transaction.getPrixTransaction() + montant);
            } else {
                long minutesRestantes = java.time.Duration.between(LocalDateTime.now().plusMinutes(dureeActuelle), trueEnd).toMinutes();
                if (minutesRestantes > 0) {
                    int montantMax = (int) Math.round((minutesRestantes / 60.0) * tarif);
                    transaction.setPrixTransaction(transaction.getPrixTransaction() + montantMax);

                }
            }
            transaction.setTempStationement(calcDuree(transaction.getPrixTransaction()));
            if (transaction.getTempStationement() > 120){
                transaction.setTempStationement(120);
                transaction.setPrixTransaction((int) Math.round((120 / 60.0) * tarif));
            }
            if (transaction.getTempStationement() < 0){
                transaction.setTempStationement(0);
                transaction.setPrixTransaction((int) Math.round((0 / 60.0) * tarif));
            }
        }

    }

    public void maxMontant(){
        boolean verif = estTarifable(LocalDateTime.now());
        if (verif) {
            long dureeMax = java.time.Duration.between(LocalDateTime.now(), getHeureFinTarif(LocalDateTime.now())).toMinutes();
            long dureeLimit = java.time.Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(120)).toMinutes();
            int tarif = (place.startsWith("G") ? 425 : 225);

            if (dureeMax < dureeLimit) {
                transaction.setTempStationement((int) dureeMax);
                transaction.setPrixTransaction((int) Math.round((dureeMax / 60.0) * tarif));
            }
        }
    }



    public void ajouterABanque(int montant){
        banque += montant;
    }


    public boolean payer(){
            if (transaction.getCarte() != null) {
                if (transaction.getPrixTransaction() < transaction.getCarte().getSolde()) {
                    transaction.getCarte().payer(transaction.getPrixTransaction());
                    return false;
                } else{
                    return true;
                }
            }
            return true;
    }























    //verif place, si bon creer transaction
    //ajouter une durée a partir de now pour un max de 120minute
    //completTransaction()       pour confirmer
    //ramasser L,argent dans carte et ajouter banque
    //transaction remis a null
}
