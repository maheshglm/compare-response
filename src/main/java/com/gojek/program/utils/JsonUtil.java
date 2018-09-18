package com.gojek.program.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gojek.program.exceptions.Exception;
import com.gojek.program.exceptions.ExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static final String IO_ERROR_WHILE_PARSING_JSON_STRING = "IO Error while parsing json string";

    /**
     * Compare 2 json Strings and returns true if both are same.
     * @param json1 string
     * @param json2 string
     * @return boolean
     */
    public boolean compareJson(final String json1, final String json2) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonTree1 = mapper.readTree(json1);
            JsonNode jsonTree2 = mapper.readTree(json2);
            return jsonTree1.equals(jsonTree2);
        } catch (IOException e) {
            LOGGER.error(IO_ERROR_WHILE_PARSING_JSON_STRING);
            throw new Exception(ExceptionType.IO_ERROR, IO_ERROR_WHILE_PARSING_JSON_STRING);
        }
    }
}
