
public class Funzione {
    private double[] coefficiente;
    private static int create = 0;
    private static double dx = 0.00001; 
    private int scelta;
    private double x;
    private double[] estremi = new double[2];

    /* Avrei usato come differenziale dx il valore più piccolo rappresentabile
    (= Double.MIN_VALUE), tuttavia era troppo piccolo ai fini del calcolo
    */

    public Funzione(double[] coefficienti) {
        this.coefficiente = new double[coefficienti.length];

        for(int i = 0; i < coefficiente.length; i++) {
            this.coefficiente[i] = coefficienti[i];
        }
        create++;
    }

    public static int funzioniCreate() {
        return Funzione.create;
    }

    public void inserisciValore(double x) {
        this.x = x;
    }

    public void inserisciScelta(int scelta) {
        this.scelta = scelta;
    }

    public void inserisciEstremi(double a, double b) {
        estremi[0] = a;
        estremi[1] = b;
    }

    public double estremoInf() {
        return estremi[0];
    }

    public double estremoSup() {
        return estremi[1];
    }

    public double x() {
        return this.x;
    }

    public double differenziale() {
        return Funzione.dx;
    }

    public int scelta() {
        return this.scelta;
    }

    public int grado() {
        for(int i = this.coefficiente.length - 1; i >= 0; i--) {
            if(coefficiente[i] != 0) {
                return i;
            }
        }
        return 0;
    }

    public double f(double x) {
        double y = 0;

        for(int i = this.coefficiente.length - 1; i >= 0; i--) {
            y += coefficiente[i] * Math.pow(x, i);
        }
        return y; 
    }

    public double derivata(double x) {
        //calcolo
        double derivata = (this.f(x+dx) - this.f(x))/(dx);

        //approssimazione:
        return Math.round(derivata * 1000)/1000.0;
    }

    public double integrale(double a, double b) {
        double integrale = 0;
        
        for(double x = a; x < b; x += Funzione.dx) { 
            integrale += this.f(x) * Funzione.dx; //rettangolo con base molto piccola (= dx) e altezza pari ad f(x)
        }
        
        //approssimazione integrale definito
        return Math.round(integrale*1000)/1000.0;
    }

    public String toString() {
        String s = "f(x) =";
        
        boolean trovato = false;
        for(int i = this.coefficiente.length - 1; i >= 0; i--) {
            
            if(coefficiente[i] != 0) { //se il coefficiente è nullo non scrivo nulla.
                if(trovato) {
                    if(coefficiente[i] < 0) {
                        s += " - "; //se è negativo riporto il segno -
                    } else if(coefficiente[i] > 0) {
                        s += " + "; //se è positivo riporto il segno +
                    }
                } else {
                    if(coefficiente[i] < 0) {
                        s += " - "; //se è negativo riporto il segno -
                    }
                    else {
                        s += " ";
                    }
                }
                
                if(i != 0) {
                    if(i != 1) {
                        if(Math.abs(coefficiente[i]) == 1) {
                            s += "x^" + i; //se il coefficiente della x è uno non lo riscrivo 
                        } else {
                            if(Math.abs(coefficiente[i]) == (double) Math.abs(Math.round(coefficiente[i]))) {
                                s += Math.abs(Math.round(coefficiente[i])) + "x^" + i;
                            } else {
                                s += Math.abs(coefficiente[i]) + "x^" + i; //altrimenti scrivo il coefficiente in valore assoluto
                            }
                            
                        }
                    } else {
                        if(Math.abs(coefficiente[i]) == 1) {
                            s += "x"; //se il coefficiente della x è uno non lo riscrivo 
                        } else {
                            if(Math.abs(coefficiente[i]) == (double) Math.abs(Math.round(coefficiente[i]))) {
                                s += Math.abs(Math.round(coefficiente[i])) + "x";
                            } else {
                                s += Math.abs(coefficiente[i]) + "x"; //altrimenti scrivo il coefficiente in valore assoluto
                            }
                            
                        }
                    }
                    
                    
                } else { //se i è zero e il coeffiente i.esimo (ovvero il termine noto) non è nullo, lo riporto.
                    if(Math.abs(coefficiente[i]) == (double) Math.abs(Math.round(coefficiente[i]))) {
                        s += Math.abs(Math.round(coefficiente[i]));
                    } else {
                        s += Math.abs(coefficiente[i]); //altrimenti scrivo il coefficiente in valore assoluto
                    }
                }
                trovato = true;
            }
        }
        if(!trovato) {
            s += " 0";
        }
        return s;
    }
}
