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

import static com.gojek.program.utils.XmlUtil.IO_ERROR_WHILE_PARSING_XML_STRING;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class XmlUtilRunIT {

    private static final String XML1 = "target/test-classes/gojek/xmls/file1.xml";
    private static final String XML2 = "target/test-classes/gojek/xmls/file2.xml";

    @Autowired
    private XmlUtil xmlUtil;

    @Autowired
    private FileDirUtil fileDirUtil;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCompareXml_successfulComparision() {
        final String xml1Data = fileDirUtil.readFileToString(XML1);
        final String xml2Data = fileDirUtil.readFileToString(XML1);
        Assert.assertTrue(xmlUtil.compareXml(xml1Data, xml2Data));
    }

    @Test
    public void testCompareXml_unSuccessfulComparision() {
        final String xml1Data = fileDirUtil.readFileToString(XML1);
        final String xml2Data = fileDirUtil.readFileToString(XML2);
        Assert.assertFalse(xmlUtil.compareXml(xml1Data, xml2Data));
    }

    @Test
    public void testCompareXml_exception(){
        thrown.expect(Exception.class);
        thrown.expectMessage(IO_ERROR_WHILE_PARSING_XML_STRING);
        xmlUtil.compareXml("<note><to>Tove</to><note>","<note><to>Tove</to></note>");
    }


}
