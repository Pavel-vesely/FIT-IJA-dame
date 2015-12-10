package ija.projekt.utils;

import java.io.File;

/**
 * Trida pro ukladani a nacitani hry z XML. Vyuziva JAXB.
 * @author Pavel Vesely, Ales Drevo
 */
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SaveNLoad {

    public SaveNLoad() {
    }

    public void save(SavedGame game, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SavedGame.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(game, file);
    }

    public SavedGame load(File file) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(SavedGame.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        SavedGame game = (SavedGame) jaxbUnmarshaller.unmarshal(file);
        return game;
    }
   
}
