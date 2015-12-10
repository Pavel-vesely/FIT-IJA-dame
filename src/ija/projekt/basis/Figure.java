package ija.projekt.basis;

import ija.projekt.basis.Position;
import ija.projekt.figure.Dame;
import ija.projekt.utils.DumpFig;

/**
 * Abstrakce figury v datovem modelu aplikace.
 * Abstraktni trida, implementuji ji Dame() a Stone() v ija.projekt.figure
 * @author Pavel Vesely, Ales Drevo
 */
public abstract class Figure {
    protected Position pos;
    protected boolean side; // true = bila
    
    /**
     * Konstruktor. Vytvori figuru zadane strany na zadane pozici.
     */
    public Figure(Position p, boolean side) {
        this.side = side;
        this.pos = p;
        p.putFigure(this);
    }
    
    /**
     * Getter pro atribut Position pos tridy.
     */
    public Position getPosition() {
        return this.pos;
    }
    
    /**
     * Getter pro atribut boolean side tridy. (true = bila)
     */
    public boolean getSide() {
        return this.side;
    }
    
    /**
     * Setter pro atribut Position pos tridy.
     */
    public void setPosition(Position p) {
        this.pos = p;
    }
    
    /**
     * Zjisti, jestli je tah na Position p korektni. Nekontroluje, kdo je na tahu,
     * ani zda se nemusi skakat.
     */
    public abstract boolean canMove(Position p);
    
    /**
     * Pokud muze figura brat pohybem na pozici p,
     * vrati sebranou figuru, jinak null.
     */
    public abstract Figure takeWho(Position p);
    
    /**
     * Zjisti, zda muze figura brat nejakou nepratelskou figuru.
     */
    public abstract boolean canTake();
    
    /**
     * Vraci index do pole obrazku pro obrazek, ktery figure patri.
     */
    public abstract int getPic();
    
    /**
     * Vrati pole pozic, na ktere by figura mohla korektne jit. Resi nutnost skakat,
     * ne ale u jinych figur.
     */
    public abstract Position[] getPossMoves();
    
    /**
     * Zjisti, zda je figurou kamen a ma se menit v damu.
     */
    public boolean toDame() {
        return false;
    }
    
    /**
     * Vytvori obraz figury vhodny pro ukladani do XML.
     */
    public DumpFig dump(){
        DumpFig dump = new DumpFig();
        if (this instanceof Dame)
            dump.setDame(true);
        else
            dump.setDame(false);
        dump.setSide(side);
        dump.setCol(pos.getColumn());
        dump.setRow(pos.getRow());
        return dump;
    }
}