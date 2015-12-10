package ija.projekt.basis;

import ija.projekt.basis.Desk;
import ija.projekt.basis.Figure;

/**
 * Abstrakce pozice hraci desky v datovem modelu aplikace.
 *
 * @author Pavel Vesely, Ales Drevo
 */
public class Position {

    protected Desk desk;
    protected char column;
    protected int row;
    protected Figure fig;

    /**
     * Konstruktor. Vytvori na desce d pozici se souradnicemi c,r.
     */
    public Position(Desk d, char c, int r) {
        this.desk = d;
        this.column = c;
        this.row = r;
        this.fig = null;
    }

    /**
     * Vraci pozici stejne desky vzdalenou o dC, dR.
     */
    public Position nextPosition(int dC, int dR) {
        dR = dR + this.row;
        dC = dC + (int) this.column;
        return this.desk.getPositionAt((char) dC, dR);
    }

    /**
     * Getter pro atribut Figure fig.
     */
    public Figure getFigure() {
        return this.fig;
    }

    /**
     * Setter pro atribut Figure fig. Vraci jeho puvodni hodnotu.
     */
    public Figure putFigure(Figure f) {
        Figure ret = this.fig;
        this.fig = f;
        return ret;
    }

    /**
     * Nastavi atribut Figure fig na null, vrati puvodni hodnotu.
     */
    public Figure removeFigure() {
        Figure ret = this.fig;
        this.fig = null;
        return ret;
    }

    /**
     * Zjisti, zda se pozice nachazi v radku r.
     */
    public boolean inRow(int r) {
        return (this.row == r);
    }

    /**
     * Zjisti, ve kterym diagonalnim smerem (SZ,SV,JV,JZ) je zadana pozice p.
     */
    public int diagDir(Position p) {
        if (Math.abs(this.column - p.column) != Math.abs(this.row - p.row)) {
            return 0;
        }
        if (this.column < p.column) {
            if (this.row < p.row) {
                return 1;
            }
            return 4;
        }
        if (this.row < p.row) {
            return 2;
        }
        return 3;
    }

    /**
     * Zjisti, jak daleko je diagonalne vzdalena pozice p.
     */
    public int diagDist(Position p) {
        if (Math.abs(this.column - p.column) != Math.abs(this.row - p.row)) {
            return 0;
        }
        return Math.abs(this.column - p.column);
    }

    /**
     * Vrati pozici, ktera diagonalne sousedi zadanym smerem.
     */
    public Position diagNext(int dir) {
        if (dir == 1) {
            return this.nextPosition(1, 1);
        }
        if (dir == 2) {
            return this.nextPosition(-1, 1);
        }
        if (dir == 3) {
            return this.nextPosition(-1, -1);
        }
        if (dir == 4) {
            return this.nextPosition(1, -1);
        }
        return null;
    }

    /**
     * Spocte, jaky index ma odpovidajici policko v gui (trida Board).
     */
    public int getGuiIndex() {
        return (int) (this.column - 'a') + (8 - this.row) * 8;
    }

    /**
     * Getter pro atribut column.
     */
    public char getColumn() {
        return this.column;
    }

    /**
     * Getter pro atribut row.
     */
    public int getRow() {
        return this.row;
    }
}