package gojek.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gojek.Exceptions.Exception;
import gojek.Exceptions.ExceptionType;
import gojek.mdl.ResponseSpec;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CompareUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompareUtil.class);
    public static final String FILE_IS_NOT_AVAILABLE = "File [{}] is not available";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";


    public static void compareApiResponses(final File file1, final File file2) {
        if (!FileDirUtil.verifyFileExists(file1.getAbsolutePath())) {
            LOGGER.error(FILE_IS_NOT_AVAILABLE, file1.getAbsolutePath());
            throw new Exception(ExceptionType.PROCESSING_FAILED, FILE_IS_NOT_AVAILABLE, file1.getAbsolutePath());
        }
        if (!FileDirUtil.verifyFileExists(file2.getAbsolutePath())) {
            LOGGER.error(FILE_IS_NOT_AVAILABLE, file2.getAbsolutePath());
            throw new Exception(ExceptionType.PROCESSING_FAILED, FILE_IS_NOT_AVAILABLE, file2.getAbsolutePath());
        }

        final LineIterator file1Iterator = FileDirUtil.getLineIterator(file1.getAbsolutePath());
        final LineIterator file2Iterator = FileDirUtil.getLineIterator(file2.getAbsolutePath());

        String file1Url;
        String file2Url;
        boolean status;

        while (file1Iterator.hasNext() && file2Iterator.hasNext()) {
            file1Url = file1Iterator.nextLine();
            file2Url = file2Iterator.nextLine();

            ResponseSpec file1ResponseSpec = ApiUtil.getApiResponseSpec(file1Url);
            ResponseSpec file2ResponseSpec = ApiUtil.getApiResponseSpec(file2Url);

            String file1ContentType = file1ResponseSpec.getContentType();
            String file2ContentType = file2ResponseSpec.getContentType();

            String file1Resp = file1ResponseSpec.getResponse();
            String file2Resp = file2ResponseSpec.getResponse();

            if (file1ContentType.contains(APPLICATION_JSON) &&
                    file2ContentType.contains(APPLICATION_JSON)) {
                status = compareJsonResponse(file1Resp, file2Resp);
            } else if (file1ContentType.contains(APPLICATION_XML) &&
                    file2ContentType.contains(APPLICATION_XML)) {
                status = compareXmlResponse(file1Resp, file2Resp);
            } else {
                LOGGER.error("Unsupported Content type for comparision");
                throw new Exception(ExceptionType.UNSUPPORTED_OPERATION, "Unsupported Content type for comparision");
            }

            if (status) {
                LOGGER.info("{} equals {}", file1Url, file2Url);
            } else {
                LOGGER.info("{} not equals {}", file1Url, file2Url);
            }
        }
    }

    public static boolean compareJsonResponse(final String json1, final String json2) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonTree1 = mapper.readTree(json1);
            JsonNode jsonTree2 = mapper.readTree(json2);
            return jsonTree1.equals(jsonTree2);
        } catch (IOException e) {
            LOGGER.error("IO Error while parsing json string");
            throw new Exception(ExceptionType.IO_ERROR, "IO Error while parsing json string");
        }
    }

    public static boolean compareXmlResponse(final String xml1, final String xml2) {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        DetailedDiff diff = null;
        try {
            diff = new DetailedDiff(XMLUnit.compareXML(xml1, xml2));
            List<?> allDifferences = diff.getAllDifferences();
            return allDifferences.size() == 0;
        } catch (SAXException | IOException e) {
            LOGGER.error("IO Error while parsing xml string");
            throw new Exception(ExceptionType.IO_ERROR, "IO Error while parsing xml string");
        }
    }
}
