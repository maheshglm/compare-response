package com.gojek.program.svc;

import com.gojek.program.exceptions.Exception;
import com.gojek.program.exceptions.ExceptionType;
import com.gojek.program.mdl.ResponseSpec;
import com.gojek.program.utils.ApiUtil;
import com.gojek.program.utils.FileDirUtil;
import com.gojek.program.utils.JsonUtil;
import com.gojek.program.utils.XmlUtil;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class CompareSvc implements ICompare {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompareSvc.class);

    public static final String FILE_IS_NOT_AVAILABLE = "File [{}] is not available";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String UNSUPPORTED_CONTENT_TYPE_FOR_COMPARISION = "Unsupported Content type for comparision";

    @Autowired
    private FileDirUtil fileDirUtil;

    @Autowired
    private ApiUtil apiUtil;

    @Autowired
    private XmlUtil xmlUtil;

    @Autowired
    private JsonUtil jsonUtil;


    /**
     * Compare Api response.
     * It reads both files line by line and compares the response for each request.
     *
     * @param file1 {@link File}
     * @param file2 {@link File}
     */
    public void compareApiResponses(final File file1, final File file2) {
        if (!fileDirUtil.verifyFileExists(file1.getAbsolutePath())) {
            LOGGER.error(FILE_IS_NOT_AVAILABLE, file1.getAbsolutePath());
            throw new Exception(ExceptionType.PROCESSING_FAILED, FILE_IS_NOT_AVAILABLE, file1.getAbsolutePath());
        }
        if (!fileDirUtil.verifyFileExists(file2.getAbsolutePath())) {
            LOGGER.error(FILE_IS_NOT_AVAILABLE, file2.getAbsolutePath());
            throw new Exception(ExceptionType.PROCESSING_FAILED, FILE_IS_NOT_AVAILABLE, file2.getAbsolutePath());
        }
        LineIterator file1Iterator = null;
        LineIterator file2Iterator = null;
        try {
            file1Iterator = fileDirUtil.getLineIterator(file1.getAbsolutePath());
            file2Iterator = fileDirUtil.getLineIterator(file2.getAbsolutePath());
            String file1Url;
            String file2Url;

            while (file1Iterator.hasNext() && file2Iterator.hasNext()) {
                file1Url = file1Iterator.nextLine();
                file2Url = file2Iterator.nextLine();

                if (this.compare(apiUtil.getApiResponseSpec(file1Url), apiUtil.getApiResponseSpec(file2Url))) {
                    LOGGER.info("{} equals {}", file1Url, file2Url);
                } else {
                    LOGGER.info("{} not equals {}", file1Url, file2Url);
                }
            }
        } finally {
            if (file1Iterator != null) {
                file1Iterator.close();
            }
            if (file2Iterator != null) {
                file2Iterator.close();
            }
        }
    }

    @Override
    public boolean compare(ResponseSpec spec1, ResponseSpec spec2) {
        if (spec1.getContentType().contains(APPLICATION_JSON) &&
                spec2.getContentType().contains(APPLICATION_JSON)) {
            return jsonUtil.compareJson(spec1.getResponse(), spec2.getResponse());
        } else if (spec1.getContentType().contains(APPLICATION_XML) &&
                spec2.getContentType().contains(APPLICATION_XML)) {
            return xmlUtil.compareXml(spec1.getResponse(), spec2.getResponse());
        } else {
            LOGGER.error(UNSUPPORTED_CONTENT_TYPE_FOR_COMPARISION);
            throw new Exception(ExceptionType.UNSUPPORTED_OPERATION, UNSUPPORTED_CONTENT_TYPE_FOR_COMPARISION);
        }
    }
}
