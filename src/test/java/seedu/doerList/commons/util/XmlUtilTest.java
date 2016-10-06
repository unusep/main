package seedu.doerList.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doerList.testutil.DoerListBuilder;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.commons.util.FileUtil;
import seedu.doerList.commons.util.XmlUtil;
import seedu.doerList.model.DoerList;
import seedu.doerList.storage.XmlSerializableDoerList;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validDoerList.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempDoerList.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, DoerList.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, DoerList.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, DoerList.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        // TODO Change to the one fit for DoerList
//        XmlSerializableDoerList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableDoerList.class);
//        assertEquals(9, dataFromFile.getTaskList().size());
//        assertEquals(0, dataFromFile.getCategoryList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new DoerList());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new DoerList());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableDoerList dataToWrite = new XmlSerializableDoerList(new DoerList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableDoerList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDoerList.class);
        assertEquals((new DoerList(dataToWrite)).toString(),(new DoerList(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        DoerListBuilder builder = new DoerListBuilder(new DoerList());
        dataToWrite = new XmlSerializableDoerList(builder.withTask(TestUtil.generateSamplePersonData().get(0)).withCategory("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDoerList.class);
        assertEquals((new DoerList(dataToWrite)).toString(),(new DoerList(dataFromFile)).toString());
    }
}
