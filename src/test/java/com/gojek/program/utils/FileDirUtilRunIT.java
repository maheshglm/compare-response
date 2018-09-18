package com.gojek.program.utils;

import com.gojek.program.SpringTestConfig;
import com.gojek.program.exceptions.Exception;
import org.apache.commons.io.LineIterator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.gojek.program.utils.FileDirUtil.FILEPATH_SHOULD_NOT_NULL_OR_EMPTY;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class FileDirUtilRunIT {

    @Autowired
    private FileDirUtil fileDirUtil;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void testVerifyFileExists_fileAvailable() {
        boolean result = fileDirUtil.verifyFileExists("target/test-classes/gojek/file1.txt");
        Assert.assertTrue(result);
    }

    @Test
    public void testVerifyFileExists_fileNotAvailable() {
        boolean result = fileDirUtil.verifyFileExists("target/test-classes/gojek/nofile.txt");
        Assert.assertFalse(result);
    }

    @Test
    public void testVerifyFileExists_emptyFilePath() {
        thrown.expect(Exception.class);
        thrown.expectMessage(FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
        fileDirUtil.verifyFileExists("");
    }

    @Test
    public void testVerifyFileExists_nullFilePath() {
        thrown.expect(Exception.class);
        thrown.expectMessage(FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
        fileDirUtil.verifyFileExists(null);
    }

    @Test
    public void testGetLineIterator_fileAvailable() {
        LineIterator lt = fileDirUtil.getLineIterator("target/test-classes/gojek/file1.txt");
        List<String> lines = new ArrayList<>();
        while (lt.hasNext()) {
            lines.add(lt.nextLine());
        }
        Assert.assertEquals(4, lines.size());
        Assert.assertEquals("https://reqres.in/api/users/3", lines.get(0));
        Assert.assertEquals("https://reqres.in/api/users/2", lines.get(1));
        Assert.assertEquals("https://reqres.in/api/users?page=2", lines.get(2));
        Assert.assertEquals("https://reqres.in/api/users?page=3", lines.get(3));
    }

    @Test
    public void testGetLineIterator_fileNotAvailable() {
        thrown.expect(Exception.class);
        thrown.expectMessage("IO Error while processing file [target/test-classes/gojek/nofile.txt]");
        fileDirUtil.getLineIterator("target/test-classes/gojek/nofile.txt");
    }

    @Test
    public void testGetLineIterator_emptyFilePath() {
        thrown.expect(Exception.class);
        thrown.expectMessage(FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
        fileDirUtil.getLineIterator("");
    }

    @Test
    public void testReadFileToString_validFile() {
        String string = fileDirUtil.readFileToString("target/test-classes/gojek/file1.txt");
        Assert.assertTrue(string != null);
        Assert.assertTrue(string.contains("https://reqres.in/api/users?page=3"));
    }

    @Test
    public void testReadFileToString_fileNotAvailable() {
        thrown.expect(Exception.class);
        thrown.expectMessage("IO Error while processing file [target/test-classes/gojek/nofile.txt]");
        fileDirUtil.readFileToString("target/test-classes/gojek/nofile.txt");
    }

    @Test
    public void testReadFileToString_emptyFilePath() {
        thrown.expect(Exception.class);
        thrown.expectMessage(FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
        fileDirUtil.readFileToString("");
    }
}