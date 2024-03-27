

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;





public class Piano extends JPanel implements ActionListener, MouseListener, KeyListener{
    int lunghezza, altezza, griglia, scala;
    int x0, x1, y0, y1, diffX, diffY, diffMax = 0;
    boolean premuto = false;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double schermoY = screenSize.getHeight();
    double raggio;
    double t0, dt;
    int dx = 0, dy = 0;
    int nowX, nowY;
    int scelta = 0;
    
    double tempoInizio = 0;
    double intervallo;
    
    float colore = 0x000000;

    private Funzione[] funzioni;
    Timer geoLoop;
    int delay = 10;

    double maxRaggio = 0;

    Bottone zoomIn, zoomOut, avanti, indietro;


    Piano (int lunghezza, int altezza, Funzione[] f) {
        this.altezza = altezza;
        this.lunghezza = lunghezza;

        this.funzioni = new Funzione[f.length];
        for(int i = 0; i < f.length; i++) {
            this.funzioni[i] = f[i];
        }
        
        setPreferredSize(new Dimension(this.lunghezza, this.altezza));
        setFocusable(true);
        setBackground(Color.white);
        geoLoop = new Timer(delay, this);
        this.addMouseListener(this);
        this.addKeyListener(this);


        zoomIn = new Bottone(lunghezza - 60, 50, 25, new ImageIcon("img/zoomIn.png"));
        zoomOut = new Bottone(lunghezza - 60, 125, 25, new ImageIcon("img/zoomOut.png"));
        avanti = new Bottone(lunghezza - 60, 200, 25, new ImageIcon("img/avanti.png"));
        indietro = new Bottone(lunghezza - 60, 275, 25, new ImageIcon("img/indietro.png"));
        //zoomIn.addMouseListener(this);

        
       

        for(int i = 0; i < funzioni.length; i++) {
            if(funzioni[i].f(1) > maxRaggio) {
                maxRaggio = funzioni[i].f(1);
            }
        }
        
        if(maxRaggio == 0) {
            griglia = 20;
        } else {
            griglia = (int) (20*maxRaggio/3);
        }
        
        if(griglia%2 != 0) {
            griglia++;
        }
        geoLoop.start();
        tempoInizio = System.currentTimeMillis();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        disegna(g2);
        
    }

    public void disegna(Graphics2D g) {
        g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 25));
        int y;
        
        scala = (int) ((double) lunghezza)/griglia;
        
        for(int i = -griglia; i < 2*griglia; i++) {
            if(i == griglia/2) {
                g.setColor(Color.black);
                g.setStroke(new BasicStroke(2));
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.setStroke(new BasicStroke(1));
            }
            g.drawLine(i*lunghezza/griglia + dx, 0, i*lunghezza/griglia + dx, altezza);
            y = i*lunghezza/griglia - (lunghezza/2- altezza/2);

            g.drawLine(0, y + dy, lunghezza, y + dy);
        }

        
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(2));
        //freccia y
        g.drawLine(lunghezza/2 + dx , 0, lunghezza/2 - 10 + dx, 20);
        g.drawLine(lunghezza/2 + dx, 0, lunghezza/2 + 10 + dx, 20);
        g.drawString("y", lunghezza/2- 50 + dx, 35);

        //freccia x
        g.drawLine(lunghezza, altezza/2 + dy, lunghezza - 20, altezza/2 - 10 + dy);
        g.drawLine(lunghezza, altezza/2 + dy, lunghezza - 20, altezza/2 + 10 + dy);
        g.drawString("x", lunghezza - 35, altezza/2 + 35 + dy);
        
        g.setStroke(new BasicStroke(1));
        


        if(scelta%4 == 0) {
            g.setColor(new Color(0x0000BB ));
        }
        if(scelta%4 == 1) {
            g.setColor(new Color(0xDD0000 ));
        }
        if(scelta%4 == 2) {
            g.setColor(new Color(0x00BB00 ));
        }
        if(scelta%4 == 3) {
            g.setColor(Color.ORANGE);
        }

        if(funzioni[scelta].grado() == 1) {
            double x = griglia*1.5;
            Punto p1 = new Punto(-x, funzioni[scelta].f(-x));
            Punto p2 = new Punto(x, funzioni[scelta].f(x));
            segCartesiano(p1, p2, scala, g);
        } else {
            disegnaFunzione(funzioni[scelta], scala, g);
        }


        /* for(int i = 0; i < funzioni.length; i++) {
            if(i%5 == 0) {
                g.setColor(new Color(0x0000BB));
            }
            if(i%5 == 1) {
                g.setColor(new Color(0xBB0000));
            }
            if(i%5 == 2) {
                g.setColor(new Color(0x00BB00));
            }
            if(i%5 == 3) {
                g.setColor(Color.YELLOW);
            }
            if(i%5 == 4) {
                g.setColor(Color.ORANGE);
            }
            
            if(funzioni[i].grado() == 1) {
                double x = griglia*1.5;
                Punto p1 = new Punto(-x, funzioni[i].f(-x));
                Punto p2 = new Punto(x, funzioni[i].f(x));
                segCartesiano(p1, p2, scala, g);
            } else {
                disegnaFunzione(funzioni[i], scala, g);
            }
        } */

        // Riquadro informazioni

        
        g.setColor(Color.white);
        g.fillRect(20, 20, lunghezza/4, altezza/3+ 20);
        g.setColor(Color.black);
        g.drawRect(20, 20, lunghezza/4, altezza/3 + 20);
        //g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 25));

        if(scelta%4 == 0) {
            g.setColor(new Color(0x0000BB ));
        }
        if(scelta%4 == 1) {
            g.setColor(new Color(0xDD0000 ));
        }
        if(scelta%4 == 2) {
            g.setColor(new Color(0x00BB00));
        }
        if(scelta%4 == 3) {
            g.setColor(Color.ORANGE);
        }

        g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 35));
        g.drawString("Funzione " + (scelta + 1)+ ":", 50, 80);
        g.setColor(new Color(0x000000 ));
        g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 2 + 500/funzioni[scelta].toString().length()));
        g.drawString(funzioni[scelta].toString(), 50, 150);

        if(funzioni[scelta].scelta() == 1) {
            g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 35));
            g.drawString("Derivata: ", 50, altezza/6 + 70);
            double x = funzioni[scelta].x();
            double d = funzioni[scelta].derivata(x);
            if(x == Math.round(x) && d == Math.round(d)) {
                g.drawString("f'(" + Math.round(x) + ") = " + Math.round(d), 50, 300);
            } else if(x == Math.round(x)) {
                g.drawString("f'(" + Math.round(x) + ") = " + d, 50, 300);
            } else if(d == Math.round(d)) {
                g.drawString("f'(" + x + ") = " + Math.round(d), 50, 300);
            } else {
                g.drawString("f'(" + x + ") = " + d, 50, 300);
            }
        } else {
            g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 35));
            double a = funzioni[scelta].estremoInf();
            double b = funzioni[scelta].estremoSup();
            g.drawString("Integrale definito:", 50, altezza/6 + 70);
            
            g.setFont(new Font("SF Pro Display", Font.PLAIN, 70));
            g.drawString("∫", 50, 310);
            g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 15));
            if(a == Math.round(a)) {
                g.drawString("" + Math.round(a), 75, 320);
            } else {
                g.drawString("" + a, 75, 320);
            }

            if(b == Math.round(b)) {
                g.drawString("" + Math.round(b), 90, 270);
            } else {
                g.drawString("" + b, 90, 270);
            }

            g.setFont(new Font("SF Pro Display", Font.CENTER_BASELINE, 30));

            double integrale = funzioni[scelta].integrale(a, b);
            if(integrale == Math.round(integrale)) {
                g.drawString("f(x) dx = " + Math.round(integrale), 110, 300);
            } else {
                g.drawString("f(x) dx = " + integrale, 110, 300);
            }
            
        }
        
        


        // bottone zoomIn
        //disegnaBottone(lunghezza - 50, 50, 25, imgZoomIn, g);
        g.setColor(new Color(0xEEEEEE));
        int raggio = zoomIn.raggioIniziale();

        if(funzioni.length == 1) {
            g.fillOval((int) (zoomIn.centro().getCoordX() - 1.5*raggio), (int) (zoomIn.centro().getCoordY() - 1.5*raggio), 3*raggio, 3*raggio);
            g.fillRect((int) (zoomIn.centro().getCoordX() - 1.5*raggio), (int) zoomIn.centro().getCoordY(), 3*raggio, (int) (zoomOut.centro().getCoordY() - zoomIn.centro().getCoordY()));
            g.fillOval((int) (zoomIn.centro().getCoordX() - 1.5*raggio), (int) (zoomOut.centro().getCoordY() - 1.5*raggio), 3*raggio, 3*raggio);
        } else {
            g.fillOval((int) (zoomIn.centro().getCoordX() - 1.5*raggio), (int) (zoomIn.centro().getCoordY() - 1.5*raggio), 3*raggio, 3*raggio);
            g.fillRect((int) (zoomIn.centro().getCoordX() - 1.5*raggio), (int) zoomIn.centro().getCoordY(), 3*raggio, (int) (indietro.centro().getCoordY() - zoomIn.centro().getCoordY()));
            g.fillOval((int) (zoomIn.centro().getCoordX() - 1.5*raggio), (int) (indietro.centro().getCoordY() - 1.5*raggio), 3*raggio, 3*raggio);
        }
        
        
        zoomIn.disegnaBottone(g);
        zoomOut.disegnaBottone(g);

        if(funzioni.length > 1) { // Se le funzioni sono più di una, metto il bottone per muoversi.
            avanti.disegnaBottone(g);
            indietro.disegnaBottone(g);
        }
        
        g.setColor(Color.black);
    }

   
    public void segCartesiano(Punto p1, Punto p2, int s, Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        g.drawLine((int) (lunghezza/2 + (p1.getCoordX())*s) + dx, (int) (altezza/2 - (p1.getCoordY())*s) + dy, (int) (lunghezza/2 + (p2.getCoordX())*s) + dx, (int) (altezza/2 - (p2.getCoordY())*s) + dy);
    }

    public void puntoCartesiano(Punto c, int s, Graphics g) {
        g.fillOval((int) (lunghezza/2 + c.getCoordX()*s) + dx - 1, (int) (altezza/2 - c.getCoordY()*s) + dy - 1, 2, 2);
    }

    public void disegnaFunzione(Funzione f, int s, Graphics2D g) {
        double dx = griglia*0.0005;
        g.setStroke(new BasicStroke(3));
        for(double x = -griglia*1.5; x < griglia*1.5; x += dx) {
            Punto p1 = new Punto(x, f.f(x));
            puntoCartesiano(p1, s, g);
            Punto p2 = new Punto(x + dx, f.f(x + dx));

            segCartesiano(p1, p2, s, g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //zoomIn.rilasciato();
        repaint();
        
        
        if(!premuto) {
            t0 = System.currentTimeMillis();
            y0 = (int) (MouseInfo.getPointerInfo().getLocation().y);
            x0 = (int) (MouseInfo.getPointerInfo().getLocation().x);
            y1 = y0;
            x1 = x0;
            
        } else {
            y1 = (int) (MouseInfo.getPointerInfo().getLocation().y);
            x1 = (int) (MouseInfo.getPointerInfo().getLocation().x);
            
            if(nowX < -lunghezza/2*scala/75) {
                if(x1 - x0 >= 0) {
                    dx = nowX + x1 - x0;
                }
            } else if(nowX > lunghezza/2*scala/75) {
                if(x1 - x0 <= 0) {
                    dx = nowX + x1 - x0;
                }
            } else {
                dx = nowX + x1 - x0;
            }

            if(nowY < -altezza/2*scala/75) {
                if(y1 - y0 >= 0) {
                    dy = nowY + y1 - y0;
                }
            } else if(nowY > altezza/2*scala/75) {
                if(y1 - y0 <= 0) {
                    dy = nowY + y1 - y0;
                }
            } else {
                dy = nowY + y1 - y0;
            }
        }

        

        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
           
    }


    @Override
    public void mousePressed(MouseEvent e) {
        nowX = dx;
        nowY = dy;
        premuto = true;
        t0 = System.currentTimeMillis();


        // Click sui bottoni

        // Zoom in
        if(zoomIn.contiene(e.getX(), e.getY())) {
            zoomIn.click();
            zoomIn.aumentaRaggio();
            zoomIn();
        }

        // Zoom out
        if(zoomOut.contiene(e.getX(), e.getY())) {
            zoomOut.click();
            zoomOut.aumentaRaggio();
            zoomOut();
        }

        // Avanti

        if(avanti.contiene(e.getX(), e.getY())) {
            avanti.click();
            avanti.aumentaRaggio();
            prossima();
        }

        // Indietro

        if(indietro.contiene(e.getX(), e.getY())) {
            indietro.click();
            indietro.aumentaRaggio();
            precedente();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        premuto = false;
        diffMax = 0;

        if(zoomIn.isPremuto()) {
            zoomIn.rilasciato();
            zoomIn.ristabilisciRaggio();
        }

        if(zoomOut.isPremuto()) {
            zoomOut.rilasciato();
            zoomOut.ristabilisciRaggio();
        }

        if(avanti.isPremuto()) {
            avanti.rilasciato();
            avanti.ristabilisciRaggio();
        }

        if(indietro.isPremuto()) {
            indietro.rilasciato();
            indietro.ristabilisciRaggio();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        

    }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void zoomOut() {
        if(scala >= 5) {
            griglia += griglia/4 + 2;
            if(griglia%2 == 1) {
                griglia--;
            }
        }
    }

    public void zoomIn() {
        if(griglia >= 6) {
            griglia -= griglia/4 + 2;
            if(griglia%2 == 1) {
                griglia++;
            }
        }
    }

    public void prossima() {
        scelta = (scelta+1)%(funzioni.length);
    }

    public void precedente() {
        if(scelta == 0) {
            scelta = funzioni.length - 1;
        } else {
            scelta = (scelta-1)%(funzioni.length);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)) {
            zoomOut();
        } 
        
        if((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)){
            zoomIn();
        } 

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            precedente();
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            prossima();
        }
        
        

    }

    @Override
    public void keyReleased(KeyEvent e) {}
}


