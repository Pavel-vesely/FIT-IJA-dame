package ija.projekt.gui;

import ija.projekt.basis.Desk;
import ija.projekt.basis.Figure;
import ija.projekt.basis.Position;
import ija.projekt.figure.Dame;
import ija.projekt.figure.Stone;
import ija.projekt.gui.Board;
import ija.projekt.utils.DumpFig;
import ija.projekt.utils.DumpTurn;
import ija.projekt.utils.SaveNLoad;
import ija.projekt.utils.Turn;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ija.projekt.utils.SavedGame;

/**
 * Abstraktni trida, ktera obsahuje metody pro praci s GUI a datovym modelem.
 *
 * @author Pavel Velely, Ales Drevo
 */
public abstract class GameGui extends javax.swing.JFrame {

    protected Desk desk;
    protected Board board;
    boolean hasTurn = true;
    int turnCnt = 1;
    protected ArrayList<Turn> playedTurns;
    protected SaveNLoad saveNLoad;

    /**
     * Konstruktor. Vytvari instanci SaveNLoad, singeltonu pro ukladani a
     * nacitani do a z XML.
     */
    public GameGui(File file) {
        saveNLoad = new SaveNLoad();
    }

    /**
     * Zjisti, zda strana side zvitezila.
     */
    public boolean didWin(boolean side) {
        if (desk.getFigs(!side).length == 0) {
            return true;
        }
        return false;
    }

    /**
     * Zjisti, zda protivnik strany side uhral pat.
     */
    public boolean isPat(boolean side) {
        if (getPossTurns(side).length == 0) {
            return true;
        }
        return false;
    }

    /**
     * Vytvori pole vsech moznych tahu strany side v dany okamzik.
     */
    public Turn[] getPossTurns(boolean side) {
        ArrayList<Turn> turns = new ArrayList<>();
        Figure[] figs = desk.whoCanTake(side);
        boolean take = true;
        if (figs.length == 0) {
            take = false;
            figs = desk.getFigs(side);
        }
        Position source;
        for (Figure fig : figs) {
            source = fig.getPosition();
            for (Position target : fig.getPossMoves()) {
                turns.add(new Turn(source, target, take));
            }
        }
        return turns.toArray(new Turn[turns.size()]);
    }

    /**
     * Abstraktni metoda, vola se pri ukonceni tahu.
     */
    public abstract void madeTurn();

    /**
     * Metoda volana z madeTurn. Overi, zda skoncila hra, zmeni hasTurn
     * urcujici, kdo je na tahu.
     */
    public void endTurn() {
        if (didWin(hasTurn)) {
            if (hasTurn) {
                JOptionPane.showMessageDialog(this, "Bílý zvítězil!", "", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Černý zvítězil!", "", JOptionPane.PLAIN_MESSAGE);
            }
            board.setFinished(true);
            return;
        }
        if (isPat(!hasTurn)) {
            JOptionPane.showMessageDialog(this, "Remíza", "", JOptionPane.PLAIN_MESSAGE);
            board.setFinished(true);
            return;
        }
        hasTurn = !hasTurn;
    }

    /**
     * Zjisti, zda figura na souradnicich col, row muze tahnout. Bere v potaz
     * kdo je na tahu, nutnost skakat i volna pole pro tah.
     */
    public boolean canMove(char col, int row) {
        Figure figure = desk.getFigureAt(col, row);
        if (figure == null || figure.getSide() != hasTurn) {
            return false;
        }
        return desk.canMove(figure);
    }

    /**
     * Ziska kontextovou napovedu, pokud hrac stiskl obraz figury na
     * souradnicich col, row.
     */
    public int[] getFocus(char col, int row) {
        Figure figure = desk.getFigureAt(col, row);
        Figure[] figs;
        if (figure == null) {
            return new int[0];
        }
        if (figure.getSide() != hasTurn) {//Neni na tahu
            figs = desk.getFigs(hasTurn);
        } else if (desk.canMove(figure)) { //Figura muze tahnout
            Position[] posits = figure.getPossMoves();
            int[] inxArr = new int[posits.length];
            for (int i = 0; i < posits.length; i++) {
                inxArr[i] = posits[i].getGuiIndex();
            }
            return inxArr;
        } else { //Je na tahu, musi brat jinou figurou
            figs = desk.whoCanTake(hasTurn);
        }
        int[] inxArr = new int[figs.length];
        for (int i = 0; i < figs.length; i++) {
            inxArr[i] = figs[i].getPosition().getGuiIndex();
        }
        return inxArr;
    }

    /**
     * Pokusi se vykonat tah z col0, row0 na col1,row1. Vraci false, je-li
     * nekorektni. Jinak tah vykona, ukonci tah a vrati true.
     */
    public boolean move(char col0, int row0, char col1, int row1) {
        Figure figure = desk.getFigureAt(col0, row0);
        Position target = desk.getPositionAt(col1, row1);
        if (figure.canTake()) {
            Figure enemy = figure.takeWho(target);
            if (enemy == null) {
                return false;
            }
            board.removeFig(enemy.getPosition().getGuiIndex());
            enemy.getPosition().removeFigure();
            figure.getPosition().removeFigure();
            figure.setPosition(target);
            if (figure.toDame()) {
                figure = new Dame(target, figure.getSide());
            } else {
                target.putFigure(figure);
            }
            board.putFig(target.getGuiIndex(), figure.getPic());
            playedTurns.add(new Turn(desk.getPositionAt(col0, row0), target, true));
            addRecord(new Turn(desk.getPositionAt(col0, row0), target, true));
            madeTurn();
            return true;
        }
        if (!figure.canMove(target)) {
            return false;
        }
        figure.getPosition().removeFigure();
        figure.setPosition(target);
        if (figure.toDame()) {
            figure = new Dame(target, figure.getSide());
        } else {
            target.putFigure(figure);
        }
        board.putFig(target.getGuiIndex(), figure.getPic());
        playedTurns.add(new Turn(desk.getPositionAt(col0, row0), target, false));
        addRecord(new Turn(desk.getPositionAt(col0, row0), target, false));
        madeTurn();
        return true;
    }

    /**
     * Prekresli hraci desku tak, aby odpovidala datovemu modelu.
     */
    public void refreshBoard() {
        board.removeAllFigs();
        Figure[] figs = desk.getFigs();
        for (Figure fig : figs) {
            board.putFig(fig.getPosition().getGuiIndex(), fig.getPic());
        }
        revalidate();
        repaint();
    }

    /**
     * Vykonani tahu pocitacem (ReplayGui, tahy AI ve VersusAIGui). Pry show ==
     * true se tah vykresli, jinak se provede jen v datovem modelu.
     */
    public void execTurn(Turn turn, boolean show) {
        Figure figure = turn.getP0().getFigure();
        Position target = turn.getP1();
        Figure enemy = figure.takeWho(target);
        if (enemy != null) {
            enemy.getPosition().removeFigure();
        }
        figure.getPosition().removeFigure();
        figure.setPosition(target);
        if (figure.toDame()) {
            figure = new Dame(target, figure.getSide());
        } else {
            target.putFigure(figure);
        }
        if (show) {
            int[] inxArr;
            board.removeFig(turn.getP0().getGuiIndex());
            board.putFig(target.getGuiIndex(), figure.getPic());
            if (enemy != null) {
                board.removeFig(enemy.getPosition().getGuiIndex());
                inxArr = new int[3];
                inxArr[2] = enemy.getPosition().getGuiIndex();
            } else {
                inxArr = new int[2];
            }
            inxArr[0] = turn.getP0().getGuiIndex();
            inxArr[1] = target.getGuiIndex();
            board.setFocus(inxArr);
        }

    }

    /**
     * Getter pro atribut playedTurns. Prevadi ho z ArrayListu na proste pole
     * Turn[].
     */
    public Turn[] getPlayedTurns() {
        return playedTurns.toArray(new Turn[playedTurns.size()]);
    }

    /**
     * Nacte hru game do datoveho modelu, vypise zaznam hry a nastavi
     * board.finished.
     */
    public void loadGame(SavedGame game) {
        if (game == null) {
            return;
        }
        board.setFinished(game.getFinished());

        DumpFig[] dumpFigs = game.getFigs();
        Figure[] figs = new Figure[dumpFigs.length];
        for (int i = 0; i < dumpFigs.length; i++) {
            figs[i] = retrieveFig(dumpFigs[i]);
        }
        desk.loadDesk(figs);

        DumpTurn[] dumpTurns = game.getTurns();
        Turn[] turns = new Turn[dumpTurns.length];
        for (int i = 0; i < dumpTurns.length; i++) {
            turns[i] = retrieveTurn(dumpTurns[i]);
        }
        loadRecords(turns);

        if (turns.length % 2 == 0) {
            hasTurn = true;
        } else {
            hasTurn = false;
        }
    }

    /**
     * Pripravi zaznam hry pro ulozeni.
     */
    public SavedGame prepSavedGame() {
        SavedGame game = new SavedGame();
        game.setFinished(board.getFinished());

        Figure[] figs = desk.getFigs();
        DumpFig[] dumpFigs = new DumpFig[figs.length];
        for (int i = 0; i < figs.length; i++) {
            dumpFigs[i] = figs[i].dump();
        }
        game.setFigs(dumpFigs);

        Turn[] turns = getPlayedTurns();
        DumpTurn[] dumpTurns = new DumpTurn[turns.length];
        for (int i = 0; i < turns.length; i++) {
            dumpTurns[i] = turns[i].dump();
        }
        game.setTurns(dumpTurns);

        return game;
    }

    /**
     * Z obrazu DumpFig vytvori Figure.
     */
    public Figure retrieveFig(DumpFig dump) {
        Position pos = desk.getPositionAt(dump.getCol(), dump.getRow());
        boolean side = dump.getSide();
        if (dump.getDame()) {
            return new Dame(pos, side);
        }
        return new Stone(pos, side);
    }

    /**
     * Z obrazu DumpTurn vytvori Turn.
     */
    public Turn retrieveTurn(DumpTurn dump) {
        Position pos0 = desk.getPositionAt(dump.getCol0(), dump.getRow0());
        Position pos1 = desk.getPositionAt(dump.getCol1(), dump.getRow1());
        return new Turn(pos0, pos1, dump.getTake());
    }

    /**
     * Prida zapis tahu do zaznamu. Abstrakni metoda.
     */
    public abstract void addRecord(Turn turn);

    /**
     * Nacte zaznam hry. Abstraktni metoda.
     */
    public void loadRecords(Turn[] turns) {
    }
}
