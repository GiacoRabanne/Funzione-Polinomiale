

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Bottone extends JPanel{
    private int x;
    private int y;
    private int raggio, raggio0;
    private ImageIcon img;
    private boolean click;

    Bottone(int x, int y, int raggio, ImageIcon img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.raggio = raggio;
        this.raggio0 = raggio;
        this.click = false;
    }

    Bottone(int x, int y, int raggio) {
        this.x = x;
        this.y = y;
        this.raggio = raggio;
        this.raggio0 = raggio;
        this.click = false;
    }
    
    public void disegnaBottone(Graphics2D g) {
        if(this.click == true) {
            g.setColor(new Color(0xDDDDDD));
        } else {
            g.setColor(new Color(0xFFFFFF));
        }
        
        g.fillOval(this.x - this.raggio, this.y - this.raggio, raggio*2, raggio*2);

        if(this.img != null) {
            int sizeImg = Math.max(this.img.getIconWidth(), this.img.getIconHeight());
            double scalaturaImg = 4.5/5*raggio/sizeImg;
            AffineTransform transform = new AffineTransform();
            transform.translate(this.x - raggio/2, this.y - raggio/2);  //Traslazione
            transform.scale(scalaturaImg, scalaturaImg);    // Scalatura
            
            g.drawImage(this.img.getImage(), transform, this);
        }
    }

    public int getRaggio() {
        return this.raggio;
    }

    public Punto centro() {
        return new Punto(this.x, this.y);
    } 

    /* public int getCoordX() {
        return this.x;
    }

    public int getCoordY() {
        return this.y;
    } */

    public void click() {
        this.click = true;
    }

    public void rilasciato() {
        this.click = false;
    }

    public boolean isPremuto() {
        return this.click;
    }

    public int raggioIniziale() {
        return raggio0;
    }

    public void aumentaRaggio() {
        this.raggio += raggio/20; 
    }

    public void ristabilisciRaggio() {
        this.raggio = raggio0; 
    }

    public boolean contiene(int x, int y) {
        /*restituisce true se il punto in R2 appartiene 
        alla boccia di centro (this.x, this.y) e raggio this.raggio
        
        in x: 
        |x - x'| < r

        in y;
        |y - y'| < r

        se valgono tutte e due le condizioni, il punto è contenuto.
        
        */
        
        return Math.abs(this.x - x) < this.raggio && Math.abs(this.y - y) < this.raggio;
    }
}