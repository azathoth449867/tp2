package stationnement;

public class Piece {
    private int valeur;

    public int getValeur() {
        return valeur;
    }

     private void setValeur(int valeur) {
         if (valeur == 25 || valeur == 100 || valeur == 200) {
             this.valeur = valeur;
         }
         this.valeur = 0;
     }

     public Piece(int valeur){
        setValeur(valeur);
     }
}
//private static int[][] horaire= new int[7][2];    // 7 jour et 2 valeur pour deux région 1 ou 2 pour gratui ou non