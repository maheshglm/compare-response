package com.gojek.program.utils;

import com.gojek.program.exceptions.Exception;
import com.gojek.program.exceptions.ExceptionType;
import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;

/**
 * This class contains Utilities for File and Directory handling operations
 */
public class FileDirUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDirUtil.class);

    public static final String FILEPATH_SHOULD_NOT_NULL_OR_EMPTY = "Filepath should not null or empty";

    /**
     * Verify file exists.
     * It returns true if filepath exist and its a file.
     *
     * @param filepath
     * @return boolean
     */
    public boolean verifyFileExists(final String filepath) {
        if (Strings.isNullOrEmpty(filepath)) {
            LOGGER.error(FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
            throw new Exception(ExceptionType.IO_ERROR, FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
        }
        File file = new File(filepath);
        return file.exists() && file.isFile();
    }

    /** Get Line Iterator.
     *
     * @param filepath
     * @return {@link LineIterator}
     */
    public LineIterator getLineIterator(final String filepath) {
        if (Strings.isNullOrEmpty(filepath)) {
            LOGGER.error(FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
            throw new Exception(ExceptionType.IO_ERROR, FILEPATH_SHOULD_NOT_NULL_OR_EMPTY);
        }
        File file = new File(filepath);
        try {
            return FileUtils.lineIterator(file);
        } catch (IOException e) {
            LOGGER.error("IO Error while processing file [{}]", filepath);
            throw new Exception(ExceptionType.IO_ERROR, "IO Error while processing file [{}]", filepath);
        }
    }
}
