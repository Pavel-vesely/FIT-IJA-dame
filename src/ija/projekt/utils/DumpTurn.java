package ija.projekt.utils;

/**
 * Obraz objektu Turn. Upraveny pro pouziti JAXB.
 *
 * @author Pavel Vesely, Ales Drevo
 */
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DumpTurn {

    boolean take;
    char col0;
    int row0;
    char col1;
    int row1;

    /**
     * Setter atributu take
     */
    @XmlElement
    public void setTake(boolean take) {
        this.take = take;
    }

    /**
     * Getter atributu take
     */
    public boolean getTake() {
        return take;
    }

    /**
     * Setter atributu col0
     */
    @XmlElement
    public void setCol0(char col) {
        this.col0 = col;
    }

    /**
     * Getter atributu col0
     */
    public char getCol0() {
        return col0;
    }

    /**
     * Setter atributu row0
     */
    @XmlElement
    public void setRow0(int row) {
        this.row0 = row;
    }

    /**
     * Getter atributu row0
     */
    public int getRow0() {
        return row0;
    }

    /**
     * Setter atributu col1
     */
    @XmlElement
    public void setCol1(char col) {
        this.col1 = col;
    }

    /**
     * Getter atributu col1
     */
    public char getCol1() {
        return col1;
    }

    /**
     * Setter atributu row1
     */
    @XmlElement
    public void setRow1(int row) {
        this.row1 = row;
    }

    /**
     * Getter atributu row1
     */
    public int getRow1() {
        return row1;
    }
}
