package ija.projekt.utils;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * Jednoducha trida pro sber MouseEventu v gui.Board. Roysiruje JPanel, pridava
 * k nemu souradnice.
 *
 * @author Pavel Vesely, Ales Drevo
 */
public class BoardSquare extends JPanel {

    int row;
    char column;

    /**
     * Konstruktor. Nastavi souradnice.
     */
    public BoardSquare(char col, int rw) {
        super(new BorderLayout());
        this.row = rw;
        this.column = col;
    }

    /**
     * Getter pro atribut column.
     */
    public char getColumn() {
        return column;
    }

    /**
     * Getter pro atribut row.
     */
    public int getRow() {
        return row;
    }

    
    /**
     * Spocte, jaky index ma BoardSquare v Board.eCatcher.
     */
    public int getIndex() {
        return (int) (this.column - 'a') + (8 - this.row) * 8;
    }
}
