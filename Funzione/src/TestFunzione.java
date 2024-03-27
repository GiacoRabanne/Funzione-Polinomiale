import fond.io.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


public class TestFunzione {
    public static void main(String[]  args) {
        InputWindow in = new InputWindow();
        OutputWindow out = new OutputWindow();
        out.setFont("SF Pro Display", 20);

        int n = in.readInt("Inserisci il numero di funzioni polinomiali: ");
        Funzione[] funzioni = new Funzione[n];

        for(int i = 0; i < n; i++) {
            int grado = in.readInt("Funzione "+ (i+1) +"\nInserisci il grado della funzione polinomiale f(x): ");
            double[] coefficienti = new double[grado + 1];

            for(int k = grado; k >= 0; k--) {
                if(k == 0) {
                    coefficienti[k] = in.readDouble("Funzione "+ (i+1) +"\nInserisci il termine noto");
                } else if(k == 1){
                    coefficienti[k] = in.readDouble("Funzione "+ (i+1) +"\nInserisci il coefficiente di x");
                } else {
                    coefficienti[k] = in.readDouble("Funzione "+ (i+1) +"\nInserisci il coefficiente di x^" + k);
                }
            }

            //Funzione funzione = new Funzione(coefficienti);
            funzioni[i] = new Funzione(coefficienti);

            out.writeln("Funzione "+ (i+1) + ":\n" + funzioni[i].toString());

            int scelta = in.readInt("1) Derivata prima\n2) Integrale definito\nScegli un'opzione: ");
            while(scelta != 1 && scelta != 2) {
                scelta = in.readInt("Valore non valido\n1) Derivata prima\n2) Integrale definito\nScegli un'opzione: ");
            }
            funzioni[i].inserisciScelta(scelta);
            if(scelta == 1) {
                double x = in.readDouble("Inserisci il punto in cui calcolare la derivata: ");
                funzioni[i].inserisciValore(x);
            } else {
                double a = in.readDouble("Inserisci l'estremo inferiore d'integrazione: ");
                double b = in.readDouble("Inserisci l'estremo superiore d'integrazione");
                while(b < a) {
                    b = in.readDouble("Valore errato\nInserisci l'estremo superiore d'integrazione");
                }
                funzioni[i].inserisciEstremi(a, b);
            }

            
            
        }
        

        //out.writeln("Funzione:\n" + funzione.toString());
        //out.writeln("f'(2) = " + funzione.derivata(2));
        //out.writeln("L'Integrale Definito calcolato nell'intervallo [" + a + ", " + b + "] è: " + funzione.integrale(a, b));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int schermoY = (int) screenSize.getHeight();
        int schermoX = (int) screenSize.getWidth();
        
        int lunghezza = schermoX; //1200
        int altezza = schermoY; //800

        JFrame frame = new JFrame("Funzioni");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(schermoX, schermoY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        Piano cartesiano = new Piano(lunghezza, altezza, funzioni);
        frame.add(cartesiano);
        frame.pack();
        cartesiano.requestFocus(); 

    }
}
