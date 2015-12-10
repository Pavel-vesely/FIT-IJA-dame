package ija.projekt.utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Trida zabalujici ulozenou hru, upravena pro pouziti JAXB.
 *
 * @author Pavel Vesely, Ales Drevo
 */
@XmlRootElement
public class SavedGame {

    protected boolean finished;
    protected DumpFig[] figs;
    protected DumpTurn[] turns;

    public boolean getFinished() {
        return finished;
    }

    public DumpFig[] getFigs() {
        return figs;
    }

    public DumpTurn[] getTurns() {
        return turns;
    }

    @XmlElement
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @XmlElement
    public void setFigs(DumpFig[] figs) {
        this.figs = figs;
    }

    @XmlElement
    public void setTurns(DumpTurn[] turns) {
        this.turns = turns;
    }
}
