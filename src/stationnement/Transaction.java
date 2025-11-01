package stationnement;

import java.time.LocalDateTime;

public class Transaction {

    private int tempStationement;
    private int prixTransaction;
    private String placeReserver;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;


    public int getTempStationement() {
        return tempStationement;
    }

    public void setTempStationement(int tempStationement) {
        this.tempStationement = tempStationement;
    }

    public int getPrixTransaction() {
        return prixTransaction;
    }

    public void setPrixTransaction(int prixTransaction) {
        this.prixTransaction = prixTransaction;
    }

    public String getPlaceReserver() {
        return placeReserver;
    }

    public void setPlaceReserver(String placeReserver) {
        this.placeReserver = placeReserver;
    }

    public LocalDateTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalDateTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDateTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalDateTime heureFin) {
        this.heureFin = heureFin;
    }

    public Transaction(String placeReserver){
        this.placeReserver = placeReserver;
        tempStationement = 0;
        prixTransaction = 0;
        heureDebut = LocalDateTime.now();
        heureFin = null;
    }

}
