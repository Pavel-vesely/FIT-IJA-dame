package ija.projekt.figure;

import ija.projekt.basis.Figure;
import ija.projekt.basis.Position;
import java.util.ArrayList;

/**
 * Abstrakce figury damy v datovem modelu aplikace. Implementuje tridu Figure z
 * ija.projekt.basis.
 *
 * @author Pavel Vesely, Ales Drevo
 */
public class Dame extends Figure {

    /**
     * Konstruktor. Vytvori novou instanci damy strany s na pozici p a rovnou ji
     * prida do datoveho modelu.
     */
    public Dame(Position p, boolean s) {
        super(p, s);
    }

    /**
     * Zjisti, jestli je tah na Position p korektni. Nekontroluje, kdo je na tahu,
     * ani zda se nemusi skakat.
     */
    @Override
    public boolean canMove(Position p) {
        int dir = this.pos.diagDir(p);
        if (dir == 0) {
            return false;
        }
        Position target = this.pos;
        for (int i = 0; i < this.pos.diagDist(p); i++) {
            target = target.diagNext(dir);
            if (target.getFigure() != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Zjisti, zda muze dama brat nejakou nepratelskou figuru.
     */
    @Override
    public boolean canTake() {
        Position target;
        Figure enemy;
        for (int dir = 1; dir <= 4; dir++) {
            target = this.pos.diagNext(dir);
            while (target != null) {
                enemy = target.getFigure();
                if (enemy != null) {
                    if (enemy.getSide() != this.side && target.diagNext(dir) != null && target.diagNext(dir).getFigure() == null) {
                        return true;
                    }
                    break;
                }
                target = target.diagNext(dir);
            }
        }
        return false;
    }

    /**
     * Pokud muze dama brat pohybem na pozici p,
     * vrati sebranou figuru, jinak null.
     */
    @Override
    public Figure takeWho(Position p) {
        int dir = this.pos.diagDir(p), dist = this.pos.diagDist(p);
        if (dir == 0) {
            return null;
        }
        Position target = this.pos;
        Figure enemy = null;
        for (int i = 0; i < dist; i++) {
            target = target.diagNext(dir);
            if (enemy == null) {
                enemy = target.getFigure();
                if ((enemy != null && enemy.getSide() == this.side) || dist - i == 1) {
                    return null;
                }
            } else {
                if (target.getFigure() != null) {
                    return null;
                }
            }
        }
        return enemy;
    }

    /**
     * Vrati pole pozic, na ktere by dama mohla korektne jit. Resi nutnost skakat,
     * ne ale u jinych figur.
     */
    @Override
    public Position[] getPossMoves() {
        ArrayList<Position> posArr = new ArrayList<>();
        Position target;
        if (canTake()) {
            Figure enemy;
            for (int dir = 1; dir <= 4; dir++) {
                target = this.pos.diagNext(dir);
                while (target != null) {
                    enemy = target.getFigure();
                    if (enemy != null) {
                        if (enemy.getSide() != this.side) {// && target.diagNext(dir) != null && target.diagNext(dir).getFigure() == null)
                            while (target.diagNext(dir) != null && target.diagNext(dir).getFigure() == null) {
                                target = target.diagNext(dir);
                                posArr.add(target);
                            }
                            break;
                        }
                    }
                    target = target.diagNext(dir);
                }
            }
        } else {
            for (int dir = 1; dir <= 4; dir++) {
                target = this.pos.diagNext(dir);
                while (target != null && target.getFigure() == null) {
                    posArr.add(target);
                    target = target.diagNext(dir);
                }
            }
        }
        return posArr.toArray(new Position[posArr.size()]);
    }

    /**
     * Vraci index do pole obrazku pro obrazek, ktery dame patri.
     */
    @Override
    public int getPic() {
        if (side) {
            return 2;
        }
        return 3;
    }
}
