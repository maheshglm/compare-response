package com.gojek.program.utils;

import com.gojek.program.exceptions.Exception;
import com.gojek.program.exceptions.ExceptionType;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public class XmlUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);

    public static final String IO_ERROR_WHILE_PARSING_XML_STRING = "IO Error while parsing xml string";

    /**
     * Compare 2 xml Strings and returns true if both are same.
     * @param xml1 String
     * @param xml2 String
     * @return boolean
     */
    public boolean compareXml(final String xml1, final String xml2) {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        DetailedDiff diff;
        try {
            diff = new DetailedDiff(XMLUnit.compareXML(xml1, xml2));
            List<?> allDifferences = diff.getAllDifferences();
            return allDifferences.size() == 0;
        } catch (SAXException | IOException e) {
            LOGGER.error(IO_ERROR_WHILE_PARSING_XML_STRING);
            throw new Exception(ExceptionType.IO_ERROR, IO_ERROR_WHILE_PARSING_XML_STRING);
        }
    }
}
