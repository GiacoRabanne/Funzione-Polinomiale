

public class Punto {
    private double coordX, coordY;

    Punto(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public double getCoordX() {
        return this.coordX;
    }

    public double getCoordY() {
        return this.coordY;
    }

    //fa la distanza tra un punto e l'oggetto stesso. è un metodo d'istanza.
    public double distanzaPunti(Punto p) {
        return Math.sqrt(Math.pow(this.coordX - p.coordX, 2) + Math.pow(this.coordY - p.coordY, 2));
    }

    public Punto puntoTraslato(double dx, double dy) {
        return new Punto(this.coordX + dx, this.coordY + dy);
    }

    public boolean equals(Punto p) {
        return (this.coordX == p.coordX && this.coordY == p.coordY);
    }

    public String toString() {
        return ("(" + this.coordX + ", " + this.coordY + ")");
    }

    //fa la distanza tra due punti in maniera statica, ovvero richiama due specifici oggetti della classe Punto
    public static double distanzaTra(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p1.coordX - p2.coordX, 2) + Math.pow(p1.coordY - p2.coordY, 2));
    }
}
