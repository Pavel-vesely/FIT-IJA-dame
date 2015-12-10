package ija.projekt.utils;

import ija.projekt.basis.Position;

/**
 * Trida je abstrakci tahu v datovem modelu. Obsahuje pocatecni a cilovou pozici
 * a informaci, zda se bere.
 *
 * @author Pavel Vesely, Ales Drevo
 */
public class Turn {

    Position pos0, pos1;
    boolean take;

    public Turn(Position pos0, Position pos1, boolean take) {
        this.pos0 = pos0;
        this.pos1 = pos1;
        this.take = take;
    }

    public Position getP0() {
        return pos0;
    }

    public Position getP1() {
        return pos1;
    }

    public boolean getTake() {
        return take;
    }

    public DumpTurn dump() {
        DumpTurn dump = new DumpTurn();
        dump.setTake(take);
        dump.setCol0(pos0.getColumn());
        dump.setRow0(pos0.getRow());
        dump.setCol1(pos1.getColumn());
        dump.setRow1(pos1.getRow());
        return dump;
    }
}
