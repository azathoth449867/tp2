package stationnement;

import java.time.YearMonth;

public class CarteCredit {
    private String num;
    private YearMonth exp;
    private int solde;

    public CarteCredit(String num, YearMonth exp, int solde) {
        this.num = num;
        this.exp = exp;
        this.solde = solde;
    }


    public String getNum() {
        return num;
    }

    public YearMonth getExp() {
        return exp;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public void payer(int montant){
        solde -= montant;
    }


}

