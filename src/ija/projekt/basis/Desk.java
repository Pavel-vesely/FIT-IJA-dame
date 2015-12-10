package ija.projekt.basis;

import ija.projekt.figure.Dame;
import ija.projekt.figure.Stone;
import ija.projekt.gui.GameGui;
import java.util.ArrayList;

/**
 * Trida predstavuje datovy model hraci desky. Je pristupovym bodem k datovemu
 * modelu. Dale obsahuje metody pracujici s vice figurami naraz.
 *
 * @author Pavel Vesely, Ales Drevo
 */
public class Desk {

    GameGui gameGui;
    Position[] posits;

    /**
     * Konstruktor tridy. Parametr gm je odkaz na gui hry.
     */
    public Desk(GameGui gm) {
        this.gameGui = gm;
        this.posits = new Position[64]; //Rozmery desky jsou vzdy 8*8

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                posits[j * 8 + i] = new Position(this, (char) ('a' + i), j + 1);
            }
        }

        this.initiateDesk();
    }

    /**
     * Vrati pozici na zadanych souradnicich.
     */
    public Position getPositionAt(char c, int r) {
        if ((c - 'a' < 8) && (c >= 'a') && (r <= 8) && (r > 0)) {
            return posits[c - 'a' + (r - 1) * 8];
        }
        return null;
    }

    /**
     * Vrati figuru na zadanych souradnicich.
     */
    public Figure getFigureAt(char c, int r) {
        return this.getPositionAt(c, r).getFigure();
    }

    /**
     * Vrati pole vsech figur zadane strany na desce.
     */
    public Figure[] getFigs(boolean side) {
        Figure f;
        ArrayList<Figure> fArr = new ArrayList<>();
        for (Position p : this.posits) {
            f = p.getFigure();
            if (f != null && f.getSide() == side) {
                fArr.add(f);
            }
        }
        return fArr.toArray(new Figure[fArr.size()]);
    }

    /**
     * Vrati pole vsech figur na desce.
     */
    public Figure[] getFigs() {
        Figure f;
        ArrayList<Figure> fArr = new ArrayList<>();
        for (Position p : this.posits) {
            f = p.getFigure();
            if (f != null) {
                fArr.add(f);
            }
        }
        return fArr.toArray(new Figure[fArr.size()]);
    }

    /**
     * Smaze vsechny figury na desce a naplni ji novymi v rozestaveni pro
     * zacatek hry.
     */
    public void initiateDesk() {
        for (Position p : posits) {
            p.removeFigure();
        }
        new Stone(getPositionAt('a', 1), true);
        new Stone(getPositionAt('a', 3), true);
        new Stone(getPositionAt('b', 2), true);
        new Stone(getPositionAt('c', 1), true);
        new Stone(getPositionAt('c', 3), true);
        new Stone(getPositionAt('d', 2), true);
        new Stone(getPositionAt('e', 1), true);
        new Stone(getPositionAt('e', 3), true);
        new Stone(getPositionAt('f', 2), true);
        new Stone(getPositionAt('g', 1), true);
        new Stone(getPositionAt('g', 3), true);
        new Stone(getPositionAt('h', 2), true);
        new Stone(getPositionAt('a', 7), false);
        new Stone(getPositionAt('b', 6), false);
        new Stone(getPositionAt('b', 8), false);
        new Stone(getPositionAt('c', 7), false);
        new Stone(getPositionAt('d', 6), false);
        new Stone(getPositionAt('d', 8), false);
        new Stone(getPositionAt('e', 7), false);
        new Stone(getPositionAt('f', 6), false);
        new Stone(getPositionAt('f', 8), false);
        new Stone(getPositionAt('g', 7), false);
        new Stone(getPositionAt('h', 6), false);
        new Stone(getPositionAt('h', 8), false);
    }

    /**
     * Zjisti, zda hrac nemusi skakat jinou figurou a jestli je volne pole, na
     * ktere muze figura tahnout.
     */
    public boolean canMove(Figure figure) {
        if (figure == null) {
            return false;
        }
        if (figure.canTake()) {
            return true;
        }
        if (whoCanTake(figure.getSide()).length > 0 || figure.getPossMoves().length == 0) {
            return false;
        }
        return true;
    }

    /**
     * Vrati pole figur zadane strany, ktere mohou skakat.
     */
    public Figure[] whoCanTake(boolean side) {
        Figure figs[] = this.getFigs(side);
        ArrayList<Figure> canArr = new ArrayList<>();
        for (Figure fig : figs) {
            if (fig.canTake()) {
                canArr.add(fig);
            }
        }
        return canArr.toArray(new Figure[canArr.size()]);
    }

    /**
     * Smaze vsechny figury na desce a naplni ji novymi v zadanem rozestaveni.
     */
    public void loadDesk(Figure[] figs) {
        for (Position p : posits) {
            p.removeFigure();
        }
        for (Figure fig : figs) {
            if (fig instanceof Dame) {
                new Dame(fig.getPosition(), fig.getSide());
            } else {
                new Stone(fig.getPosition(), fig.getSide());
            }
        }
    }
}
