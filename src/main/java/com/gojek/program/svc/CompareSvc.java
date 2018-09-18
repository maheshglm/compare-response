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
import java.util.Objects;

public class CompareUtil implements ICompare {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompareUtil.class);

    public static final String FILE_IS_NOT_AVAILABLE = "File [{}] is not available";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";

    @Autowired
    private FileDirUtil fileDirUtil;

    @Autowired
    private ApiUtil apiUtil;

    @Autowired
    private XmlUtil xmlUtil;

    @Autowired
    private JsonUtil jsonUtil;


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
        } catch (Exception e) {

        } finally {
            Objects.requireNonNull(file1Iterator).close();
            Objects.requireNonNull(file2Iterator).close();
        }
    }


    @Override
    public boolean compare(ResponseSpec spec1, ResponseSpec spec2) {
        if (spec1.getContentType().contains(APPLICATION_JSON) &&
                spec2.getContentType().contains(APPLICATION_JSON)) {
            return jsonUtil.compareJson(spec1.getResponse(), spec2.getResponse());
        } else if (spec1.getContentType().contains(APPLICATION_XML) &&
                spec2.getContentType().contains(APPLICATION_XML)) {
            return xmlUtil.compareXmls(spec1.getResponse(), spec2.getResponse());
        } else {
            LOGGER.error("Unsupported Content type for comparision");
            throw new Exception(ExceptionType.UNSUPPORTED_OPERATION, "Unsupported Content type for comparision");
        }
    }
}
