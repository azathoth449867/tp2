package stationnement;

public class Borne {

    private String place;
    private Transaction transaction;
    private int banque;


    public Borne(){

    }


    public boolean verifPlace(String place){
        String valid = "^G\\d{3}";                // !!ajouter Sq plus tard
        return place.matches(valid);
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
