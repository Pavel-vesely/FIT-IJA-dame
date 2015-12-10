package ija.projekt.figure;

import ija.projekt.basis.Figure;
import ija.projekt.basis.Position;
import java.util.ArrayList;

/**
 * Abstrakce figury kamene v datovem modelu aplikace. Implementuje tridu Figure z
 * ija.projekt.basis.
 * @author Pavel Vesely, Ales Drevo
 */
public class Stone extends Figure {
    
    /**
     * Konstruktor. Vytvori novou instanci kamene strany s na pozici p a rovnou ji
     * prida do datoveho modelu.
     */
    public Stone(Position p, boolean s) {
        super(p, s);
    }
    
    /**
     * Zjisti, jestli je tah na Position p korektni. Nekontroluje, kdo je na tahu,
     * ani zda se nemusi skakat.
     */
    @Override
    public boolean canMove(Position p) {
        if(this.side) {
            if(((this.pos.diagNext(1) != null && this.pos.diagNext(1).equals(p))) || 
                    (this.pos.diagNext(2) != null && this.pos.diagNext(2).equals(p)))
                return true;
        } else {
            if((this.pos.diagNext(3) != null && this.pos.diagNext(3).equals(p)) || 
                    (this.pos.diagNext(4) != null && this.pos.diagNext(4).equals(p)))
                return true;
        }
        return false;
    }
    
    /**
     * Zjisti, zda muze kamen brat nejakou nepratelskou figuru.
     */
    @Override
    public boolean canTake() {
        int dirs[] = new int[2];
        if(this.side) {
            dirs[0] = 1;
            dirs[1] = 2;
        } else {
            dirs[0] = 3;
            dirs[1] = 4;
        }
        Figure enemy;
        Position behind;
        for (int dir : dirs){
            if(this.pos.diagNext(dir) != null){
                enemy = this.pos.diagNext(dir).getFigure();
                if (enemy != null && enemy.getSide() != this.side) {
                    behind = this.pos.diagNext(dir).diagNext(dir);
                    if (behind != null && behind.getFigure() == null)
                        return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Pokud muze kamen brat pohybem na pozici p,
     * vrati sebranou figuru, jinak null.
     */
    @Override
    public Figure takeWho(Position p) {
        if (this.pos.diagDist(p) != 2)
            return null;
        int dir = this.pos.diagDir(p);
        
        if(this.side) {// fig. je bila
            if(dir != 1 && dir != 2)
                return null;
        } else {
            if(dir != 3 && dir != 4)
                return null;
        }
        Figure enemy = this.pos.diagNext(dir).getFigure();
        if(enemy != null && enemy.getSide() != this.side)
            return enemy;
        return null;
    }

    /**
     * Pokud kamen presel hraci desku a ma se zmenit v damu, vrati true.
     */
    @Override
    public boolean toDame() {
        if ((this.side && this.pos.inRow(8)) || (!this.side && this.pos.inRow(1)))
            return true;
        return false;
    }

    /**
     * Vrati pole pozic, na ktere by dama mohla korektne jit. Resi nutnost skakat,
     * ne ale u jinych figur.
     */
    @Override
    public Position[] getPossMoves() {
        ArrayList<Position> posArr = new ArrayList<>();
        int dirs[] = new int[2];
        if(this.side) {
            dirs[0] = 1;
            dirs[1] = 2;
        } else {
            dirs[0] = 3;
            dirs[1] = 4;
        }
        if (this.canTake()){
            Figure enemy;
            Position behind;
            for (int dir : dirs){
                if(this.pos.diagNext(dir) != null){
                    enemy = this.pos.diagNext(dir).getFigure();
                    if (enemy != null && enemy.getSide() != this.side) {
                        behind = this.pos.diagNext(dir).diagNext(dir);
                        if (behind != null && behind.getFigure() == null)
                            posArr.add(behind);
                    }
                }
            }
        } else {
            Position field;
            for (int dir : dirs){
                field = this.pos.diagNext(dir);
                if (field != null && field.getFigure() == null)
                    posArr.add(field);
            }
        }
        return posArr.toArray(new Position[posArr.size()]);
    }
    
    /**
     * Vraci index do pole obrazku pro obrazek, ktery dame patri.
     */
    @Override
    public int getPic() {
        if (side)
            return 0;
        return 1;
    }
}
