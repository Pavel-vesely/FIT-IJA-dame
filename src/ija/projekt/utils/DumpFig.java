package ija.projekt.utils;

/**
 * Obraz objektu Figure. Upraveny pro pouziti JAXB.
 *
 * @author Pavel Vesely, Ales Drevo
 */
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DumpFig {

    boolean dame;
    boolean side;
    char col;
    int row;

    /**
     * Setter atributu dame
     */
    @XmlElement
    public void setDame(boolean dame) {
        this.dame = dame;
    }

    /**
     * Getter atributu dame
     */
    public boolean getDame() {
        return dame;
    }

    /**
     * Setter atributu side
     */
    @XmlElement
    public void setSide(boolean side) {
        this.side = side;
    }

    /**
     * Getter atributu side
     */
    public boolean getSide() {
        return side;
    }

    /**
     * Setter atributu col
     */
    @XmlElement
    public void setCol(char col) {
        this.col = col;
    }

    /**
     * Getter atributu col
     */
    public char getCol() {
        return col;
    }

    /**
     * Setter atributu row
     */
    @XmlElement
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter atributu row
     */
    public int getRow() {
        return row;
    }
}
