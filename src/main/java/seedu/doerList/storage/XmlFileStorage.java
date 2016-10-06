package seedu.doerList.storage;

import javax.xml.bind.JAXBException;

import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores doerList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given doerList data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableDoerList doerList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, doerList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns doerList in the file or an empty doerList
     */
    public static XmlSerializableDoerList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableDoerList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
