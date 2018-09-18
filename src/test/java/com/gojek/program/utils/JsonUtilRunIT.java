package com.gojek.program.utils;

import com.gojek.program.SpringTestConfig;
import com.gojek.program.exceptions.Exception;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gojek.program.utils.JsonUtil.IO_ERROR_WHILE_PARSING_JSON_STRING;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class JsonUtilRunIT {

    private static final String JSON1 = "target/test-classes/gojek/json/file1.json";
    private static final String JSON2 = "target/test-classes/gojek/json/file2.json";

    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private FileDirUtil fileDirUtil;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testJsonCompare_successfulComparison() {
        final String json1Data = fileDirUtil.readFileToString(JSON1);
        final String json2Data = fileDirUtil.readFileToString(JSON1);
        Assert.assertTrue(jsonUtil.compareJson(json1Data, json2Data));
    }

    @Test
    public void testJsonCompare_unSuccessfulComparison() {
        final String json1Data = fileDirUtil.readFileToString(JSON1);
        final String json2Data = fileDirUtil.readFileToString(JSON2);
        Assert.assertFalse(jsonUtil.compareJson(json1Data, json2Data));
    }

    @Test
    public void testJsonCompare_exception(){
        thrown.expect(Exception.class);
        thrown.expectMessage(IO_ERROR_WHILE_PARSING_JSON_STRING);
        jsonUtil.compareJson("{\"name\": \"John\"","{\"name\": \"John\"}");
    }

}
