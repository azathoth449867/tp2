package stationnement;

public class Transaction {

    private int tempStationement;
    private int prixTransaction;
    private String placeReserver;


    public Transaction(int tempStationement, int prixTransaction, String placeReserver) {
        this.tempStationement = tempStationement;
        this.prixTransaction = prixTransaction;
        this.placeReserver = placeReserver;
    }
}
