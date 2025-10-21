package stationnement;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GUITP2 {
    private JPanel panel1;
    private JLabel labelLogo;
    private JPanel panelNumeros;
    private JPanel panelOperations;
    private JPanel panelComptant;
    private JTextArea champMessage;
    private JButton bouton25;
    private JButton bouton100;
    private JButton bouton200;
    private JPanel panelCredit;

    private JTextArea zoneRecu;
    private JButton boutonRapport;
    private JFormattedTextField champNumeroCarte;
    private JFormattedTextField champDateExp;
    private JButton boutonValiderDateExp;
    private JPanel panelControles;
    private JButton boutonMax;
    private JButton boutonPlus;
    private JButton boutonMoins;
    private JButton boutonOK;
    private JButton boutonAnnuler;
    private JPanel panelRecu;
    private JPanel panelGauche;
    private JPanel panelDroite;
    private JPanel panelMessages;

    private EcouteurNumero ecouteurNumero;
    private EcouteurCarteCredit ecouteurCarteCredit;
    private EcouteurMonnaie ecouteurMonnaie;
    private EcouteurControles ecouteurControles;
    private EcouteurEntree ecouteurEntree;

    // variables utiles pour vous
    String place =""; //place de stationnement choisie
    Borne borne; // borne à créer dans le constructeur

    //formatage
    public GUITP2() throws ParseException {

        labelLogo.setIcon(new ImageIcon("logo.png"));
        boutonValiderDateExp.setMargin(new Insets(10,0, 10, 0));
        //Création des écouteurs
        ecouteurNumero = new EcouteurNumero();
        ecouteurCarteCredit = new EcouteurCarteCredit();
        ecouteurMonnaie = new EcouteurMonnaie();
        ecouteurControles = new EcouteurControles();
        ecouteurEntree = new EcouteurEntree();

        // panelNumeros avec la grille
        GridBagLayout gl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        FlowLayout f = new FlowLayout(FlowLayout.CENTER);
        panelOperations.setLayout(f);

        panelNumeros.setLayout(gl);
        c.fill = GridBagConstraints.BOTH;
        c.weightx =1;
        c.weighty=1;
        for ( int i = 0; i <15 ; i++) {
            JButton temp = new JButton();
            temp.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
            temp.setForeground(new Color(0,174,239));
            temp.addActionListener(ecouteurNumero);
            if  ( i ==0 )
                temp.setText("A");
            else if ( i ==1 )
                temp.setText("B");
            else if ( i==2 )
               temp.setText("G");
            else if (i==3) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText("SQ");
            }
            else if (i<=6) {
                c.weightx=1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i-4));
            } else if (i==7) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText(String.valueOf(i-4));
            } else if (i<=10) {
                c.weightx=1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i-4));
            } else if (i==11) {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText(String.valueOf(i-4));
            } else if (i<=13) {
                c.weightx=1;
                c.gridwidth = 1;
                temp.setText(String.valueOf(i-4));
            } else {
                c.gridwidth = GridBagConstraints.REMAINDER; //end row
                temp.setText("entrée");
                temp.removeActionListener(ecouteurNumero);
                temp.addActionListener(ecouteurEntree);
            }
            gl.setConstraints(temp, c );
            panelNumeros.add( temp);
        }

        // inscrire les sources à l'écouteur
        bouton25.addActionListener(ecouteurMonnaie);
        bouton100.addActionListener(ecouteurMonnaie);
        bouton200.addActionListener(ecouteurMonnaie);

        boutonMax.addActionListener(ecouteurCarteCredit);
        boutonPlus.addActionListener(ecouteurCarteCredit);
        boutonMoins.addActionListener(ecouteurCarteCredit);
        boutonValiderDateExp.addActionListener(ecouteurCarteCredit);
        boutonOK.addActionListener(ecouteurControles);
        boutonAnnuler.addActionListener(ecouteurControles);
        boutonRapport.addActionListener(ecouteurControles);

        //1. créer objet stationnement.Borne

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            champNumeroCarte = new JFormattedTextField(new MaskFormatter("#### #### #### ####"));
            champDateExp = new JFormattedTextField(new MaskFormatter("##/##"));
        }
        catch ( ParseException pe) {
            pe.printStackTrace();
        }
    }
    private void $$$setupUI$$$() {
        createUIComponents();
    }
    private class EcouteurNumero implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //lettre ou chiffre du bouton qu'on a cliqué dessus
           String lettreChiffre = ((JButton)e.getSource()).getText();
           boutonNumeroLettre_actionPerformed( lettreChiffre);
        }
    }

    private class EcouteurEntree implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boutonEntree_actionPerformed();
        }
    }

    private class EcouteurMonnaie implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == bouton25)
                bouton25_actionPerformed();
            else if ( e.getSource()==bouton100)
                bouton100_actionPerformed();
            else if ( e.getSource() == bouton200)
                bouton200_actionPerformed();
        }
    }

    private class EcouteurCarteCredit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

             if ( e.getSource() == boutonPlus)
                boutonPlus_actionPerformed();
             else if ( e.getSource() == boutonMoins)
                 boutonMoins_actionPerformed();
            else if (e.getSource() == boutonMax)
                boutonMax_actionPerformed();
            else if ( e.getSource() == boutonValiderDateExp)
                boutonValiderDateExp_actionPerformed();
        }
    }

    private class EcouteurControles implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == boutonOK)
                boutonOK_actionPerformed();
            else if ( e.getSource() == boutonRapport)
                boutonRapport_actionPerformed();
            else if ( e.getSource() == boutonAnnuler)
                boutonAnnuler_actionPerformed();
        }
    }
    Borne b = new Borne();
    boolean entreePlace = true;
    boolean transactionEncours = false;
    public void boutonNumeroLettre_actionPerformed(String lettreChiffre) {
        if (entreePlace) {
            // 2. À compléter, afficher la place choisie dans le champMessage
            // à partir de la lettre ou du chiffre cliqué en paramètre
            place += lettreChiffre;
            champMessage.setText("Votre place : " + place);
        }
    }

    private void boutonEntree_actionPerformed() {
        if (entreePlace) {
            //3. à coder
            if (b.verifPlace(place)) {
                champMessage.setText("Place Valide...");
                entreePlace = false;
                transactionEncours = true;
            } else {
                champMessage.setText(place + "est invalid");
                place = "";
            }
            //valider, sinon afficher et vider place
            System.out.println("boutton entrer");
        }
    }

    private void bouton25_actionPerformed() {
        if (transactionEncours) {
            //4. à coder
            System.out.println("boutton 25");
        }
    }

    private void bouton100_actionPerformed() {
        if (transactionEncours) {
            //5. à coder
            System.out.println("boutton 100");
        }
    }

    private void bouton200_actionPerformed() {
        if (transactionEncours) {
            //6. à coder
            System.out.println("boutton 200");
        }
    }

    private void boutonValiderDateExp_actionPerformed(){
        if (transactionEncours) {
            //7. à coder
            System.out.println("boutton Valider");
        }
    }

    private void boutonPlus_actionPerformed() {
        if (transactionEncours) {
            //8. à coder
            System.out.println("boutton +");
        }
    }

    private void boutonMoins_actionPerformed(){
        if (transactionEncours) {
            //9. à coder
            System.out.println("boutton -");
        }
    }

    private void boutonMax_actionPerformed() {
        if (transactionEncours) {
            //10. à coder
            System.out.println("boutton Max");
        }
    }

    private void boutonOK_actionPerformed() {
        if (transactionEncours) {
            // 11 à coder
            System.out.println("boutton OK");
        }
    }

    private void boutonAnnuler_actionPerformed() {
       if (transactionEncours) {
           //12 à coder
           System.out.println("boutton Annuler");
       }
    }

    private void boutonRapport_actionPerformed() {
        if (!transactionEncours) {
            //13 à coder
            System.out.println("boutton Rapport");
            //dans message, total: banque
        }
    }

    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("GUITP2");
            frame.setContentPane(new GUITP2().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(930, 815);
            //frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println ( new GregorianCalendar(2021, 11, 5).get(Calendar.DAY_OF_WEEK));
        }
        catch ( Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
